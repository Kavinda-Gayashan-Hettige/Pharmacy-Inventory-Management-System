package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ReportsService;
import service.impl.ReportsServiceImpl;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportsController {

    @FXML
    private Button btnDailySalesReport;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Button btnMonthlySalesReport;

    @FXML
    private Button btnStockSummary;

    @FXML
    private TextField txtFromDate;

    @FXML
    private TextField txtToDate;

    ReportsService reportsService = new ReportsServiceImpl();

    @FXML
    void btnDailySalesReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Daily_Sale_Report.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"daily_sale_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDate = LocalDate.parse(txtFromDate.getText().trim(), formatter);
            LocalDate toDate = LocalDate.parse(txtToDate.getText().trim(), formatter);

            Connection con = DBConnection.getInstance();

            String sql = "INSERT INTO reports (report_type, from_date, to_date) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "stock_summary");
            ps.setDate(2, Date.valueOf(fromDate));
            ps.setDate(3, Date.valueOf(toDate));

            ps.executeUpdate();

            showAlert("Updated", "Your request has been noted. The stock summary report for the specified time period will be prepared shortly.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save report dates! Check input format yyyy-MM-dd.");
        }
    }


    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }


    @FXML
    void btnMonthlySalesReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Monthly_Sale_Report.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"monthly_sale_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnStockSummaryOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Stock_Summary_Report.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance());

            JasperExportManager.exportReportToPdfFile(jasperPrint,"stock_summary_report.pdf");

            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
