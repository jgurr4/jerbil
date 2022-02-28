package com.ple.jerbil.data.GenericInterfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorMono<T> implements ReactiveWrapper<T> {

  private final Mono<T> monoObject;

  public ReactorMono(Mono<T> monoObject) {
    this.monoObject = monoObject;
  }

  public static <T> ReactorMono<T> make(Mono<T> value) {
    return new ReactorMono<T>(value);
  }

  public Flux<T> unwrapFlux() {
    return Flux.from(monoObject);
  }

  @Override
  public T unwrap() {
    return monoObject.block();
  }

  public Mono<T> unwrapMono() {
    return monoObject;
  }

}
