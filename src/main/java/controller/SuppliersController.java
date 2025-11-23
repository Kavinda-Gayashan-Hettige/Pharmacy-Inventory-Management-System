package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.dto.Suppliers;
import service.SupplierService;
import service.impl.SupplierServiceImpl;

public class SuppliersController {

    public TableView tblSupplier;
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
    @FXML
    private Button btnAddSupplier;



    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnFilterByName;

    @FXML
    private Button btnFilterBySupplier;

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

    }

    @FXML
    void btnFilterByNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnFilterBySupplierOnAction(ActionEvent event) {

    }


    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchByIdOnAction(ActionEvent actionEvent) {
    }
}
