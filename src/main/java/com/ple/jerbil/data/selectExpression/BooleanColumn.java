package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.False;
import com.ple.jerbil.data.selectExpression.booleanExpression.True;
import org.jetbrains.annotations.Nullable;

/**
 * BooleanColumn is for compile-time checking to ensure people use a BooleanColumn in the
 * rare cases where a booleanExpression is a Boolean Column. For example: Select * from table where isTrue; // isTrue could be a column with boolean values.
 */
public class BooleanColumn extends Column<BooleanColumn> implements BooleanExpression {

  protected BooleanColumn(String columnName, Table table, DataSpec dataSpec, @Nullable BooleanExpression defaultValue,
                          BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  @Override
  public BooleanColumn make(String columnName, DataSpec dataSpec) {
    return new BooleanColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  public static BooleanColumn make(String columnName, Table table, DataSpec dataSpec, BooleanExpression defaultValue,
                                   BuildingHints hints) {
    return new BooleanColumn(columnName, table, dataSpec, defaultValue, hints);
  }

  public static BooleanColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new BooleanColumn(columnName, table, dataSpec, null, hints);
  }

  public static BooleanColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new BooleanColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  public static BooleanColumn make(String columnName, Table table) {
    return new BooleanColumn(columnName, table, DataSpec.make(DataType.bool), null, BuildingHints.make());
  }

  public BooleanExpression eq(LiteralBoolean bool) {
    return Equals.make(this, bool);
  }

  @Override
  public String toString() {
    return "BooleanColumn{" +
        "columnName='" + columnName + '\'' +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", hints=" + hints +
        ", table=" + table +
        '}';
  }

  public BooleanExpression<UnaliasedExpression> isFalse() {
    return False.make(this);
  }

  public BooleanExpression<UnaliasedExpression> isTrue() {
    return True.make(this);
  }
}
