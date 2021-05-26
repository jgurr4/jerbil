package com.ple.jerbil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Immutable
public class ImmutableHashMap<K,V>  implements Map<K,V> {

  private ImmutableHashMapEntry[][] buckets;

  private class ImmutableHashMapEntry<K,V> implements Entry<K, V> {
    
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

    @Override
    public boolean equals(Object o) {

      return false;
    }

    @Override
    public int hashCode() {

      return 0;
    }

  }

  @Override
  public int size() {

    return 0;
  }

  @Override
  public boolean isEmpty() {

    return false;
  }

  @Override
  public boolean containsKey(Object key) {

    return false;
  }

  @Override
  public boolean containsValue(Object value) {

    return false;
  }

  @Override
  public V get(Object key) {

    return null;
  }

  @Override
  public V put(K key, V value) {

    return null;
  }

  @Override
  public V remove(Object key) {

    return null;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {

  }

  @Override
  public void clear() {

  }

  @Override
  public Set<K> keySet() {

    return null;
  }

  @Override
  public Collection<V> values() {

    return null;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {

    return null;
  }

  @Override
  public V getOrDefault(Object key, V defaultValue) {

    return Map.super.getOrDefault(key, defaultValue);
  }

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {

    Map.super.forEach(action);
  }

  @Override
  public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    Map.super.replaceAll(function);
  }

  @Override
  public V putIfAbsent(K key, V value) {

    return Map.super.putIfAbsent(key, value);
  }

  @Override
  public boolean remove(Object key, Object value) {

    return Map.super.remove(key, value);
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {

    return Map.super.replace(key, oldValue, newValue);
  }

  @Override
  public V replace(K key, V value) {

    return Map.super.replace(key, value);
  }

  @Override
  public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {

    return Map.super.computeIfAbsent(key, mappingFunction);
  }

  @Override
  public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {

    return Map.super.computeIfPresent(key, remappingFunction);
  }

  @Override
  public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {

    return Map.super.compute(key, remappingFunction);
  }

  @Override
  public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {

    return Map.super.merge(key, value, remappingFunction);
  }

}
