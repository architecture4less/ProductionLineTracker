/*
AUTH: Jared O'Toole
DATE: Wed, Sep 11th, 2019
PROJ: ProductionLineTracker
FILE: Controller.java

Defines the controller class.
 */

package me.jwotoole9141.prodsline.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import me.jwotoole9141.prodsline.ItemType;
import me.jwotoole9141.prodsline.Model;

/**
 * Handles user interaction with the GUI.
 *
 * @author Jared O'Toole
 */
@SuppressWarnings("unused")
public class Controller {

  /**
   * The 'new product name' field.
   */
  @FXML
  private TextField fldNewProdName;

  /**
   * The 'new product manufacturer' field.
   */
  @FXML
  private TextField fldNewProdManuf;

  /**
   * The 'new product type' choice box.
   */
  @FXML
  private ChoiceBox<String> chbNewProdType;

  /**
   * The 'add product' button.
   */
  @FXML
  private Button btnAddProd;

  /**
   * The 'products' table.
   */
  @FXML
  private TableView<?> tblProducts;

  /**
   * The 'production options' list.
   */
  @FXML
  private ListView<?> lstProdsOpts;

  /**
   * The 'production quantity' combo box.
   */
  @FXML
  private ComboBox<Integer> cboProdsQnty;

  /**
   * The 'production log' text area.
   */
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

    for (ItemType type : ItemType.values()) {
      chbNewProdType.getItems().add(type.getDisplay());
    }
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
    try {
      ItemType type = ItemType.getFromDisplay(chbNewProdType.getValue());  // throws ex
      String manuf = fldNewProdManuf.getText().trim();
      String name = fldNewProdName.getText().trim();

      Model.addProduct(type, manuf, name);

    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
  }
}
