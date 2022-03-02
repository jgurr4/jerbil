package com.ple.jerbil.data.GenericInterfaces;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Failable is a type of Functor which is useful for catching failable methods during runtime and allowing developers the
 * option of forcing the app to exit, or to log or simply continue operations as normal without the framework deciding
 * for them.
 *
 * It has another benefit of skipping further operations on a failed object instead of continuing to fail one operation
 * after another. It will retain the failure message and exception from the original failed method all the way to the
 * end of a chain of .map() functions.
 * @param <T>
 */
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

  public Failable<T> setObject(T object) {
    return new Failable<>(object, null, null);
  }
}