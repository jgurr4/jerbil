package com.ple.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableMapTests {

  @Test
  void testSimple() {
    IMap<String, Integer> m1 = IHashMap.from("banana", 2, "grape", 3);
    IMap<String, Integer> m2 = m1.put("apple", 1);
    assertEquals(m1.size(), 2);   // How many entries in the map/bucket. for loop of all buckets, and add number of entries of each bucket together
    assertEquals(m2.size(), 3);   // Alternatively, just have a size field, and update it every time you update it. This saves time so you don't have to scan every time.
    assertNull(m1.get("apple"));
    assertEquals(m2.get("apple"), 1);
    assertEquals(m1.get("banana"), 2);
    assertEquals(m1.get("grape"), 3);
  }

}
