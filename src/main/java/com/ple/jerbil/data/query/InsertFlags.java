package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;

/**
 * InsertFlags has 2 settings:
 * doNotThrowOnDuplicateKey, triggerDeleteWhenReplacing
 *
 * Each combination means a different thing:
 *    Insert = doNotThrowOnDuplicateKey : false;
 *
 *    Insert ignore =  doNotThrowOnDuplicateKey : true;
 *
 *    //Instead of deleting a duplicate it updates it, causing the update trigger to go off.
 *    Replace = mayThrowOnDuplicate : false; triggerDeleteWhenReplacing : false;
 *
 *    //Deletes duplicates and replaces them causing delete trigger to go off.
 *    Insert on Duplicate Key Update(upsert) = doNotThrowOnDuplicateKey : true; triggerDeleteWhenReplacing = true;
 */
@Immutable
public class InsertFlags {
  public final byte flags;

  protected InsertFlags(int flags) {
    this.flags = (byte) flags;
  }

  public static InsertFlags make(int flags) {
    return new InsertFlags(flags);
  }
}
