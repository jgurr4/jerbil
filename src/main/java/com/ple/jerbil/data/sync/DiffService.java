package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;
import com.ple.jerbil.data.GenericInterfaces.ReactorMono;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static ReactiveWrapper<DbDiff> compareDatabases(ReactiveWrapper<DatabaseContainer> leftDbc,
                                                         ReactiveWrapper<DatabaseContainer> rightDbc) {
    //This method must compare every part of database starting with database props.
    //Step 1: compare database props
    ReactorMono.make(Mono.from(leftDbc.unwrapMono().map(dbc -> dbc.database))
      .concatWith(rightDbc.unwrapMono().map(dbc -> dbc.database))
      .collectList()
      .map(databases -> compareDatabaseProps(databases.get(0), databases.get(1))));

    //Step 2: compare list of tables by comparing each table together and getting all the diffs between them.
    // Including the missing and extra tables.

    //Step 3: compare list of columns and their properties.

    // Step 4: create and return a DbDiff using the database, table and column diffs you discovered.
    return null;
  }

  private static DbDiff compareDatabaseProps(Database leftDb, Database rightDb) {
    //TODO: This method should compare the database properties only. Not tables or columns.
    return null;
  }

  public static VectorDiff<TableContainer> compareListOfTables(IList<TableContainer> leftTables, IList<TableContainer> rightTables) {
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
    IList<Diff<TableContainer>> update = getListOfTableDiffs(leftTables, matches);
    IList<TableContainer> delete = getExtraTables(leftTables, nonMatches);
    return new VectorDiff<TableContainer>(create, delete, update);
  }

  public static IList<TableContainer> getMissingTables(IList<TableContainer> leftTables, IList<TableContainer> rightTables) {
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

  public static IList<TableContainer> getExtraTables(IList<TableContainer> leftTables, IList<TableContainer> rightTables) {
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

  public static IList<Diff<TableContainer>> getListOfTableDiffs(IList<TableContainer> leftTables, IList<TableContainer> rightTables) {
    //TODO: Ask if this is bad practice to use Streams or declarative style programming for accomplishing intermediate
    // operation in a synchronous/mostly imperative style method.
    return Flux.fromIterable(rightTables)
      .filter(Objects::nonNull)
      .map(table -> compareTables(getTableMatchingName(leftTables, table.table.tableName), table))
      .collectList()
      .map(tableDiffs -> IArrayList.make(tableDiffs.toArray(TableDiff.empty)))
      .defaultIfEmpty(null)
      .block();
  }

  public static IList<TableContainer> filterOutMatchingTables(IList<TableContainer> leftTables, IList<TableContainer> rightTables) {
    IList<TableContainer> nonMatchingTables = IArrayList.make();
    for (TableContainer rightTable : rightTables) {
      if (!leftTables.contains(rightTable)) {
        nonMatchingTables = nonMatchingTables.add(rightTable);
      }
    }
    return nonMatchingTables;
  }

  public static Diff<TableContainer> compareTables(TableContainer t1, TableContainer t2) {
/*
//FIXME: broken after updates to database and tables.
    ScalarDiff<String> nameDiff = t1.tableName.equals(t2.tableName) ? null : ScalarDiff.make(t1.tableName, t2.tableName);
    VectorDiff<Column> columnsDiff = compareListOfColumns(t1.columns, t2.columns);
    ScalarDiff<StorageEngine> storageEngineDiff = t1.storageEngine.name().equals(t2.storageEngine.name()) ? null : ScalarDiff.make(
        t1.storageEngine, t2.storageEngine);
    return TableDiff.make(nameDiff, columnsDiff, storageEngineDiff);
*/
    return null;
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

  public static VectorDiff<Column> compareListOfColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
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
    IList<Diff<Column>> update = getListOfColumnDiffs(leftColumns, matches);
    IList<Column> delete = getExtraColumns(leftColumns, nonMatches);
    return new VectorDiff<>(create, delete, update);
    //TODO: Decide if results should be empty or if object returned should be null.
  }

  public static IList<Diff<Column>> getListOfColumnDiffs(IList<Column> leftColumns, IList<Column> rightColumns) {
    return Flux.fromIterable(rightColumns)
      .filter(Objects::nonNull)
      .map(column -> compareColumns(getColumnMatchingName(leftColumns, column.columnName), column))
      .collectList()
      .map(columnDiffs -> IArrayList.make(columnDiffs.toArray(ColumnDiff.empty)))
      .defaultIfEmpty(null)
      .block();
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

  public static Diff<Column> compareColumns(Column c1, Column c2) {
    final ScalarDiff<String> nameDiff = c1.columnName.equals(c2.columnName) ? null : ScalarDiff.make(c1.columnName, c2.columnName);
    final ScalarDiff<DataSpec> dataSpecDiff = c1.dataSpec.equals(c2.dataSpec) ? null : ScalarDiff.make(
      c1.dataSpec, c2.dataSpec);
    final VectorDiff<Index> indexDiff = compareIndexes(c1, c2);
    final ScalarDiff<Expression> generatedDiff = c1.generatedFrom.equals(
      c2.generatedFrom) ? null : ScalarDiff.make(c1.generatedFrom, c2.generatedFrom);
    final ScalarDiff<Expression> defaultDiff = c1.defaultValue.equals(c2.defaultValue) ? null : ScalarDiff.make(
      c1.defaultValue, c2.defaultValue);
//    return ColumnDiff.make(nameDiff, dataSpecDiff, indexDiff, generatedDiff, defaultDiff);
    return null;
  }

  private static VectorDiff<Index> compareIndexes(Column c1, Column c2) {
//    if (!c1.indexed && c2.indexed || c1.indexed && !c2.indexed) {

//    }
//    final IList<IndexSpec> create = IArrayList.make(IndexSet.primary);
//    final IList<IndexSpec> delete = IArrayList.make(IndexSet.secondary);
//    final IList<IndexSpec> update = IArrayList.make(IndexDiff.make(IndexSpec.make(Index.fulltext), IndexSpec.make(Index.secondary, 3, IndexSort.ascending)));
//    VectorDiff.make(create, delete, update);
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

/* Example of how to turn these methods into generic methods.
   Note: This is not necessarily better because it still uses same amount of code. Better to somehow do it without checking instances.
  public static <T> boolean checkTableNameInList(IList<T> objects, T t1) {
    if (t1 instanceof Table) {
      for (T object : objects) {
        if (((Table) object).name.equals(((Table)t1).name)) {
          return true;
        }
      }
    } else if (t1 instanceof Column) {
      for (T object : objects) {
        if (((Column) object).name.equals(((Column)t1).name)) {
          return true;
        }
      }
    }
    return false;
  }
 */
}
