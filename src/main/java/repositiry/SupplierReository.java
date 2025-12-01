package repositiry;


import model.dto.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SupplierReository {


   void AddSupplier(Suppliers suppliers) throws SQLException;

   void DeleteSupplier(Suppliers id) throws Exception;

  ResultSet ViewSupplier(String supplier) throws Exception;

   int UpdateSupplier(Suppliers selectedItem) throws SQLException;

   ResultSet GetAllsuppliers() throws Exception;

   ResultSet ViewSupplierById(String supplier) throws SQLException;

    ResultSet ViewSupplierByContactPerson(String supplier) throws SQLException;
}
