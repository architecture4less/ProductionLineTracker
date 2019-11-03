/*
AUTH: Jared O'Toole
DATE: Sat, Sep 28th, 2019
PROJ: ProductionLineTracker
FILE: Widget.java

Defines the Widget test class.
 */

package me.jwotoole9141.prodsline.tests;

import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.items.Product;

class Widget extends Product {

  Widget(String name, ItemType type, String manuf) {
    super(name, type, manuf);
  }

  Widget(int id, String name, ItemType type, String manuf) {
    super(id, name, type, manuf);
  }
}