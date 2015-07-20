package org.erp.tarak.otherPayment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("otherPaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OtherPaymentServiceImpl implements OtherPaymentService {

	@Autowired
	private OtherPaymentDao otherPaymentDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addOtherPayment(OtherPayment otherPayment) {
		otherPaymentDao.addOtherPayment(otherPayment);
	}
	
	public List<OtherPayment> listOtherPayments(String finYear) {
		return otherPaymentDao.listOtherPayments(finYear);
	}

	public OtherPayment getOtherPayment(long otherPaymentId,String finYear) {
		return otherPaymentDao.getOtherPayment(otherPaymentId,finYear);
	}
	
	public void deleteOtherPayment(OtherPayment otherPayment) {
		otherPaymentDao.deleteOtherPayment(otherPayment);
	}

	@Override
	public List<OtherPayment> listProductionInvoicesByDate(Date balanceSheetDate) {
		return otherPaymentDao.listProductionInvoicesByDate(balanceSheetDate);
	}

}
