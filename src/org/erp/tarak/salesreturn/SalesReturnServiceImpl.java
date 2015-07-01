package org.erp.tarak.salesreturn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesReturnService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesReturnServiceImpl implements SalesReturnService {

	@Autowired
	private SalesReturnDao salesReturnDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesReturn(SalesReturn salesReturn) {
		salesReturnDao.addSalesReturn(salesReturn);
	}
	
	public List<SalesReturn> listSalesReturns(String finYear) {
		return salesReturnDao.listSalesReturns(finYear);
	}

	public SalesReturn getSalesReturn(long salesReturnId,String finYear) {
		return salesReturnDao.getSalesReturn(salesReturnId,finYear);
	}
	
	public void deleteSalesReturn(SalesReturn salesReturn) {
		salesReturnDao.deleteSalesReturn(salesReturn);
	}

	@Override
	public List<SalesReturn> listSalesReturnsByCustomer(long customerId,String finYear) {
		return  salesReturnDao.listSalesReturnsByCustomer(customerId,finYear);
	}

}
