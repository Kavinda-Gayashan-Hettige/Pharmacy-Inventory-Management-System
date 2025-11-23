package service.impl;


import model.dto.Suppliers;
import repositiry.SupplierReository;
import repositiry.impl.SupplierRepositoryImpl;
import service.SupplierService;

public class SupplierServiceImpl implements SupplierService {
SupplierReository reository = new SupplierRepositoryImpl();
    @Override
    public void AddSupplier(Suppliers suppliers) {
   reository.AddSupplier(suppliers);
    }
}

