package me.jwotoole9141.prodsline.tests;

import me.jwotoole9141.prodsline.Model;

class TestDb {

  public static void main(String[] args) {

    Model.open();
    System.out.println(Model.getProduct(1));
    Model.close();
  }

}
