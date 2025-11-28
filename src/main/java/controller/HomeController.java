package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.DashBoard;

import model.dto.Medicines;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public TableColumn colName1;
    public TableColumn colQty1;
    @FXML
    private TableColumn<?, ?> colExpiryDate;

    @FXML
    private TableColumn<?, ?> colName;


    @FXML
    private TableColumn<?, ?> colQty;



    @FXML
    private TableColumn<?, ?> colRemaining;

    @FXML
    private Label lblLowStockMedicines;

    @FXML
    private Label lblTodaysSales;

    @FXML
    private Label lblTotalMedicine;

    @FXML
    private TableView<DashBoard> tblExpiringMedicines;

    @FXML
    private TableView<DashBoard> tblLowStockItems;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      lblTodaysSales.setText("0.0");
        lblTotalMedicine.setText("");
        lblLowStockMedicines.setText("");

        double total =new PurchaseMedicinesController().calculateNetTotal();
        lblTodaysSales.setText(String.valueOf(total));


        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colQty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        tblExpiringMedicines.setItems(purchaseList);
        tblLowStockItems.setItems(purchaseList);
        loadTable();

        // Load Medicine UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/medicine_form.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MedicinesController medController = loader.getController();

        // get list
        ObservableList<Medicines> list = medController.getTotalMedicineList();

        // calculate & set label
        setTotalMedicineCount(list);
    }


    static ObservableList<DashBoard> purchaseList = FXCollections.observableArrayList();


    public void loadTable() {
        purchaseList.clear();

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM dashboard";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {

                purchaseList.add(new DashBoard(
                        rs.getString("name"),
                        rs.getDate("expiry_date").toLocalDate(),
                        rs.getInt("qty"),
                        rs.getBoolean("remaining")

                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLowStockMedicines();

    }

    public void setLowStockMedicines(){
        int totalQty = 0;

        for (DashBoard med : tblLowStockItems.getItems()) {
            totalQty += med.getQty();
        }
        lblLowStockMedicines.setText(String.valueOf(totalQty));
    }
    public void setTotalMedicineCount(ObservableList<Medicines> list) {

        int totalQty = 0;

        for (Medicines med : list) {
            totalQty += med.getQuantity();
        }

        lblTotalMedicine.setText(String.valueOf(totalQty));
    }

}
