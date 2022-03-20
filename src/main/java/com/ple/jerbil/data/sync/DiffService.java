package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IArrayList;
import com.ple.util.IEntry;
import com.ple.util.IList;
import com.ple.util.IMap;
import reactor.core.publisher.Mono;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static DbDiff compareDatabases(DatabaseContainer leftDbc, DatabaseContainer rightDbc) {
    return compareDatabaseProps(leftDbc, rightDbc);
  }

  public static ReactiveWrapper<DbDiff> compareDatabases(ReactiveWrapper<DatabaseContainer> leftDbc,
                                                         ReactiveWrapper<DatabaseContainer> rightDbc) {
    return ReactiveMono.make(leftDbc.unwrapMono()
        .concatWith(rightDbc.unwrapMono())
        .collectList()
        .map(databases -> compareDatabaseProps(databases.get(0), databases.get(1)))
    );
  }

  public static DbDiff compareDatabaseProps(DatabaseContainer leftDb, DatabaseContainer rightDb) {
    ScalarDiff<String> nameDiff = null;
    if (!leftDb.database.databaseName.equals(rightDb.database.databaseName)) {
      nameDiff = ScalarDiff.make(leftDb.database.databaseName, rightDb.database.databaseName);
    }
    VectorDiff<TableContainer, TableDiff> tablesDiff = compareListOfTables(leftDb.tables.values(),
        rightDb.tables.values());
    return DbDiff.make(nameDiff, tablesDiff, leftDb, rightDb);
  }

  public static VectorDiff<TableContainer, TableDiff> compareListOfTables(IList<TableContainer> leftTables,
                                                                          IList<TableContainer> rightTables) {
    IList<TableContainer> create = getMissingTables(leftTables, rightTables);
    final IList<TableContainer> noExactMatchesList = filterOutMatchingTables(leftTables, rightTables);
    IList<TableContainer> matches = IArrayList.make();
    IList<TableContainer> nonMatches = IArrayList.make();
    for (TableContainer table : noExactMatchesList) {
      if (checkTableNameInList(leftTables, table)) {
        matches = matches.add(table);
      } else {
        nonMatches = nonMatches.add(table);
      }
    }
    IList<TableDiff> update = getListOfTableDiffs(leftTables, matches);
    IList<TableContainer> delete = getExtraTables(leftTables, nonMatches);
    return checkNullsInResult(create, delete, update);
  }

  public static IList<TableContainer> getMissingTables(IList<TableContainer> leftTables,
                                                       IList<TableContainer> rightTables) {
    IList<TableContainer> result = IArrayList.make();
    for (TableContainer lTable : leftTables) {
      if (!rightTables.contains(lTable) && !checkTableNameInList(rightTables, lTable)) {
        result = result.add(lTable);
      }
    }
    if (result.size() == 0) {
      return null;
    }
    return result;
  }

  public static IList<TableContainer> getExtraTables(IList<TableContainer> leftTables,
                                                     IList<TableContainer> rightTables) {
    IList<TableContainer> result = IArrayList.make();
    for (TableContainer rTable : rightTables) {
      if (!leftTables.contains(rTable) && !checkTableNameInList(leftTables, rTable)) {
        result = result.add(rTable);
      }
    }
    if (result.size() == 0) {
      return null;
    }
    return result;
  }

  public static IList<TableDiff> getListOfTableDiffs(IList<TableContainer> leftTables,
                                                     IList<TableContainer> rightTables) {
    IList<TableDiff> tableDiffs = IArrayList.empty;
    for (TableContainer rightTable : rightTables) {
      if (rightTable != null) {
        tableDiffs = tableDiffs.add(
            compareTables(getTableMatchingName(leftTables, rightTable.table.tableName), rightTable));
      }
    }
    if (tableDiffs.size() == 0) {
      return null;
    }
    return tableDiffs;
  }

  public static IList<TableContainer> filterOutMatchingTables(IList<TableContainer> leftTables,
                                                              IList<TableContainer> rightTables) {
    IList<TableContainer> nonMatchingTables = IArrayList.make();
    for (TableContainer rightTable : rightTables) {
      if (!leftTables.contains(rightTable)) {
        nonMatchingTables = nonMatchingTables.add(rightTable);
      }
    }
    return nonMatchingTables;
  }

  public static TableDiff compareTables(TableContainer leftTable, TableContainer rightTable) {
    // Should compare columns and indexes separately.
//    final VectorDiff<Index> indexDiff = compareIndexes(c1, c2);
    ScalarDiff<String> nameDiff = leftTable.table.tableName.equals(rightTable.table.tableName) ? null : ScalarDiff.make(
        leftTable.table.tableName, rightTable.table.tableName);
    VectorDiff<Column, ColumnDiff> columnDiffs = compareListOfColumns(leftTable.columns.values(),
        rightTable.columns.values());
    VectorDiff<Index, IndexDiff> indexDiffs = compareListOfIndexes(leftTable.indexes, rightTable.indexes);
    ScalarDiff<StorageEngine> storageEngineDiff = leftTable.storageEngine.name()
        .equals(rightTable.storageEngine.name()) ? null : ScalarDiff.make(leftTable.storageEngine,
        rightTable.storageEngine);
    if (nameDiff != null || columnDiffs != null || indexDiffs != null || storageEngineDiff != null) {
      return TableDiff.make(nameDiff, columnDiffs, indexDiffs, storageEngineDiff, leftTable, rightTable);
    }
    return null;
  }

  private static VectorDiff<Index, IndexDiff> compareListOfIndexes(IMap<String, Index> leftIndexes,
                                                                   IMap<String, Index> rightIndexes) {
    IList<Index> create = IArrayList.empty;
    IList<Index> delete = IArrayList.empty;
    IList<IndexDiff> update = IArrayList.empty;
    Index leftIndex = null;
    if (rightIndexes != null && leftIndexes != null) {
      for (Index rightIndex : rightIndexes.values()) {
        leftIndex = leftIndexes.get(rightIndex.indexName);
        if (leftIndexes.get(rightIndex.indexName) == null) {
          delete = delete.add(rightIndex);
        }
        for (Index lIndex : leftIndexes.values()) {
        if (rightIndexes.get(lIndex.indexName) == null) {
          create = create.add(lIndex);
        }
        }
        if (leftIndexes.get(rightIndex.indexName) != null && !leftIndexes.get(rightIndex.indexName)
            .equals(rightIndex)) {
          ScalarDiff<IndexType> typeDiff = null;
          ScalarDiff<String> nameDiff = null;
          ScalarDiff<Table> tableDiff = null;
          VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumnsDiffs = compareListOfIndexedColumns(
              leftIndex.indexedColumns,
              rightIndex.indexedColumns);
          if (!leftIndex.indexName.equals(rightIndex.indexName)) {
            nameDiff = ScalarDiff.make(leftIndex.indexName, rightIndex.indexName);
          }
          if (!leftIndex.type.equals(rightIndex.type)) {
            typeDiff = ScalarDiff.make(leftIndex.type, rightIndex.type);
          }
          if (!leftIndex.table.equals(rightIndex.table)) {
            tableDiff = ScalarDiff.make(leftIndex.table, rightIndex.table);
          }
          update = update.add(
              IndexDiff.make(typeDiff, nameDiff, tableDiff, indexedColumnsDiffs, leftIndexes.get(rightIndex.indexName),
                  rightIndex));
        }
      }
    } else if (rightIndexes == null && leftIndexes != null) {
      create = leftIndexes.values();
    } else if (leftIndexes == null && rightIndexes != null) {
      delete = rightIndexes.values();
    }
    return checkNullsInResult(create, delete, update);
  }

  private static <T, D extends Diff<T>> VectorDiff<T, D> checkNullsInResult(IList<T> create, IList<T> delete,
                                                                            IList<D> update) {
    if (create != null) {
      if (create.size() == 0) {
        create = null;
      }
    }
    if (delete != null) {
      if (delete.size() == 0) {
        delete = null;
      }
    }
    if (update != null) {
      if (update.size() == 0) {
        update = null;
      }
    }
    if (create != null || delete != null || update != null) {
      return VectorDiff.make(create, delete, update);
    }
    return null;
  }

  private static VectorDiff<IndexedColumn, IndexedColumnDiff> compareListOfIndexedColumns(
      IMap<String, IndexedColumn> leftIndexedColumns,
      IMap<String, IndexedColumn> rightIndexedColumns) {
    IList<IndexedColumn> create = IArrayList.empty;
    IList<IndexedColumn> delete = IArrayList.empty;
    IList<IndexedColumnDiff> update = IArrayList.empty;
    for (IndexedColumn rightColumn : rightIndexedColumns.values()) {
      for (IndexedColumn leftColumn : leftIndexedColumns.values()) {
        if (leftIndexedColumns.get(rightColumn.column.columnName) == null) {
          delete = delete.add(rightColumn);
        }
        if (rightIndexedColumns.get(leftColumn.column.columnName) == null) {
          create = create.add(leftColumn);
        }
        if (leftIndexedColumns.get(rightColumn.column.columnName) != null && !leftIndexedColumns.get(
            rightColumn.column.columnName).equals(rightColumn)) {
          final IndexedColumn leftIndexedColumn = leftIndexedColumns.get(rightColumn.column.columnName);
          ScalarDiff<Column> columnDiff = null;
          ScalarDiff<Integer> prefixDiff = null;
          ScalarDiff<SortOrder> sortOrderDiff = null;
          if (!leftIndexedColumn.column.equals(rightColumn.column)) {
            columnDiff = ScalarDiff.make(leftIndexedColumn.column, rightColumn.column);
          }
          if (leftIndexedColumn.prefixSize != rightColumn.prefixSize) {
            prefixDiff = ScalarDiff.make(leftIndexedColumn.prefixSize, rightColumn.prefixSize);
          }
          if (leftIndexedColumn.sortOrder != null && rightColumn.sortOrder == null) {
            sortOrderDiff = ScalarDiff.make(leftIndexedColumn.sortOrder, null);
          } else if (leftIndexedColumn.sortOrder == null && rightColumn.sortOrder != null) {
            sortOrderDiff = ScalarDiff.make(null, rightColumn.sortOrder);
          } else if (leftIndexedColumn.sortOrder == null && rightColumn.sortOrder == null) {
            sortOrderDiff = null;
          } else if (leftIndexedColumn.sortOrder.equals(rightColumn.sortOrder)) {
            sortOrderDiff = ScalarDiff.make(leftIndexedColumn.sortOrder, rightColumn.sortOrder);
          }
          if (columnDiff != null || prefixDiff != null || sortOrderDiff != null) {
            update = update.add(IndexedColumnDiff.make(columnDiff, prefixDiff, sortOrderDiff));
          }
        }
      }
    }
    return checkNullsInResult(create, delete, update);
  }

  public static TableContainer getTableMatchingName(IList<TableContainer> tables, String name) {
    TableContainer result = null;
    for (TableContainer t : tables) {
      if (t.table.tableName.equals(name)) {
        result = t;
      }
    }
    return result;
  }

  public static VectorDiff<Column, ColumnDiff> compareListOfColumns(IList<Column> leftColumns,
                                                                    IList<Column> rightColumns) {
    IList<Column> create = getMissingColumns(leftColumns, rightColumns);
    final IList<Column> noExactMatchesList = filterOutMatchingColumns(leftColumns, rightColumns);
    IList<Column> matches = IArrayList.make();
    IList<Column> nonMatches = IArrayList.make();
    for (Column column : noExactMatchesList) {
      if (checkColumnNameInList(leftColumns, column)) {
        matches = matches.add(column);
      } else {
        nonMatches = nonMatches.add(column);
      }
    }
    IList<ColumnDiff> update = getListOfColumnDiffs(leftColumns, matches);
    IList<Column> delete = getExtraColumns(leftColumns, nonMatches);
    return checkNullsInResult(create, delete, update);
  }

  public static Column getColumnMatchingName(IList<Column> columns, String name) {
    Column result = null;
    for (Column c : columns) {
      if (c.columnName.equals(name)) {
        result = c;
      }
    }
    return result;
  }

  public static IList<Column> getExtraColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    IList<Column> result = IArrayList.make();
    for (Column rColumn : rightColumns) {
      if (!leftColumns.contains(rColumn) && !checkColumnNameInList(leftColumns, rColumn)) {
        result = result.add(rColumn);
      }
    }
    if (result.size() == 0) {
      return null;
    }
    return result;
  }

  public static boolean checkColumnNameInList(IList<Column> columns, Column column) {
    for (Column c1 : columns) {
      if (column.columnName.equals(c1.columnName)) {
        return true;
      }
    }
    return false;
  }

  public static IList<Column> filterOutMatchingColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    IList<Column> nonMatchingTables = IArrayList.make();
    for (Column rightColumn : rightColumns) {
      if (!leftColumns.contains(rightColumn)) {
        nonMatchingTables = nonMatchingTables.add(rightColumn);
      }
    }
    return nonMatchingTables;
  }

  public static IList<Column> getMissingColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    IList<Column> result = IArrayList.make();
    for (Column lColumn : leftColumns) {
      if (!rightColumns.contains(lColumn) && !checkColumnNameInList(rightColumns, lColumn)) {
        result = result.add(lColumn);
      }
    }
    if (result.size() == 0) {
      return null;
    }
    return result;
  }

  public static IList<ColumnDiff> getListOfColumnDiffs(IList<Column> leftColumns, IList<Column> rightColumns) {
    IList<ColumnDiff> columnDiffs = IArrayList.empty;
    for (Column rightColumn : rightColumns) {
      if (rightColumn != null) {
        columnDiffs = columnDiffs.add(
            compareColumns(getColumnMatchingName(leftColumns, rightColumn.columnName), rightColumn));
      }
    }
    if (columnDiffs.size() > 0) {
      return columnDiffs;
    }
    return null;
  }

  public static ColumnDiff compareColumns(Column c1, Column c2) {
    final ScalarDiff<Table> tableDiff = c1.table.equals(c2.table) ? null : ScalarDiff.make(c1.table, c2.table);
    final ScalarDiff<String> nameDiff = c1.columnName.equals(c2.columnName) ? null : ScalarDiff.make(c1.columnName,
        c2.columnName);
    final ScalarDiff<DataSpec> dataSpecDiff = c1.dataSpec.equals(c2.dataSpec) ? null : ScalarDiff.make(
        c1.dataSpec, c2.dataSpec);
//    final ScalarDiff<Expression> generatedDiff = c1.generatedFrom.equals(
//        c2.generatedFrom) ? null : ScalarDiff.make(c1.generatedFrom, c2.generatedFrom);
    ScalarDiff<Expression> defaultDiff = null;
    if (c1.defaultValue != null && c2.defaultValue != null) {
      defaultDiff = c1.defaultValue.equals(c2.defaultValue) ? null : ScalarDiff.make(
          c1.defaultValue, c2.defaultValue);
    } else if (c1.defaultValue == null && c2.defaultValue != null) {
      defaultDiff = ScalarDiff.make(null, c2.defaultValue);
    } else if (c1.defaultValue != null & c2.defaultValue == null) {
      defaultDiff = ScalarDiff.make(c1.defaultValue, null);
    }
    final ScalarDiff<BuildingHints> hintsDiff = c1.hints.equals(c2.hints) ? null : ScalarDiff.make(
        c1.hints, c2.hints);
    return ColumnDiff.make(nameDiff, tableDiff, dataSpecDiff, defaultDiff, hintsDiff, c1, c2);
  }

  public static IndexDiff compareIndexes(Index leftIndex, Index rightIndex) {
    ScalarDiff<Table> tableDiff = null;
    ScalarDiff<IndexType> typeDiff = leftIndex.type.equals(rightIndex.type) ? null : ScalarDiff.make(leftIndex.type,
        rightIndex.type);
    ScalarDiff<String> nameDiff = leftIndex.indexName.equals(rightIndex.indexName) ? null : ScalarDiff.make(
        leftIndex.indexName,
        rightIndex.indexName);
//    VectorDiff<Column> columnsDiff = compareListOfColumns(leftIndex.getColumns(), rightIndex.getColumns());
    VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns = compareListOfIndexedColumns(leftIndex.indexedColumns,
        rightIndex.indexedColumns);
    if (!leftIndex.table.equals(rightIndex.table)) {
      tableDiff = ScalarDiff.make(leftIndex.table, rightIndex.table);
    }
    return IndexDiff.make(typeDiff, nameDiff, tableDiff, indexedColumns, leftIndex, rightIndex);
  }

  private static IList<IndexedColumnDiff> getListOfIndexedColumnDiffs(IMap<String, IndexedColumn> leftIndexedColumns,
                                                                      IMap<String, IndexedColumn> rightIndexedColumns) {
    IList<IndexedColumnDiff> indexedColumnDiffs = IArrayList.empty;
    for (IEntry<String, IndexedColumn> rightIndexedColumn : rightIndexedColumns) {
      if (rightIndexedColumn != null) {
        indexedColumnDiffs = indexedColumnDiffs.add(compareIndexedCoumns(
            getIndexedColumnMatchingName(leftIndexedColumns, rightIndexedColumn.value.column.columnName),
            rightIndexedColumn.value));
      }
    }
    return indexedColumnDiffs;
  }

  private static IndexedColumnDiff compareIndexedCoumns(IndexedColumn leftIndexedColumn,
                                                        IndexedColumn rightIndexedColumn) {
    ScalarDiff<Column> columnDiff = null;
    ScalarDiff<Integer> prefixSizeDiff = null;
    ScalarDiff<SortOrder> sortOrderDiff = null;
    if (!leftIndexedColumn.column.equals(rightIndexedColumn.column)) {
      columnDiff = ScalarDiff.make(leftIndexedColumn.column, rightIndexedColumn.column);
    }
    if (leftIndexedColumn.prefixSize != rightIndexedColumn.prefixSize) {
      prefixSizeDiff = ScalarDiff.make(leftIndexedColumn.prefixSize, rightIndexedColumn.prefixSize);
    }
    if (!leftIndexedColumn.sortOrder.equals(rightIndexedColumn.sortOrder)) {
      sortOrderDiff = ScalarDiff.make(leftIndexedColumn.sortOrder, rightIndexedColumn.sortOrder);
    }
    return IndexedColumnDiff.make(columnDiff, prefixSizeDiff, sortOrderDiff);
  }

  private static IndexedColumn getIndexedColumnMatchingName(IMap<String, IndexedColumn> leftIndexedColumns,
                                                            String columnName) {
    for (IEntry<String, IndexedColumn> leftIndexedColumn : leftIndexedColumns) {
      if (leftIndexedColumn.key.equals(columnName)) {
        return leftIndexedColumn.value;
      }
    }
    //FIXME: Use observability bridge here for logging.
    System.out.println("IndexedColumn not found in list.");
    return null;
  }

  public static boolean checkTableNameInList(IList<TableContainer> tables, TableContainer t1) {
    for (TableContainer table : tables) {
      if (table.table.tableName.equals(t1.table.tableName)) {
        return true;
      }
    }
    return false;
  }

}
