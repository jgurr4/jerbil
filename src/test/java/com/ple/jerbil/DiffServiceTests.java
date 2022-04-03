package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.builder.DatabaseBuilderOld;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.jerbil.data.sync.*;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayMap;
import com.ple.util.IMap;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

// This class can contain non-functional tests for the DbContainer sync method with filters and the diffServices methods.
public class DiffServiceTests {

  final TestDatabaseContainer testDb = DatabaseBuilderOld.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public DiffServiceTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
        props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
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
    final StringColumn extra = StringColumn.make("extra", order.table, DataSpec.make(DataType.character, 13));
    final StringColumn modified = StringColumn.make("phrase", order.table, DataSpec.make(DataType.varchar));
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
        assertEquals(BuildingHints.make().allowNull().fulltext(),
            diffs.tables.update.get(0).columns.update.get(0).columnProps.before);
        assertEquals(BuildingHints.make(), diffs.tables.update.get(0).columns.update.get(0).columnProps.after);
        assertEquals(DataType.text, diffs.tables.update.get(0).columns.update.get(0).dataSpec.before.dataType);
        assertEquals(DataType.varchar, diffs.tables.update.get(0).columns.update.get(0).dataSpec.after.dataType);
      }
    }
  }

  @Test
  void testDbDiffFilter() {
    final Table alteredItem = Table.make("item", testDb.database);
    final NumericColumn price = NumericColumn.make("price", alteredItem, DataSpec.make(DataType.integer));
    final IMap<String, Column> alteredItemColumns = IArrayMap.make(item.itemId.columnName, item.itemId,
        item.name.columnName, item.name, item.type.columnName, item.type, price.columnName, price);
    final Table extraTable = Table.make("extra", testDb.database);
    final NumericColumn id = NumericColumn.make("id", extraTable, DataSpec.make(DataType.bigint), ColumnProps.empty.autoInc().unsigned());
    final IMap<String, Column> extraTableColumns = IArrayMap.make(id.columnName, id);
    final String indexName = DatabaseService.generateIndexName(id);
    final IMap<String, Index> indexSpecs = IArrayMap.make(indexName, Index.make(IndexType.primary, indexName, id.table, id));
    final TableContainer alteredItemTableContainer = TableContainer.make(alteredItem, alteredItemColumns,
        StorageEngine.transactional, indexSpecs, id);
    final TableContainer extraTableContainer = TableContainer.make(extraTable, extraTableColumns);
    final DatabaseContainer myDb = DatabaseContainer.make(Database.make("myDb"),
        IArrayMap.make(user.tableName, user, player.tableName, player, inventory.tableName, inventory,
            alteredItemTableContainer.table.tableName, alteredItemTableContainer,
            extraTableContainer.table.tableName, extraTableContainer));
    final DbDiff diff = DiffService.compareDatabases(testDb.wrap(), myDb.wrap()).unwrap();
    assertEquals("test", diff.databaseName.before);
    assertEquals("myDb", diff.databaseName.after);
    assertEquals(1, diff.filter(DdlOption.make().create()).tables.create.size());
    assertNull(diff.filter(DdlOption.make().create()).tables.delete);
    assertNull(diff.filter(DdlOption.make().create()).tables.update);
    assertNull(diff.filter(DdlOption.make().delete()).tables.create);
    assertEquals(1, diff.filter(DdlOption.make().delete()).tables.delete.size());
    assertNull(diff.filter(DdlOption.make().delete()).tables.update);
    assertNull(diff.filter(DdlOption.make().update()).tables.create);
    assertNull(diff.filter(DdlOption.make().update()).tables.delete);
    assertEquals(1, diff.filter(DdlOption.make().update()).tables.update.size());
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
    assertEquals(inventory.playerId, indexDiff.indexedColumns.create.get(0).columnName);
    assertNull(indexDiff.indexedColumns.delete);
    assertEquals(inventory.indexes.get("primary").indexedColumns.get("playerId").columnName,
        indexDiff.indexedColumns.create.get(0).columnName);
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
            testDb.wrap(), DatabaseContainer.make(Database.make("myDb"),
                IArrayMap.make(user.tableName, user, player.tableName, player, item.tableName, item)).wrap())
        .unwrap();
    assertEquals("inventory", diffs.tables.create.get(0).table.tableName);
    assertNull(diffs.tables.delete);
    assertNull(diffs.tables.update);
    System.out.println(diffs.tables.create);
  }

}
