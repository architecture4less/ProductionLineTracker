/*
AUTH: Jared O'Toole
DATE: Thu, Oct 10th, 2019
PROJ: ProductionLineTracker
FILE: MoviePlayer.java

Defines the MoviePlayer class.
 */

package me.jwotoole9141.prodsline.items;

/**
 * A representation of a movie player product with multimedia controls.
 *
 * @author Jared O'Toole
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * The screen.
   */
  private Screen screen;

  /**
   * The monitor type.
   */
  private MonitorType monitorType;

  public MoviePlayer(String name, String manuf, Screen screen, MonitorType monitorType) {
    this(0, name, manuf, screen, monitorType);
  }

  /**
   * Create a movie player with the given name, manufacturer, screen, and monitor type.
   *
   * @param name         the display name
   * @param manuf        the manufacturer name
   * @param screen       the screen
   * @param monitorType  the monitor type
   */
  public MoviePlayer(int id, String name, String manuf, Screen screen, MonitorType monitorType) {

    super(id, name, ItemType.VISUAL, manuf);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  public MoviePlayer(int id, String name, String manuf) {

    // temp default values for database-unsupported properties
    super(id, name, ItemType.VISUAL, manuf);
    this.screen = new Screen("?", 0, 0);
    this.monitorType = MonitorType.LCD;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void play() {
    System.out.println("Playing movie");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop() {
    System.out.println("Stopping movie");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void previous() {
    System.out.println("Previous movie");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void next() {
    System.out.println("Next movie");  // temp
  }

  /**
   * Get a description string for this movie player.
   *
   * @return a multi-line description
   */
  @Override
  public String toString() {
    return super.toString() + "\n" + String.format(
      "Screen: %s%nMonitorType: %s",
      screen, monitorType
    );
//    return super.toString() + "\nScreen: \n" + screen.toString()
//        + "\nMonitor Type: " + monitorType.name();
  }
}
