package com.ple.jerbil;

import com.ple.jerbil.data.DatabaseBuilder;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

public class sqlLanguageGeneratorTests {

  final TestDatabase testDb = DatabaseBuilder.generate(TestDatabase.class);
  final UserTable user = testDb.user;
  final ItemTable item = testDb.item;
  final PlayerTable player = testDb.player;
  final InventoryTable inventory = testDb.inventory;

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
