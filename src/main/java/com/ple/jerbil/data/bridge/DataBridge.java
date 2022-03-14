package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.DbRecord;
import com.ple.jerbil.data.DbRecordId;
import com.ple.jerbil.data.DbResult;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.translator.LanguageGenerator;
import io.r2dbc.pool.ConnectionPool;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {

  LanguageGenerator getGenerator();

  ReactiveWrapper<DbResult> execute(String toSql);

  ReactiveWrapper<DatabaseContainer> getDb(String name);

  <T extends DbRecord, I extends DbRecordId> I save(T record);
}
