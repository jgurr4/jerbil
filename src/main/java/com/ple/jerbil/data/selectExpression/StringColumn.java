package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.ColumnProps;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

public class StringColumn extends Column<StringColumn> implements StringExpression {

  protected StringColumn(String columnName, Table table, DataSpec dataSpec, @Nullable StringExpression defaultValue,
                         ColumnProps props) {
    super(columnName, table, dataSpec, defaultValue, props);
  }

  @Override
  public StringColumn make(String columnName, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) defaultValue, props);
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression defaultValue) {
    return new StringColumn(columnName, table, dataSpec, defaultValue, ColumnProps.empty);
  }

  public static StringColumn make(String columnName, Table table, int size, StringExpression defaultValue,
                                  ColumnProps props) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), defaultValue, props);
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression defaultValue,
                                  ColumnProps props) {
    if (defaultValue == null && props.isAllowNull()) {
      defaultValue = LiteralNull.instance;
    }
    return new StringColumn(columnName, table, dataSpec, defaultValue, props);
  }

  public static StringColumn make(String columnName, Table table, int size, ColumnProps props) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, props);
  }

  public static StringColumn make(String columnName, Table table, int size) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, ColumnProps.empty);
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, null, ColumnProps.empty);
  }

  public Equals eq(StringExpression value) {
    return Equals.make(this, value);
  }

  public Equals eq(String value) {
    return Equals.make(this, Literal.make(value));
  }

  public GreaterThan isGreaterThan(Expression value) {
    return GreaterThan.make(this, value);
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public String toString() {
    return "StringColumn{" +
        "columnName='" + columnName + '\'' +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", props=" + props +
        ", table=" + table +
        '}';
  }
}
