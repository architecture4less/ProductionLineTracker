/*
 * AUTH: Jared O'Toole
 * DATE: Fri, Nov 15th, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestDb.java
 *
 * Defines the TestDb test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.util.Properties;
import me.jwotoole9141.prodsline.Model;

class TestDb {

  public static void main(String[] args) {

    Model.open();
    Properties props = Model.getProperties();
    System.out.printf(
        "user:%s\npassword:%s\n",
        props.getProperty("user"),
        props.getProperty("password")
    );

    System.out.println(Model.getProduct(1));
    Model.close();
  }
}
