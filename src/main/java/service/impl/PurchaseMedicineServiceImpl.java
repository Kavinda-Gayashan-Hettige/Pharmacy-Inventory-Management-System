package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.PurchaseMedicines;
import repositiry.PurchaseMedicineRepository;
import repositiry.impl.PurchaseMedicineRepositoryImpl;
import service.PurchaseMedicineService;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;

public class PurchaseMedicineServiceImpl implements PurchaseMedicineService {
    PurchaseMedicineRepository purchaseMedicineRepository = new PurchaseMedicineRepositoryImpl();

    @Override
    public ObservableList<PurchaseMedicines> loadTable() {
        ObservableList<PurchaseMedicines>purchaseList= FXCollections.observableArrayList();
        try {
            ResultSet rs = purchaseMedicineRepository.loadTable();

            while (rs.next()) {

                purchaseList.add(new PurchaseMedicines(
                        rs.getString("purchase_id"),
                        rs.getString("medicine_name"),
                        rs.getDouble("unit_price"),
                        rs.getDouble("discount"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("qty"),

                        0
                ));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchaseList;
    }
}
