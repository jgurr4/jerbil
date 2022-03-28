package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.EnumeralColumn;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.StringExpression;

public class EnumeralColumnBuilder extends ColumnBuilder{
  private EnumeralColumn column;

  protected EnumeralColumnBuilder(DatabaseBuilder dbBuild, String columnName, Table table, EnumeralColumn column) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
  }

  public EnumeralColumnBuilder defaultValue(Enum<?> e) {
    return null;
  }

  @Override
  public EnumeralColumn indexed() {
    return null;
  }

  @Override
  public EnumeralColumn primary() {
    return null;
  }

  @Override
  public EnumeralColumn unique() {
    return null;
  }

  @Override
  public EnumeralColumn invisible() {
    return null;
  }

  @Override
  public EnumeralColumn allowNull() {
    return null;
  }

  @Override
  public EnumeralColumn defaultValue(Expression e) {
    return new EnumeralColumn(columnName, table, dataSpec, (StringExpression) e, hints);
  }

  @Override
  public EnumeralColumn defaultValue(Enum<?> value) {
    return new EnumeralColumn(columnName, table, dataSpec, Literal.make(value.name()), hints);
  }

}
