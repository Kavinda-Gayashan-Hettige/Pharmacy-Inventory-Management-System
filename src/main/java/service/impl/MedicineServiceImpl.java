package service.impl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Medicines;
import repositiry.MedicineRepository;
import repositiry.impl.MedicineRepositoryImpl;
import service.MedicineService;

import java.sql.*;


public class MedicineServiceImpl implements MedicineService {
    MedicineRepository medicineRepository = new MedicineRepositoryImpl();

    @Override
    public void AddMedicines(Medicines medicines) throws Exception {
        medicineRepository.AddMedicines(medicines);
    }

    @Override
    public void DeleteMedicines(Medicines selected) {
        try {
            medicineRepository.DeleteMedicines(selected);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Medicines> viewMedicineByExpiryDate(String expiryDate) {
        ObservableList<Medicines>medicineList= FXCollections.observableArrayList();
        try {
            ResultSet rs = medicineRepository.viewMedicineByExpiryDate(expiryDate);

            while (rs.next()) {
                boolean remaining=false;
                Medicines c = new Medicines(
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
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicineList;
    }

    @Override
    public ObservableList<Medicines> viewMedicineBySupplier(String supplier) {
        ObservableList<Medicines>medicineList=FXCollections.observableArrayList();
        try {
            ResultSet rs = medicineRepository.viewMedicineBySupplier(supplier);

            while (rs.next()) {
                boolean remaining= false;

                Medicines c = new Medicines(
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
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicineList;
    }

    @Override
    public ObservableList<Medicines> loadTable() {
        ObservableList<Medicines>medicineList=FXCollections.observableArrayList();
        try {
            ResultSet rs = medicineRepository.loadTable();

            while (rs.next()) {
                boolean remaining = false;

                medicineList.add(new Medicines(
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
            e.printStackTrace();
        }
        return medicineList;
    }

    @Override
    public ObservableList<Medicines> viewMedicineByID(String medicineId) {
        ObservableList<Medicines>medicineList=FXCollections.observableArrayList();
        try {
            ResultSet rs = medicineRepository.viewMedicineByID(medicineId);

            while (rs.next()) {
                boolean remaining = false;

                Medicines c = new Medicines(
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
                );
                medicineList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicineList;
    }

    @Override
    public boolean increaseStock(String medicineId, int receivedQty) {
        try {
            return medicineRepository.increaseStock(medicineId, receivedQty);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int updateMedicine(Medicines medicines) {

        try{
            int rowsAffected = medicineRepository.updateMedicine(medicines);

            if (rowsAffected > 0) {
                System.out.println("Medicine updated successfully.");
            } else {
                System.out.println("No medicine found with ID: " + medicines.getMedicineId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
