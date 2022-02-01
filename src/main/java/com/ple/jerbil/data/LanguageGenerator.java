package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.DbDiff;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);

  Table fromSql(String showCreateTable);

  String toSql(Column column);

  String toSql(DbDiff dbDiff);

}
