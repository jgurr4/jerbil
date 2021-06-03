package com.ple.util;

import com.ple.jerbil.Immutable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Immutable
public class IHashMap<K,V>  implements IMap<K,V,IHashMap<K, V>> {

  private final IHashMapEntry<K, V>[][] buckets;
  private final int maxBucketSize;

  public IHashMap(IHashMapEntry<K, V>[][] buckets, int maxBucketSize) {

    this.buckets = buckets;
    this.maxBucketSize = maxBucketSize;

  }

  public static <K, V> IHashMap<K, V> from(Object... objects) {

    assert objects.length % 2 == 0;
    final int bucketCount = 10;
    final IHashMapEntry<K, V>[][] buckets = new IHashMapEntry[bucketCount][];
    for (int i = 0; i < objects.length - 1; i+=2) {
      final K key = (K) objects[i];
      final V value = (V) objects[i+1];
      final int hashCode = Math.abs(key.hashCode());
      final int bucketIndex = hashCode % bucketCount;
      IHashMapEntry<K, V>[] bucket = buckets[bucketIndex];
      if (bucket == null) {
        bucket = new IHashMapEntry[10];
        buckets[bucketIndex] = bucket;
      }
      for (int entryIndex = 0; entryIndex < bucket.length; entryIndex++) {
        if (bucket[entryIndex] == null) {
          bucket[entryIndex] = IHashMapEntry.from(key, value);
          break;
        }
      }

    }
    IHashMap<K, V> map = new IHashMap<>(buckets, 10);

    return map;
  }

  @Override
  public IHashMap<K, V> putAll(Object... keyOrValue) {

    return null;

  }

  @Override
  public IHashMap<K, V> put(K key, V value) {
    // use From method here somehow.
    return null;
  }

  @Override
  public int size() {

    return 0;
  }

  @Override
  public V get(K key) {
    final int hashCode = Math.abs(key.hashCode());

    return null;
  }

  private static class IHashMapEntry<K,V> implements Map.Entry<K, V> {

    public static <K, V> IHashMapEntry<K, V> from(K key, V value) {

      return null;
    }

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
