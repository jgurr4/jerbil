package com.ple.jerbil.data;

import com.ple.jerbil.data.GenericInterfaces.Immutable;

import java.util.Objects;

/**
 * BuildingHints allows users to skip manually defining IndexSpec for each index of their tables.
 * The DatabaseBuilder will review the flags of each column and generate the IndexSpecs based on that.
 * <p>
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
 * 0000000001 = autoUpdateTime
 * 0000000000000000   total number of bits = 16
 */
@Immutable
public class BuildingHints {
  public final short flags;
  private static final short primary = 1 << 14;
  private static final short secondary = 1 << 13;
  private static final short foreign = 1 << 12;
  private static final short fulltext = 1 << 11;
  private static final short invisible = 1 << 10;
  private static final short unique = 1 << 9;
  private static final short unsigned = 1 << 8;
  private static final short autoInc = 1 << 7;
  private static final short allowNull = 1 << 6;
  private static final short autoUpdateTime = 1 << 5;

  protected BuildingHints(int flags) {
    this.flags = (short) flags;
  }

  public static BuildingHints make(int i) {
    return new BuildingHints(i);
  }

  public static BuildingHints make() {
    return new BuildingHints(0b0);
  }

  public BuildingHints primary() {
    return new BuildingHints(primary | flags);
  }

  public BuildingHints index() {
    return new BuildingHints(secondary | flags);
  }

  public BuildingHints foreign() {
    return new BuildingHints(foreign | flags);
  }

  public BuildingHints fulltext() {
    return new BuildingHints(fulltext | flags);
  }

  public BuildingHints invisible() {
    return new BuildingHints(invisible | flags);
  }

  public BuildingHints unique() {
    return new BuildingHints(unique | flags);
  }

  public BuildingHints unsigned() {
    return new BuildingHints(unsigned | flags);
  }

  public BuildingHints autoInc() {
    return new BuildingHints(autoInc | flags);
  }

  public BuildingHints allowNull() {
    return new BuildingHints(allowNull | flags);
  }

  public BuildingHints autoUpdateTime() {
    return new BuildingHints(autoUpdateTime | flags);
  }

  public boolean isPrimary() {
    return (flags & primary) > 0;
  }

  public boolean isIndexed() {
    return (flags & secondary) > 0;
  }

  public boolean isForeign() {
    return (flags & foreign) > 0;
  }

  public boolean isFulltext() {
    return (flags & fulltext) > 0;
  }

  public boolean isInvisible() {
    return (flags & invisible) > 0;
  }

  public boolean isUnique() {
    return (flags & unique) > 0;
  }

  public boolean isUnsigned() {
    return (flags & unsigned) > 0;
  }

  public boolean isAutoInc() {
    return (flags & autoInc) > 0;
  }

  public boolean isAllowNull() {
    return (flags & allowNull) > 0;
  }

  public boolean isAutoUpdateTime() {
    return (flags & autoUpdateTime) > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BuildingHints)) return false;
    BuildingHints that = (BuildingHints) o;
    return flags == that.flags;
  }

  @Override
  public int hashCode() {
    return Objects.hash(flags);
  }

  @Override
  public String toString() {
    return "BuildingHints{" +
        "flags=" + flags +
        '}';
  }
}
