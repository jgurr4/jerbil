package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.DbDiff;
import com.ple.util.IList;

public class VoidLanguageGenerator implements LanguageGenerator {

  public static final LanguageGenerator only = new VoidLanguageGenerator();

  @Override
  public String toSql(CompleteQuery completeQuery) {
    return "";
  }

  @Override
  public DatabaseContainer getDbFromSql(String dbCreateString, Database db, IList<String> tblCreateStringList) {
    return null;
  }

  @Override
  public TableContainer getTableFromSql(String showCreateTable, Database db) {
    return null;
  }

  @Override
  public Column getColumnFromSql(String tableLine, String[] columnsInIndex, Table table) {
    return null;
  }

  public Database fromSql(String dbCreateString) {
    return null;
  }

  public TableContainer fromSql(String showCreateTable, Database db) {
    return null;
  }

  public Column fromSql(String showCreateTable, TableContainer table) {
    return null;
  }

  @Override
  public String toSql(Column column) {
    return "";
  }

  @Override
  public String toSql(DbDiff diff) {
    return "";
  }

  @Override
  public String checkToAddBackticks(String name) {
    return "";
  }

}
