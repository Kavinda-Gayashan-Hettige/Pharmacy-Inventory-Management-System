package service;

import javafx.collections.ObservableList;
import model.dto.Suppliers;

public interface SupplierService {
    void AddSupplier(Suppliers suppliers);

    void DeleteSupplierById(Suppliers selected);

    Suppliers ViewSupplierByName(String supplier);

    void UpdateSupplier(Suppliers selectedItem);

    ObservableList<Suppliers> GetAllSuppliers();

    ObservableList<Suppliers> ViewSupplierBySupplierId(String supplier);

    ObservableList<Suppliers> ViewSupplierByContactPerson(String supplier);
}
