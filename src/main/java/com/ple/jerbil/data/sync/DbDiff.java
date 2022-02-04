package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.util.IMap;

public class DbDiff implements Diff {

  private final IMap<DbProps, Object> left;
  private final IMap<DbProps, Object> right;
  private final IMap<DbProps, Object> both;

  protected DbDiff(IMap<DbProps, Object> left, IMap<DbProps, Object> right, IMap<DbProps, Object> both) {
    this.left = left;
    this.right = right;
    this.both = both;
  }

  public static DbDiff make(IMap<DbProps, Object> left, IMap<DbProps, Object> right, IMap<DbProps, Object> both) {
    return new DbDiff(left, right, both);
  }

  public DbDiff filter(DdlOption ddlOption) {
    if (ddlOption.equals(0b001)) {
      return null;
    } else if (ddlOption.equals(0b010)) {
      return null;
    } else if (ddlOption.equals(0b100)) {
      return null;
    }
    return this;
  }

  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this);
  }

  @Override
  public IMap<DbProps, Object> left() {
    return left;
  }

  @Override
  public IMap<DbProps, Object> right() {
    return right;
  }

  @Override
  public IMap<DbProps, Object> both() {
    return both;
  }

  @Override
  public String toString() {
    return "DbDiff{" +
      "left=" + left +
      ", right=" + right +
      ", both=" + both +
      '}';
  }

}
