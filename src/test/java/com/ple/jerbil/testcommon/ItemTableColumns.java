package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

public class ItemTableColumns {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;

  public ItemTableColumns(NumericColumn itemId, StringColumn name, StringColumn type, NumericColumn price) {
    this.itemId = itemId;
    this.name = name;
    this.type = type;
    this.price = price;
  }
}
