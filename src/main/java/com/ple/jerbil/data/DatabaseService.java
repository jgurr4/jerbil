package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;

public class DatabaseService {

  public static String generateIndexName(Column... columns) {
    final String formattedColumns = formatForIndexNameGenerator(columns);
    return formatIndexName(formattedColumns) + "idx";
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
  public static String formatIndexName(String indexedColumns) {
    final String[] split = indexedColumns.toLowerCase().split(",");
    String result = "";
    for (String s : split) {
      result += s.replaceAll("\\B[aeiou]", "")
          .replaceAll("([a-z])\\1*", "$1") + "_";
    }
    return result;
  }

}
