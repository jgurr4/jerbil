package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IMap;

import static com.ple.jerbil.data.sync.DbProps.*;

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
    // left: { database : 'test' }
    // right: { }
    // both: { tables : [ 'user', 'orders', ], views : [ 'ordersBetweenFebAndJuly' ], storedProcedures : [ 'updateUser' ], charset : "utf8" }
    IMap<DbProps, Object> left = IHashMap.from(tables, IArrayList.make("inventory"));
    IMap<DbProps, Object> right = IHashMap.from();
    IMap<DbProps, Object> both = IHashMap.from(tables, IArrayList.make("user", "player", "item"));
//    System.out.println(left.get("exists"));
//    System.out.println(left.get("size"));
    return DbDiff.make(left, right, both);
  }

}
