package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
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

  public static DbDiff compare(Database database, Database existingDb) {
//    DataGlobal.bridge.execute()
    IMap<DbProps, Object> create = IHashMap.empty;
    IMap<DbProps, Object> delete = IHashMap.empty;
    IMap<DbProps, Object> update = IHashMap.empty;
    return DbDiff.make(create, delete, update);
  }

}
