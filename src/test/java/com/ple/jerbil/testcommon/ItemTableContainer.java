package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.EnumeralColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
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
    final NumericColumn itemId = Column.make("itemId", itemTable).id();
    final StringColumn name = Column.make("name", itemTable).asVarchar(20).indexed();
    final EnumeralColumn type = Column.make("type", itemTable).asEnum(ItemType.class);
    final NumericColumn price = Column.make("price", itemTable).asInt();
//    final IList<Index> indexSpecs = IArrayList.make(Index.make(IndexType.primary, itemId),
//        Index.make(IndexType.secondary, name));
//    final NumericColumn autoIncrementColumn = itemId;
    final IMap<String, Column> columns = IArrayMap.make(
        itemId.columnName, itemId, name.columnName, name, type.columnName, type, price.columnName, price);
    return new ItemTableContainer(itemTable, columns, itemId, name, type, price, null, null);
  }

}
