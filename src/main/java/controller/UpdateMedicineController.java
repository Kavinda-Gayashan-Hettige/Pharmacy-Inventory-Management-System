package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.dto.Medicines;
import service.MedicineService;
import service.impl.MedicineServiceImpl;


import java.time.LocalDate;


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

    private MedicinesController medicinesController;

    public void setMedicinesController(MedicinesController controller) {
        this.medicinesController = controller;
    }


    MedicineService medicineService = new MedicineServiceImpl();


    public  void setSelectedItem(Medicines newValue) {
        try {
            setSelectedMedicines(newValue);
        }catch( NullPointerException e) {
           System.out.println("");
        }
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
                txtSupplier.getText(),
                false
        );
        setSelectedItem(medicines);
        updateMedicine(medicines);
        medicinesController.loadTable();
        btnUpdate.getScene().getWindow().hide();
    }


    private int updateMedicine(Medicines medicines) {
       int selected = medicineService.updateMedicine(medicines);
       return selected;

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
