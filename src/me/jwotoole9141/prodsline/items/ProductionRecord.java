/*
AUTH: Jared O'Toole
DATE: Wed Oct 16th 2019
PROJ: ProductionLineTracker
FILE: ProductionRecord.java

Defines the ProductionRecord class.
 */

package me.jwotoole9141.prodsline.items;

import java.util.Date;
import me.jwotoole9141.prodsline.Model;

/**
 * A collection of metadata for the production of a product in the production line.
 *
 * @author Jared O'Toole
 */
public class ProductionRecord {

  /**
   * The recorded production number.
   */
  private int prodsNumber;

  /**
   * The recorded product identification number.
   */
  private int productID;

  /**
   * The recorded product serial number.
   */
  private String serialNumber;

  /**
   * The recorded date of production.
   */
  private Date dateProduced;

  /**
   * Create a record for a new production.
   *
   * <p>
   * This constructor is for use in the user GUI.
   * </p>
   *
   * @param productID the product identification number
   */
  public ProductionRecord(int productID) {

    this.prodsNumber = -1;
    this.productID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  public ProductionRecord(Product product, int prodsCount) {

    this.prodsNumber = -1;
    this.productID = product.getId();
    this.serialNumber = Model.genSerialNum(product.getManuf(), product.getType(), prodsCount);
    this.dateProduced = new Date();
  }

  /**
   * Create a representation of an existing record.
   *
   * <p>
   * This constructor is for use in loading database information.
   * </p>
   * @param prodsNumber  the production number
   * @param productID    the product identification number
   * @param serialNumber the serial number
   * @param dateProduced the date and time of this production
   */
  public ProductionRecord(int prodsNumber, int productID, String serialNumber, Date dateProduced) {

    this.prodsNumber = prodsNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Get the recorded production number.
   *
   * @return the production number
   */
  public int getProdsNumber() {
    return prodsNumber;
  }

  /**
   * Get the recorded product identification number.
   *
   * @return the product id
   */
  public int getProductID() {
    return productID;
  }

  /**
   * Get the recorded product serial number.
   *
   * @return the product serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Get the recorded date of production.
   *
   * @return the production date & time
   */
  public Date getDateProduced() {
    return dateProduced;
  }

  /**
   * Set the recorded production number.
   *
   * @param prodsNumber the production number
   */
  public void setProdsNumber(int prodsNumber) {
    this.prodsNumber = prodsNumber;
  }

  /**
   * Set the recorded product identification number.
   *
   * @param productID the product id
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Set the recorded product serial number.
   *
   * @param serialNumber the product serial number
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Set the recorded date of production.
   *
   * @param dateProduced the production date & time
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString() {

    if (productID >= 0) {
      Product prod = Model.getProduct(productID);

      if (prod != null) {
        return String.format(
            "Prod. Num: %d Product Name: %s Serial Num: %s Date: %s",
            prodsNumber, prod.getName(), serialNumber, dateProduced
        );
      }
    }
    return String.format(
        "Prod. Num: %d Product ID: %d Serial Num: %s Date: %s",
        prodsNumber, productID, serialNumber, dateProduced
    );
  }
}
