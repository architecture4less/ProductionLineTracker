/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestProducts.java
 *
 * Defines the TestProducts test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.jwotoole9141.prodsline.item.AudioPlayer;
import me.jwotoole9141.prodsline.item.GenericProduct;
import me.jwotoole9141.prodsline.item.ItemType;
import me.jwotoole9141.prodsline.item.MonitorType;
import me.jwotoole9141.prodsline.item.MoviePlayer;
import me.jwotoole9141.prodsline.item.Product;
import me.jwotoole9141.prodsline.item.Screen;

public class TestProducts {

  public static void main(String[] args) {

    List<Product> products = new ArrayList<>(Arrays.asList(
        new GenericProduct("DVD Player", ItemType.VISUAL, "ACME"),
        new Widget("Lame Widget", ItemType.VISUAL_MOBILE, "LEGO"),
        new AudioPlayer("Speaker", "ACME", "none", "none"),
        new MoviePlayer("Television", "ACME", new Screen("1x1", 1, 5), MonitorType.LCD),
        new Widget("Epic Widget", ItemType.AUDIO_MOBILE, "LEGO")
    ));

    Product.printAllOfType(Widget.class, products);
  }
}
