package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.dto.Medicines;
import org.apache.logging.log4j.message.StringMapMessage;
import service.MedicineService;
import service.impl.MedicineServiceImpl;
import util.DBConnection;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;


public class AddMedicineController  {

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

    MedicineService medicineService = new MedicineServiceImpl();

    @FXML
    void btnAddOnAction(ActionEvent event) {

        try {

            String expiryText = txtExpiryDate.getText().trim();
            if (expiryText.isEmpty()) {
                showAlert("Error", "Expiry Date cannot be empty!");
                return;
            }

            LocalDate expiryDate;
            try {
                expiryDate = LocalDate.parse(expiryText);
            } catch (Exception ex) {
                showAlert("Error", "Invalid date format! Use yyyy-MM-dd");
                return;
            }

            Medicines medicines = new Medicines(
                    txtMedicineID.getText(),
                    txtMedicineName.getText(),
                    txtBrand.getText(),
                    txtBatchNo.getText(),
                    expiryDate,
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtPurchasePrice.getText()),
                    Double.parseDouble(txtSellingPrice.getText()),
                    txtSupplier.getText(),
                    true
            );

            medicineService.AddMedicines(medicines);
            showAlert("Success", "Medicine Added Successfully!");

        } catch (Exception e) {
            showAlert("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }


}
