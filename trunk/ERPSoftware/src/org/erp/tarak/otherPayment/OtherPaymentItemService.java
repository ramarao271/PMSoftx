package org.erp.tarak.otherPayment;

import java.util.List;


public interface OtherPaymentItemService {
	public void addOtherPaymentItem(OtherPaymentItem otherPaymentItem);

	public List<OtherPaymentItem> listOtherPaymentItems();

	public OtherPaymentItem getOtherPaymentItem(long otherPaymentItemId);

	public void deleteOtherPaymentItem(OtherPaymentItem otherPaymentItem);

	public void deleteOtherPaymentItems(List<OtherPaymentItem> pois);

}
