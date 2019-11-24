package me.jwotoole9141.prodsline.item;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An enumeration of different keys to filter and sort production records.
 *
 * @author Jared O'Toole
 */
public enum RecordFilter {

  NONE("No Filter", p -> "", (a, b) -> 0),

  PROD_NUM("Production Number",
      p -> String.valueOf(p.getProductId()).toLowerCase(),
      Comparator.comparingInt(ProductionRecord::getProdsNumber)),

  PRODUCT_ID("Product Id",
      p -> String.valueOf(p.getProductId()).toLowerCase(),
      Comparator.comparingInt(ProductionRecord::getProductId)),

  EMPLOYEE_ID("Employee Id",
      p -> String.valueOf(p.getEmployeeId()).toLowerCase(),
      Comparator.comparingInt(ProductionRecord::getEmployeeId)),

  SERIAL_NUM("Serial Number",
      p -> p.getSerialNumber().toLowerCase(),
      Comparator.comparing(ProductionRecord::getSerialNumber)),

  DATE("Production Date",
      p -> p.getDateProduced().toString().toLowerCase(),
      Comparator.comparing(ProductionRecord::getDateProduced));

  /**
   * The key's display name.
   */
  private final String displayName;

  /**
   * The key's function to get data for a filter predicate.
   */
  private final Function<ProductionRecord, String> filter;

  /**
   * The key's sorting comparator.
   */
  private final Comparator<ProductionRecord> comparator;

  /**
   * Constructs a record filter key.
   *
   * @param displayName the human-friendly name
   * @param filter      the filter-data function
   * @param comparator  the sorting comparator
   */
  RecordFilter(String displayName, Function<ProductionRecord, String> filter,
      Comparator<ProductionRecord> comparator) {

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
  public Predicate<ProductionRecord> getFilter(String value) {

    return p -> filter.apply(p).toLowerCase()
        .contains(value.toLowerCase());
  }

  /**
   * Gets the key's sorting comparator.
   *
   * @return the sorting comparator
   */
  public Comparator<ProductionRecord> getComparator() {
    return comparator;
  }
}
