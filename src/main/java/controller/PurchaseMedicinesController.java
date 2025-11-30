package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.dto.Medicines;
import model.dto.PurchaseMedicines;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    public Button btnPrintInvoice;
    public JFXTextField txtSupplierId;
    public Label lblSupplierName;
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
        String medicineName = lblMedicineName.getText(); //
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

            String sql = "INSERT INTO purchase_medicines VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (PurchaseMedicines item : purchaseList) {
            ps.setString(1, item.getPurchaseId());
            ps.setString(2, item.getMedicineName());
            ps.setDouble(3, item.getUnitPrice());
            ps.setDouble(4, item.getDiscount());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setInt(6, item.getQty());
            ps.setDouble(7,item.getSubTotal());

                ps.addBatch();

            }

            ps.executeUpdate();



            int invoiceNo = generateInvoiceNo();
            String supplierName = lblSupplierName.getText();
            LocalDate today = LocalDate.now();
            double total = Double.parseDouble(lblNetTotal.getText());
            String paymentType = "Cash";

            String sql2 = "INSERT INTO purchase_history (invoice_no, date, supplier_name, total, payment_type) VALUES (?,?,?,?,?)";
            PreparedStatement ps2 = connection.prepareStatement(sql2);

            ps2.setInt(1, invoiceNo);
            ps2.setDate(2, Date.valueOf(today));
            ps2.setString(3, supplierName);
            ps2.setDouble(4, total);
            ps2.setString(5, paymentType);

            ps2.executeUpdate();



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

        String id = txtMedicineID.getText();

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


    public void btnPrintInvoiceOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/purchase_medicine_report.jrxml");


//            JRDesignQuery jrDesignQuery = new JRDesignQuery();
//            jrDesignQuery.setText("SELECT * FROM purchase_medicines WHERE purchase_id = 'P001'");
//            design.setQuery(jrDesignQuery);



            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"purchase_medicine_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void txtSupplierIdOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        SuppliersController supplier = new SuppliersController();
        String name = supplier.getSupplierName(id);

        if (name != null) {
            lblSupplierName.setText(name);
        } else {
            lblSupplierName.setText("Not Found!");
        }
    }



    public int generateInvoiceNo() {
        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT invoice_no FROM purchase_history ORDER BY invoice_no DESC LIMIT 1";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


}
