/*
AUTH: Jared O'Toole
DATE: Wed, Sep 11th, 2019
PROJ: ProductionLineTracker
FILE: Main.java

Defines the Main class.
 */

package me.jwotoole9141.prodsline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the Production Line Tracker application.
 *
 * @author Jared O'Toole
 */
public class Main extends Application {

  /**
   * Launch the application.
   *
   * @param args Command-line arguments.
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
   * @param primaryStage The root JavaFX container.
   * @throws Exception The fxml file can't be loaded.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    // connect to the database...
    Model.open();

    // create the gui window...
    Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));

    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(root, 350, 450));
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
