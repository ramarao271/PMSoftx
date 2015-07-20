package org.erp.tarak.otherPayment;

import java.util.Date;
import java.util.List;


public interface OtherPaymentService {

	public void addOtherPayment(OtherPayment otherPayment);

	public List<OtherPayment> listOtherPayments(String finYear);

	public OtherPayment getOtherPayment(long otherPaymentId,String finYear);

	public void deleteOtherPayment(OtherPayment otherPayment);

	public List<OtherPayment> listProductionInvoicesByDate(Date balanceSheetDate);
}
