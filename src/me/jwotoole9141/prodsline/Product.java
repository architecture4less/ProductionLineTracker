/*
AUTH: Jared O'Toole
DATE: Sat, Sep 28th, 2019
PROJ: ProductionLineTracker
FILE: Product.java

Defines the Product abstract class.
 */

package me.jwotoole9141.prodsline;

public abstract class Product implements Item {

  protected int id;
  protected String type;
  protected String manufacturer;
  protected String name;

  public Product(String name) {
    this.name = name;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  @Override
  public String toString() {

    return String.format(
        "Name: %s\nManufacturer: %s\nType: %s",
        name, manufacturer, type
    );
  }
}
