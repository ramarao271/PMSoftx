package org.erp.tarak.purchasePayment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchasePaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchasePaymentServiceImpl implements PurchasePaymentService {

	@Autowired
	private PurchasePaymentDao purchasePaymentDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchasePayment(PurchasePayment purchasePayment) {
		purchasePaymentDao.addPurchasePayment(purchasePayment);
	}
	
	public List<PurchasePayment> listPurchasePayments(String finYear) {
		return purchasePaymentDao.listPurchasePayments(finYear);
	}

	public PurchasePayment getPurchasePayment(long purchasePaymentId,String finYear) {
		return purchasePaymentDao.getPurchasePayment(purchasePaymentId,finYear);
	}
	
	public void deletePurchasePayment(PurchasePayment purchasePayment) {
		purchasePaymentDao.deletePurchasePayment(purchasePayment);
	}

}
