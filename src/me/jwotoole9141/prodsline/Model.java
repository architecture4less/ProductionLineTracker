/*
AUTH: Jared O'Toole
DATE: Sat, Sep 21th, 2019
PROJ: ProductionLineTracker
FILE: Model.java

Defines the Model class.
 */

package me.jwotoole9141.prodsline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Facilitates interaction with the application's database.
 *
 * @author Jared O'Toole
 */
@SuppressWarnings("WeakerAccess")
public class Model {

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/database";

  private static Connection conn;

  /**
   * Open a connection to the database.
   */
  public static void open() {

    // try to open a database conn...
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Close the database connection.
   */
  public static void close() {

    // try to close the database conn...
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Add a new product to the database.
   *
   * @param type  The new product's type.
   * @param manuf The new product's manufacturer.
   * @param name  The new product's name.
   */
  public static void addProduct(String type, String manuf, String name) {

    // try to add a row to the product table...
    try {
      Statement stmt = conn.createStatement();
      stmt.execute(String.format(
          "INSERT INTO Product(type, manufacturer, name) "
              + "VALUES ( '%s', '%s', '%s' );",
          type, manuf, name
      ));
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
