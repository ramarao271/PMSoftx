package org.erp.tarak.salesPayment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesPaymentItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesPaymentItemServiceImpl implements SalesPaymentItemService {

	@Autowired
	private SalesPaymentItemDao salesPaymentItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesPaymentItem(SalesPaymentItem salesPaymentItem) {
		salesPaymentItemDao.addSalesPaymentItem(salesPaymentItem);
	}
	
	public List<SalesPaymentItem> listSalesPaymentItems() {
		return salesPaymentItemDao.listSalesPaymentItems();
	}

	public SalesPaymentItem getSalesPaymentItem(long salesPaymentItemId) {
		return salesPaymentItemDao.getSalesPaymentItem(salesPaymentItemId);
	}
	
	public void deleteSalesPaymentItem(SalesPaymentItem salesPaymentItem) {
		salesPaymentItemDao.deleteSalesPaymentItem(salesPaymentItem);
	}

	@Override
	public void deleteSalesPaymentItems(List<SalesPaymentItem> pois) {
		// TODO Auto-generated method stub
		salesPaymentItemDao.deleteSalesPaymentItems(pois);
	}

}
