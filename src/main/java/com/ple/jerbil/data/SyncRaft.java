package com.ple.jerbil.data;
import com.ple.jerbil.data.sync.DbDiff;
import reactor.util.annotation.Nullable;

public class SyncRaft {
  @Nullable public final DbDiff filteredDiff;
  public final DbDiff dbDiff;
  @Nullable public final String sql;

  protected SyncRaft(DbDiff filteredDiff, DbDiff dbDiff, String sql) {
    this.filteredDiff = filteredDiff;
    this.dbDiff = dbDiff;
    this.sql = sql;
  }

  public static SyncRaft make(DbDiff filteredDiff, DbDiff dbDiff) {
    return new SyncRaft(filteredDiff, dbDiff, null);
  }

  public static SyncRaft make(String sql, DbDiff dbDiff) {
    return new SyncRaft(null, dbDiff, sql);
  }
}
