package com.ple.jerbil.data;

/**
 * BuildingHints allows users to skip manually defining IndexSpec for each index of their tables.
 * The DatabaseBuilder will review the flags of each column and generate the IndexSpecs based on that.
 *
 * What each flag means:
 * 10000000 = primary key
 * 01000000 = index/secondary key
 * 00100000 = foreign key
 * 00010000 = fulltext
 * 00001000 = invisible
 * 00000100 = unique
 * 00000010 = unsigned
 * 00000001 = auto_increment
 * 000000001 = allowNull
 * 0000000000000000   total number of bits = 16
 */
public class BuildingHints {
  public final short flags;

  protected BuildingHints(int flags) {
    this.flags = (short) flags;
  }

  public static BuildingHints make(int i) {
    return new BuildingHints(i);
  }

}
