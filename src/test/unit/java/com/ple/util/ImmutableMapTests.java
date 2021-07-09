package com.ple.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableMapTests {

  @Test
  void testSimple() {

    IMap<String, Integer, ?> m1 = IHashMap.from("banana", 2, "grape", 3);
    IMap<String, Integer, ?> m2 = m1.put("apple", 1);

    assertEquals(2, m1.size());
    assertEquals(3, m2.size());
    assertNull(m1.get("apple"));
    assertEquals(1, m2.get("apple"));
    assertEquals(2, m1.get("banana"));
    assertEquals(3, m1.get("grape"));
    assertEquals(2, m2.get("banana"));  // This test makes sure m2 still has the m1 values.

  }

  @Test
  void testListExpansion() {

    IHashMap<String, String> m1 = IHashMap.empty.setBucketCount(1).setBucketSize(2);
    assertEquals(m1.getBucketCount(), 1);
    assertEquals(2, m1.getBucketCapacity(0));
    assertEquals(0, m1.getBucketSize(0));

    var m2 = m1.putAll("apple", "red", "banana", "yellow");
    assertEquals(6, m2.getBucketCount());
    assertEquals(2, m2.getBucketCapacity(0));
    assertEquals(0, m2.getBucketSize(0));

    var m3 = m2.put("grape", "purple");
    assertEquals(m3.getBucketCount(), 1);
    assertEquals(m3.getBucketCapacity(0), 4);
    assertEquals(m3.getBucketSize(0), 3);
    assertEquals(m3.get("banana"), "yellow");
    assertEquals(m3.get("grape"), "purple");

    var m4 = m2.put("orange", "orange").put("blueberry", "blue");
    assertEquals(m4.getBucketCount(), 1);
    assertEquals(m4.getBucketCapacity(0), 8);
    assertEquals(m4.getBucketSize(0), 5);
    assertEquals(m4.get("apple"), "red");
    assertEquals(m4.get("orange"), "orange");

  }

}
