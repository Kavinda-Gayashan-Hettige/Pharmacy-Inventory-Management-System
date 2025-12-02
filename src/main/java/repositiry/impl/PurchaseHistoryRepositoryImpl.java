package repositiry.impl;

import model.dto.PurchaseHistory;
import repositiry.PurchaseHistoryRepository;
import util.DBConnection;

import java.sql.*;

public class PurchaseHistoryRepositoryImpl implements PurchaseHistoryRepository {
    @Override
    public void DeleteRecord(PurchaseHistory selected) throws Exception {
        Connection connection = DBConnection.getInstance();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM purchase_history WHERE invoice_no=?"
        );

        preparedStatement.setString(1, String.valueOf(selected.getInvoiceNo()));
        preparedStatement.executeUpdate();

    }

    @Override
    public ResultSet loadTable() throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM purchase_history";
        ResultSet rs = con.prepareStatement(sql).executeQuery();
        return rs;
    }

    @Override
    public ResultSet viewHistoryByExpiryDate(String expiryDate) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String SQL = "SELECT * FROM purchase_history WHERE date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, expiryDate);

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    @Override
    public int updatePurchaseHistory(PurchaseHistory purchaseHistory) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pharmacydb", "root", "1234");

        String sql = "UPDATE purchase_history SET date=?, supplier_name=?, total=?, payment_type=? WHERE invoice_no=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(5, Integer.parseInt(String.valueOf(purchaseHistory.getInvoiceNo())));
        ps.setDate(1, Date.valueOf(String.valueOf(purchaseHistory.getDate())));
        ps.setString(2,purchaseHistory.getSupplierName());
        ps.setDouble(3, Double.parseDouble(String.valueOf(purchaseHistory.getTotal())));
        ps.setString(4,purchaseHistory.getPaymentType());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
