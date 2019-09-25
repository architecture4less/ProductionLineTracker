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
  AUDIO_MOBILE("AudioMobile", "AM"),
  VISUAL_MOBILE("VisualMobile", "VM");

  /**
   * The display name of the type.
   */
  private String name;

  /**
   * The database code of the type.
   */
  private String code;

  ItemType(String name, String code) {
    this.name = name;
    this.code = code;
  }

  /**
   * Get the type's display name.
   *
   * @return Display name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the type's database code.
   *
   * @return Database code.
   */
  public String getCode() {
    return code;
  }
}
