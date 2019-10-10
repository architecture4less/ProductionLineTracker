/*
AUTH: Jared O'Toole
DATE: Wed, Sep 25th, 2019
PROJ: ProductionLineTracker
FILE: ItemType.java

Defines the ItemType enum.
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
  private String display;

  /**
   * The database code name.
   */
  private String code;

  ItemType(String display, String code) {
    this.display = display;
    this.code = code;
  }

  /**
   * Get the type's display name.
   *
   * @return the display name
   */
  public String getDisplay() {
    return display;
  }

  /**
   * Get the type's database code name.
   *
   * @return the database code name
   */
  public String getCode() {
    return code;
  }

  /**
   * Get the ItemType with the specified display name.
   *
   * @param display the display name of the ItemType
   * @return the ItemType object
   * @throws IllegalArgumentException no ItemType with the specified display name
   */
  public static ItemType getFromDisplay(String display) throws IllegalArgumentException {

    for (ItemType itemType : ItemType.values()) {
      if (itemType.display.equals(display)) {
        return itemType;
      }
    }
    throw new IllegalArgumentException(
        "No ItemType with display: '" + display + "'");
  }
}
