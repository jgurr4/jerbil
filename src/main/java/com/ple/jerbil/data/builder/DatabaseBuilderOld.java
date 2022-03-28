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
    AutoIncIndex tblAutoIndexes = null;
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
            tblAutoIndexes = getColumnAttributes(columns, indexes, customTable);
            if (tblAutoIndexes.indexes != null || tblAutoIndexes.autoIncrementColumn != null) {
              customTblConstructors = type.getDeclaredConstructors();
              customTblConstructorParams = customTblConstructors[0].getParameters();
              customTblConstructorParamClasses = IArrayList.make();
              for (Parameter param : customTblConstructorParams) {
                customTblConstructorParamClasses = customTblConstructorParamClasses.add(param.getType());
              }
              customTblConstructor = type.getDeclaredConstructor(customTblConstructorParamClasses.toArray());
            }
            customTblArgs = customTblArgs.add(tblAutoIndexes.indexes);
            customTblArgs = customTblArgs.add(tblAutoIndexes.autoIncrementColumn);
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

  private static AutoIncIndex getColumnAttributes(IList<Column> columns, IMap<String, Index> indexes,
                                                  Table customTable) {
    NumericColumn autoIncColumn = null;
    Index primary = null;
    IList<String> existingNames = indexes.keys();
    for (Column column : columns) {
      String indexName = DatabaseService.generateIndexName(existingNames, column).stripTrailing();
      existingNames = existingNames.add(indexName);
      if (column.hints.isAutoInc()) {
        indexes = indexes.put("primary", Index.make(IndexType.primary, "primary", customTable, column));
        autoIncColumn = (NumericColumn) column;
      } else if (column.hints.isPrimary()) {
        if (primary == null) {
          primary = Index.make(IndexType.primary, "primary", customTable, column);
          indexes = indexes.put("primary", primary);
        } else {
          primary = Index.make(IndexType.primary, "primary", customTable,
              primary.indexedColumns.put(column.columnName, IndexedColumn.make(column, 0, null))
                  .values().toArray(IndexedColumn.emptyArray));
          indexes = indexes.put("primary", primary);
        }
      }
      if (column.hints.isFulltext()) {
        indexes = indexes.put(indexName,
            Index.make(IndexType.fulltext, indexName, customTable, IndexedColumn.make(column, 0, null)));
      }
      if (column.hints.isForeign()) {
        indexes = indexes.put(indexName,
            Index.make(IndexType.foreign, indexName, customTable, IndexedColumn.make(column, 0, null)));
      }
      if (column.hints.isIndexed()) {
        indexes = indexes.put(indexName,
            Index.make(IndexType.secondary, indexName, customTable, IndexedColumn.make(column, 0, null)));
      }
      if (column.hints.isUnique()) {
        indexes = indexes.put(indexName,
            Index.make(IndexType.unique, indexName, customTable, IndexedColumn.make(column, 0, null)));
      }
    }
    return new AutoIncIndex(indexes, autoIncColumn);
  }

  @Immutable
  private static class AutoIncIndex {
    public final IMap<String, Index> indexes;
    public final NumericColumn autoIncrementColumn;

    private AutoIncIndex(IMap<String, Index> indexes,
                         NumericColumn autoIncrementColumn) {
      this.indexes = indexes;
      this.autoIncrementColumn = autoIncrementColumn;
    }
  }
}
