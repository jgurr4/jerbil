package com.ple.jerbil.data;

import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;
import io.r2dbc.spi.Result;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {
  LanguageGenerator getGenerator();

  ReactiveWrapper<Result> execute(String toSql);

  ReactiveWrapper<Result> execute(ReactiveWrapper<String> toSql);

  ReactiveWrapper<DatabaseContainer> getDb(String name);

}
