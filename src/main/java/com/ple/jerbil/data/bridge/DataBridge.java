package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.translator.LanguageGenerator;
import io.r2dbc.spi.Result;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {
  LanguageGenerator getGenerator();

  <T extends Result> ReactiveWrapper<T> execute(String toSql);

//  <T extends Result> ReactiveWrapper<T> execute(ReactorMono<String> toSql);

  ReactiveWrapper<DatabaseContainer> getDb(String name);

}
