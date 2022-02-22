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
    Constructor<?>[] customTableConstructors = null;
    Parameter[] customTableConstructorParams = null;
    IList<Class<?>> customTableConstructorParamClasses = null;
    Constructor<?> customTableConstructor = null;
    AutoIncIndex columnAttributes = null;
    Field[] customTableContainerFields = null;
    Object fieldValue = null;
    IList<Object> customTableArgs = null;
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
      IList<Object> customDatabaseContainerArgs = IArrayList.make(db);
      IList<Class<?>> customDbContainerParams = IArrayList.make(db.getClass());
      for (int i = 0; i < parameters.length; i++) {
        final Class<?> type = parameters[i].getType();
        if (type.getSuperclass() != null) {
          if (type.getSuperclass().equals(TableContainer.class)) {
            final Method method = type.getMethod("make", Database.class);
            TableContainer tc = (TableContainer) method.invoke(null, db);
            final IMap<String, Column> columns = tc.columns;
            for (IEntry<String, Column> column : columns) {
              if (column.value.hints.flags > 0) {
                customTableConstructors = type.getConstructors();
                customTableConstructorParams = customTableConstructors[0].getParameters();
                customTableConstructorParamClasses = IArrayList.make();
                for (Parameter param : customTableConstructorParams) {
                  customTableConstructorParamClasses = customTableConstructorParamClasses.add(param.getType());
                }
                customTableConstructor = type.getConstructor(customTableConstructorParamClasses.toArray());
                columnAttributes = getColumnAttributes(column);
              }
              customTableArgs = IArrayList.make(tc.table);
              // Get the values of fields from tc. Specifically the Table and all the Columns.
              // Add them to the customTableArgs list.
            }
            customTableContainerFields = tc.getClass().getDeclaredFields();
            for (Field field : customTableContainerFields) {
              field.setAccessible(true);
              fieldValue = field.get(tc);
              if (fieldValue instanceof Column) {
                customTableArgs = customTableArgs.add(fieldValue);
              }
            }
            customTableArgs = customTableArgs.add(columnAttributes.indexes);
            customTableArgs = customTableArgs.add(columnAttributes.autoIncrementColumn);
            // replace customTableContainer with new instance which includes the indexes and autoIncrementColumn arguments.
            tc = (TableContainer) customTableConstructor.newInstance(customTableArgs.toArray());
            customDatabaseContainerArgs = customDatabaseContainerArgs.add(tc);
            customDbContainerParams = customDbContainerParams.add(type);
          }
        }
      }
      t = (T) customDbContainerClass.getMethod("make", customDbContainerParams.toArray())
          .invoke(null, customDatabaseContainerArgs.toArray());
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
    return t;
  }

  private static AutoIncIndex getColumnAttributes(IEntry<String, Column> column) {
    // Create the indexes and autoIncrementColumn objects here.
    IList<Index> indexes = IArrayList.make();
    NumericColumn autoIncColumn = null;
    if ((column.value.hints.flags >> 8 & 1) == 1) { //Using rightshift and a number will move value at position to furthest to the right. Then using AND with a mask will obtain the last digit.
      // means auto_increment is true.
      indexes = indexes.add(Index.make(IndexType.primary, column.value));
      autoIncColumn = (NumericColumn) column.value;
    } else if ((column.value.hints.flags >> 15 & 1) == 1) {
      // means primary key is true
      indexes = indexes.add(Index.make(IndexType.primary, column.value));
    }
    if ((column.value.hints.flags >> 12 & 1) == 1) {
      // means fulltext is true.
      indexes = indexes.add(Index.make(IndexType.fulltext, column.value));
    }
    if ((column.value.hints.flags >> 13 & 1) == 1) {
      // means foreign key is true.
      indexes = indexes.add(Index.make(IndexType.foreign, column.value));
    }
    if ((column.value.hints.flags >> 14 & 1) == 1) {
      // means index/secondary key is true.
      indexes = indexes.add(Index.make(IndexType.secondary, column.value));
    }
    return new AutoIncIndex(indexes, autoIncColumn);
  }

  @Immutable
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
