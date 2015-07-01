package org.erp.tarak.purchaseorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseOrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
		purchaseOrderDao.addPurchaseOrder(purchaseOrder);
	}
	
	public List<PurchaseOrder> listPurchaseOrders(String finYear) {
		return purchaseOrderDao.listPurchaseOrders(finYear);
	}

	public PurchaseOrder getPurchaseOrder(long purchaseOrderId,String finYear) {
		return purchaseOrderDao.getPurchaseOrder(purchaseOrderId,finYear);
	}
	
	public void deletePurchaseOrder(PurchaseOrder purchaseOrder) {
		purchaseOrderDao.deletePurchaseOrder(purchaseOrder);
	}

	@Override
	public List<PurchaseOrder> listPendingPurchaseOrders(String finYear) {
		return purchaseOrderDao.listPendingPurchaseOrders(finYear);
		}

	@Override
	public List<PurchaseOrder> listProcessedPurchaseOrders(String finYear) {
		return purchaseOrderDao.listProcessedPurchaseOrders(finYear);
	}

}
