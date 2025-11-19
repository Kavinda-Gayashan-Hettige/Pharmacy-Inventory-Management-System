package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HomeController {

    @FXML
    private TableColumn<?, ?> colExpiryDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colName2;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQty2;

    @FXML
    private TableColumn<?, ?> colRemaining;

    @FXML
    private Label lblLowStockMedicines;

    @FXML
    private Label lblTodaysSales;

    @FXML
    private Label lblTotalMedicine;

    @FXML
    private TableView<?> tblExpiringMedicines;

    @FXML
    private TableView<?> tblLowStockItems;

    public void SetTotalMedicineCount(){

}
}
