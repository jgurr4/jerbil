package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayMap;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class OrderTableContainer extends TableContainer {
  public final NumericColumn orderId;
  public final StringColumn add;
  public final StringColumn phrase;
  public final NumericColumn userId;
  public final NumericColumn itemId;
  public final NumericColumn scale;
  public final NumericColumn quantity;
  public final NumericColumn price;
  public final NumericColumn total;
  public final BooleanColumn finalized;
  public final NumericColumn myDouble;
  public final NumericColumn myFloat;
  public final EnumeralColumn mySet;
  public final DateColumn saleDate;
  public final DateColumn saleTime;
  public final DateColumn saleDateTime;
  public final NumericColumn myInvis;
  public final String tableName;

  protected OrderTableContainer(Table table, IMap<String, Column> columns, NumericColumn orderId, StringColumn add,
                                StringColumn phrase, NumericColumn userId, NumericColumn itemId, NumericColumn scale,
                                NumericColumn quantity, NumericColumn price, NumericColumn total,
                                BooleanColumn finalized,
                                NumericColumn myDouble, NumericColumn myFloat, EnumeralColumn mySet,
                                DateColumn saleDate, DateColumn saleTime, DateColumn saleDateTime,
                                NumericColumn myInvis,
                                IMap<String, Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
    super(table, columns, null, indexes, autoIncrementColumn);
    this.orderId = orderId;
    this.add = add;
    this.phrase = phrase;
    this.quantity = quantity;
    this.price = price;
    this.total = total;
    this.userId = userId;
    this.itemId = itemId;
    this.finalized = finalized;
    this.scale = scale;
    this.myDouble = myDouble;
    this.myFloat = myFloat;
    this.mySet = mySet;
    this.saleDate = saleDate;
    this.saleTime = saleTime;
    this.saleDateTime = saleDateTime;
    this.myInvis = myInvis;
    this.tableName = table.tableName;
  }

  public static OrderTableContainer make(Database db) {
    final Table orderTable = Table.make("order", db);
    final NumericColumn orderId = NumericColumn.make("orderId", orderTable, DataSpec.make(DataType.bigint),
        BuildingHints.make().unsigned().autoInc());
    final StringColumn add = StringColumn.make("add", orderTable, DataSpec.make(DataType.varchar),
        Literal.make("barter"));
    final StringColumn phrase = StringColumn.make("phrase", orderTable, DataSpec.make(DataType.text), null,
        BuildingHints.empty.allowNull());
    final NumericColumn userId = NumericColumn.make("userId", orderTable, DataSpec.make(DataType.integer),
        BuildingHints.empty.unsigned());
    final NumericColumn itemId = NumericColumn.make("itemId", orderTable, DataSpec.make(DataType.integer, 10),
        BuildingHints.empty.unsigned());
    final NumericColumn scale = NumericColumn.make("scale", orderTable, DataSpec.make(DataType.mediumint),
        BuildingHints.empty.unsigned());
    final NumericColumn quantity = NumericColumn.make("quantity", orderTable, DataSpec.make(DataType.smallint),
        BuildingHints.empty.unsigned());
    final NumericColumn price = NumericColumn.make("price", orderTable, DataSpec.make(DataType.decimal, 14, 2));
    final NumericColumn total = NumericColumn.make("total", orderTable, DataSpec.make(DataType.decimal, 14, 2),
        quantity.times(price));
    final BooleanColumn finalized = BooleanColumn.make("finalized", orderTable, DataSpec.make(DataType.bool));
    final NumericColumn myDouble = NumericColumn.make("myDouble", orderTable, DataSpec.make(DataType.aDouble));
    final NumericColumn myFloat = NumericColumn.make("myFloat", orderTable, DataSpec.make(DataType.aFloat));
    final EnumeralColumn mySet = EnumeralColumn.make("mySet", orderTable, DataSpec.make(DataType.set, ItemType.class),
        ItemType.weapon);
    final DateColumn saleDate = DateColumn.make("saleDate", orderTable, DataSpec.make(DataType.date));
    final DateColumn saleTime = DateColumn.make("saleTime", orderTable, DataSpec.make(DataType.time));
    final DateColumn saleDateTime = DateColumn.make("saleDateTime", orderTable, DataSpec.make(DataType.datetime),
        LiteralDate.currentTimestamp, BuildingHints.empty.autoUpdateTime());
    final NumericColumn myInvis = NumericColumn.make("myInvis", orderTable, DataSpec.make(DataType.integer),
        BuildingHints.empty.invisible());
//    final StringColumn myChar = Column.make("myChar", orderTable, DataSpec.make(DataType.char));
    final IMap<String, Column> columns = IArrayMap.make(orderId.columnName, orderId, add.columnName, add,
        phrase.columnName, phrase, userId.columnName, userId, itemId.columnName, itemId, scale.columnName, scale,
        quantity.columnName, quantity, price.columnName, price, total.columnName, total, finalized.columnName,
        finalized, myDouble.columnName, myDouble, myFloat.columnName, myFloat, mySet.columnName, mySet,
        saleDate.columnName, saleDate, saleTime.columnName, saleTime, saleDateTime.columnName, saleDateTime,
        myInvis.columnName, myInvis);
    final IMap<String, Index> indexes = IArrayMap.make("usrd_itmd_idx",
        Index.make(IndexType.secondary, "usrd_itmd_idx", orderTable, userId, itemId), "primary",
        Index.make(IndexType.primary, "primary", orderTable, orderId),
        "ad_idx", Index.make(IndexType.unique, "ad_idx", orderTable, add), "phrs_idx",
        Index.make(IndexType.fulltext, "phrs_idx", orderTable, phrase));
    return new OrderTableContainer(orderTable, columns, orderId, add, phrase, userId,
        itemId, scale, quantity, price, total, finalized, myDouble, myFloat, mySet, saleDate, saleTime, saleDateTime,
        myInvis, indexes, orderId);
  }

}