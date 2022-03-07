package com.ple.jerbil.data.query;

import com.ple.util.Immutable;

/**
 * QueryFlags:
 * 10000000 = select distinct
 * 01000000 = "insert|replace|update|delete ignore" = doNotThrowOnDuplicateKey
 * 00100000 = "replace into" = triggerDeleteWhenReplacing. either inserts, or deletes then inserts. Incompatible with insert ont duplicate key update.
 * 00010000 = "insert on duplicate key update" = triggerUpdateWhenInserting. Either inserts or updates then inserts. Incompatible with replace into.
 * 00001000 = "explain"
 * 00000100 = "analyze"
 * Normal "replace into" statement will trigger delete on duplicate key and then it inserts. However,
 * "insert into ... on duplicate key update" will trigger an "update" instead of a "delete".
 * This is relevant when using triggers for updates, deletes or inserts.
 */
@Immutable
public class QueryFlags {
  public final byte flags;
  private static final byte distinct = 1 << 6;
  private static final byte ignore = 1 << 5;
  private static final byte replace = 1 << 4;
  private static final byte duplicateKeyUpdate = 1 << 3;
  private static final byte explain = 1 << 2;
  private static final byte analyze = 1 << 1;

  protected QueryFlags(int flags) {
    this.flags = (byte) flags;
  }

  public static QueryFlags make(int flags) {
    return new QueryFlags(flags);
  }

  public static QueryFlags make() {
    return new QueryFlags(0b0);
  }

  public QueryFlags distinct() {
    return new QueryFlags(distinct | flags);
  }
  public QueryFlags ignore() {
    return new QueryFlags(ignore | flags);
  }
  public QueryFlags replace() {
    return new QueryFlags(replace | flags);
  }
  public QueryFlags duplicateKeyUpdate() {
    return new QueryFlags(duplicateKeyUpdate | flags);
  }
  public QueryFlags explain() {
    return new QueryFlags(explain | flags);
  }
  public QueryFlags analyze() {
    return new QueryFlags(analyze | flags);
  }

  public boolean isDistinct() {
    return (flags & distinct) > 0;
  }
  public boolean isIgnore() {
    return (flags & ignore) > 0;
  }
  public boolean isReplace() {
    return (flags & replace) > 0;
  }
  public boolean isDuplicateKeyUpdate() {
    return (flags & duplicateKeyUpdate) > 0;
  }
  public boolean isExplain() {
    return (flags & explain) > 0;
  }
  public boolean isAnalyze() {
    return (flags & analyze) > 0;
  }
}
