public interface SqlOptions {
  public static String columns(String[] columns) {
    String colList = "";
    for (String column : columns) {
      colList += column + ", ";
    }
    return colList;
  }
}
