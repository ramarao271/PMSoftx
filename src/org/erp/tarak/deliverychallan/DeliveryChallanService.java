package org.erp.tarak.deliverychallan;

import java.util.List;


public interface DeliveryChallanService {

	public void addDeliveryChallan(DeliveryChallan deliveryChallan);

	public List<DeliveryChallan> listDeliveryChallans(String finYear);

	public DeliveryChallan getDeliveryChallan(long deliveryChallanId,String finYear);

	public void deleteDeliveryChallan(DeliveryChallan deliveryChallan);

	public List<DeliveryChallan> listPendingDeliveryChallans(String finYear);

	public List<DeliveryChallan> listProcessedDeliveryChallans(String finYear);
}
