package com.ple.jerbil.data.GenericInterfaces;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ReactiveMono<T> extends ReactiveWrapper<T> {
  private final Mono<T> mono;

  protected ReactiveMono(Mono<T> mono, String failMessage, Throwable exception) {
    super(failMessage, exception);
    this.mono = mono;
  }

  public static <T> ReactiveMono<T> make(Mono<T> value) {
    return new ReactiveMono<>(value, null, null);
  }

  public static <T> ReactiveMono<T> make(Mono<T> value, String failMessage, Throwable exception) {
    return new ReactiveMono<>(value, failMessage, exception);
  }

  public Flux<T> unwrapFlux() {
    return Flux.from(mono);
  }

  // Original signature of map and flatmap included ? extends R.
//  public <R> ReactiveWrapper<R> map(Function<? super T, ? extends R> mapper) {

  @Override
  public <R> ReactiveMono<R> map(Function<? super T, R> mapper) {
    return new ReactiveMono(mono.map(mapper).defaultIfEmpty((R) mono.block()), failMessage, exception);
  }

  @Override
  public <R> ReactiveMono<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    return new ReactiveMono(Mono.from(mono.map(mapper).block()).defaultIfEmpty((R) mono.block()), failMessage, exception);
  }

  @Override
  public <R> ReactiveFlux<R> flatMapMany(Function<? super T, R> mapper) {
    return null;
  }

  @Override
  public <R> ReactiveMono<R> next() {
    return null;
  }

  @Override
  public T unwrap() {
    return mono.block();
  }

  public Mono<T> unwrapMono() {
    return mono;
  }

}
