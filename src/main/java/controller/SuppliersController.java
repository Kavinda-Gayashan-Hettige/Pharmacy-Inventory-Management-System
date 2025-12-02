package controller;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.dto.Suppliers;
import service.SupplierService;
import service.impl.SupplierServiceImpl;
import util.DBConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SuppliersController implements Initializable {

    public TableView <Suppliers> tblSupplier;
    public TableColumn colSupplierID;
    public TableColumn colName;
    public TableColumn colContactPerson;
    public TableColumn colPhone;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public Button btnUpdate;
    public Button btnSearchByID;
    public JFXTextField txtSupplierID;
    public JFXTextField txtName;
    public JFXTextField txtContactPerson;
    public JFXTextField txtPhone;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public Button btnFilterByContactPerson;
    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnFilterByName;


    @FXML
    private TextField txtSearch;



    SupplierService supplierService = new SupplierServiceImpl();
    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Suppliers suppliers = new Suppliers(
                txtSupplierID.getText(),
                txtName.getText(),
                txtContactPerson.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAddress.getText()

        );
        supplierService.AddSupplier(suppliers);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("New Supplier Registered");
        alert.setContentText("Supplier successfully added!");
        alert.showAndWait();

    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        Suppliers selected = (Suppliers) tblSupplier.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a row!");
            return;
        }
            supplierService.DeleteSupplierById(selected);
        loadTable();
        showAlert("Deleted", "Supplier Deleted");
    }
    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();

    }


    @FXML
    void btnFilterByNameOnAction(ActionEvent event) {
        String supplier = txtSearch.getText();
        viewSupplierByName(supplier);
    }

    private void viewSupplierByName(String supplier) {
        suppliersList.clear();

        Suppliers s = supplierService.ViewSupplierByName(supplier);
        if (s != null) {
            suppliersList.add(s);
        }
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            Suppliers selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
            txtSupplierID.setEditable(false);


            if (selectedItem != null) {


                selectedItem.setName(txtName.getText());
                selectedItem.setContactPerson(txtContactPerson.getText());
                selectedItem.setPhone(txtPhone.getText());
                selectedItem.setEmail(txtEmail.getText());
                selectedItem.setAddress(txtAddress.getText());

                supplierService.UpdateSupplier(selectedItem);

                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "No supplier selected!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).show();
        }
    }



    public void btnSearchByIdOnAction(ActionEvent actionEvent) {
        String supplier = txtSearch.getText();
        viewSupplierBySupplierID(supplier);
    }

    private void viewSupplierBySupplierID(String supplier) {
        suppliersList.clear();
        suppliersList.addAll(supplierService.ViewSupplierBySupplierId(supplier));
        tblSupplier.setItems(suppliersList);
    }



    ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblSupplier.setOnMouseClicked(event -> {
            Suppliers s = tblSupplier.getSelectionModel().getSelectedItem();
            if (s != null) {
                txtSupplierID.setText(s.getSupplierId());
                txtName.setText(s.getName());
                txtContactPerson.setText(s.getContactPerson());
                txtPhone.setText(s.getPhone());
                txtEmail.setText(s.getEmail());
                txtAddress.setText(s.getAddress());
            }
        });

        txtSupplierID.setEditable(true);


        tblSupplier.setItems(suppliersList);
        loadTable();



    }

    public void loadTable() {
        suppliersList.clear();
        suppliersList.addAll(supplierService.GetAllSuppliers());
        tblSupplier.setItems(suppliersList);
    }




    public void btnFilterByContactPersonOnAction(ActionEvent actionEvent) {
     String supplier = txtSearch.getText();
        viewSupplierByContactPerson(supplier);
    }

    private void viewSupplierByContactPerson(String supplier) {
       suppliersList.clear();
       suppliersList.addAll(supplierService.ViewSupplierByContactPerson(supplier));
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }

    public String getSupplierName(String supplierId) {
        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT name FROM suppliers WHERE supplier_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, supplierId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
 