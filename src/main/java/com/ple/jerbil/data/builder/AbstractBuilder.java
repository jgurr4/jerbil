package com.ple.jerbil.data.builder;

public abstract class AbstractBuilder {

  public abstract TableBuilder newTable(String tableName);

  public abstract PartialColumnBuilder newColumn(String columnName);

}
