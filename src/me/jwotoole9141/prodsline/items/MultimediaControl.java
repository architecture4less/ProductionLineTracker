/*
AUTH: Jared O'Toole
DATE: Tue, Oct 8th, 2019
PROJ: ProductionLineTracker
FILE: MultimediaControl.java

Defines the MultimediaControl interface.
 */

package me.jwotoole9141.prodsline.items;

/**
 * An interface for simulating the controls of a multimedia device.
 *
 * @author Jared O'Toole
 */
public interface MultimediaControl {

  /**
   * Simulate pressing the 'play' button.
   */
  void play();

  /**
   * Simulate pressing the 'stop' button.
   */
  void stop();

  /**
   * Simulate pressing the 'previous' button.
   */
  void previous();

  /**
   * Simulate pressing the 'next' button.
   */
  void next();
}
