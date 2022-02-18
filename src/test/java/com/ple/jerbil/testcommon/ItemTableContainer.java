package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.IndexSpec;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;

@Immutable
public class ItemTableContainer extends TableContainer {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;
  public final String tableName;

  protected ItemTableContainer(Table table, NumericColumn itemId, StringColumn name, StringColumn type,
                               NumericColumn price, IList<IndexSpec> indexSpecs,
                               IList<NumericColumn> autoIncrementColumns) {
    super(table, IArrayMap.make(
        itemId.columnName, itemId, name.columnName, name, type.columnName, type, price.columnName, price), null,
        indexSpecs, autoIncrementColumns);
    this.itemId = itemId;
    this.name = name;
    this.type = type;
    this.price = price;
    this.tableName = table.tableName;
  }

  public static ItemTableContainer make(Database db) {
    final Table itemTable = Table.make("item", db);
    final NumericColumn itemId = Column.make("itemId", itemTable).asInt();
    final StringColumn name = Column.make("name", itemTable).asVarchar(20);
    final StringColumn type = Column.make("type", itemTable).asEnum(ItemType.class);
    final NumericColumn price = Column.make("price", itemTable).asInt();
    final IList<IndexSpec> indexSpecs = IArrayList.make(IndexSpec.make(IndexType.primary, IArrayList.make(itemId)),
        IndexSpec.make(IndexType.secondary, IArrayList.make(name)));
    final IList<NumericColumn> autoIncrementColumns = IArrayList.make(itemId);
    return new ItemTableContainer(itemTable, itemId, name, type, price, indexSpecs, autoIncrementColumns);
  }

}
