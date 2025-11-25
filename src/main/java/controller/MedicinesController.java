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
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        try {
            Connection con = DBConnection.getInstance();
            String sql = "DELETE FROM medicines WHERE medicineId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, selected.getMedicineId());
            ps.executeUpdate();

            loadTable();
            showAlert("Deleted", "Medicine Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void btnFilterByExpiryDateOnAction(ActionEvent event) {
        String expiryDate = txtSearch.getText();
        viewMedicineByExpiryDate(expiryDate);
    }

    private void viewMedicineByExpiryDate(String expiryDate) {
        medicineList.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM medicines WHERE expiryDate = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, expiryDate);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Medicines c = new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier")
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




@FXML
    void btnFilterBySupplierOnAction(ActionEvent event) {
        String supplier = txtSearch.getText();
        viewMedicineBySupplier(supplier);
    }

    private void viewMedicineBySupplier(String supplier) {
        medicineList.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM medicines WHERE supplier = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, supplier);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Medicines c = new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier")
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM medicines";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                medicineList.add(new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier")


                ));
            }

            tblMedicine.setItems(medicineList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {

        Medicines selected = tblMedicine.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("Please select a row first!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_medicine.fxml"));
        Parent root = loader.load();


        UpdateMedicineController controller = loader.getController();
        controller.setSelectedItem(selected);



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
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM medicines WHERE medicineId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, medicineId);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Medicines c = new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier")
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean increaseStock(String medicineId, int receivedQty) {
        try {
            Connection connection = DBConnection.getInstance();
            String sql = "UPDATE medicines SET quantity = quantity + ? WHERE medicineId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, receivedQty);
            ps.setString(2, medicineId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
