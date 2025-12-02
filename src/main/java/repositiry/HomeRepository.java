package repositiry;

import java.sql.ResultSet;

public interface HomeRepository {
    ResultSet loadExpiringMedicines(int expiryAlertDays) throws Exception;

    ResultSet loadLowStockMedicine(int threshold) throws Exception;
}
