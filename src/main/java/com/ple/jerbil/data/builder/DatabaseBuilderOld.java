package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.*;

import java.lang.reflect.*;

@Immutable
public class DatabaseBuilderOld {

  //FIXME: Figure out a way to handle multi-column indexes for secondary, fulltext, and foreign.
  //TODO: Make this handle creating ArrayMap of columns, as well as setting the fields of the CustomTableContainer class using reflection.
  public static <T extends DatabaseContainer> T generate(Class<T> customDbContainerClass, String dbName) {
    Constructor<?>[] customTblConstructors = null;
    Parameter[] customTblConstructorParams = null;
    IList<Class<?>> customTblConstructorParamClasses = IArrayList.empty;
    Constructor<?> customTblConstructor = null;
    Field[] customTblContainerFields = null;
    Object fieldValue = null;
    IList<Object> customTblArgs = IArrayList.empty;
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
      IList<Object> customDbContainerArgs = IArrayList.make(db);
      IList<Class<?>> customDbContainerParams = IArrayList.make(db.getClass());
      for (int i = 0; i < parameters.length; i++) {
        final Class<?> type = parameters[i].getType();
        if (type.getSuperclass() != null) {
          if (type.getSuperclass().equals(TableContainer.class)) {
            final Method method = type.getMethod("make", Database.class);
            TableContainer tc = (TableContainer) method.invoke(null, db);
            final Table customTable = tc.table;
            IList<Column> columns = IArrayList.empty;
            customTblContainerFields = tc.getClass().getDeclaredFields();
            customTblArgs = IArrayList.make(tc.table, tc.columns);
            for (Field field : customTblContainerFields) {
              field.setAccessible(true);
              fieldValue = field.get(tc);
              if (fieldValue instanceof Column) {
                customTblArgs = customTblArgs.add(fieldValue);
                columns = columns.add((Column) fieldValue);
              }
            }
            IMap<String, Index> indexes = IArrayMap.empty;
            if (tc.indexes != null) {
              indexes = tc.indexes;
            }
            NumericColumn autoIncColumn = null;
            for (Column column : columns) {
              if (column.props.isAutoInc()) {
                autoIncColumn = (NumericColumn) column;
              }
            }
//            tblAutoIndexes = getColumnAttributes(columns, indexes, customTable);
              customTblConstructors = type.getDeclaredConstructors();
              customTblConstructorParams = customTblConstructors[0].getParameters();
              customTblConstructorParamClasses = IArrayList.make();
              for (Parameter param : customTblConstructorParams) {
                customTblConstructorParamClasses = customTblConstructorParamClasses.add(param.getType());
              }
              customTblConstructor = type.getDeclaredConstructor(customTblConstructorParamClasses.toArray());
            customTblArgs = customTblArgs.add(indexes);
            customTblArgs = customTblArgs.add(autoIncColumn);
            // replace customTableContainer with new instance which includes the indexes and autoIncrementColumn arguments.
            customTblConstructor.setAccessible(true);
            tc = (TableContainer) customTblConstructor.newInstance(customTblArgs.toArray());
            customDbContainerArgs = customDbContainerArgs.add(tc);
            customDbContainerParams = customDbContainerParams.add(type);
          }
        }
      }
      t = (T) customDbContainerClass.getMethod("make", customDbContainerParams.toArray())
          .invoke(null, customDbContainerArgs.toArray());
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
    return t;
  }

}
