/*
AUTH: Jared O'Toole
DATE: Tue, Oct 8th, 2019
PROJ: ProductionLineTracker
FILE: AudioPlayer.java

Defines the AudioPlayer class.
 */

package me.jwotoole9141.prodsline.items;

public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  public AudioPlayer(int id, String name, String manuf, String supportedAudioFormats, String supportedPlaylistFormats) {

    super(id, name, ItemType.AUDIO, manuf);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  @Override
  public void play() {
    System.out.println("Playing");  // temp
  }

  @Override
  public void stop() {
    System.out.println("Stopping");  // temp
  }

  @Override
  public void previous() {
    System.out.println("Previous");  // temp
  }

  @Override
  public void next() {
    System.out.println("Next");  // temp
  }

  @Override
  public String toString() {

    return super.toString() + "%n" + String.format(
        "Supported Audio Formats: %s%nSupported Playlist Formats: %s",
        supportedAudioFormats, supportedPlaylistFormats
    );
  }
}
