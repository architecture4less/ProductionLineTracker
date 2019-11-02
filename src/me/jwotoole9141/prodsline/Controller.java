/*
AUTH: Jared O'Toole
DATE: Wed, Sep 11th, 2019
PROJ: ProductionLineTracker
FILE: Controller.java

Defines the controller class.
 */

package me.jwotoole9141.prodsline;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import me.jwotoole9141.prodsline.items.ItemType;
import me.jwotoole9141.prodsline.items.Product;
import me.jwotoole9141.prodsline.items.ProductionRecord;

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

  // /**
  //  * The 'add product' button.
  //  */
  // @FXML
  // private Button btnAddProd;

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

  /**
   * The 'products' table 'id' column.
   */
  @FXML
  private TableColumn<?, Product> colProductsId;

  /**
   * The 'products' table 'name' column.
   */
  @FXML
  private TableColumn<?, Product> colProductsName;

  /**
   * The 'products' table 'type' column.
   */
  @FXML
  private TableColumn<?, Product> colProductsType;

  /**
   * The 'products' table 'manufacturer' column.
   */
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
  // NOTE: the javaFX combo box would return a string
  // for getValue() even when its type was integer.
  private ComboBox<String> cboProdQnty;

  // /**
  //  * The 'produce' button.
  //  */
  // @FXML
  // private Button btnProduce;

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

  /**
   * Refresh the 'new product type' choice box data.
   */
  private void refreshChbNewProdType() {

    chbNewProdType.getItems().clear();

    for (ItemType type : ItemType.values()) {
      chbNewProdType.getItems().add(type);
    }
    chbNewProdType.getSelectionModel().selectFirst();
  }

  /**
   * Refresh the 'products' table data.
   */
  // NOTE: this method may be AKA 'setupProductLineTable'
  private void refreshTblProducts() {

    tblProducts.setItems(Model.productLine);

    colProductsId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colProductsType.setCellValueFactory(new PropertyValueFactory<>("type"));
    colProductsManuf.setCellValueFactory(new PropertyValueFactory<>("manuf"));
  }

  /**
   * Refresh the 'production options' list data.
   */
  private void refreshLstProdOpts() {

    lstProdOpts.setItems(Model.productLine);
  }

  /**
   * Refresh the 'production quantity' combo box data.
   */
  private void refreshCboProdQnty() {

    cboProdQnty.getItems().clear();

    for (int i = 0; i < 10; i++) {
      cboProdQnty.getItems().add(String.valueOf(i + 1));
    }
    cboProdQnty.setEditable(true);
    cboProdQnty.getSelectionModel().selectFirst();
  }

  /**
   * Refresh the 'production log' text area data.
   *
   * @param records the production records to display
   */
  private void refreshTxtProdsLog(List<ProductionRecord> records) {

    txtProdsLog.setText(records.stream()
        .map(ProductionRecord::toString)
        .collect(Collectors.joining("\n")) + "\n");
  }

  /**
   * Handle the 'add product' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  public void btnAddProdAction(ActionEvent event) {

    event.consume();

    // create a new product using form info...
    ItemType type = chbNewProdType.getValue();
    String manuf = fldNewProdManuf.getText().trim();
    String name = fldNewProdName.getText().trim();

    try {
      Product newProd = Model.addProduct(name, type, manuf);
      Model.productLine.add(newProd);

      // set the 'add prod' message label to success...
      lblAddProdMsg.setTextFill(Color.web("green"));
      lblAddProdMsg.setText(String.format(
          "Added product #%d '%s' successfully.",
          newProd.getId(), newProd.getName()
      ));

    } catch (SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set the 'add prod' message label to failure...
      lblAddProdMsg.setTextFill(Color.web("red"));
      lblAddProdMsg.setText("Couldn't add new product: " + ex.getMessage());
    }
  }

  /**
   * Handle the 'produce' button being pressed.
   *
   * @param event the action performed.
   */
  @FXML
  public void btnProduceAction(ActionEvent event) {

    event.consume();

    // create a production record using form info...
    Product prod = lstProdOpts.getSelectionModel().getSelectedItem();
    Integer qnty;

    try {
      qnty = Integer.parseInt(cboProdQnty.getValue());
    } catch (NumberFormatException ex) {
      qnty = null;
    }
    try {
      ProductionRecord[] records = Model.recordProduction(prod, qnty);

      // append the prods log...
      txtProdsLog.appendText(Arrays.stream(records)
          .filter(Objects::nonNull)
          .map(ProductionRecord::toString)
          .collect(Collectors.joining("\n")) + "\n");

      // set the 'produce' message label to success...
      lblProduceMsg.setTextFill(Color.web("green"));
      lblProduceMsg.setText(String.format(
          "Produced #%d '%s' x%d successfully.",
          prod.getId(), prod.getName(), qnty
      ));

    } catch (SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set the 'produce' message label to failure...
      lblProduceMsg.setTextFill(Color.web("red"));
      lblProduceMsg.setText("Couldn't do production: " + ex.getMessage());
    }
  }
}
