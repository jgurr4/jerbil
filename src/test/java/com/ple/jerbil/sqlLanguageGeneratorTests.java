package com.ple.jerbil;

import com.ple.jerbil.data.DatabaseBuilder;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

public class sqlLanguageGeneratorTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;

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
