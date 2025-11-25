package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.DashBoard;
import util.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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
      lblTodaysSales.setText("59930.0");
        lblTotalMedicine.setText("225.0");
        lblLowStockMedicines.setText("103.0");

//        double total =new PurchaseMedicinesController().calculateNetTotal();
//        lblTodaysSales.setText(String.valueOf(total));


        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colQty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        tblExpiringMedicines.setItems(purchaseList);
        tblLowStockItems.setItems(purchaseList);
        loadTable();
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
    }

}
