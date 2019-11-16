/*
 * AUTH: Jared O'Toole
 * DATE: Thu, Oct 10th, 2019
 * PROJ: ProductionLineTracker
 * FILE: ScreenSpec.java
 *
 * Defines the ScreenSpec interface.
 */

package me.jwotoole9141.prodsline.items;

/**
 * An interface for accessing a screen's specification properties.
 *
 * @author Jared O'Toole
 */
public interface ScreenSpec {

  /**
   * Gets the screen's resolution in WxH format.
   *
   * @return the resolution
   */
  String getResolution();

  /**
   * Gets the screen's refresh rate in milliseconds.
   *
   * @return the refresh rate
   */
  int getRefreshRate();

  /**
   * Gets the screen's response time in milliseconds.
   *
   * @return the response time
   */
  int getResponseTime();
}
