package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;

/**
 * Bridge is responsible for connecting and sending data to and from a database.
 * There are various types of bridges. The default MysqlBridge is asynchronous because it's built with Reactive
 * Extensions using rxjava + jdbc. But other bridges can be designed which only use jdbc.
 */
public interface DataBridge {

  LanguageGenerator getGenerator();

  ReactiveWrapper<DbResult> execute(String toSql);

  ReactiveWrapper<DbResult> execute(ReactiveWrapper<String> toSql);

  ReactiveWrapper<DatabaseContainer> getDb(String name);

  <T extends DbRecord, I extends DbRecordId> I save(T record);

}
