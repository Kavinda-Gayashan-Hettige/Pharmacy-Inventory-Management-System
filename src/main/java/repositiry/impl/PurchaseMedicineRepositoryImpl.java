package repositiry.impl;

import repositiry.PurchaseMedicineRepository;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;

public class PurchaseMedicineRepositoryImpl implements PurchaseMedicineRepository {
    @Override
    public ResultSet loadTable() throws Exception {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM purchase_medicines";
        ResultSet rs = con.prepareStatement(sql).executeQuery();
        return rs;
    }
}
