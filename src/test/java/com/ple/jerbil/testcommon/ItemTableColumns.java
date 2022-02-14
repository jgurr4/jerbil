package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.Immutable;

@Immutable
public class ItemTableColumns {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;
  public final IList<Column> columns;

  public ItemTableColumns(Table table) {
    itemId = Column.make("itemId", table).id();
    name = Column.make("name", table).asVarchar(20).indexed();
    type = Column.make("type", table).asEnum(ItemType.class);
    price = Column.make("price", table).asInt();
    columns = IArrayList.make(itemId, name, type, price);
  }
}
