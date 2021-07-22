限流实现思路：
限流条件：根据ip地址进行限流，根据请求路径进行限流
思考：同一个路径，请求方式不同，代表不同的接口，是否需要分开限流

以固定时间内对同一请求路径进行限流为例，设60s内对/index路径最多只能请求100次
1、最简单最直观的方法
每次请求/index，计数器+1,达到100且在60s内，若还有请求，则丢弃，60s后，计数器归零
存在问题：无法面对流量突增的情况，如0～10秒内流量突增超过100,这100个请求在10～20秒内处理完毕，会导致20～60秒内无法再接受外界请求
代码示例：

// 限流的个数
private int maxCount = 100;
// 指定的时间内
private long interval = 60;
// 原子类计数器
private AtomicInteger atomicInteger = new AtomicInteger(0);
// 起始时间
private long startTime = System.currentTimeMillis();

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


2、固定数量令牌方式
共有100个令牌，每次请求/index前，试图拿走一个令牌，如果令牌被拿光了，就丢弃请求;拿到令牌后处理请求，处理结束后返还令牌
存在问题：耗时较长的请求在返还令牌前
