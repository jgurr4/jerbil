package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;

/**
 * QueryFlags:
 * 10000000 = select distinct
 * 01000000 = "insert|replace|update|delete ignore" = doNotThrowOnDuplicateKey
 * 00100000 = "replace into" = triggerDeleteWhenReplacing. either inserts, or deletes then inserts. Incompatible with insert ont duplicate key update.
 * 00010000 = "insert on duplicate key update" = triggerUpdateWhenInserting. Either inserts or updates then inserts. Incompatible with replace into.
 * Normal "replace into" statement will trigger delete on duplicate key and then it inserts. However,
 * "insert into ... on duplicate key update" will trigger an "update" instead of a "delete".
 * This is relevant when using triggers for updates, deletes or inserts.
 *
 */
@Immutable
public class QueryFlags {
  public final byte flags;

  protected QueryFlags(int flags) {
    this.flags = (byte) flags;
  }

  public static QueryFlags make(int flags) {
    return new QueryFlags(flags);
  }
}
