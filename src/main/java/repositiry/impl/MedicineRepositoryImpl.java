package repositiry.impl;

import model.dto.Medicines;
import repositiry.MedicineRepository;
import util.DBConnection;

import java.sql.*;

public class MedicineRepositoryImpl implements MedicineRepository {
    @Override
    public void AddMedicines(Medicines medicines) throws Exception {

        Connection con = DBConnection.getInstance();
        con.setAutoCommit(true);

        String sql = "INSERT INTO medicines " +
                "(medicineId, name, brand, batchNo, expiryDate, quantity, purchasePrice, sellingPrice, supplier) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, medicines.getMedicineId());
        ps.setString(2, medicines.getName());
        ps.setString(3, medicines.getBrand());
        ps.setString(4, medicines.getBatchNo());
        ps.setDate(5, Date.valueOf(medicines.getExpiryDate()));
        ps.setInt(6, medicines.getQuantity());
        ps.setDouble(7, medicines.getPurchasePrice());
        ps.setDouble(8, medicines.getSellingPrice());
        ps.setString(9, medicines.getSupplier());

        ps.executeUpdate();
    }

    @Override
    public void DeleteMedicines(Medicines selected) throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "DELETE FROM medicines WHERE medicineId=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, selected.getMedicineId());
        ps.executeUpdate();
    }

    @Override
    public ResultSet viewMedicineByExpiryDate(String expiryDate) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM medicines WHERE expiryDate = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, expiryDate);

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    @Override
    public ResultSet viewMedicineBySupplier(String supplier) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM medicines WHERE supplier = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, supplier);

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    @Override
    public ResultSet loadTable() throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM medicines";
        ResultSet rs = con.prepareStatement(sql).executeQuery();
        return rs;
    }

    @Override
    public ResultSet viewMedicineByID(String medicineId) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM medicines WHERE medicineId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, medicineId);

        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    @Override
    public boolean increaseStock(String medicineId, int receivedQty) throws Exception {
        Connection connection = DBConnection.getInstance();
        String sql = "UPDATE medicines SET quantity = quantity + ? WHERE medicineId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, receivedQty);
        ps.setString(2, medicineId);

        return ps.executeUpdate() > 0;
    }

    @Override
    public int updateMedicine(Medicines medicines) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String sql = "UPDATE medicines SET name=?, brand=?, batchNo=?, expiryDate=?, quantity=?, purchasePrice=?, sellingPrice=?, supplier=? WHERE medicineId=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, medicines.getName());
        ps.setString(2, medicines.getBrand());
        ps.setString(3, medicines.getBatchNo());
        ps.setDate(4, java.sql.Date.valueOf(medicines.getExpiryDate()));
        ps.setInt(5, medicines.getQuantity());
        ps.setDouble(6, medicines.getPurchasePrice());
        ps.setDouble(7, medicines.getSellingPrice());
        ps.setString(8, medicines.getSupplier());
        ps.setString(9, medicines.getMedicineId());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
