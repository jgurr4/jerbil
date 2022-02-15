package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.Diff;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);

  Database fromSql(String dbCreateString);

  Table fromSql(String showCreateTable, Database db);

  Column fromSql(String showCreateTable, Table table);

  String toSql(Column column);

  String toSql(Diff diff);

}
