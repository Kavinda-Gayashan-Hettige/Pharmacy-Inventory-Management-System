package repositiry;

import model.dto.PurchaseHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PurchaseHistoryRepository {
    void DeleteRecord(PurchaseHistory selected) throws Exception;

    ResultSet loadTable() throws Exception;

    ResultSet viewHistoryByExpiryDate(String expiryDate) throws SQLException;

    int updatePurchaseHistory(PurchaseHistory purchaseHistory) throws SQLException;
}
