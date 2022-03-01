package com.ple.jerbil.data.GenericInterfaces;

import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Immutable
public class Functor<T>{
  public final T object;
  public final Exception exception;
  public final IList<String> warnings;
  @Nullable public final Object failResult;

  protected Functor(T object, Exception exception, IList<String> warnings, Object failResult) {
    this.object = object;
    this.exception = exception;
    this.warnings = warnings;
    this.failResult = failResult;
  }

  public static <T> Functor<T> make(T object, Exception ex, IList<String> warnings) {
    return new Functor<>(object, ex, warnings, null);
  }

  public <R> Functor<R> map(Function<T, R> mapper) {
    R result;
    if (object != null) {
      result = mapper.apply(object);
    } else {
      return new Functor<>(null, exception, warnings, failResult);
    }
    return new Functor<>(result, null, null, null);
  }

  public Object get() {
    if (object != null) {
      return object;
    }
    return failResult;
  }

}
