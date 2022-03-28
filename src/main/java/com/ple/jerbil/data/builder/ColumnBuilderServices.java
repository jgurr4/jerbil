package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.selectExpression.Expression;

public interface ColumnBuilderServices {
  public ColumnBuilder indexed();

  public ColumnBuilder primary();

  public ColumnBuilder unique();

  public ColumnBuilder invisible();

  public ColumnBuilder allowNull();

  public ColumnBuilder defaultValue(Expression e);

  public ColumnBuilder defaultValue(Enum<?> value);

}
