/*
AUTH: Jared O'Toole
DATE: Wed, Sep 11th, 2019
PROJ: ProductionLineTracker
FILE: Controller.java

Defines the controller class.
 */

package me.jwotoole9141.prodsline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Handles user interaction with the GUI.
 *
 * @author Jared O'Toole
 */
@SuppressWarnings("unused")
public class Controller {

  @FXML
  private TextField fldNewProdName;

  @FXML
  private TextField fldNewProdManuf;

  @FXML
  private ChoiceBox<String> chbNewProdType;

  @FXML
  private Button btnAddProd;

  @FXML
  private TableView<?> tblProducts;

  @FXML
  private ListView<?> lstProdsOpts;

  @FXML
  private ComboBox<Integer> cboProdsQnty;

  @FXML
  private TextArea txtProdsLog;

  /**
   * Initializes the GUI with additional data.
   */
  @FXML
  public void initialize() {

    // initialize the 'produce quantity' combo box...
    for (int i = 0; i < 10; i++) {

      cboProdsQnty.getItems().add(i + 1);
    }
    cboProdsQnty.setEditable(true);
    cboProdsQnty.getSelectionModel().selectFirst();

    // initialize the 'item type' choice box...
    chbNewProdType.getItems().add("AUDIO");
    chbNewProdType.getSelectionModel().selectFirst();
  }

  /**
   * Handles the 'btnAddProd' button being pressed.
   *
   * @param event The action performed.
   */
  @FXML
  void btnAddProdAction(ActionEvent event) {

    System.out.println("The 'Add Product' button was clicked!");

    // add product to database using form info...
    String type = chbNewProdType.getValue();
    String manuf = fldNewProdManuf.getText().trim();
    String name = fldNewProdName.getText().trim();

    Model.addProduct(type, manuf, name);
  }
}
