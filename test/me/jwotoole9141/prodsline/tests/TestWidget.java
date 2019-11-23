/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Sep 28th, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestWidget.java
 *
 * Defines the Widget test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.prod.GenericProduct;
import me.jwotoole9141.prodsline.item.ItemType;
import me.jwotoole9141.prodsline.item.Product;

class TestWidget {

  public static void main(String[] args) {

    List<Product> productLine = new ArrayList<>();

    productLine.add(new Widget(-1, "Thingamajig", ItemType.VISUAL, "ACME"));
    productLine.add(new Widget(7, "Creative Name (TM)", ItemType.VISUAL_MOBILE, "MEGACORP"));
    productLine.add(new Widget("Thingamajig MK II", ItemType.VISUAL, "ACME"));
    productLine.add(new Widget("Creative Name II (TM)", ItemType.AUDIO, "MEGACORP"));
    productLine.add(new GenericProduct("neat thing", ItemType.AUDIO_MOBILE, "ACME"));

    Model.open();

    try {
      Product widget = productLine.get(0);
      if (widget instanceof Widget) {
        widget.setManuf("A.C.M.E.");
      }

      Model.addProduct(widget.getName(), widget.getType(), widget.getManuf());

    } catch (SQLException e) {
      e.printStackTrace();
    }

    Model.close();
  }
}
