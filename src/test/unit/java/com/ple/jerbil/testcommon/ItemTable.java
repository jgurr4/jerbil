package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.Table;

public class ItemTable extends Table {

  public final Column itemId = Column.make("itemId").primary();
  public final Column name = Column.make("name").varchar();
  public final Column type = Column.make("type").enumOf(ItemType.class);
  public final Column price = Column.make("price").integer();

  public ItemTable() {
    super("item");
  }

}
