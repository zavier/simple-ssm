package com.zavier.util.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zavier.util.RedisUtil;
import com.zavier.util.SerializeUtil;

public class MybatisRedisCache implements Cache {

    private static Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.debug(">>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public void clear() {
        RedisUtil.getJedis().flushDB();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Object getObject(Object key) {
        Object value = SerializeUtil
                .unserialize(RedisUtil.getJedis().get(SerializeUtil.serialize(key.toString())));
        log.debug(">>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
        return value;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public int getSize() {
        return Integer.valueOf(RedisUtil.getJedis().dbSize().toString());
    }

    @Override
    public void putObject(Object key, Object value) {
        log.debug(">>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
        RedisUtil.getJedis().set(SerializeUtil.serialize(key.toString()),
                SerializeUtil.serialize(value));
    }

    @Override
    public Object removeObject(Object key) {
        return RedisUtil.getJedis().expire(SerializeUtil.serialize(key.toString()), 0);
    }

}
