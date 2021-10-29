package com.ple.jerbil.data;

public class DataGlobal {
  // bridge contains all the information needed to connect to database. Each bridge will only need one sqlGenerator.
  // Each language type should have it's own global. For instance html doesn't have a bridge, it only has translator,
  // But all database languages will have a bridge and a translator.
  public static DataBridge bridge;
}
