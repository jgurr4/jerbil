package com.ple.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableMapTests {

  @Test
  void testSimple() {

    IMap<String, Integer> m1 = IHashMap.from("banana", 2, "grape", 3);
    IMap<String, Integer> m2 = m1.put("apple", 1);

    assertEquals(2, m1.size());
    assertEquals(3, m2.size());
    assertNull(m1.get("apple"));
    assertEquals(1, m2.get("apple"));
    assertEquals(2, m1.get("banana"));
    assertEquals(3, m1.get("grape"));
    assertEquals(2, m2.get("banana"));

  }

  @Test
  void testHashSpaceExpansion() {

    IHashMap<String, String> m1 = IHashMap.empty.setBucketCount(1).setBucketSize(2).setThreshold(.3);
    assertEquals(m1.getBucketCount(), 1);
    assertEquals(2, m1.getBucketSize());

    var m2 = m1.putAll("apple", "red", "banana", "yellow");
    assertEquals(6, m2.getBucketCount());
    assertEquals(2, m2.getBucketSize());

    var m3 = m2.put("grape", "purple");
    assertEquals(6, m3.getBucketCount());
    assertEquals(2, m3.getBucketSize());
    assertEquals("yellow", m3.get("banana"));
    assertEquals("purple", m3.get("grape"));

    var m4 = m2.put("orange", "orange").put("blueberry", "blue");
    assertEquals(6, m4.getBucketCount());
    assertEquals(2, m4.getBucketSize());
    assertEquals("red", m4.get("apple"));
    assertEquals("orange", m4.get("orange"));

  }

  @Test
  void testEmpty() {

    IMap<String, Integer> m1 = IHashMap.empty;
    IMap<String, Integer> m2 = m1.put("apple", 1);
    IMap<String, Integer> m3 = m1.putAll("apple", 1, "banana", 2, "orange", 3);

    assertEquals(0, m1.size());
    assertEquals(1, m2.size());
    assertEquals(3, m3.size());
    assertNull(m1.get("apple"));
    assertEquals(1, m2.get("apple"));
    assertEquals(3, m3.get("orange"));
  }

}
