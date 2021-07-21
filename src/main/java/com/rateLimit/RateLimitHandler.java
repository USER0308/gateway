package com.rateLimit;

import com.constant.RedisKey;
import com.dao.RedisLimitDAO;
import com.entity.BaseConfig;
import com.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 限流，每分钟限制请求次数不能超过rateLimit
 */
@Slf4j
public class RateLimitHandler {
    @Value("{$gateway.access-rate-limit}")
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
        if (null == ipRequestTime) {
            ipRequestTime = "0";
        }
        Integer requestTime = Integer.parseInt(ipRequestTime);
        // if bigger than rateLimit, access deny
        if (requestTime > getRateLimit()) {
            log.error("access deny!!!");
            return;
        }
        // else requestTime plus 1
        // set request time, and set into redis
        redisUtil.set(redisKey, requestTime + 1);
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
