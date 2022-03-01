package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
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
                                DateColumn saleDate, DateColumn saleTime, DateColumn saleDateTime, NumericColumn myInvis,
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
    final NumericColumn orderId = Column.make("orderId", orderTable).asBigInt().ai().unsigned();
    final StringColumn add = Column.make("add", orderTable).asVarchar().defaultValue(Literal.make("barter")).unique();
    final StringColumn phrase = Column.make("phrase", orderTable).asText().fullText().allowNull();
    final NumericColumn userId = Column.make("userId", orderTable).asIntUnsigned();
    final NumericColumn itemId = Column.make("itemId", orderTable).asInt(10).unsigned();
    final NumericColumn scale = Column.make("scale", orderTable).asMediumIntUnsigned();
    final NumericColumn quantity = Column.make("quantity", orderTable).asSmallInt().unsigned();
    final NumericColumn price = Column.make("price", orderTable).asDecimal(14, 2);
    final NumericColumn total = Column.make("total", orderTable).asDecimal(14, 2).generatedFrom(quantity.times(price));
    final BooleanColumn finalized = Column.make("finalized", orderTable).asBoolean();
    final NumericColumn myDouble = Column.make("myDouble", orderTable).asDouble();
    final NumericColumn myFloat = Column.make("myFloat", orderTable).asFloat();
    final EnumeralColumn mySet = Column.make("mySet", orderTable).asSet(ItemType.class).defaultValue(ItemType.weapon);
    final DateColumn saleDate = Column.make("saleDate", orderTable).asDate();
    final DateColumn saleTime = Column.make("saleTime", orderTable).asTime();
    final DateColumn saleDateTime = Column.make("saleDateTime", orderTable).asDateTime()
        .defaultValue(LiteralDate.currentTimestamp).onUpdate(LiteralDate.currentTimestamp);
    final NumericColumn myInvis = Column.make("myInvis", orderTable).asInt().invisible();
//    final StringColumn myChar = Column.make("myChar", orderTable).asChar();
    final IMap<String, Column> columns = IArrayMap.make(orderId.columnName, orderId, add.columnName, add,
        phrase.columnName, phrase, userId.columnName, userId, itemId.columnName, itemId, scale.columnName, scale, quantity.columnName, quantity,
        price.columnName, price, total.columnName, total, finalized.columnName, finalized, myDouble.columnName, myDouble,
        myFloat.columnName, myFloat, mySet.columnName, mySet, saleDate.columnName, saleDate, saleTime.columnName,
        saleTime, saleDateTime.columnName, saleDateTime, myInvis.columnName, myInvis);
    final IMap<String, Index> indexes = IArrayMap.make("usr_itm_idx", Index.make(IndexType.secondary, userId, itemId));
    return new OrderTableContainer(orderTable, columns, orderId, add, phrase, userId,
        itemId, scale, quantity, price, total, finalized, myDouble, myFloat, mySet, saleDate, saleTime, saleDateTime,
        myInvis, indexes, null);
  }

}
