/*
AUTH: Jared O'Toole
DATE: Wed, Sep 11th, 2019
PROJ: ProductionLineTracker
FILE: Controller.java

Defines the controller class.
 */

package me.jwotoole9141.prodsline.gui;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import me.jwotoole9141.prodsline.items.ProductionRecord;
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
   * The 'add product' button message label.
   */
  @FXML
  private Label lblAddProdMsg;

  /**
   * The 'products' table.
   */
  @FXML
  private TableView<Product> tblProducts;

  @FXML
  private TableColumn<?, Product> colProductsId;

  @FXML
  private TableColumn<?, Product> colProductsName;

  @FXML
  private TableColumn<?, Product> colProductsType;

  @FXML
  private TableColumn<?, Product> colProductsManuf;

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
  // NOTE: the javaFX combo box would return a string
  // for getValue(), even though its type was integer.

  /**
   * The 'produce' button.
   */
  @FXML
  private Button btnProduce;

  /**
   * The 'produce' button message label.
   */
  @FXML
  private Label lblProduceMsg;

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

    Model.productLine.clear();
    Model.productLine.addAll(Model.getProducts());

    refreshChbNewProdType();
    refreshTblProducts();

    refreshLstProdOpts();
    refreshCboProdQnty();

    refreshTxtProdsLog(Model.getProdsRecords());

    lblProduceMsg.setText("");
    lblAddProdMsg.setText("");
  }

  public void refreshChbNewProdType() {

    // initialize the 'item type' choice box...
    chbNewProdType.getItems().clear();

    for (ItemType type : ItemType.values()) {
      chbNewProdType.getItems().add(type);
    }
    chbNewProdType.getSelectionModel().selectFirst();
  }

  public void refreshTblProducts() {

    tblProducts.setItems(Model.productLine);

    colProductsId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colProductsType.setCellValueFactory(new PropertyValueFactory<>("type"));
    colProductsManuf.setCellValueFactory(new PropertyValueFactory<>("manuf"));
  }

  public void refreshLstProdOpts() {

    lstProdOpts.setItems(Model.productLine);
  }

  public void refreshCboProdQnty() {

    // initialize the 'produce quantity' combo box...
    cboProdQnty.getItems().clear();

    for (int i = 0; i < 10; i++) {

      cboProdQnty.getItems().add(String.valueOf(i + 1));
    }
    cboProdQnty.setEditable(true);
    cboProdQnty.getSelectionModel().selectFirst();
  }

  public void refreshTxtProdsLog(List<ProductionRecord> records) {

    txtProdsLog.setText(records.stream()
        .map(ProductionRecord::toString)
        .collect(Collectors.joining("\n")) + "\n");
  }

  /**
   * Handles the 'btnAddProd' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  void btnAddProdAction(ActionEvent event) {

    // add product to database using form info...
    ItemType type = chbNewProdType.getValue();
    String manuf = fldNewProdManuf.getText().trim();
    String name = fldNewProdName.getText().trim();

    try {
      Product newProd = Model.addProduct(name, type, manuf);
      Model.productLine.add(newProd);

      // set add prod message label to success...
      lblAddProdMsg.setTextFill(Color.web("green"));
      lblAddProdMsg.setText(String.format(
          "Added product #%d '%s' successfully.",
          newProd.getId(), newProd.getName()
      ));

    } catch (SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set add prod message label to failure...
      lblAddProdMsg.setTextFill(Color.web("red"));
      lblAddProdMsg.setText("Couldn't add new product: " + ex.getMessage());
    }
  }


  @FXML
  void btnProduceAction(ActionEvent event) {

    // produce a record using form info...
    Product prod = lstProdOpts.getSelectionModel().getSelectedItem();
    Integer qnty = null;

    try {
      qnty = Integer.parseInt(cboProdQnty.getValue());
    } catch (NumberFormatException ignored) {

    }
    try {
      ProductionRecord[] records = Model.recordProduction(prod, qnty);

      // append the prods log...
      txtProdsLog.appendText(Arrays.stream(records)
          .filter(Objects::nonNull)
          .map(ProductionRecord::toString)
          .collect(Collectors.joining("\n")) + "\n");

      // set the produce message label to success...
      lblProduceMsg.setTextFill(Color.web("green"));
      lblProduceMsg.setText(String.format(
          "Produced #%d '%s' x%d successfully.",
          prod.getId(), prod.getName(), qnty
      ));

    } catch (SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set the produce message label to failure...
      lblProduceMsg.setTextFill(Color.web("red"));
      lblProduceMsg.setText("Couldn't do production: " + ex.getMessage());
    }
  }
}
