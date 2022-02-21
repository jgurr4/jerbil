package com.ple.jerbil.data;

import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IEntry;
import com.ple.util.IList;
import com.ple.util.IMap;

import java.lang.reflect.*;

@Immutable
public class DatabaseBuilder {

  //FIXME: Now I just need this to create the indexes and autoIncrementColumn fields for each CustomTableContainer using
  // BuildingHints from each column.
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
      IList<Class<?>> customDbContainerParams = IArrayList.make(db.getClass());
      boolean hintsIsNull = true;
      for (int i = 0; i < parameters.length; i++) {
        final Class<?> type = parameters[i].getType();
        if (type.getSuperclass() != null) {
          if (type.getSuperclass().equals(TableContainer.class)) {
            final Method method = type.getMethod("make", Database.class);
            Object tc = method.invoke(null, db);
            final IMap<String, Column> columns = ((TableContainer) tc).columns;
            for (IEntry<String, Column> column : columns) {
              if (!hintsIsNull) {
                final Constructor<?>[] customTableConstructors = type.getConstructors();
                final Parameter[] customTableConstructorParams = customTableConstructors[0].getParameters();
                IList<Class<?>> customTableConstructorParamClasses = IArrayList.make();
                for (Parameter param : customTableConstructorParams) {
                  customTableConstructorParamClasses = customTableConstructorParamClasses.add(param.getType());
                }
                final Constructor<?> customTableConstructor = type.getConstructor(
                    customTableConstructorParamClasses.toArray());
                final AutoIncIndex columnAttributes = getColumnAttributes(column);
                IList<Object> customTableArgs = IArrayList.make();
                // Get the values of fields from tc. Specifically the Table and all the Columns.
                // Add them to the customTableArgs list.
                final Field[] customTableContainerFields = tc.getClass().getDeclaredFields();
                for (Field field : customTableContainerFields) {
                  field.setAccessible(true);
                  final Object fieldValue = field.get(tc);
                  if (fieldValue instanceof Column) {
                    customTableArgs = customTableArgs.add(fieldValue);
                  }
                }
                customTableArgs = customTableArgs.add(columnAttributes.indexes);
                customTableArgs = customTableArgs.add(columnAttributes.autoIncrementColumn);
                // replace customTableContainer with new instance which includes the indexes and autoIncrementColumn arguments.
                tc = customTableConstructor.newInstance(customTableArgs.toArray());
              }
              args = args.add(tc);
              customDbContainerParams = customDbContainerParams.add(type);
            }
          }
        }
      }
      t = (T) customDbContainerClass.getMethod("make", customDbContainerParams.toArray())
          .invoke(null, args.toArray());
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
    return t;
  }

  private static AutoIncIndex getColumnAttributes(IEntry<String, Column> column) {
    //TODO: Implement this.

    // Create the indexes and autoIncrementColumn objects here.
    if ((column.value.hints.flags >> 7 & 0b1) == 1) {      //Using AND operator and a mask will obtain bit on right side.
      // means allowNull is true.
    }
    if ((column.value.hints.flags >> 8 & 0b1) == 1) { //Using rightshift and a number will move value at position to furthest to the right. Then using AND with a mask will obtain the last digit.
      // means auto_increment is true.
    }
    if ((column.value.hints.flags >> 9 & 0b1) == 1) {
      // means unsigned is true.
    }
    if ((column.value.hints.flags >> 10 & 0b1) == 1) {
      // means unique is true.
    }
    if ((column.value.hints.flags >> 11 & 0b1) == 1) {
      // means invisible is true.
    }
    if ((column.value.hints.flags >> 12 & 0b1) == 1) {
      // means fulltext is true.
    }
    if ((column.value.hints.flags >> 13 & 0b1) == 1) {
      // means foreign key is true.
    }
    if ((column.value.hints.flags >> 14 & 0b1) == 1) {
      // means index/secondary key is true.
    }
    if ((column.value.hints.flags >> 15 & 0b1) == 1) {
      // means primary key is true.
    }

    return null;
  }

  private static class AutoIncIndex {
    public final IList<Index> indexes;
    public final NumericColumn autoIncrementColumn;

    private AutoIncIndex(IList<Index> indexes,
                         NumericColumn autoIncrementColumn) {
      this.indexes = indexes;
      this.autoIncrementColumn = autoIncrementColumn;
    }
  }
}
