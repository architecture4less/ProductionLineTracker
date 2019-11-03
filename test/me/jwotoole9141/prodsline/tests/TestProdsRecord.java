/*
AUTH: Jared O'Toole
DATE: Sat, Nov 2nd, 2019
PROJ: ProductionLineTracker
FILE: TestProdsRecord.java

Defines the production record test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.items.Product;
import me.jwotoole9141.prodsline.items.ProductionRecord;

class TestProdsRecord {

  public static void main(String[] args) {

    List<ProductionRecord> records = new ArrayList<>();

    Model.open();

    Product prod = Objects.requireNonNull(Model.getProduct(1));

    records.add(new ProductionRecord(prod.getId()));
    records.add(new ProductionRecord(prod, Model.getProdsCount(prod.getId())));

    try {
      Model.recordProduction(prod, 5);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Model.close();

    ProductionRecord rec = records.get(0);
    ProductionRecord newRec = new ProductionRecord(
        rec.getProdsNumber(), rec.getProductID(),
        rec.getSerialNumber(), rec.getDateProduced());

    newRec.setDateProduced(new Date());
    newRec.setProductID(0);
    newRec.setProdsNumber(0);
    newRec.setSerialNumber("0");

    records.add(newRec);

    for (ProductionRecord record : records) {
      System.out.println();
      System.out.println(record);
    }
  }
}
