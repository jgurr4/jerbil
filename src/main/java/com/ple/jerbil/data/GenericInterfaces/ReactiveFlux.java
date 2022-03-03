package com.ple.jerbil.data.GenericInterfaces;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ReactiveFlux<T> extends ReactiveWrapper<T> {
  private final Flux<T> flux;

  protected ReactiveFlux(Flux<T> flux, String failMessage, Throwable exception) {
    super(failMessage, exception);
    this.flux = flux;
  }

  public static <T> ReactiveFlux<T> make(Flux<T> value) {
    return new ReactiveFlux<T>(value, null, null);
  }

  public static <T> ReactiveFlux<T> make(Flux<T> value, String failMessage, Throwable exception) {
    return new ReactiveFlux<T>(value, failMessage, exception);
  }


  public Flux<T> unwrapFlux() {
    return flux;
  }

  @Override
  public <R> ReactiveFlux<R> map(Function<? super T, R> mapper) {
    return null;
  }

  @Override
  public <R> ReactiveFlux<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper) {
    return null;
  }

  @Override
  public <R> ReactiveFlux<R> flatMapMany(Function<? super T, ? extends Publisher<R>> mapper) {
    return null;
  }

  @Override
  public <R> ReactiveMono<R> next() {
    return null;
  }

  @Override
  public T unwrap() {
    return flux.blockLast();
  }

  public Mono<T> unwrapMono() {
    return flux.next();
  }

  @Override
  public void subscribe(Subscriber<? super T> s) {

  }
}
