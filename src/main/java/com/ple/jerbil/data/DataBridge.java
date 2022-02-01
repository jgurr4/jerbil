package com.ple.jerbil.data;

import io.r2dbc.spi.Result;
import reactor.core.publisher.Flux;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {
  LanguageGenerator getGenerator();

  Flux<Result> execute(String toSql);

  Result executeSync(String toSql);

}
