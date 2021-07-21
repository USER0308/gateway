package com.constant;

/**
 * 获取配置信息的顺序，先从redis中获取，获取不到时从配置中心获取，再获取不到时，从数据库中获取，还是获取不到时，最后从本地配置文件中获取
 * 定期检查redis中配置和配置中心、数据库中是否一致，不一致时更新数据库中配置
 */
public class ConfigOrder {
    public static final int REDIS = 0;
    public static final int CONFIG_CENTER = REDIS + 1;
    public static final int DATABASE = CONFIG_CENTER + 1;
    public static final int LOCAL_CONFIG = DATABASE + 1;
}
