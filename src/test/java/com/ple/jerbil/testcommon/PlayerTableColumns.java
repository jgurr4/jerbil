package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class PlayerTableColumns {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;
  public final IList<Column> columns;

  public PlayerTableColumns(Table table) {
    playerId = Column.make("playerId", table).id();
    userId = Column.make("userId", table).asInt();
    name = Column.make("name", table).asVarchar(20);
    columns = IArrayList.make(playerId, userId, name);
  }

}
