/*
AUTH: Jared O'Toole
DATE: Wed, Sep 25th, 2019
PROJ: ProductionLineTracker
FILE: ItemType.java

Defines the ItemType enum.
 */

package me.jwotoole9141.prodsline;

/**
 * Enumerates the item types of products.
 *
 * @author Jared O'Toole
 */
public enum ItemType {

  AUDIO("Audio", "AU"),
  VISUAL("Visual", "VI"),
  AUDIO_MOBILE("Audio Mobile", "AM"),
  VISUAL_MOBILE("Visual Mobile", "VM");

  /**
   * The display name of the type.
   */
  private String display;

  /**
   * The database code of the type.
   */
  private String code;

  ItemType(String display, String code) {
    this.display = display;
    this.code = code;
  }

  /**
   * Get the type's display name.
   *
   * @return Display name.
   */
  public String getDisplay() {
    return display;
  }

  /**
   * Get the type's database code.
   *
   * @return Database code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Get the ItemType with the specified display.
   *
   * @param display The display name of the ItemType.
   * @return The ItemType object.
   * @throws IllegalArgumentException No ItemType has the specified display.
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
