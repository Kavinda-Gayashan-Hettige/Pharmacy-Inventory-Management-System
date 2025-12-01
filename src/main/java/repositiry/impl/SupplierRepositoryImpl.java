package repositiry.impl;

import model.dto.Suppliers;
import repositiry.SupplierReository;
import util.DBConnection;

import java.sql.*;



public class SupplierRepositoryImpl implements SupplierReository {
    @Override
    public void AddSupplier(Suppliers suppliers) throws SQLException {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO suppliers VALUES (?,?,?,?,?,?)");
preparedStatement.setObject(1,suppliers.getSupplierId());
preparedStatement.setObject(2,suppliers.getName());
preparedStatement.setObject(3,suppliers.getContactPerson());
preparedStatement.setObject(4,suppliers.getPhone());
preparedStatement.setObject(5,suppliers.getEmail());
preparedStatement.setObject(6,suppliers.getAddress());

preparedStatement.executeUpdate();


    }

    @Override
    public void DeleteSupplier(Suppliers selected) throws Exception {

            Connection con = DBConnection.getInstance();
            String sql = "DELETE FROM suppliers WHERE supplier_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, selected.getSupplierId());
            ps.executeUpdate();



    }

    @Override
    public ResultSet ViewSupplier(String supplier) throws Exception {

        Connection connection = DBConnection.getInstance();

        String SQL = "SELECT * FROM suppliers WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, supplier);

        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    @Override
    public int UpdateSupplier(Suppliers selectedItem) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");
        String sql = "UPDATE suppliers SET name=?, contact_person=?, phone=?, email=?, address=? WHERE supplier_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, selectedItem.getName());
        ps.setString(2, selectedItem.getContactPerson());
        ps.setString(3, selectedItem.getPhone());
        ps.setString(4, selectedItem.getEmail());
        ps.setString(5, selectedItem.getAddress());
        ps.setString(6, selectedItem.getSupplierId());


       return ps.executeUpdate();
    }

    @Override
    public ResultSet GetAllsuppliers() throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM suppliers";
      return con.prepareStatement(sql).executeQuery();
    }

    @Override
    public ResultSet ViewSupplierById(String supplier) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM suppliers WHERE supplier_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, supplier);

        ResultSet rs = preparedStatement.executeQuery();

return rs;
    }

    @Override
    public ResultSet ViewSupplierByContactPerson(String supplier) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM suppliers WHERE contact_person = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, supplier);

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

}
