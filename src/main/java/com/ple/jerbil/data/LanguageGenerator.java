package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;

public interface LanguageGenerator {

  String toSql(CompleteQuery completeQuery);
}
