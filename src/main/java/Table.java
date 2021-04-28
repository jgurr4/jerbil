public final class Table {
  private String tableName;
  private String stmt;

  public Table(String name) {
    this.tableName = name;
  }
    public Table select() {
      this.stmt = "Select ";
      return this;
  }

    public String columns(String... columns) {
    String colList = this.stmt;
    for (String column : columns) {
      colList += column + ", ";
    }
    return colList.replaceAll(", $","");
  }

}
/*
  public String select(String[] columns) {
    String colList = "SELECT ";
    for (String column : columns) {
      colList += column + " ";
    }
    return colList;
  }

  public String select(String column) {
    String colList = "SELECT " + column;
    return colList;
  }
*/
