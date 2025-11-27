package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import model.dto.PurchaseHistory;

import java.sql.*;


public class UpdateHistoryController {


    public JFXTextField txtDate;
    public JFXTextField txtSupplierName;
    public JFXTextField txtInvoiceNo;
    public JFXTextField txtPaymentType;
    public JFXTextField txtTotal;
    @FXML
    private Button btnUpdate;


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
        new PurchaseHistoryController().loadTable();

    }


    private void updatePurchaseHistory(PurchaseHistory purchaseHistory) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pharmacydb", "root", "1234")) {

            String sql = "UPDATE purchase_history SET date=?, supplier_name=?, total=?, payment_type=? WHERE invoice_no=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(5, Integer.parseInt(String.valueOf(purchaseHistory.getInvoiceNo())));
            ps.setDate(1, Date.valueOf(String.valueOf(purchaseHistory.getDate())));
            ps.setString(2,purchaseHistory.getSupplierName());
            ps.setDouble(3, Double.parseDouble(String.valueOf(purchaseHistory.getTotal())));
            ps.setString(4,purchaseHistory.getPaymentType());



            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("History updated successfully.");
            } else {
                System.out.println("No history found with ID: " + purchaseHistory.getInvoiceNo());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setSelectedPurchaseHistory(PurchaseHistory purchaseHistory) {

        txtInvoiceNo.setText(String.valueOf(purchaseHistory.getInvoiceNo()));
        txtDate.setText(String.valueOf(purchaseHistory.getDate()));
        txtSupplierName.setText(purchaseHistory.getSupplierName());
        txtTotal.setText(String.valueOf(purchaseHistory.getTotal()));
        txtPaymentType.setText(String.valueOf(purchaseHistory.getPaymentType()));

    }


}
