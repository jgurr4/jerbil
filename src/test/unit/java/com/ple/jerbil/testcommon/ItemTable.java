package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class ItemTable extends Table {

  public final Column itemId = Column.make("itemId", this).integer().primary();
  public Column name = Column.make("name", this).indexed().varchar(20);  // Made not-final for a test.
  public final Column type = Column.make("type", this).enumOf(ItemType.class);
  public final Column price = Column.make("price", this).integer();
  public final Column add = Column.make("add", this).integer(); // Tests if our translator checks reserved words correctly and puts backticks around it.
//  example of using .def() to set default value for column. Also how to use decimal or other numeric data types with precision and specific options like unsigned or zerofill.
//  public final Column price = Column.make("price", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).decimal(9, 2, unsigned, zerofill).def(1.00);
  // I also want .ai() for auto_increment, .fulltext() for fulltext index, .set(), .bigInt(), .mediumInt(), .smallInt() etc...

  public ItemTable() {
    super("item");
  }

}
