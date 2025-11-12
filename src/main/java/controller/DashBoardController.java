package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashBoardController {
    public AnchorPane loadFormContent;
    public JFXButton btnSalesHistory;
    public JFXButton btnSalePOSForm;
    public JFXButton btnSettingsForm;
    public JFXButton btnReportsForm;
    public JFXButton btnSupplierForm;
    public JFXButton btnMedicineForm;
    public JFXButton btnDashboardForm;

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

    public void btnSupplierFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/suppliers_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }

    public void btnReportsFormOnAction(ActionEvent actionEvent) {
    }

    public void btnSettingsFormOnAction(ActionEvent actionEvent) {
    }

    public void btnSalePOSFormOnAction(ActionEvent actionEvent) throws IOException {
//        Stage stage = new Stage();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sale-POS_form.fxml"))));
//        stage.show();

        URL resource = this.getClass().getResource("/view/sale-POS_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

    }

    public void btnSalesHistoryFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/sales_history_form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);
    }
}
