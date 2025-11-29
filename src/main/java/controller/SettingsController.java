package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.Medicines;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{


    public TextField txtLowStockAleart;
    public TextField txtExpiryAleart;
    @FXML
    public TextField txtAdmin;
    @FXML
    private Button btnResetToDefault;

    @FXML
    private Button btnSaveSetting;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    public TextField txtPharmacyName;

    @FXML
    private TextField txtPhone;

    @FXML
    void btnResetToDefaultOnAction(ActionEvent event) {
txtPharmacyName.setText("Pharmacy Inventory Management System");
txtAddress.setText("No.356,Galle Road,Panadura.");
txtPhone.setText("0760375176");
txtEmail.setText("kavinda@gmail.com");
txtAdmin.setText(" Kavinda");

    }

//    @FXML
//    void btnSaveSettingOnAction(ActionEvent event) {
//        String name = setPharmacyName();
//        System.out.println("Saved Name: " + name);
//    }




    @FXML
    void btnSaveSettingOnAction(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dash_board_form.fxml"));
            Parent root = loader.load();


            DashBoardController controller = loader.getController();


            String name = txtPharmacyName.getText();
            controller.setPharmacyName(name);

            String address = txtAddress.getText();
            controller.setPharmacyAddress(address);

            String phone = txtPhone.getText();
            controller.setPharmacyPhone(phone);

            String email = txtEmail.getText();
            controller.setPharmacyEmail(email);

            String admin = txtAdmin.getText();
            controller.setPharmacyAdmin(admin);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home_form.fxml"));
            Parent root = loader.load();

            HomeController controller = loader.getController();


            int lowStock = Integer.parseInt(txtLowStockAleart.getText());
            controller.setLowStockQty(Integer.parseInt(String.valueOf(lowStock)));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String setPharmacyName(){
        String name = "Pharmacy Inventory Management System";
        try {
            if (txtPharmacyName!=null) {
              name= txtPharmacyName.getText();
            }
        }catch (NullPointerException e) {
            System.out.println("setPharmacyName() Error: " + e.getMessage());
        }
        return name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
