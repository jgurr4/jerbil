package com.ple.jerbil.data;

import java.lang.reflect.InvocationTargetException;

public class DatabaseBuilder {

  public static <T> T generate(Class<T> databaseContainerClass, Database db) {
    //Step 1: Use Reflection methods to retrieve all the parameters of the TestDatabase.make method, which includes the
    // each CustomTableContainer. except for map of tables.
    //Step 2: Each parameter class must be instantiated using the make method. representing each CustomTableContainer.
    //Step 3: Use Reflection methods to retrieve and invoke the make method of customDatabaseContainerClass.
    //Step 4: Each of the instantiated classes must be used inside the make method of customDatabaseContainerClass.
    T t = null;
    try {
      databaseContainerClass.getMethod("make").invoke();
      t = databaseContainerClass.getDeclaredMethod("make");
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return t;
  }

}
