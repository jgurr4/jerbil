package com.ple.jerbil.data.sync;

import com.ple.util.IMap;

public interface Diff {
  public IMap<String, Object> left();
  public IMap<String, Object> right();
  public IMap<String, Object> both();
}
