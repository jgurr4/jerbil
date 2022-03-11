package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.Diff;

public class VoidLanguageGenerator implements LanguageGenerator {

  public static final LanguageGenerator only = new VoidLanguageGenerator();

  @Override
  public String toSql(CompleteQuery completeQuery) {
    return "";
  }

  @Override
  public Database fromSql(String dbCreateString) {
    return null;
  }

  @Override
  public TableContainer fromSql(String showCreateTable, Database db) {
    return null;
  }

  @Override
  public Column fromSql(String showCreateTable, TableContainer table) {
    return null;
  }

  @Override
  public String toSql(Column column) {
    return "";
  }

  @Override
  public String toSql(Diff diff) {
    return "";
  }

}
