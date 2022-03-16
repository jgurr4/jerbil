package com.ple.jerbil.testcommon.orm;

import com.ple.jerbil.data.DbRecordId;
import com.ple.util.Immutable;

@Immutable
public class UserId extends DbRecordId {
  public final long id;

  public UserId(long id) {
    this.id = id;
  }

  public static UserId make(long id) {
    return new UserId(id);
  }
}
