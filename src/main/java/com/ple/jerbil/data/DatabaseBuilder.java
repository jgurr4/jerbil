package com.ple.jerbil.data;

import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DatabaseBuilder {

  public static <T extends DatabaseContainer> T generate(Class<T> customDbContainerClass, String dbName) {
    T t = null;
    try {
      Parameter[] parameters = null;
      final Database db = Database.make(dbName);
      final Method[] declaredMethods = customDbContainerClass.getDeclaredMethods();
      for (Method declaredMethod : declaredMethods) {
        if (declaredMethod.getName().equals("make")) {
          parameters = declaredMethod.getParameters();
        }
      }
      IList<Object> args = IArrayList.make(db);
      IList<Class<?>> customDbContainerParams = IArrayList.make();
      for (int i = 0; i < parameters.length; i++) {
        final Class<?> type = parameters[i].getType();
        if (type.getSuperclass() != null) {
          if (type.getSuperclass().equals(TableContainer.class)) {
            final Method method = type.getMethod("make", Database.class);
            final Object tc = method.invoke(null, db);
            args = args.add(tc);
            customDbContainerParams = customDbContainerParams.add(type);
          }
        }
      }
      t = (T) customDbContainerClass.getMethod("make", customDbContainerParams.toArray()).invoke(null, args.toArray());
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return t;
  }

}
