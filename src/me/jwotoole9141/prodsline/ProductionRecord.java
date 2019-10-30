/*
AUTH: Jared O'Toole
DATE: Wed Oct 16th 2019
PROJ: ProductionLineTracker
FILE: ProductionRecord.java

Defines the ProductionRecord class.
 */

package me.jwotoole9141.prodsline;

import java.util.Date;
import me.jwotoole9141.prodsline.items.Product;

/**
 * A collection of metadata for the production of a product in the production line.
 *
 * @author Jared O'Toole
 */
public class ProductionRecord {

  /**
   * The recorded date of production.
   */
  private Date dateProduced;

  /**
   * The recorded product identification number.
   */
  private int productID;

  /**
   * The recorded production number.
   */
  private int prodsNumber;

  /**
   * The recorded product serial number.
   */
  private String serialNumber;

//  /**
//   * Create a record for a new production.
//   *
//   * <p>
//   * This constructor is for use in the user GUI.
//   * </p>
//   *
//   * @param productID the product identification number
//   */
//  public ProductionRecord(int productID) {
//
//    this.dateProduced = new Date();
//    this.productID = productID;
//    this.prodsNumber = 0;
//    this.serialNumber = "0";
//  }

  /**
   * Create a representation of an existing record.
   *
   * <p>
   * This constructor is for use in loading database information.
   * </p>
   *
   * @param productID    the product identification number
   * @param prodsNumber  the production number
   * @param serialNumber the serial number
   * @param dateProduced the date and time of this production
   */
  public ProductionRecord(Date dateProduced, int productID, int prodsNumber, String serialNumber) {

    this.dateProduced = dateProduced;
    this.productID = productID;
    this.prodsNumber = prodsNumber;
    this.serialNumber = serialNumber;
  }

//  public ProductionRecord(Product product, int index) {
//
//    this.productID = product.getId();
//    this.serialNumber = product.genSerialNum(index);
//    this.dateProduced = new Date();
//  }

  /**
   * Get the recorded date of production.
   *
   * @return the production date & time
   */
  public Date getDateProduced() {
    return dateProduced;
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
   * Get the recorded production number.
   *
   * @return the production number
   */
  public int getProdsNumber() {
    return prodsNumber;
  }

  /**
   * Get the recorded product serial number.
   *
   * @return the product serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

//  /**
//   * Set the recorded date of production.
//   *
//   * @param dateProduced the production date & time
//   */
//  public void setDateProduced(Date dateProduced) {
//    this.dateProduced = dateProduced;
//  }
//
//  /**
//   * Set the recorded product identification number.
//   *
//   * @param productID the product id
//   */
//  public void setProductID(int productID) {
//    this.productID = productID;
//  }
//
//  /**
//   * Set the recorded production number.
//   *
//   * @param prodsNumber the production number
//   */
//  public void setProdsNumber(int prodsNumber) {
//    this.prodsNumber = prodsNumber;
//  }
//
//  /**
//   * Set the recorded product serial number.
//   *
//   * @param serialNumber the product serial number
//   */
//  public void setSerialNumber(String serialNumber) {
//    this.serialNumber = serialNumber;
//  }

  @Override
  public String toString() {
    return String.format(
        "Prod. Num: %d Product ID: %d Serial Num: %s Date: %s",
        prodsNumber, productID, serialNumber, dateProduced
    );
  }
}
