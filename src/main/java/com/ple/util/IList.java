package com.ple.util;

public interface IList<T> {

    T[] toArray();

  IList<T> addAll(IList<T> list);

  public IList<T> add(T t);

}
