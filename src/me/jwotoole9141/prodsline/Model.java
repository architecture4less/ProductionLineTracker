/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Sep 21th, 2019
 * PROJ: ProductionLineTracker
 * FILE: Model.java
 *
 * Defines the Model class.
 */

package me.jwotoole9141.prodsline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import me.jwotoole9141.prodsline.item.GenericProduct;
import me.jwotoole9141.prodsline.item.ItemType;
import me.jwotoole9141.prodsline.item.Product;
import me.jwotoole9141.prodsline.item.ProductionRecord;

/**
 * Facilitates interaction with the application's data.
 *
 * @author Jared O'Toole
 */
public class Model {

  /**
   * The database connection.
   */
  private static Connection conn;

  /**
   * The name of the database properties file.
   */
  private static final String DB_PROPERTIES = "db.properties";

  /**
   * Fetches the properties defined in the properties file.
   *
   * @return properties such as user and password
   */
  public static Properties getProperties() {

    Properties props = new Properties();

    try (InputStream is = Main.class.getClassLoader()
        .getResourceAsStream(DB_PROPERTIES)) {

      if (is == null) {
        throw new FileNotFoundException(String.format(
            "The resource '%s' was not found.", DB_PROPERTIES
        ));
      }
      props.load(is);

      // decrypt the stored password
      String pw = props.getProperty("password");
      if (pw != null) {
        props.setProperty("password", reverseStr(pw));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return props;
  }

  /**
   * Opens a connection to the database.
   */
  public static void open() {

    if (conn != null) {
      close();
    }
    try {
      Properties props = getProperties();

      String dbUrl = (String) Objects.requireNonNull(props.remove("url"));
      String jdbcDriver = (String) Objects.requireNonNull(props.remove("driver"));

      Class.forName(jdbcDriver);
      conn = DriverManager.getConnection(dbUrl, props);

    } catch (SQLException | ClassNotFoundException
        | ClassCastException | NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Closes the database connection.
   */
  public static void close() {

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      conn = null;
    }
  }

  /**
   * Tests the database connection.
   *
   * @return true if the database is open and valid, else false
   */
  private static boolean isOpen() {

    if (conn != null) {
      try {
        if (conn.isValid(1)) {
          return true;
        }
      } catch (SQLException ex) {
        return false;
      }
    }
    return false;
  }

  /**
   * Requires that isOpen() returns true.
   *
   * @throws IllegalStateException there is no connection to the database
   */
  private static void requireConnection() throws IllegalStateException {

    if (!isOpen()) {
      throw new IllegalStateException(
          "Attempted a database operation without a database connection.");
    }
  }

  /**
   * Adds a new product to the database and product line.
   *
   * <p>
   * New products are added to the PRODUCT table.
   * </p>
   *
   * @param name  the new product's name
   * @param type  the new product's type
   * @param manuf the new product's manufacturer
   * @return the created product
   * @throws SQLException             the database couldn't perform the operation
   * @throws IllegalArgumentException 'name' is null or empty, 'type' is null, or 'manuf' is empty
   *                                  or less than three chars
   * @throws IllegalStateException    there is no connection to the database
   */
  public static Product addProduct(String name, ItemType type, String manuf)
      throws SQLException, IllegalArgumentException, IllegalStateException {

    requireConnection();

    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO product (name, type, manuf) VALUES (?, ?, ?);")) {

      // validate the given properties...
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("A product name was not given.");
      }
      if (type == null) {
        throw new IllegalArgumentException("A product type was not selected.");
      }
      if (manuf == null || manuf.length() < 3) {
        throw new IllegalArgumentException("The Manufacturer name must be at least three chars.");
      }

      // add the given properties to the database...
      stmt.setString(1, name);
      stmt.setString(2, type.getCode());
      stmt.setString(3, manuf);
      stmt.execute();

      // create a new product to be returned...
      return new GenericProduct(getMaxProdId(), name, type, manuf);
    }
  }

  /**
   * Records the production of a product to the database.
   *
   * <p>
   * Production records are added to the PRODSRECORD table.
   * </p>
   *
   * @param prod the product being produced
   * @param qnty the quantity being produced
   * @param time the time of the production
   * @return an array of production records created
   * @throws SQLException             the database could not perform the operation
   * @throws IllegalArgumentException 'prod' is null or 'qnty' is null or less than one
   * @throws IllegalStateException    there is no connection to the database
   */
  public static List<ProductionRecord> recordProduction(Product prod, Integer qnty, Timestamp time)
      throws SQLException, IllegalArgumentException, IllegalStateException {

    // NOTE: this method may be AKA 'addToProductionDB'

    requireConnection();

    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO prodsrecord (prodid, serialnum, date) VALUES (?, ?, ?);"
    )) {

      // validate the given properties...
      if (prod == null) {
        throw new IllegalArgumentException("No product was selected.");
      }
      if (qnty == null || qnty < 1) {
        throw new IllegalArgumentException("Invalid quantity chosen.");
      }

      List<ProductionRecord> prodsRun = new ArrayList<>();
      int prodsCount = getProdsCount(prod.getId()) + 1;

      // iterate over the range of the given quantity...
      for (int i = 0; i < qnty; i++) {

        String serialNum = genSerialNum(
            prod.getManuf(), prod.getType(), prodsCount++);

        // add the given properties to a new row on the PRODSRECORD table...
        stmt.setInt(1, prod.getId());
        stmt.setString(2, serialNum);
        stmt.setTimestamp(3, time);
        stmt.execute();

        // create a new record to be returned...
        prodsRun.add(new ProductionRecord(
            getMaxProdsNum(), prod.getId(), serialNum, time));
      }
      return prodsRun;
    }
  }

  /**
   * Gets a product from the database with the specified id.
   *
   * @param id the product id number.
   * @return the product, if it exists, else null
   * @throws IllegalStateException there is no connection to the database
   */
  public static Product getProduct(int id) throws IllegalStateException {

    requireConnection();

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
   * Gets a list of all products in the database.
   *
   * <p>
   * A product will not be returned if its row in the database contains an invalid property.
   * </p>
   *
   * @return a list of valid products
   * @throws IllegalStateException there is no connection to the database
   */
  static List<Product> getProducts() throws IllegalStateException {

    // NOTE: this method may be AKA 'loadProductList'

    requireConnection();

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
   * Gets a list of all production records in the database.
   *
   * @return a list of valid production records
   * @throws IllegalStateException there is no connection to the database
   */
  static List<ProductionRecord> getProdsRecords() throws IllegalStateException {

    // NOTE: this method may be AKA 'loadProductionLog'

    requireConnection();

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
        Timestamp date = rs.getTimestamp("date");

        // create a new prods record to be returned...
        records.add(new ProductionRecord(prodsNum, prodId, serialNum, date));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return records;
  }

  /**
   * Gets the maximum product id present in the database's PRODUCT table.
   *
   * <p>
   * This represents the most recently added product.
   * </p>
   *
   * @return the maximum product id if any products exist, else zero
   * @throws IllegalStateException there is no connection to the database
   */
  private static int getMaxProdId() throws IllegalStateException {

    requireConnection();

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
   * Gets the maximum production number present in the database's PRODSRECORD table.
   *
   * <p>
   * This represents the most recently added production record.
   * </p>
   *
   * @return the maximum production number if any records exist, else zero
   * @throws IllegalStateException there is no connection to the database
   */
  private static int getMaxProdsNum() throws IllegalStateException {

    requireConnection();

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
   * Gets the production count for a specified product.
   *
   * @param prodId the product's id
   * @return the number of production records that exist for this product if it exists, else zero
   * @throws IllegalStateException there is no connection to the database
   */
  public static int getProdsCount(int prodId) throws IllegalStateException {

    requireConnection();

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
   * Generates a serial number using the given properties.
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

  /**
   * Gets a string that is the reverse of the given string using recursion.
   *
   * @param string the string to be reversed
   * @return the reversed string
   */
  private static String reverseStr(String string) {

    return (string.length() <= 1) ? string : string.substring(string.length() - 1 /* to end */)
        + reverseStr(string.substring(0, string.length() - 1));
  }
}
