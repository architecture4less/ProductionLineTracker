/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: LoginController.java
 *
 * Defines the LoginController class.
 */

package me.jwotoole9141.prodsline.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Handles user interaction with the Login GUI.
 *
 * @author Jared O'Toole
 */
public class LoginController {

  /**
   * The 'username login' field.
   */
  @FXML
  private TextField fldLoginUser;

  /**
   * The 'password login' field.
   */
  @FXML
  private TextField fldLoginPass;

  /**
   * The 'login' button.
   */
  @FXML
  private Button btnLogin;

  /**
   * The 'login' button message label.
   */
  @FXML
  private Label lblLoginMsg;

  /**
   * Handles the 'login' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  void btnLoginAction(ActionEvent event) {

    // TODO on login
  }
}

