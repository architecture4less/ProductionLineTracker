/*
AUTH: Jared O'Toole
DATE: Sat, Sep 28th, 2019
PROJ: ProductionLineTracker
FILE: TestWidget.java

Defines the test Widget driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.items.ItemType;

public class TestWidget {

  public static void main(String[] args) {

    List<Widget> productLine = new ArrayList<>();

    productLine.add(new Widget(-1, "Thingamajig", ItemType.VISUAL, "ACME"));

    Model.open();

    try {
      Widget widget = productLine.get(0);
      Model.addProduct(widget.getName(), widget.getType(), widget.getManuf());

    } catch (SQLException e) {
      e.printStackTrace();
    }

    Model.close();
  }
}
