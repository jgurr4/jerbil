package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.Diff;
import com.ple.util.IList;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);

  Database fromSql(String dbCreateString);

  TableContainer fromSql(String showCreateTable, Database db);

  Column fromSql(String showCreateTable, TableContainer table);

  String toSql(Column column);

  String toSql(Diff diff);

}
