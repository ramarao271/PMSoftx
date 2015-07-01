package org.erp.tarak.supplier;

import java.util.List;


public interface SupplierService {

	public void addSupplier(Supplier supplier);

	public List<Supplier> listSuppliers();

	public Supplier getSupplier(long supplierId);

	public void deleteSupplier(Supplier supplier);
	
	public List<Supplier> listSuppliersbyCompanyName(String companyName);

	public List<Supplier> listSuppliersbyCompanyNameRegex(String search);
}
