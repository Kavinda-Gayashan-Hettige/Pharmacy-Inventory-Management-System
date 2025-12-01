package repositiry;

import model.dto.Medicines;

public interface MedicineRepository {
    void AddMedicines(Medicines medicines) throws Exception;
}
