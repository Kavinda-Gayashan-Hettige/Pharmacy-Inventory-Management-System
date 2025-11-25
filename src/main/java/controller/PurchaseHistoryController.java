package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class PurchaseHistoryController {

    public TableColumn colSupplierName;
    @FXML
    private Button btnDeleteRecord;

    @FXML
    private Button btnReprintInvoice;

    @FXML
    private Button btnViewInvoice;


    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colInvoiceNo;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblDate;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnDeleteRecordOnAction(ActionEvent event) {

    }

    @FXML
    void btnReprintInvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewInvoiceOnAction(ActionEvent event) {


    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
