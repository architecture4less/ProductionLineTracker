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
  public static Product addProduct(String name, ItemType type, String manuf) {

    // try to add a row to the products table...
    try (Statement stmt = conn.createStatement()) {

      stmt.execute(String.format(  // language=SQL
          "INSERT INTO product (name, type, manuf)"
              + " VALUES ('%s', '%s', '%s');",
          name, type.getCode(), manuf
      ));
      return new Product(getMaxProdId(), name, type, manuf);

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static ProductionRecord[] recordProduction(Product prod, Integer qnty) {

    if (prod != null && qnty != null && qnty >= 1) {

      ProductionRecord[] records = new ProductionRecord[qnty];
      int prodsCount = getProdsCount(prod.getId()) + 1;
      Date curDate = new Date();

      // try to add a row to the records table...
      try (Statement stmt = conn.createStatement()) {
        for (int i = 0; i < qnty; i++) {

          String serialNum = genSerialNum(
              prod.getManuf(), prod.getType(), prodsCount
          );

          stmt.execute(String.format(  // language=SQL
              "INSERT INTO prodsrecord (date, prodid, prodsnum, serialnum)"
                  + " VALUES ('%s', '%s', '%s', '%s');",
              curDate, prod.getId(), prodsCount, serialNum
          ));
          records[i] = new ProductionRecord(
              getMaxProdsNum(), prod.getId(), serialNum, curDate
          );
        }
        return records;

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    return new ProductionRecord[0];
  }

  public static int getMaxProdId() {

    try (Statement stmt = conn.createStatement()) {

      stmt.execute("SELECT MAX(id) AS id FROM product");
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

  public static int getMaxProdsNum() {

    try (Statement stmt = conn.createStatement()) {

      stmt.execute("SELECT MAX(prodsnum) AS prodsnum FROM prodsrecord");
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        return rs.getInt("prodsnum");
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  public static int getProdsCount(int prodId) {

    try (Statement stmt = conn.createStatement()) {

      stmt.execute(String.format(  // language=SQL
          "SELECT COUNT(*) AS prodscount FROM prodsrecord WHERE (prodid = '%s')",
          prodId
      ));
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        return rs.getInt("prodscount");
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  public static String genSerialNum(String manuf, ItemType type, int prodsCount) {
    return manuf.substring(0, 3) + type.getCode() + String.format("%05d", prodsCount);
  }
}
