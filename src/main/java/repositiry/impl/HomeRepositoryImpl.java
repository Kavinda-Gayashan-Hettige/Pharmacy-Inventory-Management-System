package repositiry.impl;

import repositiry.HomeRepository;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomeRepositoryImpl implements HomeRepository {
    @Override
    public ResultSet loadExpiringMedicines(int expiryAlertDays) throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM medicines WHERE DATEDIFF(expiryDate, CURDATE()) <= ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, expiryAlertDays);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    @Override
    public ResultSet loadLowStockMedicine(int threshold) throws Exception {
        Connection conn = DBConnection.getInstance();
        String sql = "SELECT * FROM medicines WHERE quantity < ?";
        conn= DBConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, threshold);

        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
