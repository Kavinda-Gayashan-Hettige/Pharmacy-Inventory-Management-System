package service;

import javafx.collections.ObservableList;
import model.dto.PurchaseHistory;

public interface PurchaseHistoryService {
    void DeleteRecord(PurchaseHistory selected);

    ObservableList<PurchaseHistory> loadTable();

    ObservableList<PurchaseHistory> viewHistoryByExpiryDate(String expiryDate);

    int updatePurchaseHistory(PurchaseHistory purchaseHistory);
}
