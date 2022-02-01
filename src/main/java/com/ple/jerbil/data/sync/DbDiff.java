package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;

public class DbDiff {

  public DbDiff filter(DdlOption ddlOption) {
    return null;
  }

  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this);
  }

}
