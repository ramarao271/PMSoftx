package org.erp.tarak.deliverychallan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("deliveryChallanItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeliveryChallanItemServiceImpl implements DeliveryChallanItemService {

	@Autowired
	private DeliveryChallanItemDao deliveryChallanItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem) {
		deliveryChallanItemDao.addDeliveryChallanItem(deliveryChallanItem);
	}
	
	public List<DeliveryChallanItem> listDeliveryChallanItems() {
		return deliveryChallanItemDao.listDeliveryChallanItems();
	}

	public DeliveryChallanItem getDeliveryChallanItem(long deliveryChallanItemId) {
		return deliveryChallanItemDao.getDeliveryChallanItem(deliveryChallanItemId);
	}
	
	public void deleteDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem) {
		deliveryChallanItemDao.deleteDeliveryChallanItem(deliveryChallanItem);
	}

	@Override
	public void deleteDeliveryChallanItems(List<DeliveryChallanItem> pois) {
		// TODO Auto-generated method stub
		deliveryChallanItemDao.deleteDeliveryChallanItems(pois);
	}

}
