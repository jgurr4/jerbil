package com.ple.jerbil.data.GenericInterfaces;

import com.ple.util.IList;

import java.util.Optional;

@Immutable
public class Functor<T>{
  private final Optional<T> object;
  private final Optional<Exception> ex;
  private final Optional<IList<String>> warnings;

  protected Functor(Optional<T> object, Optional<Exception> ex, Optional<IList<String>> warnings) {
    this.object = object;
    this.ex = ex;
    this.warnings = warnings;
  }

  public static <T> Functor<T> make(Optional<T> object, Optional<Exception> ex, Optional<IList<String>> warnings) {
    return new Functor<>(object, ex, warnings);
  }

  public boolean isPresent() {
    if (object.isPresent()) {
      return true;
    }
    return false;
  }

  public T get() {
    return object.get();
  }

  public Exception getEx() {
    return ex.get();
  }

  public IList<String> getWarnings() {
    return warnings.get();
  }

}
