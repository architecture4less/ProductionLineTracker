/*
AUTH: Jared O'Toole
DATE: Tue, Oct 8th, 2019
PROJ: ProductionLineTracker
FILE: AudioPlayer.java

Defines the AudioPlayer class.
 */

package me.jwotoole9141.prodsline;

public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {

    super(name);
    this.type = ItemType.AUDIO.name();
    this.manufacturer = manufacturer;
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  @Override
  public void play() {

    System.out.println("Playing");
  }

  @Override
  public void stop() {

    System.out.println("Stopping");
  }

  @Override
  public void previous() {

    System.out.println("Previous");
  }

  @Override
  public void next() {

    System.out.println("Next");
  }

  @Override
  public String toString() {

    return super.toString() + String.format(
        "Type: %s\nSupported Audio Formats: %s\n Supported Playlist Formats: %s",
        type, String.join("/", supportedAudioFormats), String.join("/", supportedPlaylistFormats)
    );
  }
}
