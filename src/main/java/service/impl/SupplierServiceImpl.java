package service.impl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dto.Suppliers;
import repositiry.SupplierReository;
import repositiry.impl.SupplierRepositoryImpl;
import service.SupplierService;

import java.sql.*;

public class SupplierServiceImpl implements SupplierService {
    SupplierReository reository = new SupplierRepositoryImpl();

    @Override
    public void AddSupplier(Suppliers suppliers) {
        try {
            reository.AddSupplier(suppliers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DeleteSupplierById(Suppliers selected) {
        try {
            reository.DeleteSupplier(selected);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Suppliers ViewSupplierByName(String supplier) {
        try {
            ResultSet rs = reository.ViewSupplier(supplier);

            if (rs.next()) {
                return new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Supplier not found!");
                alert.show();
                return null;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database Error!");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateSupplier(Suppliers selectedItem) {

        try{

        int rowsAffected =  reository.UpdateSupplier(selectedItem);
            if (rowsAffected > 0) {
                System.out.println("Supplier updated successfully.");
            } else {
                System.out.println("No supplier found with ID: " + selectedItem.getSupplierId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Suppliers> GetAllSuppliers() {

        ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
        try {
            ResultSet rs = reository.GetAllsuppliers();

            while (rs.next()) {
                suppliersList.add(new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suppliersList;
    }

    @Override
    public ObservableList<Suppliers> ViewSupplierBySupplierId(String supplier) {

        ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
        try {
            ResultSet rs =  reository.ViewSupplierById(supplier);

            while (rs.next()) {

                Suppliers c = new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliersList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliersList;
    }

    @Override
    public ObservableList<Suppliers> ViewSupplierByContactPerson(String supplier) {
        ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
        try {
           ResultSet rs = reository.ViewSupplierByContactPerson(supplier);

            while (rs.next()) {

                Suppliers c = new Suppliers(
                        rs.getString("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliersList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliersList;
    }

}

