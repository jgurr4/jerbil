package com.ple.util;

public interface IMap<K, V, T extends IMap<K, V, T>> {

   T putAll(Object... keyOrValue);

   T put(K key, V value);

   int size();

   V get(K key);

}
