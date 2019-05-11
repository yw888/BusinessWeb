package com.neuedu.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Guava Cache缓存封装类
 */
public class GuavaCacheUtil {

    //LRU
    private static LoadingCache<String, String> localCach = CacheBuilder.newBuilder().initialCapacity(1000).
            maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        //当根据可以获取缓存时，如果缓存不存在就会默认调用load方法
        @Override
        public String load(String s) throws Exception {
            return "null";
        }
    });

    //设置值
    public static void put(String key, String value){
        localCach.put(key, value);
    }

    //获取存储中的值
    public static String get(String key){
        String value = null;
        try {
            value = localCach.get(key);
            if("null".equals(value)){
                return null;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

}
