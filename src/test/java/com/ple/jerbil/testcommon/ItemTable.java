package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class ItemTable extends Table {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;
  public final ItemTableColumns columns;

  public ItemTable(Database db) {
    super("item", db);
    itemId = Column.make("itemId", this).id();
    name = Column.make("name", this).asVarchar(20).indexed();
    type = Column.make("type", this).asEnum(ItemType.class);
    price = Column.make("price", this).asInt();
    columns = new ItemTableColumns(itemId, name, type, price);
  }
}
