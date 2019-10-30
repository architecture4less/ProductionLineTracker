/*
AUTH: Jared O'Toole
DATE: Fri, Sep 27th, 2019
PROJ: ProductionLineTracker
FILE: Item.java

Defines the Item interface.
 */

package me.jwotoole9141.prodsline.items;

/**
 * An interface for an item with an id, name, and manufacturer.
 *
 * @author Jared O'Toole
 */
public interface Item {

  /**
   * Get the identification number of the item.
   *
   * @return the ID number
   */
  int getId();

  /**
   * Get the name of this item.
   *
   * @return the item name
   */
  String getName();

  /**
   * Get manufacturer name of this item.
   *
   * @return the manufacturer name
   */
  String getManuf();

  /**
   * Set the name of this item.
   *
   * @param name the item name
   */
  void setName(String name);

  /**
   * Set the manufacturer name of this item.
   *
   * @param manuf the manufacturer name
   */
  void setManuf(String manuf);
}
