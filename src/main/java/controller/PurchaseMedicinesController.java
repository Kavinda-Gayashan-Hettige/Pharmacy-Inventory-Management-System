package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Medicines;
import model.dto.PurchaseMedicines;
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

    public Label lblMedicineName;
    public JFXTextField txtMedicineID;
    public  JFXTextField txtQty;
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
    public  Label lblUnitPrice;

    @FXML
    private TableView<PurchaseMedicines> tblAddToCart;



    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String purchaseId = txtPurchaseId.getText();
        String medicineName = lblMedicineName.getText(); // FLow depend on UI
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double discount = Double.parseDouble(lblDiscount.getText());
        LocalDate date = LocalDate.parse(lblDate.getText());
        int qty = Integer.parseInt(txtQty.getText());

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

    public double calculateNetTotal() {
        double total = 0;

        try {
            if (purchaseList != null) {
                for (PurchaseMedicines item : purchaseList) {
                    if (item != null) {
                        total += item.getSubTotal();
                    }
                }
            }


            if (lblNetTotal != null) {
                lblNetTotal.setText(String.valueOf(total));
            }

        } catch (Exception e) {
            System.out.println("calculateNetTotal() Error: " + e.getMessage());
        }

        return total;
    }
//    public double calculateNetTotal() {
//        double total = 0;
//
//        for (PurchaseMedicines item : purchaseList) {
//            total += item.getSubTotal();
//        }
//
//        lblNetTotal.setText(String.valueOf(total));
//        return total;
//    }



    @FXML
    void btnPurchaseOnAction(ActionEvent event) {

        Connection connection = null;

        try {
            connection = DBConnection.getInstance();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO purchase_medicines VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (PurchaseMedicines item : purchaseList) {
            ps.setString(1, item.getPurchaseId());
            ps.setString(2, item.getMedicineName());
            ps.setDouble(3, item.getUnitPrice());
            ps.setDouble(4, item.getDiscount());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setInt(6, item.getQty());
                ps.addBatch();

            }

            ps.executeUpdate();


            String medId = txtMedicineID.getText();
            int receivedQty = Integer.parseInt(txtQty.getText());

            MedicinesController medicineController = new MedicinesController();

            boolean updated = medicineController.increaseStock(medId, receivedQty);

            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Stock Update Failed!").show();
            }


            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Purchase Saved!").show();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Duplicate ID!").show();

        } catch (Exception e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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


    public void txtMedicineIDOnAction(ActionEvent actionEvent) {

        String id = txtMedicineID.getText(); // M001

        for (Medicines m : MedicinesController.medicineList) {
            if (m.getMedicineId().equals(id)) {
                lblMedicineName.setText(m.getName());
                lblUnitPrice.setText(String.valueOf(m.getPurchasePrice()));
                lblDiscount.setText("0.0");
                return;
            }
        }

        lblMedicineName.setText("Not Found!");
    }

    
}
