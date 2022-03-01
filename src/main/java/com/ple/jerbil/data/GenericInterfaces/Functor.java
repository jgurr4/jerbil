package com.ple.jerbil.data.GenericInterfaces;

import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Immutable
public class Functor<T, T2>{
  public final T object;
  public final Exception exception;
  public final IList<String> warnings;
  @Nullable public final T2 failResult;

  protected Functor(T object, Exception exception, IList<String> warnings, T2 failResult) {
    this.object = object;
    this.exception = exception;
    this.warnings = warnings;
    this.failResult = failResult;
  }

  public static <T, T2> Functor<T, T2> make(T object, Exception ex, IList<String> warnings) {
    return new Functor<>(object, ex, warnings, null);
  }

  public <R> Functor<R, T> map(Function<? super T, ? extends R> mapper) {
    R result;
    if (this.object != null) {
      result = mapper.apply(this.object);
    } else {
      return new Functor<>(null, exception, warnings, object);
      //FIXME: Figure out how to make it return the failed functor result at the end of the chain of .map().
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
