package org.erp.tarak.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("supplierService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSupplier(Supplier supplier) {
		supplierDao.addSupplier(supplier);
	}
	
	public List<Supplier> listSuppliers() {
		return supplierDao.listSuppliers();
	}

	public Supplier getSupplier(long supplierId) {
		return supplierDao.getSupplier(supplierId);
	}
	
	public void deleteSupplier(Supplier supplier) {
		supplierDao.deleteSupplier(supplier);
	}

	@Override
	public List<Supplier> listSuppliersbyCompanyName(String companyName) {
		return supplierDao.listSuppliersbyCompanyName(companyName);
	}

	@Override
	public List<Supplier> listSuppliersbyCompanyNameRegex(String search) {
		return supplierDao.listSuppliersbyCompanyNameRegex(search);
	}

}
