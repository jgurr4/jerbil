package com.ple.jerbil.data;

public class IndexSpec {

  public final Index index;
  public final int size;
  public final IndexSort order;

  protected IndexSpec(Index index, int size, IndexSort order) {
    this.index = index;
    this.size = size;
    this.order = order;
  }

  public static IndexSpec make(Index index, int size, IndexSort order) {
    return new IndexSpec(index, size, order);
  }

  public static IndexSpec make(Index index) {
    return new IndexSpec(index, 0, null);
  }

}
