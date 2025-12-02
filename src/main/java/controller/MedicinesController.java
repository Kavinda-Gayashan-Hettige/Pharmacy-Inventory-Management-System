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
import service.MedicineService;
import service.impl.MedicineServiceImpl;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class MedicinesController implements Initializable {
    @FXML
    private TableView<Medicines> tblMedicine;

    @FXML
    private TableColumn<Medicines, String> colMedicineID;
    @FXML
    private TableColumn<Medicines, String> colName;
    @FXML
    private TableColumn<Medicines, String> colBrand;
    @FXML
    private TableColumn<Medicines, String> colBatchNo;
    @FXML
    private TableColumn<Medicines, String> colExpiryDate;
    @FXML
    private TableColumn<Medicines, Integer> colQuantity;
    @FXML
    private TableColumn<Medicines, Double> colPurchasePrice;
    @FXML
    private TableColumn<Medicines, Double> colSellingPrice;
    @FXML
    private TableColumn<Medicines, String> colSupplier;
    @FXML
    private Button btnAddMedicine;

    @FXML
    private Button btnDeleteMedicine;

    @FXML
    private Button btnFilterByExpiryDate;

    @FXML
    private Button btnFilterBySupplier;



    @FXML
    private TextField txtSearch;

    static ObservableList<Medicines> medicineList = FXCollections.observableArrayList();
    MedicineService medicineService = new MedicineServiceImpl();

    @FXML
    void btnAddMedicineOnAction(ActionEvent event) throws IOException {

        loadTable();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_medicine.fxml"))));
        stage.show();


    }

    @FXML
    void btnDeleteMedicineOnAction(ActionEvent event) {
        Medicines selected = (Medicines) tblMedicine.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a row!");
            return;
        }
        medicineService.DeleteMedicines(selected);
        loadTable();
        showAlert("Deleted", "Medicine Deleted");
    }



    @FXML
    void btnFilterByExpiryDateOnAction(ActionEvent event) {
        String expiryDate = txtSearch.getText();
         viewMedicineByExpiryDate(expiryDate);
    }

    private void viewMedicineByExpiryDate(String expiryDate) {
        medicineList.clear();
        medicineList = medicineService.viewMedicineByExpiryDate(expiryDate);
        tblMedicine.setItems(medicineList);
    }


@FXML
    void btnFilterBySupplierOnAction(ActionEvent event) {
        String supplier = txtSearch.getText();
        viewMedicineBySupplier(supplier);
    }

    private void viewMedicineBySupplier(String supplier) {
        medicineList.clear();
        medicineList=medicineService.viewMedicineBySupplier(supplier);
       tblMedicine.setItems(medicineList);
    }



    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String text = txtSearch.getText();

        ObservableList<Medicines> searchResults = FXCollections.observableArrayList();

        for (Medicines m : medicineList) {
            if (m.getMedicineId().contains(text)||
                    m.getBrand().contains(text) ||
                    m.getSupplier().contains(text)) {

                searchResults.add(m);
            }
        }

        tblMedicine.setItems(searchResults);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMedicineID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colBatchNo.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

       tblMedicine.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
               new UpdateMedicineController().setSelectedItem((Medicines) newValue);
            }
        })));

        loadTable();
    }

    void loadTable() {
        medicineList.clear();
        medicineList=medicineService.loadTable();
        tblMedicine.setItems(medicineList);
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {

        Medicines selectedMedicine = tblMedicine.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_medicine.fxml"));
        Parent root = loader.load();

        UpdateMedicineController controller = loader.getController();


        controller.setMedicinesController(this);


        controller.setSelectedItem(selectedMedicine);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void btnSearchByIDOnAction(ActionEvent actionEvent) {
        String medicineId = txtSearch.getText();
        viewMedicineByID(medicineId);
    }

    private void viewMedicineByID(String medicineId) {
        medicineList.clear();
        medicineList=medicineService.viewMedicineByID(medicineId);
       tblMedicine.setItems(medicineList);
    }



    public boolean increaseStock(String medicineId, int receivedQty) {
        return medicineService.increaseStock(medicineId, receivedQty);
    }

    public ObservableList<Medicines> getTotalMedicineList() {
        return tblMedicine.getItems();
    }



}
