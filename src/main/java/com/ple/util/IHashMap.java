package com.ple.util;

import com.ple.jerbil.Immutable;

@Immutable
public class IHashMap<K, V> implements IMap<K, V, IHashMap<K, V>> {

  static public final IHashMap empty = new IHashMap(new IHashMapEntry[0], 5, 0, 6);
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
    final int entryCount = (int) (objects.length / threshold);
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

    return null;

  }

  @Override
  public IHashMap<K, V> put(K key, V value) {

    int putBucketCount = bucketCount;
    if (entriesInUse > entries.length * threshold) {
      final int entryCount = (int) (entriesInUse * 2 / threshold);  // Makes possible size double what the current max value is.
      putBucketCount = entryCount / bucketSize;
    }
    final int hashCode = Math.abs(key.hashCode());
    final int bucketIndex = hashCode % putBucketCount;
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

    return new IHashMap<>(entries, bucketSize, entriesInUse, putBucketCount);
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
        IHashMapEntry<K, V> entry = buckets[i];   // i is the old bucket index. All buckets start with nulls. So the first null you find in a bucket is where you plugin each entry.
        final int newBucketIndex = entry.getKey().hashCode() % newBuckets.length;
        for (int k = 0; k < newBuckets.length; k++) {
          if (newBuckets[k] == null) {
            newBuckets[k] = entry;
            break;
        }
      }
    }

  }

  private void copyHashTable(IHashMapEntry<K, V>[] buckets, IHashMapEntry<K, V>[] newBuckets) {

    for (int i = 0; i < buckets.length; i++) {
        newBuckets[i] = buckets[i];
      }
    }

  public IHashMap<K, V> setMaxBucketSize(int bucketSize) {   // Should we get rid of this? It's in tests.

    return null;
  }

  public int getBucketCount() {

    return 0;
  }

  public int getBucketCapacity(int bucketIndex) {
    // Probably used to get the number of available spots in the specified bucket.

    return 0;
  }

  public int getBucketSize(int bucketIndex) {
// Probably used to get the number of entries in use of this bucket. Since otherwise if just trying to get actual bucket
// size you don't need the index, since each bucket is exactly the same size.
    return 0;
  }

}

  class IHashMapEntry<K, V> {    // This used to be private static, but for some reason it's not allowed anymore.

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

