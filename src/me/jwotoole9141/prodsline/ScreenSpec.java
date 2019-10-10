/*
AUTH: Jared O'Toole
DATE: Thu, Oct 10th, 2019
PROJ: ProductionLineTracker
FILE: ScreenSpec.java

Defines the ScreenSpec interface.
 */

package me.jwotoole9141.prodsline;

/**
 * An interface for accessing a screen resolution, refresh rate, and response time.
 *
 * @author Jared O'Toole
 */
public interface ScreenSpec {

  /**
   * Get the screen's resolution in WxH format.
   *
   * @return the resolution
   */
  String getResolution();

  /**
   * Get the screen's refresh rate in milliseconds.
   *
   * @return the refresh rate
   */
  int getRefreshRate();

  /**
   * Get the screen's response time in milliseconds.
   *
   * @return the response time
   */
  int getResponseTime();
}
