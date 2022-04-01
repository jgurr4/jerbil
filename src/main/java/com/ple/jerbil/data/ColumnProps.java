package com.ple.jerbil.data;


import com.ple.util.Immutable;

import java.util.Objects;

/**
 * What each flag means:
 * 10000000 = invisible
 * 01000000 = unsigned
 * 00100000 = auto_increment
 * 00010000 = allowNull
 * 00001000 = autoUpdateTime
 * 00000000   total number of bits = 8
 */
@Immutable
public class ColumnProps {
  public static final ColumnProps empty = ColumnProps.make();
  public final byte flags;
  private static final byte invisible = 1 << 6;
  private static final byte unsigned = 1 << 5;
  private static final byte autoInc = 1 << 4;
  private static final byte allowNull = 1 << 3;
  private static final byte autoUpdateTime = 1 << 2;

  protected ColumnProps(int flags) {
    this.flags = (byte) flags;
  }

  public static ColumnProps make(int i) {
    return new ColumnProps(i);
  }

  public static ColumnProps make() {
    return new ColumnProps(0b0);
  }

  public ColumnProps invisible() {
    return new ColumnProps(invisible | flags);
  }

  public ColumnProps unsigned() {
    return new ColumnProps(unsigned | flags);
  }

  public ColumnProps autoInc() {
    return new ColumnProps(autoInc | flags);
  }

  public ColumnProps allowNull() {
    return new ColumnProps(allowNull | flags);
  }

  public ColumnProps autoUpdateTime() {
    return new ColumnProps(autoUpdateTime | flags);
  }

  public boolean isInvisible() {
    return (flags & invisible) > 0;
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
    if (!(o instanceof ColumnProps)) return false;
    ColumnProps that = (ColumnProps) o;
    return flags == that.flags;
  }

  @Override
  public int hashCode() {
    return Objects.hash(flags);
  }

  @Override
  public String toString() {
    String props = "";
    if (this.isAutoInc()) {
      props += "auto_increment, ";
    }
    if (this.isAutoUpdateTime()) {
      props += "current_timestamp, ";
    }
    if (this.isInvisible()) {
      props += "invisible, ";
    }
    if (this.isUnsigned()) {
      props += "unsigned, ";
    }
    if (this.isAllowNull()) {
      props += "allow null, ";
    }
    return "ColumnProps{" +
        "flags=" + flags + ", " +
        "props=" + props.replaceFirst(", $","") +
        '}';
  }
}
