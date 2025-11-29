package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.DashBoard;

import model.dto.Medicines;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private TableView<Medicines> tblLowStockItems;


    private static int LOW_STOCK_THRESHOLD = 100;

    public static void setLowStockThreshold(int threshold) {
        LOW_STOCK_THRESHOLD = threshold;
    }

    public static int getLowStockThreshold() {
        return LOW_STOCK_THRESHOLD;
    }


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
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        tblExpiringMedicines.setItems(purchaseList);
        tblLowStockItems.setItems(medicineList);
        loadTable();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/medicine_form.fxml"));
            loader.load();
            MedicinesController medController = loader.getController();

            ObservableList<Medicines> list = medController.getTotalMedicineList();

            setTotalMedicineCount(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static ObservableList<DashBoard> purchaseList = FXCollections.observableArrayList();
    static ObservableList<Medicines> medicineList = FXCollections.observableArrayList();

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

        loadLowStockMedicine(HomeController.getLowStockThreshold());


    }

    public void loadLowStockMedicine(int threshold){
        medicineList.clear();

        try {
            Connection conn = DBConnection.getInstance();
            String sql = "SELECT * FROM medicines WHERE quantity < ?";
            conn= DBConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, threshold);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int quantity = rs.getInt("quantity");


                boolean remaining;

                if (quantity == 0) {
                    remaining = false;
                } else {
                    remaining = true;
                }


                medicineList.add(new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier"),
                        remaining

                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setLowStockMedicines(){
        int totalQty = 0;

        for (Medicines med : tblLowStockItems.getItems()) {
            totalQty += med.getQuantity();
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
