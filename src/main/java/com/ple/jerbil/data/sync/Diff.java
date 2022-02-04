package com.ple.jerbil.data.sync;

import com.ple.util.IMap;

public interface Diff {
  public IMap<DbProps, Object> left();
  public IMap<DbProps, Object> right();
  public IMap<DbProps, Object> both();
}
