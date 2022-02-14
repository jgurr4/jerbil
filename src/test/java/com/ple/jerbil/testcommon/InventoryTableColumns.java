package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class InventoryTableColumns {
  public final NumericColumn playerId;
  public final NumericColumn itemId;
  public final IList<Column> columns;
  public InventoryTableColumns(Table table) {
     playerId = Column.make("playerId", table).asInt().primary();
     itemId = Column.make("itemId", table).asInt().primary();
     columns = IArrayList.make(playerId, itemId);
  }
}
