package com.ple.jerbil.testcommon.orm;

import com.ple.jerbil.data.DbRecord;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.Immutable;

@Immutable
public class User extends DbRecord<User, UserId> {
  public final UserId userId;
  public final String name;
  public final int age;

  protected User(UserId userId, String name, int age) {
    this.userId = userId;
    this.name = name;
    this.age = age;
  }

  public static User make(UserId userId, String name, int age) {
    return new User(userId, name, age);
  }

  public User setName(String name) {
    return new User(userId, name, age);
  }

  public User setAge(int age) {
    return new User(userId, name, age);
  }
}
