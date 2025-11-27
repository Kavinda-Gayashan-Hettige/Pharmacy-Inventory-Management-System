package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Medicines;
import model.dto.PurchaseHistory;
import model.dto.PurchaseMedicines;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PurchaseHistoryController implements Initializable {



    public TableColumn colSupplierName;
    public TableView <PurchaseHistory> tblPurchaseHistory;
    public TextField txtDate;
    public Button btnSearch;
    @FXML
    private Button btnDeleteRecord;

    @FXML
    private Button btnReprintInvoice;

    @FXML
    private Button btnViewInvoice;


    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colInvoiceNo;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colTotal;




    @FXML
    void btnDeleteRecordOnAction(ActionEvent event) {

        PurchaseHistory selected = (PurchaseHistory) tblPurchaseHistory.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a row!");
            return;
        }
        Connection connection = null;
        try {
            connection = DBConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE purchase_history WHERE invoice_no=?");
            preparedStatement.setString(1, String.valueOf(selected.getInvoiceNo()));
            preparedStatement.executeUpdate();

            loadTable();
            showAlert("Deleted", "Medicine Deleted");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }

    @FXML
    void btnReprintInvoiceOnAction(ActionEvent event) throws IOException {
    Stage stage = new Stage();
    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/update_history.fxml"))));
    stage.show();


    }

    @FXML
    void btnViewInvoiceOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/PurchaseHistory.jrxml");


//            JRDesignQuery jrDesignQuery = new JRDesignQuery();
//            jrDesignQuery.setText("SELECT * FROM purchase_history WHERE invoice_no = '1'");
//            design.setQuery(jrDesignQuery);


            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"purchase_history_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    static ObservableList<PurchaseHistory> purchaseList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        tblPurchaseHistory.setItems(purchaseList);
        loadTable();

        txtDate.setText(LocalDate.now().toString());
    }


    public void loadTable() {
        purchaseList.clear();

        try {
            Connection con = DBConnection.getInstance();
            String sql = "SELECT * FROM purchase_history";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {

                purchaseList.add(new PurchaseHistory(
                        rs.getInt("invoice_no"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("supplier_name"),
                        rs.getDouble("total"),
                        rs.getString("payment_type")

                ));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtDateOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }
}
