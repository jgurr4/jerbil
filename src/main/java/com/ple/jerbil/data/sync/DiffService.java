package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IMap;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static Database getDb(String name) {
    return null;
  }

  public static DbDiff compare(Database database, Database existingDb) {
    IMap<String, Object> map = IHashMap.from("exists", IArrayList.make("auto_increment", "null"), "size", 10, "precision", 2);
    System.out.println(map.get("exists"));
    System.out.println(map.get("size"));
    return null;
  }

}
