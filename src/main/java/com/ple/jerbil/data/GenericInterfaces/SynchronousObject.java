package com.ple.jerbil.data.GenericInterfaces;

import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class SynchronousObject<T> extends ReactiveWrapper<T> {
  @Nullable private final T object;
  @Nullable public final String failMessage;
  @Nullable public final Throwable exception;

  protected SynchronousObject(T object, String failMessage, Throwable exception) {
    this.failMessage = failMessage;
    this.exception = exception;
    this.object = object;
  }

  public static <T> SynchronousObject make(T value) {
    return new SynchronousObject(value, null, null);
  }

  public static <T> SynchronousObject<T> make(Mono<T> value, String failMessage, Throwable exception) {
    return new SynchronousObject(value, failMessage, exception);
  }

  public T unwrap() {
    return object;
  }

  @Override
  public Mono<T> unwrapMono() {
    return Mono.just(object);
  }

  @Override
  public Flux<T> unwrapFlux() {
    return Flux.just(object);
  }

  @Override
  public <R> SynchronousObject<R> map(Function<? super T, R> mapper) {
    R result;
    if (object != null) {
      result = mapper.apply(object);
    } else {
      return (SynchronousObject<R>) this;
    }
    return new SynchronousObject<>(result, null, null);
  }

  @Override
  public <R> SynchronousObject<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    R result;
    if (object != null) {
      result = Mono.from(mapper.apply(object)).block();
    } else {
      return (SynchronousObject<R>) this;
    }
    return new SynchronousObject<>(result, null, null);
  }

  @Override
  public <R> ReactiveFlux<R> flatMapMany(Function<? super T, ? extends Publisher<R>> mapper) {
    return (ReactiveFlux<R>) ReactiveFlux.make(Flux.just(object).map(mapper));
  }

  @Override
  public <T> ReactiveMono<T> next() {
    return (ReactiveMono<T>) ReactiveMono.make(Mono.just(object));
  }

  @Override
  public void subscribe(Subscriber<? super T> s) {

  }
}
