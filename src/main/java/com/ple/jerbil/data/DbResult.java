package com.ple.jerbil.data;

import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.ITable;
import io.r2dbc.spi.Result;
import org.reactivestreams.Publisher;

public class DbResult {

  public static final DbResult empty = new DbResult(IArrayList.empty, IArrayList.empty);
  public final IList<ITable> resultList;
  public final IList<IList<String>> columnNames;

  public DbResult(IList<ITable> resultList, IList<IList<String>> columnNames) {
    this.resultList = resultList;
    this.columnNames = columnNames;
  }

  public static Publisher<DbResult> make(Publisher<? extends Result> execute) {
    return null;
  }

  // This should be in streaming interface only.
  //This returns the next result table in the list, which contains an Array of objects. It has a toString method which makes it
  // look exactly like the mysql results. Or a low-formatted version which takes less memory and just returns a simple table.
/*
  public DbResult nextResult() {
    //Iterate through resultList to find the next table after currentResult.
    boolean nextResultIsCorrect = false;
    for (ITable iTable : resultList) {
    if (nextResultIsCorrect) {
      return new DbResult(resultList, columnNames, Optional.of(iTable));
    }
      if (iTable.equals(currentResult)) {
        nextResultIsCorrect = true;
      }
    }
    System.out.println("Could not find any more results in the list");
    return this;
  }

  // while (DbResult.hasNextResult()) { ITable = DbResult.nextResult().get(); }
  public boolean hasNextResult() {
    return false;
  }
*/
  // Returns an array of all the objects inside a specified column. For example `name` column may be the 3rd column.
  // So getColumn("name") will return all the values of get(n, 2) which would be range of 0-numRows.
  // So (0,2), (1,2), (2,2) etc...
  public Object[] getColumn(String columnName) {
    return null;
  }

  public Object[] getColumn(int columnIndex) {
    return null;
  }
  //This returns the next row of resultList.values. Which is a Object[]. Row 1 starts at 0. If there are 5 columns row 2 starts at 5.
  // The user specifies rowNum, then result is Object[5]{rowNum*5 + rowNum*5 + 5}
  public Object[] getRow(int rowIndex) {
    return null;
  }

  // This returns a specific element from a specific row. get(0, 0) will return the first value, get(2,3) will return the 4th column value of the 3rd row.
  public Object get(int rowIndex, int columnIndex) {
    return null;
  }


}
