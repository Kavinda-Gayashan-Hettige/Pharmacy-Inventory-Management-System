package repositiry.impl;

import model.dto.Suppliers;
import repositiry.SupplierReository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierRepositoryImpl implements SupplierReository {
    @Override
    public void AddSupplier(Suppliers suppliers) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO suppliers VALUES (?,?,?,?,?,?)");
preparedStatement.setObject(1,suppliers.getSupplierId());
preparedStatement.setObject(2,suppliers.getName());
preparedStatement.setObject(3,suppliers.getContactPerson());
preparedStatement.setObject(4,suppliers.getPhone());
preparedStatement.setObject(5,suppliers.getEmail());
preparedStatement.setObject(6,suppliers.getAddress());

preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
