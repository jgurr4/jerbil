package com.ple.jerbil.data;

import com.ple.util.Immutable;

import java.util.Objects;

/**
 * Database is a object representing the database and it's tables.
 */
@Immutable
public class Database {
  public final String databaseName;

  protected Database(String databaseName) {
    this.databaseName = databaseName;
  }

  public static Database make(String name) {
    return new Database(name);
  }

  @Override
  public String toString() {
    return "Database{" +
        "databaseName='" + databaseName + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Database)) return false;
    Database database = (Database) o;
    return databaseName.equals(database.databaseName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseName);
  }
}
