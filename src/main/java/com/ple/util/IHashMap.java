package com.ple.util;

import com.ple.jerbil.Immutable;

@Immutable
public class IHashMap<K, V> implements IMap<K, V, IHashMap<K, V>> {

  static public final IHashMap empty = new IHashMap(new IHashMapEntry[0], 5, 0, bucketCount);
  private final IHashMapEntry<K, V>[] entries;
  private final int bucketSize;
  private static final float threshold = 0.3f;
  private final int entriesInUse;
  private final int bucketCount;

  public IHashMap(IHashMapEntry<K, V>[] entries, int bucketSize, int entriesInUse, int bucketCount) {

    this.entries = entries;
    this.bucketSize = bucketSize;
    this.entriesInUse = entriesInUse;
    this.bucketCount = bucketCount;

  }

  public static <K, V> IHashMap<K, V> from(Object... objects) {

    assert objects.length % 2 == 0;
    final int entriesInUse = objects.length / 2;
    final int bucketSize = 5;
    final int entryCount = (int) (objects.length / threshold);  // Should be (objects.length / 2 / threshold)
    final int bucketCount = entryCount / bucketSize;
    final IHashMapEntry<K, V>[] entries = new IHashMapEntry[entryCount];
    for (int i = 0; i < objects.length - 1; i += 2) {
      final K key = (K) objects[i];
      final V value = (V) objects[i + 1];
      final int hashCode = Math.abs(key.hashCode());
      final int bucketIndex = hashCode % bucketCount;
      int c = 0;
      int entryIndex = bucketIndex * bucketSize;
      while (c < entries.length) {
        if (entries[entryIndex] == null) {
          entries[entryIndex] = IHashMapEntry.from(key, value);
          break;
        }
        c++;
        entryIndex++;
        if (entryIndex >= entries.length) {
          entryIndex = 0;
        }
      }
    }
    IHashMap<K, V> map = new IHashMap<>(entries, bucketSize, entriesInUse, bucketCount);
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

    final int hashCode = Math.abs(key.hashCode());
    final int bucketIndex = hashCode % bucketCount;
    int c = 0;
    int entryIndex = bucketIndex * bucketSize;
    while (c < entries.length) {
      if (entries[entryIndex] == null) {
        entries[entryIndex] = IHashMapEntry.from(key, value);
        break;
      }
      c++;
      entryIndex++;
      if (entryIndex >= entries.length) {
        entryIndex = 0;
      }
    }

    return new IHashMap<>(entries, bucketSize, entriesInUse, bucketCount);
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

    final IHashMapEntry<K, V>[] newBuckets = new IHashMapEntry[entries.length];
    copyHashTable(entries, newBuckets);
    return new IHashMap(newBuckets, newBucketSize, entriesInUse, bucketCount);

  }

  public IHashMap<K, V> setBucketCount(int newBucketCount) {

    final IHashMapEntry<K, V>[] newBuckets = new IHashMapEntry[newBucketCount];
    rehash(entries, newBuckets);
    return new IHashMap(newBuckets, newBucketCount, entriesInUse, bucketCount);
  }

  private void rehash(IHashMapEntry<K, V>[] buckets, IHashMapEntry<K, V>[] newBuckets) {  // rehash is useful for adding when your bucket array is full.

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

  private void copyHashTable(IHashMapEntry<K, V>[] buckets, IHashMapEntry<K, V>[] newBuckets) {

    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].length; j++) {
        newBuckets[i][j] = buckets[i][j];
      }
    }

  }

  private static class IHashMapEntry<K, V> {

    private final K key;
    private final V value;
    public static IHashMapEntry empty = new IHashMapEntry(new IHashMapEntry[0], 10);

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
