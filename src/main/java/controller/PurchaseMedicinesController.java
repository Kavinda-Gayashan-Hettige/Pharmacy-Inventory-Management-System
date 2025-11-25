package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.dto.Medicines;
import model.dto.PurchaseMedicines;
import model.dto.Suppliers;
import util.DBConnection;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PurchaseMedicinesController implements Initializable {
    @FXML
    public Button btnPurchase;
    @FXML
    public JFXTextField txtPurchaseId;

    public JFXTextField txtSupplierId;
    public Label lblQty;
    public Label lblMedicineName;
    public JFXTextField txtMedicineID;
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
        String purchaseId = txtPurchaseId.getText();
        String medicineName = lblMedicineName.getText(); // FLow depend on UI
        double unitPrice = Double.parseDouble(lblQty.getText());
        double discount = Double.parseDouble(lblDiscount.getText());
        LocalDate date = LocalDate.parse(lblDate.getText());
        int qty = Integer.parseInt(lblQty.getText());

        PurchaseMedicines item = new PurchaseMedicines(
                purchaseId,
                medicineName,
                unitPrice,
                discount,
                date,
                qty,
                (unitPrice - discount) * qty
        );

        purchaseList.add(item);
        tblAddToCart.refresh();

        calculateNetTotal();
    }
    private void calculateNetTotal() {
        double total = 0;
        for (PurchaseMedicines item : purchaseList) {
            total += item.getSubTotal();
        }
        lblNetTotal.setText(String.valueOf(total));
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

        lblDate.setText(LocalDate.now().toString());


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
    public String generatePurchaseId() {
        String lastId = null;
        try {
            Connection con = DBConnection.getInstance();
            ResultSet rs = con.prepareStatement(
                    "SELECT purchase_id FROM purchase_medicines ORDER BY purchase_id DESC LIMIT 1"
            ).executeQuery();

            if (rs.next()) {
                lastId = rs.getString(1); // "P012"
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lastId == null) {
            return "P001";
        }

        int num = Integer.parseInt(lastId.substring(1)); // remove "P" → 12
        num++;

        return String.format("P%03d", num);
    }



    public void txtPurchaseIdOnAction(ActionEvent actionEvent) {

        String id = txtPurchaseId.getText(); // P001

        for (PurchaseMedicines p : purchaseList) {
            if (p.getPurchaseId().equals(id)) {
                lblQty.setText(String.valueOf(p.getQty()));
                lblUnitPrice.setText(String.valueOf(p.getUnitPrice()));
                lblDiscount.setText(String.valueOf(p.getDiscount()));
                return;
            }
        }

        lblQty.setText("Not Found!");
    }



    public void txtMedicineIDOnAction(ActionEvent actionEvent) {

        String id = txtMedicineID.getText(); // M001

        for (Medicines m : MedicinesController.medicineList) {
            if (m.getMedicineId().equals(id)) {
                lblMedicineName.setText(m.getName());

                return;
            }
        }

        lblMedicineName.setText("Not Found!");
    }


}
