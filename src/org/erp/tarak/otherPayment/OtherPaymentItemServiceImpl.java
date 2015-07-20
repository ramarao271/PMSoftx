package org.erp.tarak.otherPayment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("otherPaymentItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OtherPaymentItemServiceImpl implements OtherPaymentItemService {

	@Autowired
	private OtherPaymentItemDao otherPaymentItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addOtherPaymentItem(OtherPaymentItem otherPaymentItem) {
		otherPaymentItemDao.addOtherPaymentItem(otherPaymentItem);
	}
	
	public List<OtherPaymentItem> listOtherPaymentItems() {
		return otherPaymentItemDao.listOtherPaymentItems();
	}

	public OtherPaymentItem getOtherPaymentItem(long otherPaymentItemId) {
		return otherPaymentItemDao.getOtherPaymentItem(otherPaymentItemId);
	}
	
	public void deleteOtherPaymentItem(OtherPaymentItem otherPaymentItem) {
		otherPaymentItemDao.deleteOtherPaymentItem(otherPaymentItem);
	}

	@Override
	public void deleteOtherPaymentItems(List<OtherPaymentItem> pois) {
		// TODO Auto-generated method stub
		otherPaymentItemDao.deleteOtherPaymentItems(pois);
	}

}
