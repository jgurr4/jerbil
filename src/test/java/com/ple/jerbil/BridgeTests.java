package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Failable;
import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.sync.*;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public BridgeTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
        props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password")
    );
  }

  @Test
  void testCompareSameDatabase() {
    final DbDiff diffs = DiffService.compareDatabases(testDb.wrap(), testDb.wrap()).unwrap();
    assertNull(diffs.databaseName);
    assertNull(diffs.tables);
    assertEquals(0, diffs.getTotalDiffs());
    assertNull(diffs.toSql());
  }

  @Test
  void testCompareDifferentDatabase() {
    final Column extra = Column.make("extra", order.table).asChar(13);
    final Column modified = Column.make("phrase", order.table).asVarchar();
    DatabaseContainer testDb2 = testDb;
    TableContainer newOrder = testDb2.tables.get("order").add(extra, modified).remove(order.add);
    newOrder = newOrder.add(extra);
    testDb2 = testDb2.add(newOrder);
    final DbDiff diffs = DiffService.compareDatabases(testDb.wrap(), testDb2.wrap()).unwrap();
    if (diffs.tables != null) {
      assertNull(diffs.tables.create);
      assertNull(diffs.tables.delete);
      assertNotNull(diffs.tables.update);
      assertEquals(order.add, diffs.tables.update.get(0).columns.create.get(0));
      assertEquals(extra, diffs.tables.update.get(0).columns.delete.get(0));
      if (diffs.tables.update.get(0).columns.update != null) {
        assertEquals(BuildingHints.make().allowNull().fulltext(), diffs.tables.update.get(0).columns.update.get(0).buildingHints.before);
        assertEquals(BuildingHints.make(), diffs.tables.update.get(0).columns.update.get(0).buildingHints.after);
        assertEquals(DataType.text, diffs.tables.update.get(0).columns.update.get(0).dataSpec.before.dataType);
        assertEquals(DataType.varchar, diffs.tables.update.get(0).columns.update.get(0).dataSpec.after.dataType);
      }
    }
  }

  @Test
  void testDbDiffFilter() {
    final Table alteredItem = Table.make("item", testDb.database);
    final NumericColumn price = Column.make("price", alteredItem).asInt();
    final IMap<String, Column> alteredItemColumns = IArrayMap.make(item.itemId, item.name, item.type, price);
    //FIXME: Find out how this altered table can be added to the database. Perhaps use DatabaseContainer.
    final Table extraTable = Table.make("extra", testDb.database);
    final IMap<String, Column> extraTableColumns = IArrayMap.make(Column.make("id", extraTable).bigId());
    //FIXME: Find out how this altered table can be added to the database. Perhaps use DatabaseContainer.
    final TableContainer alteredItemTableContainer = TableContainer.make(alteredItem, alteredItemColumns, StorageEngine.transactional, null, null);
    final TableContainer extraTableContainer = TableContainer.make(extraTable, extraTableColumns);
    final DbDiff diff = DiffService.compareDatabases(testDb.wrap(), DatabaseContainer.make(Database.make("myDb"),
        IArrayMap.make(user, player, alteredItemTableContainer, extraTableContainer)
    ).wrap()).unwrap();
//    diff.filter(DdlOption.make(0b010));
    //inventory needs create, alteredItem needs update, extraTable needs delete.
//    assertEquals(1, diff.filter(DdlOption.make().create()).tables.create.length());
//    assertEquals(0, diff.filter(DdlOption.make().create()).tables.delete.length());
//    assertEquals(0, diff.filter(DdlOption.make().create()).tables.update.length());
//    assertEquals(0, diff.filter(DdlOption.make().delete()).tables.create.length());
//    assertEquals(1, diff.filter(DdlOption.make().delete()).tables.delete.length());
//    assertEquals(0, diff.filter(DdlOption.make().delete()).tables.update.length());
//    assertEquals(0, diff.filter(DdlOption.make().update()).tables.create.length());
//    assertEquals(0, diff.filter(DdlOption.make().update()).tables.delete.length());
//    assertEquals(1, diff.filter(DdlOption.make().update()).tables.update.length());
    // How to access table properties, column properties and columnDiffs:
    // diff.filter().tables.create.get(0).engine;
    // diff.filter().tables.update.get(0).create.get(0).dataSpec;
    // diff.filter().tables.update.get(0).update.get(0). ;
  }

  @Test
  void testCompareTables() {
//    DiffService.compareTables();
  }

  @Test
  void testCompareIndexes() {
    final Index leftIdx = inventory.indexes.get("primary");
    final Index rightIdx = item.indexes.get("primary");
    final IndexDiff indexDiff = DiffService.compareIndexes(leftIdx, rightIdx);
    assertNull(indexDiff.indexName);
    assertEquals(IArrayList.make(inventory.playerId), indexDiff.indexedColumns.create);
    assertNull(indexDiff.indexedColumns.delete);
    assertEquals(inventory.table.tableName, indexDiff.indexedColumns.update.get(0).column.before.table);
    assertEquals(item.table.tableName, indexDiff.indexedColumns.update.get(0).column.after.table);
  }

  @Test
  void testCompareColumns() {
//    DiffService.compareColumns();
  }

  @Test
  void testCompareListOfTables() {
//    DiffService.compareListOfTables();
  }

  @Test
  void testCompareColumnLists() {
//    DiffService.compareColumnLists();
//    DiffService.filterOutMatchingTables();
  }

  @Test
  void testGetExtraTables() {
//    DiffService.getExtraTables();
  }

  @Test
  void testGetMissingTables() {
//    DiffService.getMissingTables();
  }

  @Test
  void testGetMatchingTables() {
//    DiffService.getMatchingTables();
  }

  @Test
  void testGetTableMatchingName() {
//    DiffService.getTableMatchingName();
  }

  @Test
  void testGetListOfTableDiffs() {
//    DiffService.getListOfTableDiffs();
  }

  @Test
  void testGetExtraColumns() {
//    DiffService.getExtraColumns();
  }

  @Test
  void testGetMissingColumns() {
//    DiffService.getMissingColumns();
  }

  @Test
  void testGetMatchingColumns() {
//    DiffService.getMatchingColumns();
  }

  @Test
  void testGetListOfColumnDiffs() {
//    DiffService.getListOfColumnDiffs();
  }

  @Test
  void testGetExtraIndexes() {
  }
  @Test
  void testGetMissingIndexes() {
  }
  @Test
  void testGetMatchingIndexes() {
  }
  @Test
  void testGetListOfIndexDiffs() {
  }

  @Test
  void testCompareMissingTable() {
    final DbDiff diffs = DiffService.compareDatabases(
            testDb.wrap(), DatabaseContainer.make(Database.make("myDb"), IArrayMap.make(user, player, item)).wrap())
        .unwrap();
    assertEquals(IArrayList.make("inventory"), diffs.tables.create);
    assertNull(diffs.tables.delete);
    assertNull(diffs.tables.update);
    System.out.println(diffs.tables.create);
  }

}
