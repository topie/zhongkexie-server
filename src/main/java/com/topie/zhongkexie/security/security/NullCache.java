package com.topie.zhongkexie.security.security;

import com.topie.zhongkexie.common.tools.cache.IBasicCache;

/**
 * Created by cgj on 2016/4/13.
 */
public class NullCache<K, V> implements IBasicCache<K, V> {

    @Override
    public void set(K key, V value) {
    }

    @Override
    public void set(K key, V value, int seconds) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void del(K key) {

    }

    @Override
    public void expire(K key, int seconds) {
    }
}
