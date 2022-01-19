package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

@Immutable
public class ItemTableColumns {

  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;
//  public final NumericColumn add;

  public ItemTableColumns(Table table) {

    itemId = Column.make("itemId").id();
    name = Column.make("name").asVarchar(20).indexed();
    type = Column.make("type").asEnum(ItemType.class);
    price = Column.make("price").asInt();
//    add = Column.make("add", table).asInt(); // Tests if our translator checks reserved words correctly and puts backticks around it.
    table.add(itemId, name, type, price);
  }
  //  example of using .def() to set default value for column. Also how to use decimal or other numeric data types with precision and specific options like unsigned or zerofill.
//  public final Column price = Column.make("price", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).decimal(9, 2, unsigned, zerofill).def(1.00);
  // I also want .ai() for auto_increment, .fulltext() for fulltext index, .set(), .bigInt(), .mediumInt(), .smallInt() etc...
}