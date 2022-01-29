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

  public ItemTableColumns(Table table) {

    itemId = Column.make("itemId").id();
    name = Column.make("name").asVarchar(20).indexed();
    type = Column.make("type").asEnum(ItemType.class);
    price = Column.make("price").asInt();
    table.add(itemId, name, type, price);
  }
}
