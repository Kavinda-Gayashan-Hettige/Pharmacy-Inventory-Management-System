package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SalePOSController {
    @FXML
    public Button btnPurchase;
    @FXML
    public JFXTextField txtSeleId;
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
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<?> tblAddToCart;

    @FXML
    private JFXTextField txtCustomerId;



    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }


    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {

    }



    public void btnPurchaseOnAction(ActionEvent actionEvent) {
    }


    public void txtSeleIdOnAction(ActionEvent actionEvent) {
    }
}
