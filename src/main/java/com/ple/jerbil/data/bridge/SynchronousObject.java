package com.ple.jerbil.data.bridge;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SynchronousObject<T> implements ReactiveWrapper {

  private final T value;

  protected SynchronousObject(T value) {
    this.value = value;
  }

  public static <T> SynchronousObject make(T value) {
    return new SynchronousObject(value);
  }

  public T unwrap() {
    return value;
  }

  @Override
  public Mono unwrapMono() {
    return Mono.just(value);
  }

  @Override
  public Flux unwrapFlux() {
    return Flux.just(value);
  }

}
