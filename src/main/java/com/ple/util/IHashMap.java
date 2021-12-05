package com.ple.util;

import com.ple.jerbil.data.Immutable;
import org.jetbrains.annotations.NotNull;

import java.rmi.NoSuchObjectException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Immutable
public class IHashMap<K, V> implements IMap<K, V> {

  static public final IHashMap empty = new IHashMap(new IHashMapEntry[0], 5, 0, 6, 2);
  private final IHashMapEntry<K, V>[] entries;
  private final int bucketSize;
  private final float threshold = 0.3f;
  private final int entriesInUse;
  private final int bucketCount;
  private final int expansionFactor;

  private IHashMap(IHashMapEntry<K, V>[] entries, int bucketSize, int entriesInUse, int bucketCount, int expansionFactor) {

    this.entries = entries;
    this.bucketSize = bucketSize;
    this.entriesInUse = entriesInUse;
    this.bucketCount = bucketCount;
    this.expansionFactor = expansionFactor;

  }

  public static <K, V> IHashMap<K, V> from(Object... objects) {

    assert objects.length % 2 == 0;
    final int entriesInUse = objects.length / 2;
    final int bucketSize = 5;
    final int entryCount = (int) (objects.length / .3f);
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
    final IHashMap<K, V> map = new IHashMap<>(entries, bucketSize, entriesInUse, bucketCount, 2);
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
    final IHashMap<K, V> map = new IHashMap<>(newEntries, bucketSize, newEntriesInUse, newBucketCount, 2);
    return map;

  }

  /**
   * .put() should never be placed in a loop of multiple values. This is because it will force Java to
   * recreate the IHashMap object for each loop/entry. Instead you should use .putAll() method since that
   * will only recreate the IHashMap one time no matter how many entries you put in.
   *
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
      final int entryCount = getEntryCount();
      newBucketCount = entryCount / bucketSize;
      newEntries = new IHashMapEntry[entryCount];
    }
    final IHashMap<K, V> map = new IHashMap<>(newEntries, bucketSize, newEntriesInUse, newBucketCount, 2);
    return map;
  }

  @Override
  public IMap<K, V> remove(K key) {
    return null;
  }

  private int getEntryCount() {
    return (int) (entriesInUse * expansionFactor / threshold);
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

    final IHashMapEntry<K, V>[] newEntries = new IHashMapEntry[entries.length];
    rehash(entries, newEntries, bucketCount, newBucketSize);
    return new IHashMap(newEntries, newBucketSize, entriesInUse, bucketCount, 2);

  }

  public IHashMap<K, V> setBucketCount(int newBucketCount) {

    final IHashMapEntry<K, V>[] newEntries = new IHashMapEntry[newBucketCount * bucketSize];
    rehash(entries, newEntries, newBucketCount, bucketSize);
    return new IHashMap(newEntries, bucketSize, entriesInUse, newBucketCount, 2);
  }

  private void rehash(IHashMapEntry<K, V>[] entries, IHashMapEntry<K, V>[] newEntries, int newBucketCount, int newBucketSize) {

    for (int i = 0; i < entries.length; i++) {
      if (entries[i] != null) {
        IHashMapEntry<K, V> entry = entries[i];
        final int newBucketIndex = Math.abs(entry.getKey().hashCode()) % newBucketCount * newBucketSize;
        for (int k = newBucketIndex; k < newEntries.length; k++) {
          if (k > newEntries.length) {
            k = 0;
          }
          if (newEntries[k] == null) {
            newEntries[k] = entry;
            break;
          }
        }
      }
    }

  }

  public int getBucketCount() {
    return bucketCount;
  }

  public int getBucketSize() {
    return bucketSize;
  }

  public IHashMap<K, V> setThreshold(double v) {

    IHashMapEntry<K, V>[] newEntries = entries;
    int newBucketCount = bucketCount;
    if (entriesInUse > entries.length * threshold) {
      newEntries = new IHashMapEntry[entries.length];
      newBucketCount = getEntryCount() / bucketSize;
      rehash(entries, newEntries, newBucketCount, bucketSize);
    }
    return new IHashMap(newEntries, bucketSize, entriesInUse, newBucketCount, expansionFactor);
  }

  @NotNull
  @Override
  public Iterator<IEntry<K, V>> iterator() {
    return new Iterator<>() {

      public int currentIndex;

      @Override
      public boolean hasNext() {
        return currentIndex < entries.length;
      }

      @Override
      public IEntry<K, V> next() {
        if (hasNext()) {
          currentIndex++;
          return entries[currentIndex];
        } else {
          throw new NoSuchElementException();
        }
      }
    };
  }

}

class IHashMapEntry<K, V> implements IEntry<K, V>{

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

