package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;

public class DatabaseService {

  public static String generateIndexName(Column... columns) {
    return generateIndexName(IArrayList.make(), columns);
  }

  public static String generateIndexName(IList<String> listOfIdxNames, Column... columns) {
    final String formattedColumns = formatForIndexNameGenerator(columns);
    String idxName = createIndexName(formattedColumns);
    int i = 2;
    while (listOfIdxNames.contains(idxName)) {
      idxName += "_" + i;
      i++;
    }
    return idxName + "_idx";
  }

  public static String formatForIndexNameGenerator(Column... columns) {
    String separator = "";
    String indexedColumns = "";
    for (Column column : columns) {
      indexedColumns += separator + column.columnName;
      separator = ",";
    }
    return indexedColumns;
  }

  //FIXME: Make it remove Id or _id and also handle camelcase and _ separated words differently. For example: itemId and item_id should become itm_idx.
  // Or also user_info or userInfo = usrinf instead of usrnf
  public static String createIndexName(String indexedColumns) {
    final String[] split = indexedColumns.toLowerCase().split(",");
    String result = "";
    String separator = "";
    for (String s : split) {
      result += separator + s.replaceAll("\\B[aeiou]", "")
          .replaceAll("`", "")
          .replaceAll("([a-z])\\1*", "$1");
      separator = "_";
    }
    return result;
  }

}
