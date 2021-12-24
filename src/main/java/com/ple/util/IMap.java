package com.ple.util;

import java.util.List;

public interface IMap<K, V> extends Iterable<IEntry<K, V>> {

  IMap<K, V> putAll(Object... keyOrValue);

  IMap<K, V> put(K key, V value);

  IMap<K, V> remove(K key);

  int size();

  V get(K key);

  List<K> keySet();

  List<V> values();

}
