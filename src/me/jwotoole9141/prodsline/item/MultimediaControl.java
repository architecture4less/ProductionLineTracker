/*
 * AUTH: Jared O'Toole
 * DATE: Tue, Oct 8th, 2019
 * PROJ: ProductionLineTracker
 * FILE: MultimediaControl.java
 *
 * Defines the MultimediaControl interface.
 */

package me.jwotoole9141.prodsline.item;

/**
 * An interface for simulating the controls of a multimedia device.
 *
 * @author Jared O'Toole
 */
public interface MultimediaControl {

  /**
   * Simulates pressing the 'play' button.
   */
  void play();

  /**
   * Simulates pressing the 'stop' button.
   */
  void stop();

  /**
   * Simulates pressing the 'previous' button.
   */
  void previous();

  /**
   * Simulates pressing the 'next' button.
   */
  void next();
}
