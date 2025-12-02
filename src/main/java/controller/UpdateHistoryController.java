package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import model.dto.PurchaseHistory;
import service.PurchaseHistoryService;
import service.impl.PurchaseHistoryServiceImpl;

import java.sql.*;


public class UpdateHistoryController {


    public JFXTextField txtDate;
    public JFXTextField txtSupplierName;
    public JFXTextField txtInvoiceNo;
    public JFXTextField txtPaymentType;
    public JFXTextField txtTotal;
    @FXML
    private Button btnUpdate;

    private PurchaseHistoryController purchaseHistoryController;

    public void setPurchaseHistoryController(PurchaseHistoryController controller) {
        this.purchaseHistoryController = controller;
    }

    PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryServiceImpl();

    public  void setSelectedItem(PurchaseHistory newValue) {
        setSelectedPurchaseHistory(newValue);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        PurchaseHistory purchaseHistory = new PurchaseHistory(

                Integer.parseInt(txtInvoiceNo.getText()),
                Date.valueOf(txtDate.getText()).toLocalDate(),
                txtSupplierName.getText(),
                Double.parseDouble(txtTotal.getText()),
                txtPaymentType.getText()


        );
        setSelectedItem(purchaseHistory);
        updatePurchaseHistory(purchaseHistory);
        purchaseHistoryController.loadTable();
        btnUpdate.getScene().getWindow().hide();
    }


    private void updatePurchaseHistory(PurchaseHistory purchaseHistory) {

      purchaseHistoryService.updatePurchaseHistory(purchaseHistory);

    }


    private void setSelectedPurchaseHistory(PurchaseHistory purchaseHistory) {

        txtInvoiceNo.setText(String.valueOf(purchaseHistory.getInvoiceNo()));
        txtDate.setText(String.valueOf(purchaseHistory.getDate()));
        txtSupplierName.setText(purchaseHistory.getSupplierName());
        txtTotal.setText(String.valueOf(purchaseHistory.getTotal()));
        txtPaymentType.setText(String.valueOf(purchaseHistory.getPaymentType()));

    }


}
