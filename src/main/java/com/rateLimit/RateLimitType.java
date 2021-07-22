package com.rateLimit;

/**
 * 限流策略
 */
public enum RateLimitType {
    /**
     * 自定义key值
     */
    CUSTOMER,
    /**
     * ip地址限流
     */
    IP,
    /**
     * 请求路径限流
     */
    PATH;
}
