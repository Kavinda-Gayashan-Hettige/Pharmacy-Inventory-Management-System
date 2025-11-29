package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DBConnection;

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

    }

    @FXML
    void btnMonthlySalesReportOnAction(ActionEvent event) {

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
