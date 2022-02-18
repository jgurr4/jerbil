package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.IndexSpec;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;

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
  public final StringColumn mySet;
  public final DateColumn saleDate;
  public final DateColumn saleTime;
  public final DateColumn saleDateTime;

  protected OrderTableContainer(Table table, IMap<String, Column> columns, IList<IndexSpec> indexSpecs,
                                NumericColumn autoIncrementColumn, NumericColumn orderId, StringColumn add,
                                StringColumn phrase, NumericColumn userId, NumericColumn itemId, NumericColumn scale,
                                NumericColumn quantity, NumericColumn price, NumericColumn total,
                                BooleanColumn finalized, NumericColumn myDouble, NumericColumn myFloat, StringColumn mySet,
                                DateColumn saleDate, DateColumn saleTime, DateColumn saleDateTime) {
    super(table, columns, null, indexSpecs, autoIncrementColumn);
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
  }

  public static OrderTableContainer make(Database db) {
    final Table orderTable = Table.make("order", db);
    final NumericColumn orderId = Column.make("orderId", orderTable).asBigInt().unsigned();  //Alternatively just use .bigId() to replace all 3.
    final StringColumn add = Column.make("add", orderTable).asVarchar().defaultValue(Literal.make("barter")).unique();  //Tests unique(), null and defaultValue()
    final StringColumn phrase = Column.make("phrase", orderTable).asText().fullText();
    final NumericColumn userId = Column.make("userId", orderTable).asIntUnsigned();
    final NumericColumn itemId = Column.make("itemId", orderTable).asInt(10);  //Tests specifying the digits amount.
    final NumericColumn scale = Column.make("scale", orderTable).asMediumIntUnsigned();
    final NumericColumn quantity = Column.make("quantity", orderTable).asSmallInt().unsigned();
    final NumericColumn price = Column.make("price", orderTable).asDecimal(14,2);
    final NumericColumn total = Column.make("total", orderTable).asDecimal(14, 2).generatedFrom(quantity.times(price));
    final BooleanColumn finalized = Column.make("finalized", orderTable).asTinyInt(1);
    final NumericColumn myDouble = Column.make("myDouble", orderTable).asDouble();
    final NumericColumn myFloat = Column.make("myFloat", orderTable).asFloat();
    final StringColumn mySet = Column.make("mySet", orderTable).asSet(ItemType.class);
    final DateColumn saleDate = Column.make("saleDate", orderTable).asDate();
    final DateColumn saleTime = Column.make("saleTime", orderTable).asTime();
    final DateColumn saleDateTime = Column.make("saleDateTime", orderTable).asDateTime();
    final IMap<String, Column> columns = IArrayMap.make(orderId.columnName, orderId, add.columnName, add, phrase.columnName,
        phrase, userId.columnName, userId, itemId.columnName, itemId, scale.columnName, scale, total.columnName, total,
        finalized.columnName, finalized, myDouble.columnName, myDouble, myFloat.columnName, myFloat, mySet.columnName,
        mySet, saleDate.columnName, saleDate, saleTime.columnName, saleTime, saleDateTime.columnName, saleDateTime);
    //FIXME: Find a way to do compile-time checking for Fulltext. If not possible at least do checks at runtime.
    final IList<IndexSpec> indexSpecs = IArrayList.make(
        IndexSpec.make(IndexType.primary, orderId),
        IndexSpec.make(IndexType.secondary, itemId),
        IndexSpec.make(IndexType.fulltext, phrase));
    final NumericColumn autoIncrementColumn = orderId;
    return new OrderTableContainer(orderTable, columns, indexSpecs, autoIncrementColumn, orderId, add, phrase, userId,
        itemId, scale, quantity, price, total, finalized, myDouble, myFloat, mySet, saleDate, saleTime, saleDateTime);
  }

  //total decimal(14,2) as (price * quantity);
  //to insert on generated columns without specifying each column name use `default`: insert into experimental values (0, 2.34, 15, default);
  // add support for
}
