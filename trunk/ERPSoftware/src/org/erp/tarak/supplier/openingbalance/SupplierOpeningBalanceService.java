package org.erp.tarak.supplier.openingbalance;

import java.util.List;


public interface SupplierOpeningBalanceService {
	public void addSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance);

	public void addSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances);
	
	public List<SupplierOpeningBalance> listSupplierOpeningBalances();
	
	public SupplierOpeningBalance getSupplierOpeningBalance(int supplierOpeningBalanceId);
	
	public void deleteSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance);
	
	public void deleteSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances);

	public SupplierOpeningBalance getSupplierOpeningBalance(String finYear,
			long supplierId);

}
