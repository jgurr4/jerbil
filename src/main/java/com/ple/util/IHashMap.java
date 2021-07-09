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
    final IHashMap<K, V> map = new IHashMap<>(entries, bucketSize, entriesInUse, bucketCount);
    return map;
  }

  @Override
  public IHashMap<K, V> putAll(Object... keyOrValue) {

    int c = 0;
    assert keyOrValue.length % 2 == 0;
    IHashMapEntry<K, V>[] newEntries = new IHashMapEntry[entries.length];
    int newBucketCount = bucketCount;
    int newEntriesInUse = keyOrValue.length / 2 + entriesInUse;
    if (newEntriesInUse > (int) (entries.length * threshold)) {
      final int entryCount = (int) (newEntriesInUse * 2 / threshold);
      newBucketCount = entryCount / bucketSize;
      newEntries = new IHashMapEntry[entryCount];
      rehash(entries, newEntries, newBucketCount, bucketSize);
    } else {
      while (c < entries.length) {
        newEntries[c] = entries[c];
        c++;
      }
      c = 0;
    }
    for (int i = 0; i < keyOrValue.length - 1; i += 2) {
      final K key = (K) keyOrValue[i];
      final V value = (V) keyOrValue[i + 1];
      final int hashCode = Math.abs(key.hashCode());
      final int bucketIndex = hashCode % newBucketCount;
      int entryIndex = bucketIndex * bucketSize;
      while (c < newEntries.length) {
        if (newEntries[entryIndex] == null) {
          newEntries[entryIndex] = IHashMapEntry.from(key, value);
          break;
        }
        c++;
        entryIndex++;
        if (entryIndex >= newEntries.length) {
          entryIndex = 0;
        }
      }
    }
    final IHashMap<K, V> map = new IHashMap<>(newEntries, bucketSize, newEntriesInUse, newBucketCount);
    return map;

  }

  /**
   * .put() should never be placed in a loop of multiple values. This is because it will force Java to
   * recreate the IHashMap object for each loop/entry. Instead you should use .putAll() method since that
   * will only recreate the IHashMap one time no matter how many entries you put in.
   *
   * @param key
   * @param value
   * @return
   */
  @Override
  public IHashMap<K, V> put(K key, V value) {

    int newBucketCount = bucketCount;
    IHashMapEntry<K, V>[] newEntries = new IHashMapEntry[entries.length];
    int c = 0;
    while (c < entries.length) {
      newEntries[c] = entries[c];
      c++;
    }
    c = 0;
    final int hashCode = Math.abs(key.hashCode());
    final int bucketIndex = hashCode % newBucketCount;
    int entryIndex = bucketIndex * bucketSize;
    int newEntriesInUse = entriesInUse;
    while (c < newEntries.length) {
      if (newEntries[entryIndex] == null) {
        newEntries[entryIndex] = IHashMapEntry.from(key, value);
        newEntriesInUse += 1;
        break;
      }
      c++;
      entryIndex++;
      if (entryIndex >= newEntries.length) {
        entryIndex = 0;
      }
    }
    if (entriesInUse > newEntries.length * threshold) {
      final int entryCount = (int) (entriesInUse * 2 / threshold);
      newBucketCount = entryCount / bucketSize;
      newEntries = new IHashMapEntry[entryCount];
    }
    final IHashMap<K, V> map = new IHashMap<>(newEntries, bucketSize, newEntriesInUse, newBucketCount);
    return map;
  }

  @Override
  public int size() {

    return entriesInUse;
  }

  @Override
  public V get(K key) {

    final int hashCode = Math.abs(key.hashCode());
    final int bucketIndex = hashCode % bucketCount;
    int entryIndex = bucketIndex * bucketSize;
    int c = 0;
    while (c < entries.length) {
      if (entries[entryIndex] == null) {
        return null;
      } else if (entries[entryIndex].getKey() == key) {
        return entries[entryIndex].getValue();
      }
      c++;
      entryIndex++;
    }
    return null;
  }

  public IHashMap<K, V> setBucketSize(int newBucketSize) {

    final IHashMapEntry<K, V>[] newBuckets = new IHashMapEntry[entries.length];
    rehash(entries, newBuckets, bucketCount, newBucketSize);
    return new IHashMap(newBuckets, newBucketSize, entriesInUse, bucketCount);

  }

  public IHashMap<K, V> setBucketCount(int newBucketCount) {

    final IHashMapEntry<K, V>[] newBuckets = new IHashMapEntry[newBucketCount * bucketSize];
    rehash(entries, newBuckets, newBucketCount, bucketSize);
    return new IHashMap(newBuckets, bucketSize, entriesInUse, newBucketCount);
  }

  private void rehash(IHashMapEntry<K, V>[] entries, IHashMapEntry<K, V>[] newBuckets, int newBucketCount, int newBucketSize) {

    for (int i = 0; i < entries.length; i++) {
      if (entries[i] != null) {
        IHashMapEntry<K, V> entry = entries[i];
        final int newBucketIndex = Math.abs(entry.getKey().hashCode()) % newBucketCount * newBucketSize;
        for (int k = newBucketIndex; k < newBuckets.length; k++) {
          if (k > newBuckets.length) {
            k = 0;
          }
          if (newBuckets[k] == null) {
            newBuckets[k] = entry;
            break;
          }
        }
      }
    }

  }

  private void copyHashTable(IHashMapEntry<K, V>[] buckets, IHashMapEntry<K, V>[] newBuckets) {

    for (int i = 0; i < buckets.length; i++) {
      newBuckets[i] = buckets[i];
    }
  }

  public int getBucketCount() {

    return bucketCount;
  }

  public int getBucketCapacity(int bucketIndex) {

    int spaceAvailable = 0;
    for (int i = 0; i < bucketSize; i++) {
      if (entries[bucketIndex + i] == null) {
        spaceAvailable++;
      }
    }

    return spaceAvailable;
  }

  public int getBucketSize(int bucketIndex) {

    int numValues = 0;
    for (int i = 0; i < bucketSize; i++) {
      if (entries[bucketIndex + i] != null) {
        numValues++;
      }
    }
    return numValues;
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

