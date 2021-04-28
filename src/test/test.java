public class test {
  public static void main(String[] args) {
    System.out.println("test1 result: " + testRetrieval("user"));
  }

  public static String testRetrieval(String tablename) {
    Table user = new Table(tablename);
    String firstname = user.select().columns("firstname","lastname");
    return firstname;
  }
}
