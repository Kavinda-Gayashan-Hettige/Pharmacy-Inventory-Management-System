package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Medicines;
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

        try {
            Connection con = DBConnection.getInstance();
            String sql = "DELETE FROM suppliers WHERE supplier_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, selected.getSupplierId());
            ps.executeUpdate();

            loadTable();
            showAlert("Deleted", "Supplier Deleted");

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


    @FXML
    void btnFilterByNameOnAction(ActionEvent event) {
        String supplier = txtSearch.getText();
        viewSupplierByName(supplier);
    }

    private void viewSupplierByName(String supplier) {
       suppliersList.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM suppliers WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, supplier);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Suppliers c = new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliersList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchByIdOnAction(ActionEvent actionEvent) {
        String supplier = txtSearch.getText();
        viewSupplierBySupplierID(supplier);
    }

    private void viewSupplierBySupplierID(String supplier) {
       suppliersList.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM suppliers WHERE supplier_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, supplier);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Suppliers c = new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliersList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    static ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblSupplier.setItems(suppliersList);
        loadTable();
    }

    public void loadTable() {
        suppliersList.clear();

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM suppliers";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {

                suppliersList.add(new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btnFilterByContactPersonOnAction(ActionEvent actionEvent) {
     String supplier = txtSearch.getText();
        viewSupplierByContactPerson(supplier);
    }

    private void viewSupplierByContactPerson(String supplier) {
       suppliersList.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

            String SQL = "SELECT * FROM suppliers WHERE contact_person = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, supplier);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {

                Suppliers c = new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliersList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
 