/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 2nd, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestProdsRecord.java
 *
 * Defines the production record test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.item.Product;
import me.jwotoole9141.prodsline.item.ProductionRecord;
import me.jwotoole9141.prodsline.user.Employee;

/**
 * Tests the production record class.
 *
 * @author Jared O'Toole
 */
class TestProdsRecord {

  /**
   * Runs the test.
   *
   * @param args unused command-line args
   */
  public static void main(String[] args) {

    List<ProductionRecord> records = new ArrayList<>();

    Model.open();

    Employee user = new Employee(3, "Tim Lee", "abCd!", false);

    Product prod = Objects.requireNonNull(Model.getProduct(1));

    records.add(new ProductionRecord(prod.getId()));
    records.add(new ProductionRecord(prod, Model.getProdsCount(prod.getId())));

    try {
      Model.recordProduction(prod, 5, new Timestamp(System.currentTimeMillis()), user);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Model.close();

    ProductionRecord rec = records.get(0);
    ProductionRecord newRec = new ProductionRecord(
        rec.getProdsNumber(), rec.getProductId(), rec.getEmployeeId(),
        rec.getSerialNumber(), rec.getDateProduced());

    newRec.setDateProduced(new Date());
    newRec.setProductId(0);
    newRec.setProdsNumber(0);
    newRec.setEmployeeId(5);
    newRec.setSerialNumber("0");

    records.add(newRec);

    for (ProductionRecord record : records) {
      System.out.println();
      System.out.println(record);
    }
  }
}
