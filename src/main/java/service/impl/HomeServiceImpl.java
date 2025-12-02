package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Medicines;
import repositiry.HomeRepository;
import repositiry.impl.HomeRepositoryImpl;
import service.HomeService;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomeServiceImpl implements HomeService {
    HomeRepository homeRepository = new HomeRepositoryImpl();

    @Override
    public ObservableList<Medicines> loadExpiringMedicines(int expiryAlertDays) {
        ObservableList<Medicines>expiringList= FXCollections.observableArrayList();

        try {
            ResultSet rs = homeRepository.loadExpiringMedicines(expiryAlertDays);

            while (rs.next()) {
                expiringList.add(new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier"),
                        rs.getInt("quantity") > 0 // remaining
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return expiringList;
    }

    @Override
    public ObservableList<Medicines> loadLowStockMedicine(int threshold) {
        ObservableList<Medicines>lowStockList=FXCollections.observableArrayList();
        try {
            ResultSet rs = homeRepository.loadLowStockMedicine(threshold);


            while (rs.next()) {

                int quantity = rs.getInt("quantity");


                boolean remaining;

                if (quantity == 0) {
                    remaining = false;
                } else {
                    remaining = true;
                }


                lowStockList.add(new Medicines(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batchNo"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getString("supplier"),
                        remaining

                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lowStockList;
    }
}
