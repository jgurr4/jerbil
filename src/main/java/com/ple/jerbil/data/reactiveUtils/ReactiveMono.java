package com.ple.jerbil.data.reactiveUtils;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ReactiveMono<T> extends ReactiveWrapper<T> {
  private final Mono<T> mono;

  protected ReactiveMono(Mono<T> mono) {
    this.mono = mono;
  }

  public static <T> ReactiveMono<T> make(Mono<T> value) {
    return new ReactiveMono<>(value);
  }

  public Flux<T> unwrapFlux() {
    return Flux.from(mono);
  }

  // Original signature of map and flatmap included ? extends R.
//  public <R> ReactiveWrapper<R> map(Function<? super T, ? extends R> mapper) {

  @Override
  public <R> ReactiveMono<R> map(Function<? super T, R> mapper) {
    final Mono<R> map = mono.map(mapper);
    return new ReactiveMono(map);
  }

  @Override
  public <R> ReactiveMono<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    final Function<? super T, Mono<R>> monoFunction = mapper.andThen(publisher -> Mono.from(publisher));
    return new ReactiveMono<>(mono.flatMap(monoFunction));
  }

  @Override
  public <R> ReactiveFlux<R> flatMapMany(Function<? super T, ? extends Publisher<R>> mapper) {
    return new ReactiveFlux<>(Mono.from(mono.map(mapper)).flatMapMany(e -> e));
  }

  @Override
  public <T> ReactiveMono<T> next() {
    return (ReactiveMono<T>) this;
  }

  @Override
  public ReactiveMono<T> log() {
    return new ReactiveMono<>(mono.log());
  }

  @Override
  public T unwrap() {
    return mono.block();
  }

  public Mono<T> unwrapMono() {
    return mono;
  }

  @Override
  public void subscribe(Subscriber<? super T> s) {
  }
}
