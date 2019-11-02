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
public class Product implements Item {

  /**
   * The identity number of the product.
   */
  private int id;

  /**
   * The display name of the product.
   */
  private String name;

  /**
   * The item type of the product.
   */
  private ItemType type;

  /**
   * The manufacturer name of the product.
   */
  private String manuf;

  /**
   * Create a Product with the given name.
   *
   * @param name the display name
   */
  public Product(int id, String name, ItemType type, String manuf) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.manuf = manuf;
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

  public ItemType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getManuf() {
    return manuf;
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
  public void setManuf(String manuf) {
    this.manuf = manuf;
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
        name, manuf, type.name()
    );
  }
}
