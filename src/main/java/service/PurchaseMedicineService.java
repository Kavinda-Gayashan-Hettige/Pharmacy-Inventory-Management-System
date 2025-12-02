package service;

import javafx.collections.ObservableList;
import model.dto.PurchaseMedicines;

public interface PurchaseMedicineService {
    ObservableList<PurchaseMedicines> loadTable();
}
