package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {
    public void btnDashboardFormOnAction(ActionEvent actionEvent) {
    }

    public void btnMedicineFormOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierFormOnAction(ActionEvent actionEvent) {
    }

    public void btnReportsFormOnAction(ActionEvent actionEvent) {
    }

    public void btnSettingsFormOnAction(ActionEvent actionEvent) {
    }

    public void btnSalePOSFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sale-POS.fxml"))));
        stage.show();
    }
}
