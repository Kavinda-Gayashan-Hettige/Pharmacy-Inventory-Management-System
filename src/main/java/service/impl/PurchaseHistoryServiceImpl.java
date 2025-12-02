package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dto.PurchaseHistory;
import repositiry.PurchaseHistoryRepository;
import repositiry.impl.PurchaseHistoryRepositoryImpl;
import service.PurchaseHistoryService;

import java.sql.*;

public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    PurchaseHistoryRepository purchaseHistoryRepository = new PurchaseHistoryRepositoryImpl();

    @Override
    public void DeleteRecord(PurchaseHistory selected) {

        try {
            purchaseHistoryRepository.DeleteRecord(selected);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<PurchaseHistory> loadTable() {
        ObservableList<PurchaseHistory>purchaseList= FXCollections.observableArrayList();
        try {
            ResultSet rs = purchaseHistoryRepository.loadTable();

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
        return purchaseList;
    }

    @Override
    public ObservableList<PurchaseHistory> viewHistoryByExpiryDate(String expiryDate) {
        ObservableList<PurchaseHistory>purchaseList=FXCollections.observableArrayList();
        try {
            ResultSet rs = purchaseHistoryRepository.viewHistoryByExpiryDate(expiryDate);

            while (rs.next()) {

                PurchaseHistory c = new PurchaseHistory(
                        rs.getInt("invoice_no"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("supplier_name"),
                        rs.getDouble("total"),
                        rs.getString("payment_type")
                );
                purchaseList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchaseList;
    }

    @Override
    public int updatePurchaseHistory(PurchaseHistory purchaseHistory) {

        try{
            int rowsAffected = purchaseHistoryRepository.updatePurchaseHistory(purchaseHistory);

            if (rowsAffected > 0) {
                System.out.println("History updated successfully.");
                new Alert(Alert.AlertType.INFORMATION, "History updated successfully!").show();
            } else {
                System.out.println("No history found with ID: " + purchaseHistory.getInvoiceNo());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
