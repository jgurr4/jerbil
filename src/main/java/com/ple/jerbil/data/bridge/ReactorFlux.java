package com.ple.jerbil.data.bridge;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorFlux<T> implements ReactiveWrapper<T> {

  private final Flux<T> fluxObject;

  public ReactorFlux(Flux<T> fluxObject) {
    this.fluxObject = fluxObject;
  }

  public static <T> ReactorFlux<T> make(Flux<T> value) {
    return new ReactorFlux<T>(value);
  }

  public Flux<T> unwrapFlux() {
    return fluxObject;
  }

  @Override
  public T unwrap() {
    return fluxObject.blockLast();
  }

  public Mono<T> unwrapMono() {
    return fluxObject.next();
  }

}
