package com.ple.util;

import com.ple.jerbil.Immutable;

import java.util.Arrays;

@Immutable
public class IHashMap<K, V> implements IMap<K, V, IHashMap<K, V>> {

  static public final IHashMap empty = new IHashMap(new IHashMapEntry[10][], 10);
  private final IHashMapEntry<K, V>[][] buckets;
  private final int maxBucketSize;
  private int threshold;
  private int size;

  public IHashMap(IHashMapEntry<K, V>[][] buckets, int maxBucketSize) {

    this.buckets = buckets;
    this.maxBucketSize = maxBucketSize;

  }

  public static <K, V> IHashMap<K, V> from(Object... objects) {

    assert objects.length % 2 == 0;
    final int bucketCount = 10;
    final IHashMapEntry<K, V>[][] buckets = new IHashMapEntry[bucketCount][];
    for (int i = 0; i < objects.length - 1; i += 2) {
      final K key = (K) objects[i];
      final V value = (V) objects[i + 1];
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
    // If the buckets are full, and they reach a certain max size then these methods need to add more buckets instead of
    // increasing bucket size infinitely, because increasing bucket size will negatively impact performance.
    // In other words, they may have to rehash. If the hashmap reaches a threshold of 30% is a good time to increase the
    // number of buckets. Because some buckets will get close to overflowing which will auto-increase the
    // size of those buckets.

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

  public IHashMap<K, V> setBucketSize(int newBucketSize) {

    final IHashMapEntry<K, V>[][] newBuckets = new IHashMapEntry[buckets.length][];
    copyHashTable(buckets, newBuckets);
    return new IHashMap(newBuckets, newBucketSize);

  }

  public IHashMap<K, V> setBucketCount(int newBucketCount) {

    final IHashMapEntry<K, V>[][] newBuckets = new IHashMapEntry[newBucketCount][buckets[0].length];
    rehash(buckets, newBuckets);
    return new IHashMap(newBuckets, newBucketCount);
  }

  private void rehash(IHashMapEntry<K, V>[][] buckets, IHashMapEntry<K, V>[][] newBuckets) {  // rehash is useful for adding when your bucket array is full.

    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].length; j++) {
        IHashMapEntry<K, V> entry = buckets[i][j];   // i is the old bucket index. All buckets start with nulls. So the first null you find in a bucket is where you plugin each entry.
        final int newBucketIndex = entry.getKey().hashCode() % newBuckets.length;
        for (int k = 0; k < newBuckets[0].length; k++) {
          if (newBuckets[newBucketIndex][k] == null) {
            newBuckets[newBucketIndex][k] = entry;
            break;
          }
        }
      }
    }

  }

  private void copyHashTable(IHashMapEntry<K, V>[][] buckets, IHashMapEntry<K, V>[][] newBuckets) {

    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].length; j++) {
        newBuckets[i][j] = buckets[i][j];
      }
    }

  }

  private static class IHashMapEntry<K, V> {

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

      return new IHashMapEntry<K, V>(key, v);
    }

  }

}
