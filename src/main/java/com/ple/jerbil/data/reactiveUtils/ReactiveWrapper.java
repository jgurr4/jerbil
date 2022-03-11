package com.ple.jerbil.data.reactiveUtils;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class ReactiveWrapper<T> implements Publisher<T>{

  public abstract T unwrap();

  public abstract Mono<T> unwrapMono();

  public abstract Flux<T> unwrapFlux();

  public abstract <R> ReactiveWrapper<R> map(Function<? super T, R> mapper);

  public abstract <R> ReactiveWrapper<R> flatMap(Function<? super T, ? extends Publisher<R>> mapper);

  public abstract <R> ReactiveWrapper<R> flatMapMany(Function<? super T, ? extends Publisher<R>> mapper);

  public abstract <T> ReactiveWrapper<T> next();

  public abstract ReactiveWrapper<T> log();
}
