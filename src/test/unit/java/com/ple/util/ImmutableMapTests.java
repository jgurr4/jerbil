package com.ple.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableMapTests {

  @Test
  void testSimple() {
    IMap<String, Integer> m1 = IHashMap.from("banana", 2, "grape", 3);
    var m2 = m1.put("apple", 1);
    assertEquals(m1.size(), 2);
    assertEquals(m2.size(), 3);
    assertNull(m1.get("apple"), null);
    assertEquals(m2.get("apple"), 1);
    assertEquals(m1.get("banana"), 2);
    assertEquals(m1.get("grape"), 3);
  }

}
