package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.builder.DatabaseBuilder;
import com.ple.jerbil.data.JavaCodeGenerator;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.util.Immutable;

@Immutable
public class Generator {
  // This is the generator which the user must define. Rebul will call this main method and pass in the target where the
  // DBO classes will be generated in as well as the package name.
  // this main method calls JavaCodeGenerator("../pojoClasses", DbBuilder, package to generate this inside)
  public static void main(String[] args) {
    final DatabaseBuilder db = DatabaseBuilder.make("test");
    db.newTable("order")
        .newColumn("orderId").asBigInt().ai().unsigned()
        .newColumn("add").asVarchar().defaultValue(Literal.make("barter")).unique()
        .newColumn("phrase").asText().fullText().allowNull()
        .newColumn("userId").asIntUnsigned()
        .newColumn("itemId").asInt(10).unsigned()
        .newColumn("scale").asMediumIntUnsigned()
        .newColumn("quantity").asSmallInt().unsigned()
        .newColumn("price").asDecimal(14, 2)
        .newColumn("total").asDecimal(14, 2)  //.generatedFrom(quantity.times(price))
        .newColumn("finalized").asBoolean()
        .newColumn("myDouble").asDouble()
        .newColumn("myFloat").asFloat()
        .newColumn("mySet").asSet(ItemType.class).defaultValue(ItemType.weapon)
        .newColumn("saleDate").asDate()
        .newColumn("saleTime").asTime()
        .newColumn("saleDateTime").asDateTime()
        .newColumn("myInvis").asInt().invisible();
        //.newColumn("myChar").asChar()
    db.newTable("user")
        .newColumn("userId").bigId()
        .newColumn("name").asVarchar().indexed()
        .newColumn("age").asInt();
    db.newTable("inventory")
        .newColumn("playerId").asInt().primary()
        .newColumn("itemId").asInt().primary();
    db.newTable("item")
        .newColumn("itemId").id()
        .newColumn("name").asVarchar(20).indexed()
        .newColumn("type").asEnum(ItemType.class)
        .newColumn("price").asInt();
    db.newTable("player")
        .newColumn("playerId").asInt().ai()
        .newColumn("userId").asInt()
        .newColumn("name").asVarchar(20);
    JavaCodeGenerator.generate(db, "../DBOClasses", "ORM");

//    final IMap<String, Index> indexes = IArrayMap.make("usrd_itmd_idx",
//        Index.make(IndexType.secondary, "usrd_itmd_idx", orderTable, userId, itemId));

//    return new OrderTableContainer(orderTable, columns, orderId, add, phrase, userId,
//        itemId, scale, quantity, price, total, finalized, myDouble, myFloat, mySet, saleDate, saleTime, saleDateTime,
//        myInvis, indexes, null);
  }

}
