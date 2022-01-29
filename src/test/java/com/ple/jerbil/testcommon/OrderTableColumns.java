package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.BooleanColumn;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.DateColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

@Immutable
public class OrderTableColumns {

  public final NumericColumn orderId;
  public final StringColumn add;
  public final StringColumn phrase;
  public final NumericColumn quantity;
  public final NumericColumn price;
  public final NumericColumn total;
  public final NumericColumn userId;
  public final NumericColumn itemId;
  public final BooleanColumn finalized;
  public final NumericColumn scale;
  public final NumericColumn myDouble;
  public final NumericColumn myFloat;
  public final StringColumn mySet;
  public final DateColumn saleDate;
  public final DateColumn saleTime;
  public final DateColumn saleDateTime;

  public OrderTableColumns(Table table) {

    orderId = Column.make("orderId").asBigInt().primary().ai();  //Alternatively just use .bigId() to replace all 3.
    add = Column.make("add").asVarchar().unique().defaultValue();  //Tests unique(), null and defaultValue()
    phrase = Column.make("phrase").asText().fullText();
    userId = Column.make("userId").asInt().indexed();
    itemId = Column.make("itemId").asInt(10);  //Tests specifying the digits amount.
    scale = Column.make("scale").asMediumInt();
    quantity = Column.make("quantity").asSmallInt();
    price = Column.make("price").asDecimal(14,2);
    total = Column.make("total").asDecimal(14, 2).generatedFrom(quantity.times(price));
    finalized = Column.make("finalized").asTinyInt(1);
    myDouble = Column.make("myDouble").asDouble();
    myFloat = Column.make("myFloat").asFloat();
    mySet = Column.make("mySet").asSet(ItemType.class);
    saleDate = Column.make("saleDate").asDate();
    saleTime = Column.make("saleTime").asTime();
    saleDateTime = Column.make("saleDateTime").asDateTime();

    table.add(orderId, add, phrase, userId, itemId, scale, quantity, price, total, finalized, myDouble, myFloat, mySet, saleDate, saleTime, saleDateTime);
  }
  //total decimal(14,2) as (price * quantity);
  //to insert on generated columns without specifying each column name use `default`: insert into experimental values (0, 2.34, 15, default);
  // add support for
}
