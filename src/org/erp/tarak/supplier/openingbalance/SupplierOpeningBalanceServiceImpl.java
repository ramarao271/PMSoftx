package org.erp.tarak.supplier.openingbalance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("supplierOpeningBalanceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SupplierOpeningBalanceServiceImpl implements SupplierOpeningBalanceService {

	@Autowired
	private SupplierOpeningBalanceDao supplierOpeningBalanceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance) {
		supplierOpeningBalanceDao.addSupplierOpeningBalance(supplierOpeningBalance);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances) {
		supplierOpeningBalanceDao.addSupplierOpeningBalances(supplierOpeningBalances);
	}
	
	public List<SupplierOpeningBalance> listSupplierOpeningBalances() {
		return supplierOpeningBalanceDao.listSupplierOpeningBalances();
	}

	public SupplierOpeningBalance getSupplierOpeningBalance(int supplierOpeningBalanceId) {
		return supplierOpeningBalanceDao.getSupplierOpeningBalance(supplierOpeningBalanceId);
	}
	
	public void deleteSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance) {
		supplierOpeningBalanceDao.deleteSupplierOpeningBalance(supplierOpeningBalance);
	}
	public void deleteSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances) {
		supplierOpeningBalanceDao.deleteSupplierOpeningBalances(supplierOpeningBalances);
	}

	@Override
	public SupplierOpeningBalance getSupplierOpeningBalance(String finYear,
			long supplierId) {
		return supplierOpeningBalanceDao.getSupplierOpeningBalance(finYear,supplierId);
	}

}
