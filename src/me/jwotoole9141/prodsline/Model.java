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
class Model {

  /**
   * The JDBC driver class to use.
   */
  private static final String JDBC_DRIVER = "org.h2.Driver";

  /**
   * The URL of the database.
   */
  private static final String DB_URL = "jdbc:h2:./res/database";

  /**
   * The database connection.
   */
  private static Connection conn;

  /**
   * Open a connection to the database.
   */
  static void open() {

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
  static void close() {

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
  static void addProduct(ItemType type, String manuf, String name) {

    // try to add a row to the product table...
    try {
      Statement stmt = conn.createStatement();
      stmt.execute(String.format(
          "INSERT INTO Product(type, manufacturer, name) "
              + "VALUES ( '%s', '%s', '%s' );",
          type.getCode(), manuf, name
      ));
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
