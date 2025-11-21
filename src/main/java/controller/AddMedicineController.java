package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class AddMedicineController {

    @FXML
    private JFXTextField txtMedicineID;
    @FXML
    private JFXTextField txtMedicineName;
    @FXML
    private JFXTextField txtBrand;
    @FXML
    private JFXTextField txtBatchNo;
    @FXML
    private JFXTextField txtExpiryDate;
    @FXML
    private JFXTextField txtQuantity;
    @FXML
    private JFXTextField txtPurchasePrice;
    @FXML
    private JFXTextField txtSellingPrice;
    @FXML
    private JFXTextField txtSupplier;
    @FXML
    private Button btnAdd;

    @FXML
    void btnAddOnAction(ActionEvent event) {

        try {
            Connection con = DBConnection.getInstance();
            String sql = "INSERT INTO medicines " +
                    "(medicineId, name, brand, batchNo, expiryDate, quantity, purchasePrice, sellingPrice, supplier) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtMedicineID.getText());
            ps.setString(2, txtMedicineName.getText());
            ps.setString(3, txtBrand.getText());
            ps.setString(4, txtBatchNo.getText());
            ps.setDate(5, java.sql.Date.valueOf(txtExpiryDate.getText()));
            ps.setInt(6, Integer.parseInt(txtQuantity.getText()));
            ps.setDouble(7, Double.parseDouble(txtPurchasePrice.getText()));
            ps.setDouble(8, Double.parseDouble(txtSellingPrice.getText()));
            ps.setString(9, txtSupplier.getText());

            ps.executeUpdate();

            showAlert("Success", "Medicine Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }
}
