/*
 * AUTH: Jared O'Toole
 * DATE: Wed, Sep 11th, 2019
 * PROJ: ProductionLineTracker
 * FILE: ProdsLineController.java
 *
 * Defines the prods line controller class.
 */

package me.jwotoole9141.prodsline.gui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import me.jwotoole9141.prodsline.Main;
import me.jwotoole9141.prodsline.Model;
import me.jwotoole9141.prodsline.item.ItemType;
import me.jwotoole9141.prodsline.item.Product;
import me.jwotoole9141.prodsline.item.ProductionRecord;
import me.jwotoole9141.prodsline.user.Employee;

/**
 * Handles user interaction with the prods line GUI.
 *
 * @author Jared O'Toole
 */
public class ProdsLineController {

  // NOTE: IntelliJ says declaration access can be weaker,
  // but it is required to be public for FXML.

  /**
   * The employee currently logged into the prods line tracker.
   */
  private static Employee user;

  /**
   * The list of products loaded from the database.
   */
  private static final ObservableList<Product> productLine =
      FXCollections.observableArrayList();

  /**
   * The list of production records loaded from the database.
   */
  private static final ObservableList<ProductionRecord> productionLog =
      FXCollections.observableArrayList();

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
  private ComboBox<String> cboProdQnty;

  // NOTE: the javaFX combo box would return a string
  // for getValue() even when its type was integer.

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
   * The 'employee name' field.
   */
  @FXML
  public TextField fldEmplName;

  /**
   * The 'employee username' field.
   */
  @FXML
  public TextField fldEmplUser;

  /**
   * The 'employee email' field.
   */
  @FXML
  public TextField fldEmplEmail;

  /**
   * The 'logout' button.
   */
  @FXML
  public Button btnLogout;

  /**
   * The 'update employee name' field.
   */
  @FXML
  public TextField fldUpdateEmplName;

  /**
   * The 'update employee password' field.
   */
  @FXML
  public TextField fldUpdateEmplPass;

  /**
   * The 'employee password' field.
   */
  @FXML
  public TextField fldEmplPass;

  /**
   * The 'update employee' button.
   */
  @FXML
  public Button btnUpdateEmpl;

  /**
   * The 'update employee' button message label.
   */
  @FXML
  public Label lblUpdateEmplMsg;

  /**
   * Initializes the prods line GUI with additional data.
   */
  @FXML
  public void initialize() {

    productLine.clear();
    productionLog.clear();

    initChbNewProdType();
    initTblProducts();
    initLstProdOpts();
    initCboProdQnty();
    initTxtProdsLog();

    lblProduceMsg.setText("");
    lblAddProdMsg.setText("");

    productLine.addAll(Model.getProducts());
    productionLog.addAll(Model.getProdsRecords());

    Main.launchLoginPopup();
  }

  /**
   * Initializes the 'new product type' choice box data.
   */
  private void initChbNewProdType() {

    chbNewProdType.getItems().clear();

    for (ItemType type : ItemType.values()) {
      chbNewProdType.getItems().add(type);
    }
    chbNewProdType.getSelectionModel().selectFirst();
  }

  /**
   * Initializes the 'products' table data.
   */
  private void initTblProducts() {

    // NOTE: this method may be AKA 'setupProductLineTable'

    tblProducts.setItems(productLine);

    colProductsId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colProductsType.setCellValueFactory(new PropertyValueFactory<>("type"));
    colProductsManuf.setCellValueFactory(new PropertyValueFactory<>("manuf"));
  }

  /**
   * Initializes the 'production options' list data.
   */
  private void initLstProdOpts() {

    lstProdOpts.setItems(productLine);
  }

  /**
   * Initializes the 'production quantity' combo box data.
   */
  private void initCboProdQnty() {

    cboProdQnty.getItems().clear();

    for (int i = 0; i < 10; i++) {
      cboProdQnty.getItems().add(String.valueOf(i + 1));
    }
    cboProdQnty.setEditable(true);
    cboProdQnty.getSelectionModel().selectFirst();
  }

  /**
   * Initializes the 'production log' text area data.
   */
  private void initTxtProdsLog() {

    txtProdsLog.setText("");

    // NOTE: this listener implements the
    // behavior of 'showProduction'

    productionLog.addListener(
        (ListChangeListener<ProductionRecord>) c -> {
          while (c.next()) {
            txtProdsLog.appendText(
                c.getAddedSubList().stream()
                    .filter(Objects::nonNull)
                    .map(ProductionRecord::toString)
                    .collect(Collectors.joining("\n")) + "\n"
            );
          }
        }
    );
  }

