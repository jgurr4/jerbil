package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.reactiveUtils.SynchronousObject;
import com.ple.jerbil.data.translator.LanguageGenerator;
import com.ple.jerbil.data.translator.VoidLanguageGenerator;

public class VoidBridge implements DataBridge {

  @Override
  public LanguageGenerator getGenerator() {
    return VoidLanguageGenerator.only;
  }

  @Override
  public ReactiveWrapper<DbResult> execute(String toSql) {
    return SynchronousObject.make(DbResult.empty);
  }

  @Override
  public ReactiveWrapper<DbResult> execute(ReactiveWrapper<String> toSql) {
    return SynchronousObject.make(DbResult.empty);
  }

  @Override
  public ReactiveWrapper<DatabaseContainer> getDb(String name) {
    return SynchronousObject.make(DatabaseContainer.empty);
  }

  @Override
  public <T extends DbRecord, I extends DbRecordId> I save(T record) {
    return (I) DbRecordId.empty;
  }

}
