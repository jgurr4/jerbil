package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

@Immutable
public class ItemTableColumns {

  public final Column itemId;
  public final Column name;
  public final Column type;
  public final Column price;
  public final Column add;

  public ItemTableColumns(Table table) {

    itemId = Column.make("itemId", table).primary();
    name = Column.make("name", table).indexed().varchar(20);  // Made not-final for a test.
    type = Column.make("type", table).enumOf(ItemType.class);
    price = Column.make("price", table).integer();
    add = Column.make("add", table).integer(); // Tests if our translator checks reserved words correctly and puts backticks around it.
  }
  //  example of using .def() to set default value for column. Also how to use decimal or other numeric data types with precision and specific options like unsigned or zerofill.
//  public final Column price = Column.make("price", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).decimal(9, 2, unsigned, zerofill).def(1.00);
  // I also want .ai() for auto_increment, .fulltext() for fulltext index, .set(), .bigInt(), .mediumInt(), .smallInt() etc...
}
