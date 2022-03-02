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
    return new ReactorMono<T>(value, null, null);
  }

  public Flux<T> unwrapFlux() {
    return Flux.from(mono);
  }

  @Override
  public <R> ReactiveWrapper<R> map(Function<? super T, ? extends R> mapper) {
    R result;
    mono.map()
    if (mono != null) {
      result = mapper.apply(mono);
    } else {
      return (ReactorMono<R>) this;
    }
    return new ReactorMono<>(result, null, null);
  }

  @Override
  public <R> ReactiveWrapper<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
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
