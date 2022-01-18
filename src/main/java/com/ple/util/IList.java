package com.ple.util;

public interface IList<T> extends Iterable<T>{

    T[] toArray();

  IList<T> addAll(IList<T> list);

  public IList<T> add(T t);

  T get(int i);

  IList<T> remove(T t);

  boolean contains(T t);

  int length();

}
