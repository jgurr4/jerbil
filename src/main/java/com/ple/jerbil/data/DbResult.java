package com.ple.jerbil.data;

import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.ITable;
import reactor.core.publisher.Mono;

public class DbResult {

  public static final DbResult empty = new DbResult(ITable.empty, IArrayList.empty, IArrayList.empty, 0);
  public final ITable result;
  public final IList<String> error;
  public final IList<String> warning;
  public final int rowsUpdated;

  public DbResult(ITable result, IList<String> error, IList<String> warning, int rowsUpdated) {
    this.result = result;
    this.error = error;
    this.warning = warning;
    this.rowsUpdated = rowsUpdated;
  }

  public static DbResult make(ITable resultList) {
    return new DbResult(resultList, IArrayList.empty, IArrayList.empty, 0);
  }

  public static DbResult make(ITable result, IList<String> error, IList<String> warning, int rowsUpdated) {
    return new DbResult(result, error, warning, rowsUpdated);
  }

  public Object[] getColumn(String columnName) {
    return result.getColumn(columnName);
  }

  public Object[] getColumn(int columnIndex) {
    return result.getColumn(columnIndex);
  }

  public Object[] getRow(int rowIndex) {
    return result.getRow(rowIndex);
  }

  public Object get(int rowIndex, int columnIndex) {
    return result.get(rowIndex, columnIndex);
  }

}
