package com.ple.jerbil.data.GenericInterfaces;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class SynchronousObject<T> extends ReactiveWrapper<T> {
  private final T object;

  protected SynchronousObject(T object, String failMessage, Throwable exception) {
    super(failMessage, exception);
    this.object = object;
  }

  public static <T> SynchronousObject make(T value) {
    return new SynchronousObject(value, null, null);
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
  public <R> ReactiveWrapper<R> map(Function<? super T, R> mapper) {
    R result;
    if (object != null) {
      result = mapper.apply(object);
    } else {
      return (SynchronousObject<R>) this;
    }
    return new SynchronousObject<>(result, null, null);
  }

  @Override
  public <R> ReactiveWrapper<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    R result;
    if (object != null) {
      result = Mono.from(mapper.apply(object)).block();
    } else {
      return (SynchronousObject<R>) this;
    }
    return new SynchronousObject<>(result, null, null);
  }

}
