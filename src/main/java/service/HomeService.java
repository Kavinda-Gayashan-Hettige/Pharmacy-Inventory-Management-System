package service;

import javafx.collections.ObservableList;
import model.dto.Medicines;

public interface HomeService {
    ObservableList<Medicines> loadExpiringMedicines(int expiryAlertDays);

    ObservableList<Medicines> loadLowStockMedicine(int threshold);
}
