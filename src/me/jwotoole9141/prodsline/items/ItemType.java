/*
 * AUTH: Jared O'Toole
 * DATE: Wed, Sep 25th, 2019
 * PROJ: ProductionLineTracker
 * FILE: ItemType.java
 *
 * Defines the ItemType enum.
 */

package me.jwotoole9141.prodsline.items;

/**
 * An enumeration of product item types.
 *
 * @author Jared O'Toole
 */
public enum ItemType {

  AUDIO("Audio", "AU"),
  VISUAL("Visual", "VI"),
  AUDIO_MOBILE("Audio Mobile", "AM"),
  VISUAL_MOBILE("Visual Mobile", "VM");

  /**
   * The display name.
   */
  private final String display;

  /**
   * The two letter database code.
   */
  private final String code;

  /**
   * Construct an item type.
   *
   * @param display the display name
   * @param code    the two letter database code
   */
  ItemType(String display, String code) {

    this.display = display;
    this.code = code;
  }

  /**
   * Get the item type's database code name.
   *
   * @return the database code name
   */
  public String getCode() {

    return code;
  }

  /**
   * Get the display name for this item type.
   *
   * @return the display name
   */
  @Override
  public String toString() {

    return display;
  }

  /**
   * Get the item type with the specified code.
   *
   * @param code a two letter code
   * @return the item type with the specified code
   * @throws IllegalArgumentException no item type with the specified code exists
   */
  public static ItemType getFromCode(String code) throws IllegalArgumentException {

    for (ItemType itemType : ItemType.values()) {
      if (itemType.code.equalsIgnoreCase(code)) {
        return itemType;
      }
    }
    throw new IllegalArgumentException(
        "No ItemType with code '" + code + "'");
  }
}
