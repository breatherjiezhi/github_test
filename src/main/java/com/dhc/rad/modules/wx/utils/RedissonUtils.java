package com.dhc.rad.modules.wx.utils;

import com.dhc.rad.common.config.Global;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 10951
 */
public class RedissonUtils {

    public static RedissonClient getRedissonClient(Integer dataBase){
        // 1. 配置文件
        Config config = new Config();
        config.useSingleServer()
                .setAddress(Global.getConfig("redis.address"))
                .setPassword(Global.getConfig("redis.password"))
                .setDatabase(dataBase);
        //2. 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
