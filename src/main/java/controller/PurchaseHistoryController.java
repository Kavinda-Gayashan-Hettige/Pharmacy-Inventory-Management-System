package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.dto.Medicines;
import model.dto.PurchaseHistory;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.PurchaseHistoryService;
import service.impl.PurchaseHistoryServiceImpl;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PurchaseHistoryController implements Initializable {



    public TableColumn colSupplierName;
    public TableView <PurchaseHistory> tblPurchaseHistory;
    public TextField txtDate;
    public Button btnSearch;
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


PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryServiceImpl();

    @FXML
    void btnDeleteRecordOnAction(ActionEvent event) {

        PurchaseHistory selected = (PurchaseHistory) tblPurchaseHistory.getSelectionModel().getSelectedItem();
        purchaseHistoryService.DeleteRecord(selected);
        if (selected == null) {
            showAlert("Error", "Please select a row!");
            return;
        }

        loadTable();
        showAlert("Deleted", "Medicine Deleted");

    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }

    @FXML
    void btnReprintInvoiceOnAction(ActionEvent event) throws IOException {

        PurchaseHistory selected = tblPurchaseHistory.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a record!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_history.fxml"));
        Parent root = loader.load();

        UpdateHistoryController controller = loader.getController();


        controller.setPurchaseHistoryController(this);


        controller.setSelectedItem(selected);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void btnViewInvoiceOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/PurchaseHistory.jrxml");

//            JRDesignQuery jrDesignQuery = new JRDesignQuery();
//            jrDesignQuery.setText("SELECT * FROM purchase_history WHERE invoice_no = '1'");
//            design.setQuery(jrDesignQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"purchase_history_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

        txtDate.setText(LocalDate.now().toString());
    }


    public void loadTable() {
        purchaseList.clear();
       purchaseList= purchaseHistoryService.loadTable();
      tblPurchaseHistory.setItems(purchaseList);
    }

    public void txtDateOnAction(ActionEvent actionEvent) {
    }
    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        String expiryDate = txtDate.getText();
        viewHistoryByExpiryDate(expiryDate);
    }

    private void viewHistoryByExpiryDate(String expiryDate) {
        purchaseList.clear();
        purchaseList=purchaseHistoryService.viewHistoryByExpiryDate(expiryDate);
      tblPurchaseHistory.setItems(purchaseList);
    }


}
