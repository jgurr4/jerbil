package com.ple.jerbil.data.GenericInterfaces;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Immutable
public class Failable<T> {
  @Nullable public final T object;
  @Nullable public final String failMessage;
  @Nullable public final Throwable exception;

  protected Failable(T object, @Nullable String failMessage, @Nullable Throwable exception) {
    this.object = object;
    this.failMessage = failMessage;
    this.exception = exception;
  }

  public static <T> Failable<T> make(T object, String failMessage, Throwable exception) {
    return new Failable<>(object, failMessage, exception);
  }

  public <R> Failable<R> map(Function<T, R> mapper) {
    R result;
    if (object != null) {
      result = mapper.apply(object);
    } else {
      return (Failable<R>) this;
    }
    return new Failable<>(result, null, null);
  }

}
