package com.ple.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableMapTests {

  @Test
  void testSimple() {

    IMap<String, Integer, ?> m1 = IHashMap.from("banana", 2, "grape", 3);
    IMap<String, Integer, ?> m2 = m1.put("apple", 1);

    assertEquals(m1.size(), 2);   // How many entries in the map/bucket. for loop of all buckets, and add number of entries of each bucket together
    assertEquals(m2.size(), 3);   // Alternatively, just have a size field, and update it every time you update it. This saves time so you don't have to scan every time.
    assertNull(m1.get("apple"));
    assertEquals(m2.get("apple"), 1);
    assertEquals(m1.get("banana"), 2);
    assertEquals(m1.get("grape"), 3);

  }

  @Test
  void testListExpansion() {

    IHashMap<String, String> m1 = IHashMap.empty.setBucketCount(1).setBucketSize(2).setMaxBucketSize(10);
    assertEquals(m1.getBucketCount(), 1);
    assertEquals(m1.getBucketCapacity(0), 2);
    assertEquals(m1.getBucketSize(0), 0);

    var m2 = m1.putAll("apple", "red", "banana", "yellow");
    assertEquals(m2.getBucketCount(), 1);
    assertEquals(m2.getBucketCapacity(0), 2);
    assertEquals(m2.getBucketSize(0), 2);

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
