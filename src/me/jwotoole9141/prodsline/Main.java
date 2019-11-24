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
import javafx.util.Pair;
import me.jwotoole9141.prodsline.gui.ErrorController;
import me.jwotoole9141.prodsline.gui.LoginController;
import me.jwotoole9141.prodsline.gui.ProdsLineController;
import me.jwotoole9141.prodsline.user.Employee;

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

  private static Stage primaryStage;

  private static Stage mainWindow;
  private static Stage loginPopup;
  private static Stage errorPopup;

  private static ProdsLineController mainCtrl;
  private static LoginController loginCtrl;
  private static ErrorController errorCtrl;

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

    Main.setPrimaryStage(primaryStage);

    makeLoginPopup();
    getLoginPopup().show();
    getLoginCtrl().setUserField("tlee");

    // connect to the database...
    Model.open();
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

  /**
   * Closes the login window and opens the main window.
   *
   * @param user the user that has logged in
   */
  public static void login(Employee user) {

    closeLoginPopup();

    makeMainWindow();
    getMainCtrl().setUser(user);
    getMainWindow().show();

  }

  /**
   * Closes the main window and opens the login window.
   */
  public static void logout() {

    closeMainWindow();

    makeLoginPopup();
    getLoginPopup().show();
  }

  /**
   * Opens the error popup window.
   *
   * @param ex the message to show in the popup
   */
  public static void showError(Exception ex) {

    makeErrorPopup();
    getErrorPopup().show();
    getErrorCtrl().setMessage(ex.getMessage());

    ex.printStackTrace();
  }

  /**
   * Sets the primary stage used for the main window.
   *
   * @param stage the stage
   */
  private static void setPrimaryStage(Stage stage) {
    primaryStage = stage;
  }

  /**
   * Gets the prods line GUI window.
   *
   * @return the main window
   */
  private static Stage getMainWindow() {
    return mainWindow;
  }

  /**
   * Gets the login GUI window.
   *
   * @return the login popup window
   */
  private static Stage getLoginPopup() {
    return loginPopup;
  }

  /**
   * Gets the error GUI window.
   *
   * @return the error message popup window
   */
  private static Stage getErrorPopup() {
    return errorPopup;
  }

  /**
   * Gets the prods line controller.
   *
   * @return the main window's controller
   */
  private static ProdsLineController getMainCtrl() {
    return mainCtrl;
  }

  /**
   * Gets the login controller.
   *
   * @return the login popup's controller
   */
  private static LoginController getLoginCtrl() {
    return loginCtrl;
  }

  /**
   * Gets the error controller.
   *
   * @return the error message popup's controller
   */
  private static ErrorController getErrorCtrl() {
    return errorCtrl;
  }

  /**
   * Creates the prods line GUI window.
   */
  private static void makeMainWindow() {

    if (mainWindow != null) {
      mainWindow.close();
    }

    try {
      Pair<Stage, Object> result = createWindow(
          primaryStage, "Production Line Tracker",
          PRODSLINE_GUI_FXML, 350, 450,
          null);

      mainWindow = result.getKey();
      mainCtrl = (ProdsLineController) result.getValue();

    } catch (IOException | ClassCastException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Creates the login GUI window.
   */
  private static void makeLoginPopup() {

    if (loginPopup != null) {
      loginPopup.close();
    }
    try {
      Pair<Stage, Object> result = createWindow(
          new Stage(), "Employee Login",
          LOGIN_GUI_FXML, 300, 200, // 327, 171,
          Modality.APPLICATION_MODAL);

      loginPopup = result.getKey();
      loginCtrl = (LoginController) result.getValue();

    } catch (IOException | ClassCastException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Creates the error message GUI window.
   */
  private static void makeErrorPopup() {

    if (errorPopup != null) {
      errorPopup.close();
    }
    try {

      Pair<Stage, Object> result = createWindow(
          new Stage(), "Production Line Error",
          ERROR_GUI_FXML, 450, 200,
          Modality.APPLICATION_MODAL);

      errorPopup = result.getKey();
      errorCtrl = (ErrorController) result.getValue();

    } catch (IOException | ClassCastException e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes the prods line GUI window.
   */
  private static void closeMainWindow() {

    if (mainWindow != null) {
      mainWindow.close();
      mainWindow = null;
      mainCtrl = null;
    }
  }

  /**
   * Closes the login GUI window.
   */
  private static void closeLoginPopup() {

    if (loginPopup != null) {
      loginPopup.close();
      loginPopup = null;
      loginCtrl = null;
    }
  }

  /**
   * Closes the error GUI window.
   */
  public static void closeErrorPopup() {

    if (errorPopup != null) {
      errorPopup.close();
      errorPopup = null;
      errorCtrl = null;
    }
  }

  /**
   * Creates a window with the given properties.
   *
   * @param stage    a pre-existing stage, or null
   * @param title    the title of the window
   * @param fxmlFile the fxml file path to load the window's scene from
   * @param width    the width of the window
   * @param height   the height of the window
   * @param modality the modality of the window
   * @return the new window
   * @throws IOException the fxml file couldn't be loaded
   */
  private static Pair<Stage, Object> createWindow(Stage stage, String title,
      String fxmlFile, int width, int height, Modality modality) throws IOException {

    // load the fxml file...
    URL url = Main.class.getResource(fxmlFile);
    if (url == null) {
      throw new FileNotFoundException(String.format(
          "The resource '%s' was not found.", fxmlFile
      ));
    }

    FXMLLoader loader = new FXMLLoader(url);
    if (stage == null) {
      stage = new Stage();
    }

    // set the title, scene, and modality...
    stage.setTitle(title);
    stage.setScene(new Scene(loader.load(), width, height));
    if (modality != null) {
      stage.initModality(modality);
    }

    return new Pair<>(stage, loader.getController());
  }
}
