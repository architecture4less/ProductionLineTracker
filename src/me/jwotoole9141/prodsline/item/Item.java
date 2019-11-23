/*
 * AUTH: Jared O'Toole
 * DATE: Fri, Sep 27th, 2019
 * PROJ: ProductionLineTracker
 * FILE: Item.java
 *
 * Defines the Item interface.
 */

package me.jwotoole9141.prodsline.item;

/**
 * An interface for an item with an id, name, and manufacturer.
 *
 * @author Jared O'Toole
 */
public interface Item {

  /**
   * Gets the identification number of the item.
   *
   * @return the ID number
   */
  int getId();

  /**
   * Gets the name of this item.
   *
   * @return the item name
   */
  String getName();

  /**
   * Gets manufacturer name of this item.
   *
   * @return the manufacturer name
   */
  String getManuf();

  /**
   * Sets the name of this item.
   *
   * @param name the item name
   */
  void setName(String name);

  /**
   * Sets the manufacturer name of this item.
   *
   * @param manuf the manufacturer name
   */
  void setManuf(String manuf);
}
