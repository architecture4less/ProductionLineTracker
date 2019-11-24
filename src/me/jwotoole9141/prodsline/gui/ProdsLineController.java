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
import java.util.ArrayList;
import java.util.List;
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
import me.jwotoole9141.prodsline.item.ProductFilter;
import me.jwotoole9141.prodsline.item.ProductionRecord;
import me.jwotoole9141.prodsline.item.RecordFilter;
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
  private Employee user;

  /**
   * The list of products loaded from the database.
   */
  private final ObservableList<Product> productLine =
      FXCollections.observableArrayList();

  /**
   * The list of production records loaded from the database.
   */
  private final ObservableList<ProductionRecord> productionLog =
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
   * The 'filter options key' choice box.
   */
  @FXML
  public ChoiceBox<ProductFilter> chbFilterOptsKey;

  /**
   * The 'filter options value' field.
   */
  @FXML
  public TextField fldFilterOptsVal;

  /**
   * The 'filter options' button.
   */
  @FXML
  public Button btnFilterOpts;

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
   * The 'filter log key' choice box.
   */
  @FXML
  public ChoiceBox<RecordFilter> chbFilterLogKey;

  /**
   * The 'filter log value' field.
   */
  @FXML
  public TextField fldFilterLogVal;

  /**
   * The 'filter log' button.
   */
  @FXML
  public Button btnFilterLog;

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

    initChbFilterOptsKey();
    initLstProdOpts();
    initCboProdQnty();

    initChbFilterLogKey();
    initTxtProdsLog();

    lblProduceMsg.setText("");
    lblAddProdMsg.setText("");
    lblUpdateEmplMsg.setText("");

    try {
      productLine.addAll(Model.getProducts());
      productionLog.addAll(Model.getProdsRecords());

    } catch (IllegalStateException ex) {
      Main.showError(ex);
    }
  }

  /**
   * Initializes the 'new product type' choice box data.
   */
  private void initChbNewProdType() {

    chbNewProdType.getItems().clear();
    chbNewProdType.getItems().addAll(ItemType.values());
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
   * Initializes the 'filter options key' choice box.
   */
  private void initChbFilterOptsKey() {

    chbFilterOptsKey.getItems().clear();
    chbFilterOptsKey.getItems().addAll(ProductFilter.values());
    chbFilterOptsKey.getSelectionModel().selectFirst();
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
   * Initializes the 'filter log key' choice box.
   */
  private void initChbFilterLogKey() {

    chbFilterLogKey.getItems().clear();
    chbFilterLogKey.getItems().addAll(RecordFilter.values());
    chbFilterLogKey.getSelectionModel().selectFirst();
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
            appendTxtProdsLog(c.getAddedSubList());
          }
        }
    );
  }

  /**
   * Appends the given records to the 'prods log' text area.
   *
   * @param list the production records
   */
  private void appendTxtProdsLog(List<? extends ProductionRecord> list) {

    txtProdsLog.appendText(
        list.stream()
            .filter(Objects::nonNull)
            .map(ProductionRecord::toString)
            .collect(Collectors.joining("\n")) + "\n"
    );
  }

  // /**
  //  * Gets the user currently logged into the prods line tracker.
  //  *
  //  * @return the employee user
  //  */
  // public Employee getUser() {
  //
  //   return this.user;
  // }

  /**
   * Sets the user currently logged into the prods line tracker.
   *
   * @param user the employee user
   */
  public void setUser(Employee user) {

    this.user = user;
    updateUserDisplay();
  }

  /**
   * Updates the employee tab with the credentials of the user currently logged in.
   */
  private void updateUserDisplay() {

    if (user != null) {

      fldEmplName.setText(user.getName());
      fldEmplUser.setText(user.getUsername());
      fldEmplEmail.setText(user.getEmail());
    } else {
      fldEmplName.setText("");
      fldEmplUser.setText("");
      fldEmplEmail.setText("");
    }
  }

  /**
   * Requires that a user has been logged in.
   *
   * @throws IllegalStateException a user has not been logged in
   */
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
      Main.showError(ex);

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
      productionLog.addAll(Model.recordProduction(prod, qnty, time, user));

      // set the 'produce' message label to success...
      lblProduceMsg.setTextFill(Color.web("green"));
      lblProduceMsg.setText(String.format(
          "Produced #%d '%s' x%d successfully.",
          prod.getId(), prod.getName(), qnty
      ));

    } catch (IllegalStateException | SQLException ex) {
      Main.showError(ex);

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

    user = null;
    updateUserDisplay();
    Main.logout();

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

      String newName = fldUpdateEmplName.getText();
      String newPass = fldUpdateEmplPass.getText();

      // make sure the password is correct...
      if (!fldEmplPass.getText().equals(user.getPassword())) {
        throw new IllegalArgumentException(
            "Invalid password entered.");
      }
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

        // lblUpdateEmplMsg.setTextFill(Color.web("green"));
        // lblUpdateEmplMsg.setText("Updated credentials successfully.");

        lblUpdateEmplMsg.setTextFill(Color.web("red"));
        lblUpdateEmplMsg.setText("This has not yet been implemented.");

      } else {
        throw new IllegalArgumentException("Nothing was updated.");
      }
    } catch (IllegalStateException ex) {
      Main.showError(ex);

    } catch (IllegalArgumentException ex) {

      // set the label to failure...
      lblUpdateEmplMsg.setTextFill(Color.web("red"));
      lblUpdateEmplMsg.setText(ex.getMessage());

    }

    fldUpdateEmplName.setText("");
    fldUpdateEmplPass.setText("");
    fldEmplPass.setText("");

    event.consume();
  }

  /**
   * Handles the 'filter options' button being pressed.
   *
   * @param event the action being performed
   */
  public void btnFilterOptsAction(ActionEvent event) {

    // get the filter key and value...
    ProductFilter key = chbFilterOptsKey.getSelectionModel().getSelectedItem();
    String val = fldFilterOptsVal.getText().toLowerCase();

    // if no key was selected, quit...
    if (key == ProductFilter.NONE) {

      lstProdOpts.setItems(productLine);
      return;
    }

    ArrayList<Product> alterList = new ArrayList<>();

    // if a val was given, filter the product options...
    if (val.isEmpty()) {
      alterList.addAll(productLine);
    } else {
      alterList.addAll(productLine.filtered(key.getFilter(val)));
    }

    // order the product options by key...
    alterList.sort(key.getComparator());

    // update product list...
    lstProdOpts.setItems(FXCollections.observableArrayList(alterList));

    event.consume();
  }

  /**
   * Handles the 'filter log' button being pressed.
   *
   * @param event the action being performed
   */
  public void btnFilterLogAction(ActionEvent event) {

    // get the filter key and value...
    RecordFilter key = chbFilterLogKey.getSelectionModel().getSelectedItem();
    String val = fldFilterLogVal.getText().toLowerCase();

    if (key == RecordFilter.NONE) {

      txtProdsLog.setText("");
      appendTxtProdsLog(productionLog);
      return;
    }

    ArrayList<ProductionRecord> alterLog = new ArrayList<>();

    // if a val was given, filter the prods log...
    if (val.isEmpty()) {
      alterLog.addAll(productionLog);
    } else {
      alterLog.addAll(productionLog.filtered(key.getFilter(val)));
    }

    // order the prods log by key...
    alterLog.sort(key.getComparator());

    // update prods log...
    txtProdsLog.setText("");
    appendTxtProdsLog(alterLog);

    event.consume();
  }
}
