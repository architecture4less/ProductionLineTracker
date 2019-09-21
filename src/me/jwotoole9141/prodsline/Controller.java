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

@SuppressWarnings("unused")
public class Controller {

    @FXML
    private TextField fldNewProdName;

    @FXML
    private TextField fldNewProdManuf;

    @FXML
    private ChoiceBox<?> chbNewProdType;

    @FXML
    private Button btnAddProd;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private ListView<?> lstProdsOpts;

    @FXML
    private ComboBox<?> cboProdsQnty;

    @FXML
    private TextArea txtProdsLog;

    @FXML
    void addProduct(ActionEvent event) {

        System.out.println("The 'Add product' button was clicked!");
    }
}
