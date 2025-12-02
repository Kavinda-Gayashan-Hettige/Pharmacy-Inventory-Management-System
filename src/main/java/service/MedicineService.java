package service;


import javafx.collections.ObservableList;
import model.dto.Medicines;

public interface MedicineService {
    void AddMedicines(Medicines medicines)throws Exception;

    void DeleteMedicines(Medicines selected);

    ObservableList<Medicines> viewMedicineByExpiryDate(String expiryDate);

    ObservableList<Medicines> viewMedicineBySupplier(String supplier);

    ObservableList<Medicines> loadTable();

    ObservableList<Medicines> viewMedicineByID(String medicineId);

    boolean increaseStock(String medicineId, int receivedQty);

    int updateMedicine(Medicines medicines);
}
