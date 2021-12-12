package com.ple.util;

public interface IList<T> extends Iterable<T>{

    T[] toArray();

  IList<T> addAll(IList<T> list);

  public IList<T> add(T t);

}
