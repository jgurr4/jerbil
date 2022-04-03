package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.EnumeralColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayMap;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class ItemTableContainer extends TableContainer {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final EnumeralColumn type;
  public final NumericColumn price;
  public final String tableName;

  protected ItemTableContainer(Table table, IMap<String, Column> columns, NumericColumn itemId,
                               StringColumn name, EnumeralColumn type, NumericColumn price,
                               @Nullable IMap<String, Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
    super(table, columns, null, indexes, autoIncrementColumn);
    this.itemId = itemId;
    this.name = name;
    this.type = type;
    this.price = price;
    this.tableName = table.tableName;
  }

  public static ItemTableContainer make(Database db) {
    final Table itemTable = Table.make("item", db);
    final NumericColumn itemId = NumericColumn.make("itemId", itemTable, DataSpec.make(DataType.integer), ColumnProps.empty.autoInc().unsigned());
    final StringColumn name = StringColumn.make("name", itemTable, DataSpec.make(DataType.varchar, 20));
    final EnumeralColumn type = EnumeralColumn.make("type", itemTable, DataSpec.make(DataType.enumeration, ItemType.class));
    final NumericColumn price = NumericColumn.make("price", itemTable, DataSpec.make(DataType.integer));
    final IMap<String, Index> indexSpecs = IArrayMap.make("primary", Index.make(IndexType.primary, "primary", itemTable, itemId),
        "nm_idx", Index.make(IndexType.secondary, "nm_idx", itemTable, name));
    final IMap<String, Column> columns = IArrayMap.make(
        itemId.columnName, itemId, name.columnName, name, type.columnName, type, price.columnName, price);
    return new ItemTableContainer(itemTable, columns, itemId, name, type, price, indexSpecs, itemId);
  }

}
