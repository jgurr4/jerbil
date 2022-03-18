package com.ple.jerbil.data;

import com.ple.util.Nullable;

import java.util.Optional;

public enum DataType {
  character("char", Optional.of(255)),
  varchar("varchar", Optional.of(255)),
  enumeration("enum", Optional.empty()),
  set("set", Optional.empty()),
  text("text", Optional.empty()),
  bigint("bigint", Optional.of(20)),
  mediumint("mediumint", Optional.of(8)),
  integer("int", Optional.of(11)),
  smallint("smallint", Optional.of(5)),
  tinyint("tinyint", Optional.of(4)),
  decimal("decimal", Optional.empty()),
  aDouble("double", Optional.empty()),
  aFloat("float", Optional.empty()),
  date("date", Optional.empty()),
  time("time", Optional.empty()),
  datetime("datetime", Optional.empty()),
  timestamp("timestamp", Optional.empty()),
  bool("boolean", Optional.empty()),
  ;

  public final String sqlName;
  @Nullable public final Optional<Integer> defaultSize;

  DataType(String sqlName, Optional<Integer> defaultSize) {
    this.sqlName = sqlName;
    this.defaultSize = defaultSize;
  }
}
