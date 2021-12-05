package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.query.Query;
import com.ple.jerbil.data.query.SelectQuery;

public class GeneratorQuery {

  protected static Query query;


  public static void set(Query newQuery) {
    query = newQuery;
  }

}
