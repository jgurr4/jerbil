package com.ple.jerbil.data.selectExpression;

public class CurrentTimestamp implements DateExpression{

  public static CurrentTimestamp make() {
    return new CurrentTimestamp();
  }
}
