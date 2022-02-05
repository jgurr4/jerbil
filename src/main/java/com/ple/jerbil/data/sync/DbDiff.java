package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.util.IMap;

public class DbDiff implements Diff {

  private final IMap<DbProps, Object> create;
  private final IMap<DbProps, Object> delete;
  private final IMap<DbProps, Object> update;

  protected DbDiff(IMap<DbProps, Object> create, IMap<DbProps, Object> delete, IMap<DbProps, Object> update) {
    this.create = create;
    this.delete = delete;
    this.update = update;
  }

  public static DbDiff make(IMap<DbProps, Object> left, IMap<DbProps, Object> right, IMap<DbProps, Object> both) {
    return new DbDiff(left, right, both);
  }

  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this);
  }

  public DbDiff filter(DdlOption ddlOption) {
    return null;
  }

  @Override
  public IMap<DbProps, Object> create() {
    return create;
  }

  @Override
  public IMap<DbProps, Object> delete() {
    return delete;
  }

  @Override
  public IMap<DbProps, Object> update() {
    return update;
  }

  @Override
  public String toString() {
    return "DbDiff{" +
      "left=" + create +
      ", right=" + delete +
      ", both=" + update +
      '}';
  }

}
