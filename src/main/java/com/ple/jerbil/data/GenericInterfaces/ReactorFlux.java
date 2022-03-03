package com.ple.jerbil.data.GenericInterfaces;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ReactorFlux<T> extends ReactiveWrapper<T> {
  private final Flux<T> flux;

  protected ReactorFlux(Flux<T> flux, String failMessage, Throwable exception) {
    super(failMessage, exception);
    this.flux = flux;
  }

  public static <T> ReactorFlux<T> make(Flux<T> value) {
    return new ReactorFlux<T>(value, null, null);
  }

  public Flux<T> unwrapFlux() {
    return flux;
  }

  @Override
  public <R> ReactiveWrapper<R> map(Function<? super T, R> mapper) {
    return null;
  }

  @Override
  public <R> ReactiveWrapper<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    return null;
  }

  @Override
  public T unwrap() {
    return flux.blockLast();
  }

  public Mono<T> unwrapMono() {
    return flux.next();
  }

}
