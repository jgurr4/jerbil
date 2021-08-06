package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class ItemTable extends Table {

  public final Column itemId = Column.make("itemId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).primary();
  public final Column name = Column.make("name", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).varchar();
  public final Column type = Column.make("type", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).enumOf(ItemType.class);
  public final Column price = Column.make("price", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).integer();
//  example of using .def() to set default value for column. Also how to use decimal or other numeric data types with precision and specific options like unsigned or zerofill.
//  public final Column price = Column.make("price", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).decimal(9, 2, unsigned, zerofill).def(1.00);

  public ItemTable() {
    super("item");
  }

}
