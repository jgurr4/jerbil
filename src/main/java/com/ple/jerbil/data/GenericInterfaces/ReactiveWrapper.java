package com.ple.jerbil.data.GenericInterfaces;

import com.ple.jerbil.data.Database;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveWrapper<T> {

  public T unwrap();

  public Mono<T> unwrapMono();

  public Flux<T> unwrapFlux();

}
