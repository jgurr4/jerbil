package com.ple.jerbil.data.GenericInterfaces;

import com.ple.util.IList;

public class Functor<T> {
  public final T object;
  public final Exception ex;
  public final IList<String> warnings;

  protected Functor(T object, Exception ex, IList<String> warnings) {
    this.object = object;
    this.ex = ex;
    this.warnings = warnings;
  }

  public static <T> Functor<T> make(T object, Exception ex, IList<String> warnings) {
    return new Functor<>(object, ex, warnings);
  }

}
