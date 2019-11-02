/*
AUTH: Jared O'Toole
DATE: Wed, Sep 25th, 2019
PROJ: ProductionLineTracker
FILE: ItemType.java

Defines the ItemType enum.
 */

package me.jwotoole9141.prodsline.items;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An enumeration of product item types.
 *
 * @author Jared O'Toole
 */
public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  /**
   * The database code name.
   */
  private String code;

  /**
   * The display name.
   */
  private String display;

  ItemType(String code) {
    this.code = code;
    this.display = Arrays.stream(name().split("_"))
        .map((s) -> (s.substring(0, 1) + s.substring(1).toLowerCase()))
        .collect(Collectors.joining(" "));
  }

  /**
   * Get the type's database code name.
   *
   * @return the database code name
   */
  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return display;
  }

  /**
   * Get the ItemType with the specified display name.
   *
   * @param code the display name of the ItemType
   * @return the ItemType object
   * @throws IllegalArgumentException no ItemType with the specified display name
   */
  public static ItemType getFromCode(String code) throws IllegalArgumentException {

    for (ItemType itemType : ItemType.values()) {
      if (itemType.code.equals(code)) {
        return itemType;
      }
    }
    throw new IllegalArgumentException(
        "No ItemType with code '" + code + "'");
  }
}
