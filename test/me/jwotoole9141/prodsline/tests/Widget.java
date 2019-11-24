/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Sep 28th, 2019
 * PROJ: ProductionLineTracker
 * FILE: Widget.java
 *
 * Defines the Widget test class.
 */

package me.jwotoole9141.prodsline.tests;

import me.jwotoole9141.prodsline.item.ItemType;
import me.jwotoole9141.prodsline.item.Product;

/**
 * Represents a widget for testing the product class.
 *
 * @author Jared O'Toole
 */
class Widget extends Product {

  /**
   * Creates a widget.
   *
   * @param name  the widget name
   * @param type  the widget product type
   * @param manuf the widget's manufacturer
   */
  Widget(String name, ItemType type, String manuf) {
    super(name, type, manuf);
  }

  /**
   * Creates a widget with an id from the database.
   *
   * @param id    the product id
   * @param name  the widget name
   * @param type  the widget product type
   * @param manuf the widget's manufacturer
   */
  Widget(int id, String name, ItemType type, String manuf) {
    super(id, name, type, manuf);
  }
}
