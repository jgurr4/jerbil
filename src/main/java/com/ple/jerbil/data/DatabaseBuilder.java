package com.ple.jerbil.data;

import java.lang.reflect.InvocationTargetException;

public class DatabaseBuilder {

  public static <T> T generate(Class<T> databaseClass) {
    // This method needs to generate the code for creating the TestTable class.
    // This method uses lots of reflection because this is builder code to add convenience methods for avoiding boilerplate.
    // Basically it has to create an instance of the database class passed into it, by getting all the required parameters
    // passed into it.
    T t = null;
    try {
      t = databaseClass.getDeclaredMethod("make");
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return t;
  }

}
