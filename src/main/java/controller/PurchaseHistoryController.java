package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.PurchaseHistory;
import model.dto.PurchaseMedicines;
import util.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PurchaseHistoryController implements Initializable {

    public TableColumn colSupplierName;
    public TableView <PurchaseHistory> tblPurchaseHistory;
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
    static ObservableList<PurchaseHistory> purchaseList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        tblPurchaseHistory.setItems(purchaseList);
        loadTable();

        lblDate.setText(LocalDate.now().toString());
    }


    public void loadTable() {
        purchaseList.clear();

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM purchase_history";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {

                purchaseList.add(new PurchaseHistory(
                        rs.getInt("invoice_no"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("supplier_name"),
                        rs.getDouble("total"),
                        rs.getString("payment_type")

                ));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
