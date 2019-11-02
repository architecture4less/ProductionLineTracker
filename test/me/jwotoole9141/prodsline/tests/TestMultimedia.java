package me.jwotoole9141.prodsline.tests;

import java.util.ArrayList;
import me.jwotoole9141.prodsline.items.AudioPlayer;
import me.jwotoole9141.prodsline.items.MonitorType;
import me.jwotoole9141.prodsline.items.MoviePlayer;
import me.jwotoole9141.prodsline.items.MultimediaControl;
import me.jwotoole9141.prodsline.items.Screen;

public class TestMultimedia {

  public static void main(String[] args) {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);

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
