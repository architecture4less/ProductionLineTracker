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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.items.Product;

/**
 * Handles user interaction with the GUI.
 *
 * @author Jared O'Toole
 */
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
  private ChoiceBox<ItemType> chbNewProdType;

  /**
   * The 'add product' button.
   */
  @FXML
  private Button btnAddProd;


  /**
   * The 'add product' error message label.
   */
  @FXML
  private Label lblErrMsgAddProd;

  /**
   * The 'products' table.
   */
  @FXML
  private TableView<?> tblProducts;

  /**
   * The 'production options' list.
   */
  @FXML
  private ListView<Product> lstProdOpts;

  /**
   * The 'production quantity' combo box.
   */
  @FXML
  private ComboBox<String> cboProdQnty;
  // NOTE: the javaFX combo box returns a string
  // for getValue(), even when its type is integer.

  /**
   * The 'produce' button.
   */
  @FXML
  private Button btnProduce;


  /**
   * The 'produce' error message label.
   */
  @FXML
  private Label lblErrMsgProduce;

  /**
   * The 'production log' text area.
   */
  @FXML
  private TextArea txtProdsLog;

  /**
   * Initialize the GUI with additional data.
   */
  @FXML
  public void initialize() {

    // initialize the 'produce quantity' combo box...

    for (int i = 0; i < 10; i++) {

      cboProdQnty.getItems().add(String.valueOf(i + 1));
    }
    cboProdQnty.setEditable(true);
    cboProdQnty.getSelectionModel().selectFirst();

    // initialize the 'item type' choice box...

    for (ItemType type : ItemType.values()) {
      chbNewProdType.getItems().add(type);
    }
    chbNewProdType.getSelectionModel().selectFirst();
  }

  /**
   * Handles the 'btnAddProd' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  void btnAddProdAction(ActionEvent event) {

    System.out.println("The 'Add Product' button was clicked!");

    // add product to database using form info...
    ItemType type = chbNewProdType.getValue();
    String manuf = fldNewProdManuf.getText().trim();
    String name = fldNewProdName.getText().trim();

    Model.addProduct(name, type, manuf);
  }


  @FXML
  void btnProduceAction(ActionEvent event) {

    System.out.println("The 'Produce' button was clicked!");

    // produce item using form info...
    Product prod = lstProdOpts.getSelectionModel().getSelectedItem();
    int qnty = 0;
    try {
      qnty = Integer.parseInt(cboProdQnty.getValue());
    } catch (NumberFormatException ignored) {
    }

    System.out.printf("qnty: %d\nprod:\n%s\n\n", qnty, prod);

    Model.recordProduction(prod, qnty);
  }
}
