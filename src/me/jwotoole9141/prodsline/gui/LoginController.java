/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: LoginController.java
 *
 * Defines the LoginController class.
 */

package me.jwotoole9141.prodsline.gui;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import me.jwotoole9141.prodsline.Main;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.user.Employee;

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
   * Initializes the login GUI.
   */
  @FXML
  public void initialize() {

    // clear the message label.
    lblLoginMsg.setText("");
  }

  /**
   * Sets the username field.
   *
   * @param username the username
   */
  public void setUserField(String username) {

    fldLoginUser.setText(username);
  }

  /**
   * Handles the 'login' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  void btnLoginAction(ActionEvent event) {

    try {
      // make sure a username was given...
      String username = fldLoginUser.getText().trim();
      if (username.isEmpty()) {
        throw new IllegalArgumentException(
            "A username was not given.");
      }

      // make sure a password was given...
      String password = fldLoginPass.getText().trim();
      if (password.isEmpty()) {
        throw new IllegalArgumentException(
            "A password was not given.");
      }

      // get the employee credentials for that username...
      Employee user = Model.getUser(fldLoginUser.getText());
      if (user == null) {
        throw new IllegalArgumentException(
            "The given username was invald.");
      }

      // make sure the password was correct for that username...
      if (!user.getPassword().equals(password)) {
        throw new IllegalArgumentException(
            "The given password was invalid.");
      }

      // set the label to success...
      lblLoginMsg.setTextFill(Color.web("red"));
      lblLoginMsg.setText("Login successful!");

      // log the user in...
      Main.login(user);

    } catch (IllegalStateException | SQLException ex) {
      Main.showError(ex);

    } catch (IllegalArgumentException ex) {

      // set the label to failure...
      lblLoginMsg.setTextFill(Color.web("red"));
      lblLoginMsg.setText(ex.getMessage());
    }

    event.consume();
  }
}