  private void updateUserDisplay() {

    if (user != null) {

      fldEmplName.setText(user.getName());
      fldEmplUser.setText(user.getUsername());
      fldEmplEmail.setText(user.getEmail());
    }
    else {
      fldEmplName.setText("");
      fldEmplUser.setText("");
      fldEmplEmail.setText("");
    }
  }

  private void requireUserLoggedIn() {

    if (user == null) {
      throw new IllegalStateException(
          "You must be logged in to do that.");
    }
  }

  /**
   * Handles the 'add product' button being pressed.
   *
   * @param event the action performed
   */
  @FXML
  public void btnAddProdAction(ActionEvent event) {

    System.out.println(btnAddProd.toString() + " was pressed!");

    requireUserLoggedIn();

    // create a new product using form info...
    String name = fldNewProdName.getText().trim();
    String manuf = fldNewProdManuf.getText().trim();
    ItemType type = chbNewProdType.getValue();

    try {
      // NOTE: a new product is returned and put into
      // productLine instead of reloading everything
      // from the database.

      // add to database and the product line...
      Product newProd = Model.addProduct(name, type, manuf);
      productLine.add(newProd);

      // set the 'add prod' message label to success...
      lblAddProdMsg.setTextFill(Color.web("green"));
      lblAddProdMsg.setText(String.format(
          "Added product #%d '%s' successfully.",
          newProd.getId(), newProd.getName()
      ));

    } catch (IllegalStateException | SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set the 'add prod' message label to failure...
      lblAddProdMsg.setTextFill(Color.web("red"));
      lblAddProdMsg.setText("Couldn't add product: " + ex.getMessage());
    }
    event.consume();
  }

  /**
   * Handles the 'produce' button being pressed.
   *
   * @param event the action performed.
   */
  @FXML
  public void btnProduceAction(ActionEvent event) {

    System.out.println(btnProduce.toString() + " was pressed!");

    requireUserLoggedIn();

    // create production records using form info...
    Product prod = lstProdOpts.getSelectionModel().getSelectedItem();
    Integer qnty;
    Timestamp time = new Timestamp(System.currentTimeMillis());

    try {
      qnty = Integer.parseInt(cboProdQnty.getValue());
    } catch (NumberFormatException ex) {
      qnty = null;
    }

    try {
      // NOTE: new production records are returned and put
      // into productionLog instead of reloading everything
      // from the database.

      // record to database and the production log...
      productionLog.addAll(Model.recordProduction(prod, qnty, time));

      // set the 'produce' message label to success...
      lblProduceMsg.setTextFill(Color.web("green"));
      lblProduceMsg.setText(String.format(
          "Produced #%d '%s' x%d successfully.",
          prod.getId(), prod.getName(), qnty
      ));

    } catch (IllegalStateException | SQLException ex) {
      ex.printStackTrace();

    } catch (IllegalArgumentException ex) {

      // set the 'produce' message label to failure...
      lblProduceMsg.setTextFill(Color.web("red"));
      lblProduceMsg.setText("Couldn't produce: " + ex.getMessage());
    }
    event.consume();
  }

  /**
   * Handles the 'logout' button being pressed.
   *
   * @param event the action performed
   */
  public void btnLogoutAction(ActionEvent event) {

    requireUserLoggedIn();

    user = null;
    updateUserDisplay();
    Main.launchLoginPopup();

    event.consume();
  }

  /**
   * Handles the 'update employee' button being pressed.
   *
   * @param event the action performed
   */
  public void btnUpdateEmplAction(ActionEvent event) {

    requireUserLoggedIn();

    try {
      // if the password field matches the employee's password...
      if (fldEmplPass.getText().equals(user.getPassword())) {

        String newName = fldUpdateEmplName.getText();
        String newPass = fldUpdateEmplPass.getText();

        boolean updated = false;

        // update the name if specified...
        if (!newName.isEmpty()) {
          user.setName(newName);
          updated = true;
        }

        // update the password if specified...
        if (!newPass.isEmpty()) {
          user.setPassword(newPass);
          updated = true;
        }

        // set the label to success if something was updated...
        if (updated) {
          lblUpdateEmplMsg.setTextFill(Color.web("green"));
          lblUpdateEmplMsg.setText("Updated credentials successfully.");
        }
        else {
          throw new IllegalArgumentException("Nothing was updated.");
        }
      }
    } catch (IllegalArgumentException ex) {

      // set the label to failure...
      lblUpdateEmplMsg.setTextFill(Color.web("red"));
      lblUpdateEmplMsg.setText(ex.getMessage());

    } catch (IllegalStateException ex) {

      Main.launchErrorPopup();
      // TODO set error message
    }

    event.consume();
  }
}
