package repositiry;

import model.dto.Medicines;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MedicineRepository {
    void AddMedicines(Medicines medicines) throws Exception;

    void DeleteMedicines(Medicines selected) throws Exception;

    ResultSet viewMedicineByExpiryDate(String expiryDate) throws SQLException;

    ResultSet viewMedicineBySupplier(String supplier) throws SQLException;

    ResultSet loadTable() throws Exception;

    ResultSet viewMedicineByID(String medicineId) throws SQLException;

    boolean increaseStock(String medicineId, int receivedQty) throws Exception;

    int updateMedicine(Medicines medicines) throws SQLException;
}
