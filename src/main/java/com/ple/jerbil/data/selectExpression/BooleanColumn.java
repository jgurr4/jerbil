package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.False;
import com.ple.jerbil.data.selectExpression.booleanExpression.True;
import org.jetbrains.annotations.Nullable;

/**
 * BooleanColumn is for compile-time checking to ensure people use a BooleanColumn in the
 * rare cases where a booleanExpression is a Boolean Column. For example: Select * from table where isTrue; // isTrue could be a column with boolean values.
 */
public class BooleanColumn extends Column<BooleanColumn> implements BooleanExpression {

  protected BooleanColumn(String columnName, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom,
                          @Nullable BooleanExpression defaultValue, @Nullable BooleanExpression onUpdate,
                          BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }

  @Override
  public BooleanColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new BooleanColumn(columnName, table, dataSpec, generatedFrom, null, null,
        BuildingHints.make(0b0));
  }

  @Override
  public BooleanColumn indexed() {
    return new BooleanColumn(columnName, table, dataSpec, generatedFrom, (BooleanExpression) defaultValue,
        (BooleanExpression) onUpdate, hints.index());
  }

  @Override
  public BooleanColumn primary() {
    return null;
  }

  @Override
  public BooleanColumn unique() {
    return null;
  }

  @Override
  public BooleanColumn invisible() {
    return null;
  }

  @Override
  public BooleanColumn allowNull() {
    return null;
  }

  @Override
  public BooleanColumn defaultValue(Expression e) {
    return null;
  }

  @Override
  public BooleanColumn defaultValue(Enum<?> value) {
    return null;
  }

  @Override
  public BooleanColumn onUpdate(Expression e) {
    return null;
  }

  @Override
  public BooleanColumn onUpdate(Enum<?> value) {
    return null;
  }

  public static BooleanColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new BooleanColumn(columnName, table, dataSpec, null, null, null, hints);
  }

  public static BooleanColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new BooleanColumn(columnName, table, dataSpec, null, null, null, BuildingHints.make(0b0));
  }

  public static BooleanColumn make(String columnName, Table table) {
    return new BooleanColumn(columnName, table, DataSpec.make(DataType.bool), null, null,
        null, BuildingHints.make(0b0));
  }

/*
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }
*/

  //TODO: Decide whether or not to include .eq(make(false)) option for users on BooleanColumn.
  public BooleanExpression eq(LiteralBoolean item) {
    return null;
  }

  public BooleanColumn defaultValue(BooleanExpression bool) {
    return new BooleanColumn(columnName, table, dataSpec, generatedFrom, bool, (BooleanExpression) onUpdate, hints);
  }

  public BooleanExpression<UnaliasedExpression> isFalse() {
    return False.make(this);
  }

  public BooleanExpression<UnaliasedExpression> isTrue() {
    return True.make(this);
  }
}
