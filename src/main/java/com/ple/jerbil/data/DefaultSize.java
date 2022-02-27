package com.ple.jerbil.data;

public enum DefaultSize {
  varchar(255),
  enumeration(255),
  character(255),
  bigint(20),
  integer(11),
  mediumint(9),
  smallint(5),
  tinyint(4),
  bool(1),
//  decimal(10, 0),
  ;

  private final int size;

  DefaultSize(int i) {
    this.size = i;
  }

  public int getSize() {
    return size;
  }
}
