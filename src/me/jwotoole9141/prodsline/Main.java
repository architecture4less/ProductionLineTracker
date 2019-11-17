/*
 * AUTH: Jared O'Toole
 * DATE: Wed, Sep 11th, 2019
 * PROJ: ProductionLineTracker
 * FILE: Main.java
 *
 * Defines the Main class.
 */

package me.jwotoole9141.prodsline;

import java.io.FileNotFoundException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class of the Production Line Tracker application.
 *
 * @author Jared O'Toole
 */
public class Main extends Application {

  /**
   * The name of the prodsline gui fxml file.
   */
  private static final String PRODSLINE_GUI_FXML = "gui/prodsline_gui.fxml";

  /**
   * Launches the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {

    launch(args);
  }

  /**
   * The starting point of the application.
   *
   * <p>
   * Creates the GUI window and connects to the database.
   * </p>
   *
   * @param primaryStage the root JavaFX container
   * @throws Exception the fxml file can't be loaded
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    // connect to the database...
    Model.open();

    // load the fxml file...
    URL url = getClass().getResource(PRODSLINE_GUI_FXML);
    if (url == null) {
      throw new FileNotFoundException(String.format(
          "The resource '%s' was not found.", PRODSLINE_GUI_FXML
      ));
    }

    // create the gui window...
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(FXMLLoader.load(url), 350, 450));
    primaryStage.show();
  }

  /**
   * The ending point of the application.
   *
   * <p>
   * Disconnects from the database.
   * </p>
   */
  @Override
  public void stop() {

    // close the database...
    Model.close();
  }
}
