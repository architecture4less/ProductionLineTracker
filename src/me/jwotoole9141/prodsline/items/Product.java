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
   * The unique id number of the product.
   */
  private final int id;

  /**
   * The display name of the product.
   */
  private String name;

  /**
   * The item type of the product.
   */
  private final ItemType type;

  /**
   * The manufacturer name of the product.
   */
  private String manuf;

  /**
   * Create a new product.
   *
   * <p>
   * A default id of 0 is automatically assigned.
   * </p>
   *
   * @param name  the display name
   * @param type  the item type
   * @param manuf the manufacturer name
   */
  protected Product(String name, ItemType type, String manuf) {

    this(0, name, type, manuf);
  }

  /**
   * Create a new product with a known id.
   *
   * <p>
   * The id number of a product is automatically generated by the database. Therefore, this
   * constructor should only be used when fetching data from the database.
   * </p>
   *
   * @param id    the product id
   * @param name  the display name
   * @param type  the item type
   * @param manuf the manufacturer name
   */
  protected Product(int id, String name, ItemType type, String manuf) {

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

  /**
   * Get the item type of this product.
   *
   * @return the item type
   */
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
        name, manuf, type.getCode()
    );
  }
}
