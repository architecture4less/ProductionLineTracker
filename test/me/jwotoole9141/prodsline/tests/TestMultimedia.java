/*
 * AUTH: Jared O'Toole
 * DATE: Thu, Oct 24th, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestMultimedia.java
 *
 * Defines the Multimedia test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import java.util.ArrayList;
import me.jwotoole9141.prodsline.item.AudioPlayer;
import me.jwotoole9141.prodsline.item.Item;
import me.jwotoole9141.prodsline.item.MonitorType;
import me.jwotoole9141.prodsline.item.MoviePlayer;
import me.jwotoole9141.prodsline.item.MultimediaControl;
import me.jwotoole9141.prodsline.item.Screen;
import me.jwotoole9141.prodsline.item.ScreenSpec;

class TestMultimedia {

  public static void main(String[] args) {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");

    AudioPlayer anotherAudioProduct = new AudioPlayer(2, "DP-X2B", "Onkyo",
        "DSD/ALAC/WAV/Ogg-Vorbis/MP3", "M3U/WPL");

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction",
        new Screen("720x480", 40, 22), MonitorType.LCD);

    MoviePlayer anotherMovieProduct = new MoviePlayer(4, "DBPOWER MK102", "OracleProduction",
        new Screen("1144x720", 50, 28), MonitorType.LED);

    ArrayList<MultimediaControl> productList = new ArrayList<>();

    productList.add(newAudioProduct);
    productList.add(anotherAudioProduct);
    productList.add(newMovieProduct);
    productList.add(anotherMovieProduct);

    ScreenSpec spec = anotherMovieProduct.getScreen();
    System.out.printf("%nThe specs of another movie product:%n  Response Time: %d%n"
            + "  Resolution: %s%n  Refresh Rate: %d%n",
        spec.getResponseTime(), spec.getResolution(), spec.getRefreshRate());

    ((Item) newAudioProduct).setName("Not a DP-X1A");
    ((Item) newAudioProduct).setManuf("Not Onkyo");

    System.out.printf("Supported audio formats: %s%n", newAudioProduct.getAudioFormats());
    System.out.printf("Supported playlist formats: %s%n", newAudioProduct.getPlaylistFormats());

    System.out.printf("The screen: %s%n", newMovieProduct.getScreen());
    System.out.printf("The monitor type: %s%n", newMovieProduct.getMonitorType());

    System.out.printf("%nThe audio item has the following properties:%n"
            + "Id: %d%n Manufacturer: %s%n Name: %s%n",
        ((Item) newAudioProduct).getId(),
        ((Item) newAudioProduct).getManuf(),
        ((Item) newAudioProduct).getName());

    for (MultimediaControl p : productList) {
      System.out.println();
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }
}
