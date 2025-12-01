package service.impl;

import model.dto.Medicines;
import repositiry.MedicineRepository;
import repositiry.impl.MedicineRepositoryImpl;
import service.MedicineService;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class MedicineServiceImpl implements MedicineService {
    MedicineRepository medicineRepository = new MedicineRepositoryImpl();

    @Override
    public void AddMedicines(Medicines medicines) throws Exception {
        medicineRepository.AddMedicines(medicines);
    }

}
