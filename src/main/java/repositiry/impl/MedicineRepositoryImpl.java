package repositiry.impl;

import model.dto.Medicines;
import repositiry.MedicineRepository;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

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
}
