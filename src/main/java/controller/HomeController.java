package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import model.dto.Medicines;
import service.HomeService;
import service.impl.HomeServiceImpl;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public TableColumn colName1;
    public TableColumn colQty1;
    @FXML
    private TableColumn<?, ?> colExpiryDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRemaining;

    @FXML
    private Label lblLowStockMedicines;

    @FXML
    private Label lblTodaysSales;

    @FXML
    private Label lblTotalMedicine;

    @FXML
    private TableView<Medicines> tblExpiringMedicines;

    @FXML
    private TableView<Medicines> tblLowStockItems;


    private static int LOW_STOCK_THRESHOLD = 50;
    private static int expiryAlertDays = 30;

    static ObservableList<Medicines> lowStockList  = FXCollections.observableArrayList();
    private ObservableList<Medicines> expiringList = FXCollections.observableArrayList();

    HomeService homeService = new HomeServiceImpl();


    public static void setLowStockThreshold(int threshold) {

        LOW_STOCK_THRESHOLD = threshold;
    }

    public static int getLowStockThreshold() {

        return LOW_STOCK_THRESHOLD;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeControllerSingleton.setHomeController(this);

      lblTodaysSales.setText("0.0");
        lblTotalMedicine.setText("");
        lblLowStockMedicines.setText("");

        double total =new PurchaseMedicinesController().calculateNetTotal();
        lblTodaysSales.setText(String.valueOf(total));


        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        colName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQty1.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        loadExpiringMedicines();
        tblLowStockItems.setItems(lowStockList);
        tblExpiringMedicines.setItems(expiringList);
        updateLowStockCount();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/medicine_form.fxml"));
            loader.load();
            MedicinesController medController = loader.getController();

            ObservableList<Medicines> list = medController.getTotalMedicineList();

            setTotalMedicineCount(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateLowStockCount() {
        int totalQty = lowStockList.stream().mapToInt(Medicines::getQuantity).sum();
        lblLowStockMedicines.setText(String.valueOf(totalQty));
    }


    public void setExpiryAlertDays(int days) {
        this.expiryAlertDays = days;
        loadExpiringMedicines();
    }




    private void loadExpiringMedicines() {
        expiringList.clear();
        expiringList = homeService.loadExpiringMedicines(expiryAlertDays);
        tblExpiringMedicines.setItems(expiringList);
        setLowStockMedicines();

        loadLowStockMedicine(HomeController.getLowStockThreshold());


    }

    public void loadLowStockMedicine(int threshold){
        lowStockList.clear();
        lowStockList= homeService.loadLowStockMedicine(threshold);
        tblLowStockItems.setItems(lowStockList);
        updateLowStockCount();
    }

    public void setLowStockMedicines(){
        int totalQty = 0;

        for (Medicines med : tblLowStockItems.getItems()) {
            totalQty += med.getQuantity();
        }
        lblLowStockMedicines.setText(String.valueOf(totalQty));
    }
    public void setTotalMedicineCount(ObservableList<Medicines> list) {

        int totalQty = 0;

        for (Medicines med : list) {
            totalQty += med.getQuantity();
        }

        lblTotalMedicine.setText(String.valueOf(totalQty));
    }


}
