package com.ple.jerbil;

import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;

public class ReactiveWrapperTests {

  @Test
  public void testMap() {
  }
  @Test
  public void testFlatMap() {
    final ReactiveMono<String> test = ReactiveMono.make(Mono.just("Hello")
        .flatMap(s -> Mono.from(Mono.just(s + " World"))));
    assertEquals("Hello World", test.unwrap());
  }
  @Test
  public void testFlatMapMany() {
  }
  @Test
  public void testNext() {
  }
}
