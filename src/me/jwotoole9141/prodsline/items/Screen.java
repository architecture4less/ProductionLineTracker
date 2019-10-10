/*
AUTH: Jared O'Toole
DATE: Thu, Oct 10th, 2019
PROJ: ProductionLineTracker
FILE: Screen.java

Defines the Screen class.
 */

package me.jwotoole9141.prodsline.items;

/**
 * A representation of a screen.
 *
 * @author Jared O'Toole
 */
public class Screen implements ScreenSpec {

  /**
   * The screen's resolution in WxH format.
   */
  private String resolution;

  /**
   * The screen's refresh rate in milliseconds.
   */
  private int refreshrate;

  /**
   * The screen's response time in milliseconds.
   */
  private int responsetime;

  /**
   * Create a screen with the given resolution, refresh rate, and response time.
   *
   * @param resolution   the resolution in WxH format
   * @param refreshRate  the refresh rate in milliseconds
   * @param responseTime the response time in milliseconds
   */
  public Screen(String resolution, int refreshRate, int responseTime) {

    this.resolution = resolution;
    this.refreshrate = refreshRate;
    this.responsetime = responseTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRefreshRate() {
    return refreshrate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getResponseTime() {
    return responsetime;
  }

  /**
   * Get a description string for this screen.
   *
   * @return a multi-line description
   */
  @Override
  public String toString() {

    return String.format(
        "Resolution: %s%nRefresh rate: %s%nResponse time: %s",
        resolution, refreshrate, responsetime
    );
  }
}
