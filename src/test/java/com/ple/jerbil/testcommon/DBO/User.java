package com.ple.jerbil.testcommon.DBO;

import com.ple.jerbil.data.DbRecord;
import com.ple.jerbil.data.DbResult;
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

  public static User make(DbResult result) {
    return null;
  }

  public static User make(String name, int age) {
    return null;
  }

  public User setName(String name) {
    return new User(userId, name, age);
  }

  public User setAge(int age) {
    return new User(userId, name, age);
  }
}
