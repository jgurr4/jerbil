package com.ple.jerbil.data.query;

import com.ple.util.Immutable;

@Immutable
public class Union {
  public final SelectQuery selectQuery;
  public final UnionType unionType;

  protected Union(SelectQuery selectQuery, UnionType unionType) {
    this.selectQuery = selectQuery;
    this.unionType = unionType;
  }

  public static Union make(SelectQuery selectQuery, UnionType unionType) {
    return new Union(selectQuery, unionType);
  }
}
