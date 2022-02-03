package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.FilteredDiff;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IList;
import com.ple.util.IMap;

public class DbDiff implements Diff {

  private final IMap<String, Object> left;
  private final IMap<String, Object> right;
  private final IMap<String, Object> both;

  protected DbDiff(IMap<String, Object> left, IMap<String, Object> right, IMap<String, Object> both) {
    this.left = left;
    this.right = right;
    this.both = both;
  }

  public FilteredDiff filter(DdlOption ddlOption) {
    if (ddlOption.equals(0b001)) {
      return null;
    } else if (ddlOption.equals(0b010)) {
      return null;
    } else if (ddlOption.equals(0b100)) {
      return null;
    }
    return FilteredDiff.make(this, null, null);
  }

  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this);
  }

  @Override
  public IMap<String, Object> left() {
    return null;
  }

  @Override
  public IMap right() {
    return null;
  }

  @Override
  public IMap both() {
    return null;
  }

}
