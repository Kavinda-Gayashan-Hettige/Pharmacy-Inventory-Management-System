package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.dto.Medicines;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateMedicineController {

    @FXML
    private Button btnUpdate;

    @FXML
    private  JFXTextField txtBatchNo;

    @FXML
    private  JFXTextField txtBrand;

    @FXML
    private  JFXTextField txtExpiryDate;

    @FXML
    private  JFXTextField txtMedicineID;

    @FXML
    private  JFXTextField txtMedicineName;

    @FXML
    private  JFXTextField txtPurchasePrice;

    @FXML
    private  JFXTextField txtQuantity;

    @FXML
    private  JFXTextField txtSellingPrice;

    @FXML
    private  JFXTextField txtSupplier;

    public  void setSelectedItem(Medicines newValue) {
        setSelectedMedicines(newValue);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Medicines medicines = new Medicines(
                txtMedicineID.getText(),
                txtMedicineName.getText(),
                txtBrand.getText(),
                txtBatchNo.getText(),
                LocalDate.parse(txtExpiryDate.getText()),
                Integer.parseInt(txtQuantity.getText()),
                Double.parseDouble(txtPurchasePrice.getText()),
                Double.parseDouble(txtSellingPrice.getText()),
                txtSupplier.getText()
        );
        setSelectedItem(medicines);
        updateMedicine(medicines);
        new MedicinesController().loadTable();

    }


    private void updateMedicine(Medicines medicines) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pharmacydb", "root", "1234")) {

            String sql = "UPDATE medicines SET name=?, brand=?, batchNo=?, expiryDate=?, quantity=?, purchasePrice=?, sellingPrice=?, supplier=? WHERE medicineId=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, medicines.getName());
            ps.setString(2, medicines.getBrand());
            ps.setString(3, medicines.getBatchNo());
            ps.setDate(4, java.sql.Date.valueOf(medicines.getExpiryDate())); // safer than Timestamp
            ps.setInt(5, medicines.getQuantity());
            ps.setDouble(6, medicines.getPurchasePrice());
            ps.setDouble(7, medicines.getSellingPrice());
            ps.setString(8, medicines.getSupplier());
            ps.setString(9, medicines.getMedicineId()); // WHERE clause

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medicine updated successfully.");
            } else {
                System.out.println("No medicine found with ID: " + medicines.getMedicineId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setSelectedMedicines(Medicines medicines) {
        txtMedicineID.setText(medicines.getMedicineId());
        txtMedicineName.setText(medicines.getName());
        txtBrand.setText(medicines.getBrand());
        txtBatchNo.setText(medicines.getBatchNo());
        txtExpiryDate.setText(String.valueOf(medicines.getExpiryDate()));
        txtQuantity.setText(String.valueOf(medicines.getQuantity()));
        txtSellingPrice.setText(String.valueOf(medicines.getSellingPrice()));
        txtSupplier.setText(medicines.getSupplier());
        txtPurchasePrice.setText(String.valueOf(medicines.getPurchasePrice()));
    }


}
