package com.ple.util;

public interface IMap<K, V> {

   IMap<K, V> putAll(Object... keyOrValue);

   IMap<K, V> put(K key, V value);

   int size();

   V get(K key);

}
