/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: ErrorController.java
 *
 * Defines the ErrorController class.
 */

package me.jwotoole9141.prodsline.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import me.jwotoole9141.prodsline.Main;

/**
 * Handles interaction with the error GUI.
 *
 * @author Jared O'Toole
 */
public class ErrorController {

  /**
   * The 'error message' text area.
   */
  @FXML
  public TextArea txtErrorMsg;

  /**
   * The 'okay' button.
   */
  @FXML
  public Button btnOkay;

  /**
   * Initializes the error message GUI.
   */
  @FXML
  public void initialize() {

    txtErrorMsg.setText("");
  }

  /**
   * Sets the error message.
   *
   * @param message the message
   */
  public void setMessage(String message) {

    txtErrorMsg.setText(message);
  }

  /**
   * Handles the 'okay' button being pressed.
   *
   * @param event the action performed
   */
  public void btnOkayAction(ActionEvent event) {

    Main.closeErrorPopup();
    event.consume();
  }
}
