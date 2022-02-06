package com.ple.jerbil.data;

import com.ple.jerbil.data.bridge.ReactiveWrapper;
import io.r2dbc.spi.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {
  LanguageGenerator getGenerator();

  Flux<Result> execute(String toSql);

  Flux<Result> execute(Mono<String> toSql);

  ReactiveWrapper<Database> getDb(String name);

}
