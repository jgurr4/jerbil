package com.ple.jerbil;

import com.ple.jerbil.data.reactiveUtils.ReactiveFlux;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReactiveWrapperTests {

  @Test
  public void testMap() {
    final ReactiveMono<String> test = ReactiveMono.make(Mono.just("hello")).map(e -> "world");
    assertEquals("world", test.unwrap());
  }

  @Test
  public void testFlatMap() {
    final ReactiveMono<String> test = ReactiveMono.make(Mono.just("Hello"))
        .flatMap(s -> Mono.from(Mono.just(s + " World")));
    assertEquals("Hello World", test.unwrap());
  }

  @Test
  public void testFlatMapMany() {
    final ReactiveFlux<String> test = ReactiveMono.make(Mono.just("Hello"))
        .flatMapMany(s -> Flux.just(s.split("")));
    final Mono<ArrayList> result = test.unwrapFlux().collectList().map(list -> new ArrayList(list));
    assertEquals("o", result.block().get(4));
  }

  @Test
  public void testNext() {
    final ReactiveFlux<String> test = ReactiveMono.make(Mono.just("Hello"))
        .flatMapMany(s -> Flux.just(s.split("")));
    assertEquals("H", test.next().unwrap());
  }
}
