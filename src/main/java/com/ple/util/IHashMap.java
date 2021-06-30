package com.ple.util;

import com.ple.jerbil.Immutable;

import java.util.Arrays;

@Immutable
public class IHashMap<K,V>  implements IMap<K,V,IHashMap<K, V>> {

  static public final IHashMap empty = new IHashMap(new IHashMapEntry[10][], 10);
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

  public IHashMap<K, V> setBucketCount(int newBucketSize) {
    return new IHashMap<K, V>(new IHashMapEntry[newBucketSize][], maxBucketSize);
  }

  public IHashMap<K, V> setInitialBucketSize(int newInitialBucketSize) {

    final IHashMapEntry<K, V>[][] newBuckets = new IHashMapEntry[buckets.length][];
    rehash(buckets, newBuckets);
    return new IHashMap(newBuckets, newInitialBucketSize);
  }

  private void rehash(IHashMapEntry<K, V>[][] buckets, IHashMapEntry<K, V>[][] newBuckets) {
    // Just need to make a copy of existing IHashMap, and injecting it into a new IHashMap that has
    // different size. Same as .from method. For example, existing could be 10x10, the new one could be
    // 20x10. hashcode % 20 would fit perfectly since 12319 % 20 = 19 and 12320 % 20 = 0.
    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].length; j++) {
        newBuckets[i][j] = buckets[i][j];
      }
    }
  }

  private static class IHashMapEntry<K,V> {

    private final K key;
    private final V value;
    public static IHashMapEntry empty = new IHashMapEntry(new IHashMapEntry[0][], 10);

    private IHashMapEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public static <K, V> IHashMapEntry<K, V> from(K key, V value) {
      return new IHashMapEntry<>(key, value);
    }

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }

    public IHashMapEntry<K, V> setValue(V v) {
      return new IHashMapEntry<K,V>(key, v);
    }

  }

}
