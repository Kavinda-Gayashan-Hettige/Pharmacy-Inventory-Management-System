package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.PurchaseMedicines;
import model.dto.Suppliers;
import util.DBConnection;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PurchaseMedicinesController implements Initializable {
    @FXML
    public Button btnPurchase;
    @FXML
    public JFXTextField txtPurchaseId;
    public Label lblSupplierName;
    public JFXTextField txtSupplierId;
    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnAddToCart1;

    @FXML
    private TableColumn<?, ?> colMedicineName;

    @FXML
    private TableColumn<?, ?> colSubtotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<PurchaseMedicines> tblAddToCart;

    
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }



    public void btnPurchaseOnAction(ActionEvent actionEvent) {

    }


    static ObservableList<PurchaseMedicines> purchaseList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));


        tblAddToCart.setItems(purchaseList);
        loadTable();
    }

    public void loadTable() {
        purchaseList.clear();

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM purchase_medicines";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {

                purchaseList.add(new PurchaseMedicines(
                        rs.getString("purchase_id"),
                        rs.getString("medicine_name"),
                        rs.getDouble("unit_price"),
                        rs.getDouble("discount"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("qty"),
                        0
                ));

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void txtPurchaseIdOnAction(ActionEvent actionEvent) {
    }

    public void txtSupplierIdOnAction(ActionEvent actionEvent) {
    }
}
