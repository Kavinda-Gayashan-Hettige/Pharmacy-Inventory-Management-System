package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.dto.Setting;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    public AnchorPane loadFormContent;
    public JFXButton btnSalesHistory;
    public JFXButton btnSalePOSForm;
    public JFXButton btnSettingsForm;
    public JFXButton btnReportsForm;
    public JFXButton btnSupplierForm;
    public JFXButton btnMedicineForm;
    public JFXButton btnDashboardForm;
    @FXML
    public Label lblPharmacyName;
    @FXML
    public Label lblAdminName;
    @FXML
    public Label lblAddress;
    @FXML
    public Label lblPhone;
    @FXML
    public Label lblEmail;

    public void btnDashboardFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/home_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);


    }

    public void btnMedicineFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/medicine_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }

    public void btnSupplierFormOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/suppliers_form.fxml");
            if (resource == null) {
                System.err.println("FXML file not found!");
                return;
            }

            Parent load = FXMLLoader.load(resource);
            loadFormContent.getChildren().clear();
            loadFormContent.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void btnReportsFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/reports_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }

    public void btnSettingsFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/settings_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }

    public void btnSalePOSFormOnAction(ActionEvent actionEvent) throws IOException {
//        Stage stage = new Stage();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/purchase-medicine-form.fxml"))));
//        stage.show();

        URL resource = this.getClass().getResource("/view/purchase-medicine-form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);


    }

    public void btnSalesHistoryFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/purchas_history_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblPharmacyName.setText("");
        String name = new SettingsController().setPharmacyName();
        lblPharmacyName.setText(name);

    }


    public void setPharmacyName(String name) {
        lblPharmacyName.setText(name);

    }

    public void setPharmacyAddress(String address) {
        lblAddress.setText(address);
    }

    public void setPharmacyPhone(String phone) {
        lblPhone.setText(phone);
    }

    public void setPharmacyEmail(String email) {
        lblEmail.setText(email);
    }

    public void setPharmacyAdmin(String admin) {
        lblAdminName.setText(admin);
    }
}
