/*
AUTH: Jared O'Toole
DATE: Fri, Sep 27th, 2019
PROJ: ProductionLineTracker
FILE: Item.java

Defines the Item interface.
 */

package me.jwotoole9141.prodsline;

/**
 * An interface for an item with an id, name, and manufacturer.
 *
 * @author Jared O'Toole
 */
public interface Item {

  /**
   * Get the identification number of the item.
   *
   * @return The ID number.
   */
  int getId();

  /**
   * Get the name of this item.
   *
   * @return The item name.
   */
  String getName();

  /**
   * Get manufacturer name of this item.
   *
   * @return The manufacturer name.
   */
  String getManufacturer();

  /**
   * Set the name of this item.
   *
   * @param name The item name.
   */
  void setName(String name);

  /**
   * Set the manufacturer name of this item.
   *
   * @param manufacturer The manufacturer name.
   */
  void setManufacturer(String manufacturer);

}
