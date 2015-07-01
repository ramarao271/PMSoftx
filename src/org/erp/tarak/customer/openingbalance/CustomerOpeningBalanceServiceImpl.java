package org.erp.tarak.customer.openingbalance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("customerOpeningBalanceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomerOpeningBalanceServiceImpl implements CustomerOpeningBalanceService {

	@Autowired
	private CustomerOpeningBalanceDao customerOpeningBalanceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance) {
		customerOpeningBalanceDao.addCustomerOpeningBalance(customerOpeningBalance);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances) {
		customerOpeningBalanceDao.addCustomerOpeningBalances(customerOpeningBalances);
	}
	
	public List<CustomerOpeningBalance> listCustomerOpeningBalances() {
		return customerOpeningBalanceDao.listCustomerOpeningBalances();
	}

	public CustomerOpeningBalance getCustomerOpeningBalance(int customerOpeningBalanceId) {
		return customerOpeningBalanceDao.getCustomerOpeningBalance(customerOpeningBalanceId);
	}
	
	public void deleteCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance) {
		customerOpeningBalanceDao.deleteCustomerOpeningBalance(customerOpeningBalance);
	}
	public void deleteCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances) {
		customerOpeningBalanceDao.deleteCustomerOpeningBalances(customerOpeningBalances);
	}

	@Override
	public CustomerOpeningBalance getCustomerOpeningBalance(String finYear,
			long customerId) {
		return customerOpeningBalanceDao.getCustomerOpeningBalance(finYear,customerId);
	}

}
