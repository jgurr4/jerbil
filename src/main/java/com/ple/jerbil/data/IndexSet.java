package com.ple.jerbil.data;

public class IndexSet {
  public final byte indexes;
  private static final byte primary = 1 << 4;
  private static final byte secondary = 1 << 3;
  private static final byte foreign = 1 << 2;
  private static final byte fulltext = 1 << 1;
  private static final byte spatial = 1;
  protected IndexSet(int indexes) {
    this.indexes = (byte) indexes;
  }

  public static IndexSet make(int indexes) {
    return new IndexSet(indexes);
  }

}
