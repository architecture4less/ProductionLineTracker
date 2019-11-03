/*
AUTH: Jared O'Toole
DATE: Sat, Sep 21th, 2019
PROJ: ProductionLineTracker
FILE: Model.java

Defines the Model class.
 */

package me.jwotoole9141.prodsline;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.jwotoole9141.prodsline.items.GenericProduct;
import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.items.Product;
import me.jwotoole9141.prodsline.items.ProductionRecord;

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
   * The list of products used in the GUI.
   */
  static final ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Open a connection to the database.
   */
  public static void open() {

    // try to open a database connection...
    try {
      Class.forName(JDBC_DRIVER);  // make sure the h2 driver class exists
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
   * <p>
   * Products are added to the PRODUCT table.
   * </p>
   *
   * @param name  the new product's name
   * @param type  the new product's type
   * @param manuf the new product's manufacturer
   * @return the created product
   * @throws SQLException             the database couldn't perform the operation
   * @throws IllegalArgumentException 'name' is null or empty, 'type' is null or unsupported, or
   *                                  'manuf' is empty or less than three chars
   */
  public static Product addProduct(String name, ItemType type, String manuf)
      throws SQLException, IllegalArgumentException {

    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO product (name, type, manuf) VALUES (?, ?, ?);"
    )) {

      // validate the given parameters...
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("A product name was not given.");
      }
      if (type == null) {
        throw new IllegalArgumentException("A product type was not selected.");
      }
      if (manuf == null || manuf.length() < 3) {
        throw new IllegalArgumentException("The Manufacturer name must be at least three chars.");
      }

      // add the given properties to a new row on the PRODUCT table...
      stmt.setString(1, name);
      stmt.setString(2, type.getCode());
      stmt.setString(3, manuf);
      stmt.execute();

      // return a new product of the appropriate class...
      return new GenericProduct(getMaxProdId(), name, type, manuf);
    }
  }

  /**
   * Record the production of a product to the database.
   *
   * <p>
   * Production records are added to the PRODSRECORD table.
   * </p>
   *
   * @param prod the product being produced
   * @param qnty the quantity being produced
   * @return an array of production records created
   * @throws SQLException             the database could not perform the operation
   * @throws IllegalArgumentException 'prod' is null or 'qnty' is null or less than one
   */
  public static ProductionRecord[] recordProduction(Product prod, Integer qnty)
      throws SQLException, IllegalArgumentException {

    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO prodsrecord (prodid, serialnum, date) VALUES (?, ?, ?);"
    )) {

      // validate the given parameters...
      if (prod == null) {
        throw new IllegalArgumentException("No product was selected.");
      }
      if (qnty == null || qnty < 1) {
        throw new IllegalArgumentException("Invalid quantity chosen.");
      }

      ProductionRecord[] records = new ProductionRecord[qnty];
      int prodsCount = getProdsCount(prod.getId()) + 1;
      Date curDate = new Date(System.currentTimeMillis());

      // iterate over the range of the given quantity...
      for (int i = 0; i < qnty; i++) {

        String serialNum = genSerialNum(
            prod.getManuf(), prod.getType(), prodsCount++
        );

        // add the given properties to a new row on the PRODSRECORD table...
        stmt.setInt(1, prod.getId());
        stmt.setString(2, serialNum);
        stmt.setDate(3, curDate);
        stmt.execute();

        // create a new record object to be returned...
        records[i] = new ProductionRecord(
            getMaxProdsNum(), prod.getId(), serialNum, curDate
        );
      }
      return records;
    }
  }

  /**
   * Get a product from the database with the specified id.
   *
   * @param id the product id number.
   * @return the product, if it exists, else null
   */
  public static Product getProduct(int id) {

    try (PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM product WHERE id = ?"
    )) {

      stmt.setInt(1, id);
      stmt.execute();

      try (ResultSet rs = stmt.getResultSet()) {
        if (rs.next()) {

          // get properties from the result set...
          String name = rs.getString("name");
          ItemType type = ItemType.getFromCode(rs.getString("type"));
          String manuf = rs.getString("manuf");

          // return a new product of the appropriate class...
          return new GenericProduct(id, name, type, manuf);
        }
      } catch (IllegalArgumentException ex) {
        return null;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Get a list of all products in the database.
   *
   * <p>
   * A product will not be returned if its row in the database contains an invalid property.
   * </p>
   *
   * @return a list of valid products
   */
  static List<Product> getProducts() {

    List<Product> products = new ArrayList<>();

    try (
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM product");

        ResultSet rs = stmt.executeQuery()
    ) {
      while (rs.next()) {

        // get properties from the result set...
        int id = rs.getInt("id");
        String name = rs.getString("name");
        ItemType type;
        try {
          type = ItemType.getFromCode(rs.getString("type"));

        } catch (IllegalArgumentException ex) {
          continue;
        }
        String manuf = rs.getString("manuf");

        // create a new product of the appropriate class to be returned...
        products.add(new GenericProduct(id, name, type, manuf));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return products;
  }

  /**
   * Get a list of all production records in the database.
   *
   * @return a list of valid production records
   */
  static List<ProductionRecord> getProdsRecords() {

    List<ProductionRecord> records = new ArrayList<>();

    try (
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM prodsrecord");

        ResultSet rs = stmt.executeQuery()
    ) {
      while (rs.next()) {

        // get properties from the result set...
        int prodsNum = rs.getInt("prodsnum");
        int prodId = rs.getInt("prodid");
        String serialNum = rs.getString("serialnum");
        Date date = rs.getDate("date");

        // create a new prods record to be returned...
        records.add(new ProductionRecord(prodsNum, prodId, serialNum, date));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return records;
  }

  /**
   * Get the maximum product id present in the database's PRODUCT table.
   *
   * <p>
   * This represents the most recently added product.
   * </p>
   *
   * @return the maximum product id if any products exist, else zero
   */
  private static int getMaxProdId() {

    try (
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT MAX(id) AS id FROM product");

        ResultSet rs = stmt.executeQuery()
    ) {
      if (rs.next()) {
        return rs.getInt("id");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  /**
   * Get the maximum production number present in the database's PRODSRECORD table.
   *
   * <p>
   * This represents the most recently added production record.
   * </p>
   *
   * @return the maximum production number if any records exist, else zero
   */
  private static int getMaxProdsNum() {

    try (
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT MAX(prodsnum) AS prodsnum FROM prodsrecord");

        ResultSet rs = stmt.executeQuery()
    ) {
      if (rs.next()) {
        return rs.getInt("prodsnum");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  /**
   * Get the production count for a specified product.
   *
   * @param prodId the product's id
   * @return the number of production records that exist for this product if it exists, else zero
   */
  public static int getProdsCount(int prodId) {

    try (PreparedStatement stmt = conn.prepareStatement(
        "SELECT COUNT(*) AS prodscount FROM prodsrecord WHERE (prodid = ?)"
    )) {

      stmt.setInt(1, prodId);
      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          return rs.getInt("prodscount");
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  /**
   * Generate a serial number using the given properties.
   *
   * <p>
   * The serial number is composed of the first three letters of the manufacturer's name, followed
   * by the item type's two-letter code, and finally a five-digit id-specific production count.
   * </p>
   *
   * @param manuf      a product's manufacturer name
   * @param type       a product's item type
   * @param prodsCount the production count of a specific product
   * @return the serial number
   */
  public static String genSerialNum(String manuf, ItemType type, int prodsCount) {

    return manuf.substring(0, 3).toUpperCase()
        + type.getCode()
        + String.format("%05d", prodsCount);
  }
}
