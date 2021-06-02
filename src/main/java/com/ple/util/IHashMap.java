package com.ple.util;

import com.ple.jerbil.Immutable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Immutable
public class IHashMap<K,V>  implements IMap<K,V> {

  private IHashMapEntry[][] buckets;

  private class IHashMapEntry<K,V> implements Map.Entry<K, V> {

    @Override
    public K getKey() {

      return null;
    }

    @Override
    public V getValue() {

      return null;
    }

    @Override
    public V setValue(V value) {

      return null;
    }

  }
    
}
