package org.erp.tarak.salesPayment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesPaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesPaymentServiceImpl implements SalesPaymentService {

	@Autowired
	private SalesPaymentDao salesPaymentDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesPayment(SalesPayment salesPayment) {
		salesPaymentDao.addSalesPayment(salesPayment);
	}
	
	public List<SalesPayment> listSalesPayments(String finYear) {
		return salesPaymentDao.listSalesPayments(finYear);
	}

	public SalesPayment getSalesPayment(long salesPaymentId,String finYear) {
		return salesPaymentDao.getSalesPayment(salesPaymentId,finYear);
	}
	
	public void deleteSalesPayment(SalesPayment salesPayment) {
		salesPaymentDao.deleteSalesPayment(salesPayment);
	}

	@Override
	public List<SalesPayment> listSalesInvoicesByDate(Date balanceSheetDate) {
		return salesPaymentDao.listSalesInvoicesByDate(balanceSheetDate);
	}

}
