package me.jwotoole9141.prodsline.item;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An enumeration of different keys to filter and sort products.
 *
 * @author Jared O'Toole
 */
public enum ProductFilter {

  NONE("No Filter", p -> "", (a, b) -> 0),

  ID("Id Number",
      p -> String.valueOf(p.getId()),
      Comparator.comparingInt(Product::getId)),

  NAME("Name",
      Product::getName,
      Comparator.comparing(Product::getName)),

  TYPE("Type",
      p -> p.getType().toString(),
      Comparator.comparing(Product::getType)),

  MANUFACTURER("Manufacturer",
      Product::getManuf,
      Comparator.comparing(Product::getManuf));

  /**
   * The key's display name.
   */
  private final String displayName;

  /**
   * The key's function to get data for a filter predicate.
   */
  private final Function<Product, String> filter;

  /**
   * The key's sorting comparator.
   */
  private final Comparator<Product> comparator;

  /**
   * Constructs a product filter key.
   *
   * @param displayName the human-friendly name
   * @param filter      the filter-data function
   * @param comparator  the sorting comparator
   */
  ProductFilter(String displayName, Function<Product, String> filter,
      Comparator<Product> comparator) {

    this.displayName = displayName;
    this.filter = filter;
    this.comparator = comparator;
  }

  /**
   * Gets the key's display name.
   *
   * @return the display name
   */
  @Override
  public String toString() {
    return this.displayName;
  }

  /**
   * Gets the key's filter predicate.
   *
   * @param value the string to test against
   * @return the filter predicate
   */
  public Predicate<Product> getFilter(String value) {

    return p -> filter.apply(p).toLowerCase()
        .contains(value.toLowerCase());
  }

  /**
   * Gets the key's sorting comparator.
   *
   * @return the sorting comparator
   */
  public Comparator<Product> getComparator() {
    return comparator;
  }
}
