/*
AUTH: Jared O'Toole
DATE: Wed Oct 16th 2019
PROJ: ProductionLineTracker
FILE: ProductionRecord.java

Defines the ProductionRecord class.
 */

package me.jwotoole9141.prodsline;

import java.util.Date;

/**
 * A collection of metadata for the production of a product in the production line.
 */
public class ProductionRecord {

  /**
   * The recorded product identification number.
   */
  private int productID;

  /**
   * The recorded production number.
   */
  private int productionNumber;

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

    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  /**
   * Create a representation of an existing record.
   *
   * <p>
   * This constructor is for use in loading database information.
   * </p>
   *
   * @param productID        the product identification number
   * @param productionNumber the production number
   * @param serialNumber     the serial number
   * @param dateProduced     the date and time of this production
   */
  public ProductionRecord(int productID, int productionNumber, String serialNumber,
      Date dateProduced) {

    this.productID = productID;
    this.productionNumber = productionNumber;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
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
   * Get the recorded date of production.
   *
   * @return the production date & time
   */
  public Date getDateProduced() {
    return dateProduced;
  }

  /**
   * Get the recorded production number.
   *
   * @return the production number
   */
  public int getProductionNumber() {
    return productionNumber;
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
   * Set the recorded date of production.
   *
   * @param dateProduced the production date & time
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
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
   * Set the recorded production number.
   *
   * @param productionNumber the production number
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   *  Set the recorded product serial number.
   *
   * @param serialNumber the product serial number
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  @Override
  public String toString() {
    return String.format(
        "Prod. Num: %d Product ID: %d Serial Num: %s Date: %s",
        productionNumber, productID, serialNumber, dateProduced
    );
  }
}
