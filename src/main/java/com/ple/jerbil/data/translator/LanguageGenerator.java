package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.DbDiff;
import com.ple.jerbil.data.sync.Diff;
import com.ple.jerbil.data.sync.TableDiff;
import com.ple.util.IList;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);

  DatabaseContainer getDbFromSql(String dbCreateString, Database db, IList<String> tblCreateStringList);

  TableContainer getTableFromSql(String showCreateTable, Database db);

  Column getColumnFromSql(String tableLine, String[] columnsInIndex, Table table);

  String toSql(Column column);

  String toSql(DbDiff diff);

  String toSql(TableDiff tableDiff);

  String checkToAddBackticks(String name);

  String drop(DatabaseContainer databaseContainer);

}
