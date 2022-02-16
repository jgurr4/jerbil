package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ColumnService {

  public static <T> IList<Column> asList(T customTableColumns) {
    IList<Column> columns = IArrayList.make();
    Field[] declaredFields = customTableColumns.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        if (field.get(customTableColumns) instanceof Column) {
          columns = columns.add((Column) field.get(customTableColumns));
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return columns;
  }

}
