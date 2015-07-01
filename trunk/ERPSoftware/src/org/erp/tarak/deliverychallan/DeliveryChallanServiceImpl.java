package org.erp.tarak.deliverychallan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("deliveryChallanService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeliveryChallanServiceImpl implements DeliveryChallanService {

	@Autowired
	private DeliveryChallanDao deliveryChallanDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDeliveryChallan(DeliveryChallan deliveryChallan) {
		deliveryChallanDao.addDeliveryChallan(deliveryChallan);
	}
	
	public List<DeliveryChallan> listDeliveryChallans(String finYear) {
		return deliveryChallanDao.listDeliveryChallans(finYear);
	}

	public DeliveryChallan getDeliveryChallan(long deliveryChallanId,String finYear) {
		return deliveryChallanDao.getDeliveryChallan(deliveryChallanId,finYear);
	}
	
	public void deleteDeliveryChallan(DeliveryChallan deliveryChallan) {
		deliveryChallanDao.deleteDeliveryChallan(deliveryChallan);
	}

	@Override
	public List<DeliveryChallan> listPendingDeliveryChallans(String finYear) {
		return deliveryChallanDao.listPendingDeliveryChallans(finYear);
	}

	@Override
	public List<DeliveryChallan> listProcessedDeliveryChallans(String finYear) {
		return deliveryChallanDao.listProcessedDeliveryChallans(finYear);
	}

}
