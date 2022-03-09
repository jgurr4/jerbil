package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;
import com.ple.jerbil.data.GenericInterfaces.SynchronousObject;
import com.ple.jerbil.data.translator.MysqlLanguageGenerator;
import io.r2dbc.spi.Result;

public class VoidBridge implements DataBridge {


  @Override
  public LanguageGenerator getGenerator() {
    return MysqlLanguageGenerator.only;
  }

  @Override
  public ReactiveWrapper<Result> execute(String toSql) {
    return SynchronousObject.make()
  }

  @Override
  public ReactiveWrapper<Result> execute(ReactiveWrapper<String> toSql) {
  }

  @Override
  public ReactiveWrapper<DatabaseContainer> getDb(String name) {
  }

  @Override
  public <T extends DbRecord, I extends DbRecordId> I save(T record) {
  }

}
