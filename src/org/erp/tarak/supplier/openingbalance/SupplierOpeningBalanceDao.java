package org.erp.tarak.supplier.openingbalance;

import java.util.List;


public interface SupplierOpeningBalanceDao {
	
	public void addSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance);
	
	public void addSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances);

	public List<SupplierOpeningBalance> listSupplierOpeningBalances();
	
	public SupplierOpeningBalance getSupplierOpeningBalance(long supplierOpeningBalanceId);
	
	public void deleteSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance);
	
	public void deleteSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances);

	public SupplierOpeningBalance getSupplierOpeningBalance(String finYear,
			long supplierId);
}
