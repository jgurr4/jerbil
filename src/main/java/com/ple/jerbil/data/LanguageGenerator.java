package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);

  Table fromSql(String showCreateTable);
}
