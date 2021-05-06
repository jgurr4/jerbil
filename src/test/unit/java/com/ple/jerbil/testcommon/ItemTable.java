package com.ple.jerbil.testcommon;

import com.ple.jerbil.Column;
import com.ple.jerbil.ItemType;
import com.ple.jerbil.StorageEngine;
import com.ple.jerbil.Table;

public class ItemTable extends Table {

  public final Column itemId = Column.make("itemId").primary();
  public final Column name = Column.make("name").varchar();
  public final Column type = Column.make("type").enumOf(ItemType.class);

  public ItemTable() {
    super("item");
  }

}
