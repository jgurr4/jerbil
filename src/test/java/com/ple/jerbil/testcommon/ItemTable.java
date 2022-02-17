package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayMap;

/*
FIXME: Currently you cannot do .add() or .remove() operations on columns inside this table because original Table had
 IList<Column> inside and would add to that.
 Option #1: Make CustomTables extend IArrayMap, and make them hold key-value pairs.
 Benefits: This means users would not have to create the ItemTableColumns class because they could include a entry
 that has an array of key value pairs representing each column, for better protection against column name conflicts with
 table attributes.
 Cons: This would mean it loses access to the Table methods and attributes. But, a workaround might be possible. For example
 we can make a ITableArrayMap class that extends Table and implements IMap. The map itself should be a key name with
 value of column. Any other values that are not columns go directly in as a field of ITableArrayMap. Any methods will
 also be accessible inside the class.
 Here is how it should look in api: testdb.inventory is the ITableArrayMap.
 inventory.itemId   -Retrieve column.
 inventory.tableName -Retrieve name of table (inventory).
 inventory.add(Column) -Add a column to list and arrayMap.
 inventory.remove(Column)  -Remove a column from list and arrayMap.
 inventory.columns   -Retrieve InventoryTableColumns                 NOT: IList<Column>
 inventory.storageEngine -Retrieve attribute of Table.
 There is one main problems with this solution:
 The fact that even if I made customClasses implement arraymap and extend Table, it still wouldn't make those methods available
 inside the userContainer.table field. Because that is a generic table, not a specific CustomTable.

 Option #2: Use Decorator pattern to basically use .add to return a new object which has all the fields of original
 plus some extra ones. I could also use Decorator pattern to create CustomTable object which is made from each table
 and that way it would contain all the methods as well. This seems like the simplest option and most likely to work.
 Here is how it would look in practice for the api:


 Option #3: Add a field or remove it from the root class at runtime (dynamic class changes at runtime). Failure because
 this is impossible to do and breaks the philosophy of statically typed languages like Java.

FIXME:
 I also have another problem with my current setup.
 #1: It seems like customTables breaks the circular dependency rule because each custom table contains a column which
 contains a table. I may have to rework this whole system again.
 I also don't even understand the use of TableContainer anymore because of my current system, which is a sign that
 something is wrong with how I designed it.
 Option #1: Make each CustomTable extend something else besides Table because this is not supposed to be a table, it is
 a convenient list of columns that are accessible for the api. Perhaps it could even extend TableContainer.
 Problems with this solution: A) It would cause me to lose access to the Table methods and attributes. but this might
 be possible to overcome with use of decorator pattern inside of DatabaseBuilder.generate, or by adding a Table as a field
 of this class.
 If it extends TableContainer, It could look like this:
 ItemTable extends TableContainer {
 public final NumericColumn itemId;
 public final NumericColumn playerId;
 protected ItemTable(Database db, IList<Column> columns) {
 super(Table.make("item", db), columns);
 this.
 }
 public static ItemTable make(db) {
 final NumericColumn itemId = Column.make();
 final NumericColumn playerId = Column.make();
  IList<Column> columns = IArrayList.make(itemId, playerId);
  return new ItemTable(db, columns, itemId, playerId);

  How it looks inside api:
  testDb.item.table;   -Access Table from inside ItemTableContainer.
  testDb.item.itemId;  -Retrieve Column from item.
  testDb.item.table.storageEngine -Retrieve attribute of Item Table.
  testDb.charSet;      -Retrieve attribute of Database.
  testDb.item.add(Column)  -Use method from TableContainer to add column to list and use decorator pattern to create a
                            new object of ItemTable which contains a new field representing that column.
  testDb.item.remove(Column)  -Same as above just removing instead of add.
  testDb.item.columns  -Retrieve ItemTableColumns from ItemTableContainer. But what if user didn't make ItemTableColumns Class?
                        Then they would retrieve an IList<Column>

 Which means it would be possible to move the methods into TableContainer from Table and also use the api like normal.
 With one minor caveat, the DatabaseBuilder method would have to use .make() instead of creating a new instance.
 Problems with this solution:
 A) I don't know how to access .make() when CustomTable is a generic object from inside DatabaseBuilder.generate().

*/

@Immutable
public class ItemTable extends TableContainer {
  public final NumericColumn itemId;
  public final StringColumn name;
  public final StringColumn type;
  public final NumericColumn price;
  public final String tableName;

  protected ItemTable(Table table, NumericColumn itemId, StringColumn name, StringColumn type, NumericColumn price) {
    super(table, IArrayMap.make(
        itemId.columnName, itemId, name.columnName, name, type.columnName, type, price.columnName, price), null);
    this.itemId = itemId;
    this.name = name;
    this.type = type;
    this.price = price;
    this.tableName = table.tableName;
  }

  public static ItemTable make(Database db) {
    final Table itemTable = Table.make("item", db);
    final NumericColumn itemId = Column.make("itemId", itemTable).id();
    final StringColumn name = Column.make("name", itemTable).asVarchar(20).indexed();
    final StringColumn type = Column.make("type", itemTable).asEnum(ItemType.class);
    final NumericColumn price = Column.make("price", itemTable).asInt();
    return new ItemTable(itemTable, itemId, name, type, price);
  }

}
