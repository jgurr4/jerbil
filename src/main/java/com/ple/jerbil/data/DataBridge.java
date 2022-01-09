package com.ple.jerbil.data;

import org.mariadb.r2dbc.api.MariadbResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {
  LanguageGenerator getGenerator();

  Flux<MariadbResult> executeQuery(String toSql);

  Mono<Long> executeUpdate(String toSql);

  Mono<MariadbResult> execute(String toSql);

}
