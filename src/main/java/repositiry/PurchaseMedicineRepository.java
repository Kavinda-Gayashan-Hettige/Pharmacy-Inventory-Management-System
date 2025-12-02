package repositiry;

import java.sql.ResultSet;

public interface PurchaseMedicineRepository {
    ResultSet loadTable() throws Exception;
}
