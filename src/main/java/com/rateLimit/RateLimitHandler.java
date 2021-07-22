package com.rateLimit;

import com.constant.RedisKey;
import com.dao.RedisLimitDAO;
import com.entity.BaseConfig;
import com.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流，每分钟限制请求次数不能超过rateLimit
 */
@Slf4j
@Component
public class RateLimitHandler {
    // 限流的个数
    private static final int maxCount = 10;

    // 指定的时间内
    private static final int interval = 60;

    // 原子类计数器
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    // 起始时间
    private long startTime = System.currentTimeMillis();

    @Value("${gateway.access-rate-limit}")
    private Integer rateLimitInLocal;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisLimitDAO redisLimitDAO;

    public void handle() {
        // get current request from which ip address
        String ip  = getIpAddressFromRequest();
        // get ip address request time from redis, set 0 if not present
        String redisKey = RedisKey.ip.concat(":").concat(ip);
        String ipRequestTime = (String) redisUtil.get(redisKey);
        log.info("key [{}] for ipRequestTime in redis is [{}]", redisKey, ipRequestTime);
        if (null == ipRequestTime) {
            ipRequestTime = "0";
        }
        Integer requestTime = Integer.parseInt(ipRequestTime);
        // if bigger than rateLimit, access deny
        if (requestTime > getRateLimit()) {
            log.error("access deny!!!");
            throw new RuntimeException("Access Deny");
        }
        // else requestTime plus 1
        // set request time, and set into redis
        redisUtil.set(redisKey, String.valueOf(requestTime + 1), interval);
    }


    public void handlePath() {
        if (!limit(maxCount, interval)) {
            throw new RuntimeException("Access Deny");
        }
    }

    public boolean limit(int maxCount, int interval) {
        atomicInteger.addAndGet(1);
        if (atomicInteger.get() == 1) {
            startTime = System.currentTimeMillis();
            atomicInteger.addAndGet(1);
            return true;
        }
        // 超过了间隔时间，直接重新开始计数
        if (System.currentTimeMillis() - startTime > interval * 1000) {
            startTime = System.currentTimeMillis();
            atomicInteger.set(1);
            return true;
        }
        // 还在间隔时间内,check有没有超过限流的个数
        if (atomicInteger.get() > maxCount) {
            return false;
        }
        return true;
    }

    private Integer getRateLimit() {
        log.info("rateLimit in local config is: [{}]", rateLimitInLocal);
        Integer rateLimit = getRateLimitFromRedis();
        if (null == rateLimit) {
            rateLimit = getRateLimitFromConfigCenter();
        }
        if (null == rateLimit) {
            rateLimit = getRateLimitFromDatabase();
        }
        if (null == rateLimit) {
            rateLimit = getRateLimitFromLocal();
        }
        log.info("rateLimit finally is [{}]", rateLimit);
        return rateLimit;
    }

    private Integer getRateLimitFromRedis() {
        String s = (String) redisUtil.get(RedisKey.gatewayRateLimit);
        log.info("getRateLimitFromRedis, result = [{}]", s);
        return null == s? null : Integer.parseInt(s);
    }

    private Integer getRateLimitFromDatabase() {
        BaseConfig baseConfig = redisLimitDAO.getByName(RedisKey.gatewayRateLimit);
        log.info("getRateLimitFromDatabase, result = [{}]", baseConfig);
        return null == baseConfig? null : Integer.parseInt(baseConfig.getValue());
    }

    private Integer getRateLimitFromLocal() {
        return rateLimitInLocal;
    }

    private Integer getRateLimitFromConfigCenter() {
        // to finish
        Integer i = null;
        log.info("getRateLimitFromConfigCenter, result = [{}]", i);
        return i;
    }

    private String getIpAddressFromRequest() {
        return "127.0.0.1";
    }
}
