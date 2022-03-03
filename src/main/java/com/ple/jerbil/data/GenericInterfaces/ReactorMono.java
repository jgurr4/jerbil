package com.ple.jerbil.data.GenericInterfaces;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ReactorMono<T> extends ReactiveWrapper<T> {
  private final Mono<T> mono;

  protected ReactorMono(Mono<T> mono, String failMessage, Throwable exception) {
    super(failMessage, exception);
    this.mono = mono;
  }

  public static <T> ReactorMono<T> make(Mono<T> value) {
    return new ReactorMono<>(value, null, null);
  }

  public Flux<T> unwrapFlux() {
    return Flux.from(mono);
  }

  // Original signature of map and flatmap included ? extends R.
//  public <R> ReactiveWrapper<R> map(Function<? super T, ? extends R> mapper) {

  @Override
  public <R> ReactiveWrapper<R> map(Function<? super T, R> mapper) {
    return new ReactorMono(mono.map(mapper).defaultIfEmpty((R) mono.block()), failMessage, exception);
  }

  @Override
  public <R> ReactiveWrapper<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    return new ReactorMono(Mono.from(mono.map(mapper).block()).defaultIfEmpty((R) mono.block()), failMessage, exception);
  }

  @Override
  public T unwrap() {
    return mono.block();
  }

  public Mono<T> unwrapMono() {
    return mono;
  }

}
