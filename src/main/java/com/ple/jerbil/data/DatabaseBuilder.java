package com.ple.jerbil.data;

import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseBuilder {

  public static <T> T generate(Class<T> customDbContainerClass, String dbName) {
    T t = null;
    try {
      final Database db = Database.make(dbName);
      final Field[] fields = customDbContainerClass.getDeclaredFields();
      IList<Object> args = IArrayList.make(db);
      IList<Class> parameters = IArrayList.make(Database.class);
      for (int i = 0; i < fields.length; i++) {
        final Class<?> type = fields[i].getType();
        if (type.getSuperclass() != null) {
          if (type.getSuperclass().equals(TableContainer.class)) {
            final Method method = type.getMethod("make", Database.class);
            final Object tc = method.invoke(null, db);
            args = args.add(tc);
            parameters = parameters.add(type);
          }
        }
      }
      t = (T) customDbContainerClass.getMethod("make", parameters.toArray()).invoke(null, args.toArray());
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return t;
  }

}
