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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.items.Product;

/**
 * Facilitates interaction with the application's database.
 *
 * @author Jared O'Toole
 */
public class Model {

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
   * @param name  the new product's name
   * @param type  the new product's type
   * @param manuf the new product's manufacturer
   */
  public static void addProduct(String name, ItemType type, String manuf) {

    // try to add a row to the products table...
    try (Statement stmt = conn.createStatement()) {

      stmt.execute(String.format(  // language=SQL
          "INSERT INTO product (name, type, manuf)"
              + " VALUES ('%s', '%s', '%s');",
          name, type.getCode(), manuf
      ));
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public static void recordProduction(Product prod, int qnty) {

    Date curDate = new Date();
    int idProdsNum = getMaxProdsNum(prod.getId()) + 1;

    // try to add a row to the records table...
    try (Statement stmt = conn.createStatement()) {
      for (int i = 0; i < qnty; i++) {

        stmt.execute(String.format(  // language=SQL
            "INSERT INTO prodsrecord (date, prodid, prodsnum, serialnum)"
                + " VALUES ('%s', '%s', '%s', '%s');",
            curDate, prod.getId(), idProdsNum, prod.genSerialNum(idProdsNum)
        ));
        idProdsNum++;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public static int getMaxProdId() {

    try (Statement stmt = conn.createStatement()) {

      stmt.execute("SELECT MAX(id) FROM product");
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        return rs.getInt("id");
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  public static int getMaxProdsNum(int prodId) {

    try (Statement stmt = conn.createStatement()) {

      stmt.execute(String.format(  // language=SQL
          "SELECT MAX(prodsnum) FROM prodsrecord WHERE (prodid = '%s')",
          prodId
      ));
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        return rs.getInt("prodsnum");
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }
}
