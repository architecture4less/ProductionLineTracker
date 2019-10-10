/*
AUTH: Jared O'Toole
DATE: Tue, Oct 8th, 2019
PROJ: ProductionLineTracker
FILE: AudioPlayer.java

Defines the AudioPlayer class.
 */

package me.jwotoole9141.prodsline.items;

/**
 * A representation of an audio player product with multimedia controls.
 *
 * @author Jared O'Toole
 */
public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * The supported audio formats, delimited by a '/'.
   */
  private String supportedAudioFormats;

  /**
   * The supported playlist formats, delimited by a '/'.
   */
  private String supportedPlaylistFormats;

  /**
   * Create an audio player with the given name, manufacturer, and audio & playlist formats.
   *
   * @param name                     the display name
   * @param manufacturer             the manufacturer name
   * @param supportedAudioFormats    the supported audio formats, delimited by a '/'
   * @param supportedPlaylistFormats the supported playlist formats, delimited by a '/'
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {

    super(name);
    this.type = ItemType.AUDIO.name();
    this.manufacturer = manufacturer;
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void play() {

    System.out.println("Playing");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop() {

    System.out.println("Stopping");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void previous() {

    System.out.println("Previous");  // temp
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void next() {

    System.out.println("Next");  // temp
  }

  /**
   * Get a description string for this audio player.
   *
   * @return a multi-line description
   */
  @Override
  public String toString() {

    return super.toString() + "%n" + String.format(
        "Supported Audio Formats: %s%nSupported Playlist Formats: %s",
        supportedAudioFormats, supportedPlaylistFormats
    );
  }
}
