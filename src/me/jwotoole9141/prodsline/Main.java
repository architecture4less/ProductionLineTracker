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
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
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
  private static final String LOGIN_GUI_FXML = "gui/login_gui.fxml";
  private static final String ERROR_GUI_FXML = "gui/error_gui.fxml";

  private static Stage mainWindow;
  private static Stage loginPopup;
  private static Stage errorPopup;

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
   */
  @Override
  public void start(Stage primaryStage) {

    // connect to the database...
    Model.open();

    launchMainWindow(primaryStage);
    launchLoginPopup();
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

    closeErrorPopup();
    closeLoginPopup();
    closeMainWindow();

    // disconnect from the database...
    Model.close();
  }

  private static Stage launchWindow(Stage stage, String title,
      String fxmlFile, int width, int height, Modality modality) throws IOException {

    // load the fxml file...
    URL url = Main.class.getResource(fxmlFile);
    if (url == null) {
      throw new FileNotFoundException(String.format(
          "The resource '%s' was not found.", fxmlFile
      ));
    }

    // set the title, scene, and modality...
    stage.setTitle(title);
    stage.setScene(new Scene(FXMLLoader.load(url), width, height));
    if (modality != null) {
      stage.initModality(modality);
    }

    // launch and return...
    stage.show();
    return stage;
  }

  public static Stage getMainWindow() {
    return mainWindow;
  }

  public static Stage getLoginPopup() {
    return loginPopup;
  }

  public static Stage getErrorPopup() {
    return errorPopup;
  }

  public static void launchMainWindow(Stage primaryStage) {

    if (Main.mainWindow != null) {
      Main.mainWindow.close();
    }
    try {
      Main.mainWindow = launchWindow(
          primaryStage, "Production Line Tracker",
          PRODSLINE_GUI_FXML, 350, 450,
          null);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void launchLoginPopup() {

    if (Main.loginPopup != null) {
      Main.loginPopup.close();
    }
    try {
      Main.loginPopup = launchWindow(
          new Stage(), "Employee Login",
          LOGIN_GUI_FXML, 300, 200, // 327, 171,
          Modality.APPLICATION_MODAL);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void launchErrorPopup() {

    if (Main.errorPopup != null) {
      Main.errorPopup.close();
    }
    try {
      Main.errorPopup = launchWindow(
          new Stage(), "Production Line Error",
          ERROR_GUI_FXML, 450, 200,
          Modality.APPLICATION_MODAL);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void closeMainWindow() {

    if (Main.mainWindow != null) {
      Main.mainWindow.close();
      Main.mainWindow = null;
    }
  }

  public static void closeLoginPopup() {

    if (Main.loginPopup != null) {
      Main.loginPopup.close();
      Main.loginPopup = null;
    }
  }

  public static void closeErrorPopup() {

    if (Main.errorPopup != null) {
      Main.errorPopup.close();
      Main.errorPopup = null;
    }
  }
}
