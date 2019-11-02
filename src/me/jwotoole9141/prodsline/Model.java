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
import me.jwotoole9141.prodsline.items.AudioPlayer;
import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.items.MoviePlayer;
import me.jwotoole9141.prodsline.items.Product;
import me.jwotoole9141.prodsline.items.ProductionRecord;

/**
 * Facilitates interaction with the application's database.
 *
 * @author Jared O'Toole
 */
public class Model {

  public static final ObservableList<Product> productLine = FXCollections.observableArrayList();

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
  public static void open() {

    // try to open a database conn...
    try {
      Class.forName(JDBC_DRIVER);  // make sure h2 driver class exists
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
   * @param name  the new product's name
   * @param type  the new product's type
   * @param manuf the new product's manufacturer
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
      if(type == ItemType.AUDIO_MOBILE || type == ItemType.VISUAL_MOBILE) {
        throw new IllegalArgumentException("The chosen item type is not yet supported.");
      }
      if (manuf == null || manuf.length() < 3) {
        throw new IllegalArgumentException("The Manufacturer name must be at least three chars.");
      }

      // add the given properties to a new row on the product table...
      stmt.setString(1, name);
      stmt.setString(2, type.getCode());
      stmt.setString(3, manuf);
      stmt.execute();

      // return a new product of the appropriate class...
      switch (type) {

        case AUDIO:
          return new AudioPlayer(getMaxProdId(), name, manuf);

        case VISUAL:
          return new MoviePlayer(getMaxProdId(), name, manuf);
      }
    }
    // shouldn't get here...
    throw new AssertionError("Unhandled ItemType: '" + type.name() + "'");
  }

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

        // add the given properties to a new row on the records table...
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
          switch (type) {

            case AUDIO:
              return new AudioPlayer(id, name, manuf);

            case VISUAL:
              return new MoviePlayer(id, name, manuf);
          }
        }
      } catch (IllegalArgumentException ignored) {

      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static List<Product> getProducts() {

    List<Product> products = new ArrayList<>();

    try (
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product");
        ResultSet rs = stmt.executeQuery()
    ) {
      while (rs.next()) {
        try {

          // get properties from the result set...
          int id = rs.getInt("id");
          String name = rs.getString("name");
          ItemType type = ItemType.getFromCode(rs.getString("type"));
          String manuf = rs.getString("manuf");

          // create a new product of the appropriate class to be returned...
          switch (type) {

            case AUDIO:
              products.add(new AudioPlayer(id, name, manuf));
              break;

            case VISUAL:
              products.add(new MoviePlayer(id, name, manuf));
              break;
          }
        } catch (IllegalArgumentException ignored) {

        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return products;
  }

  public static List<ProductionRecord> getProdsRecords() {

    List<ProductionRecord> records = new ArrayList<>();

    try (
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM prodsrecord");
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

  public static int getMaxProdId() {

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

  public static int getMaxProdsNum() {

    try (
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT MAX(prodsnum) AS prodsnum FROM prodsrecord");

        ResultSet rs = stmt.executeQuery();
    ) {
      if (rs.next()) {
        return rs.getInt("prodsnum");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

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

  public static String genSerialNum(String manuf, ItemType type, int prodsCount) {

    return manuf.substring(0, 3).toUpperCase()
        + type.getCode()
        + String.format("%05d", prodsCount);
  }
}
