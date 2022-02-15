package com.ple.jerbil.data;

public class DatabaseBuilder {

  // This method would take in a class called TestDbTables and output a TestDatabase object.
  // The TestDatabase object should contain the TestDbTables list and the list of TableContainers.
  // The TestDbTables list would contain the Columns. So you could use it like so:
  // testDb.tables.player.name  (This selects the name column from player table.)
  // This makes it a basic convenience object.
  // Perhaps the resulting object actually is a DatabaseContainer instead of just a Database.
  // Which would mean it has a list of Table Containers inside it.
  public static <T1 extends DatabaseContainer, T2> T1 generate(Class<T2> testDbTables) {
    //This method needs to generate the code for creating the TestTable class.

    return null;
  }

}
