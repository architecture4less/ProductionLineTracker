package me.jwotoole9141.prodsline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button btnAddProd;

    @FXML
    void addProduct(ActionEvent event) {
        System.out.println("Button clicked!");
    }
}
