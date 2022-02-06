package com.ple.jerbil;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class sqlLanguageGeneratorTests {

  final UserTable user = new UserTable();
  final UserTableColumns userColumns = new UserTableColumns(user);
  final PlayerTable player = new PlayerTable();
  final PlayerTableColumns playerColumns = new PlayerTableColumns(player);
  final ItemTable item = new ItemTable();
  final ItemTableColumns itemColumns = new ItemTableColumns(item);
  final InventoryTable inventory = new InventoryTable();
  final InventoryTableColumns inventoryColumns = new InventoryTableColumns(inventory);
  final Database testDb = Database.make("test").add(user, player, item, inventory);

  @Test
  void testFromSql() {

  }

  @Test
  void testGetTableNameFromSql() {

  }

  @Test
  void testGetEngineFromSql() {

  }

  @Test
  void testGetColumnFromString() {

  }

  @Test
  void testCreateColumnsFromTableString() {

  }

  @Test
  void testGetGeneratedFromSql() {

  }

  @Test
  void getDataSpecFromSql() {

  }

  @Test
  void testFormatToColumnLines() {

  }

  @Test
  void testFormatTable() {

  }

  @Test
  void testTransformColumns() {

  }

}