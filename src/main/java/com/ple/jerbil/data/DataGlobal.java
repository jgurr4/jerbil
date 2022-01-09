package com.ple.jerbil.data;

public class DataGlobal {
  // bridge contains all the information needed to connect to database. Each bridge will only need one language Generator.
  // Each language type should have it's own global. All database languages will have a bridge and a translator.
  // But other languages will not need a bridge. For instance html doesn't have a bridge, it only has translator.

  public static DataBridge bridge;
}
