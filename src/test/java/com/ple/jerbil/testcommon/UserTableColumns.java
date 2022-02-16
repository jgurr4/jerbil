package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

public class UserTableColumns {
  public final NumericColumn userId;
  public final StringColumn name;
  public final NumericColumn age;

  public UserTableColumns(NumericColumn userId, StringColumn name, NumericColumn age) {
    this.userId = userId;
    this.name = name;
    this.age = age;
  }
}
