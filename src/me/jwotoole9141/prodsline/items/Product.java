/*
AUTH: Jared O'Toole
DATE: Sat, Sep 28th, 2019
PROJ: ProductionLineTracker
FILE: Product.java

Defines the Product abstract class.
 */

package me.jwotoole9141.prodsline.items;

/**
 * An abstract representation of a product.
 *
 * @author Jared O'Toole
 */
public abstract class Product implements Item {

  /**
   * The identity number of the product.
   */
  protected int id;

  /**
   * The display name of the product.
   */
  protected String name;

  /**
   * The item type of the product.
   */
  protected String type;

  /**
   * The manufacturer name of the product.
   */
  protected String manufacturer;

  /**
   * Create a Product with the given name.
   *
   * @param name the display name
   */
  public Product(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Get a description string for this product.
   *
   * @return a multi-line description
   */
  @Override
  public String toString() {

    return String.format(
        "Name: %s%nManufacturer: %s%nType: %s",
        name, manufacturer, type
    );
  }
}
