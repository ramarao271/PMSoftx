package org.erp.tarak.purchasereturn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseReturnService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

	@Autowired
	private PurchaseReturnDao purchaseReturnDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseReturn(PurchaseReturn purchaseReturn) {
		purchaseReturnDao.addPurchaseReturn(purchaseReturn);
	}
	
	public List<PurchaseReturn> listPurchaseReturns(String finYear) {
		return purchaseReturnDao.listPurchaseReturns(finYear);
	}

	public PurchaseReturn getPurchaseReturn(long purchaseReturnId,String finYear) {
		return purchaseReturnDao.getPurchaseReturn(purchaseReturnId,finYear);
	}
	
	public void deletePurchaseReturn(PurchaseReturn purchaseReturn) {
		purchaseReturnDao.deletePurchaseReturn(purchaseReturn);
	}

	@Override
	public List<PurchaseReturn> listPurchaseReturnsBySupplier(long supplierId,String finYear) {
		return  purchaseReturnDao.listPurchaseReturnsBySupplier(supplierId,finYear);
	}

}
