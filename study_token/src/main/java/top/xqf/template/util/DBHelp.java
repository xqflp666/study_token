package top.xqf.template.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelp {

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test"
    ,"root","123456");
  }
}
