package org.erp.tarak.deliverychallan;

import java.util.List;


public interface DeliveryChallanItemService {
	public void addDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem);

	public List<DeliveryChallanItem> listDeliveryChallanItems();

	public DeliveryChallanItem getDeliveryChallanItem(long deliveryChallanItemId);

	public void deleteDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem);

	public void deleteDeliveryChallanItems(List<DeliveryChallanItem> pois);

}
