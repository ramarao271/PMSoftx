package org.erp.tarak.customer.openingbalance;

import java.util.List;


public interface CustomerOpeningBalanceService {
	public void addCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance);

	public void addCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances);
	
	public List<CustomerOpeningBalance> listCustomerOpeningBalances();
	
	public CustomerOpeningBalance getCustomerOpeningBalance(int customerOpeningBalanceId);
	
	public void deleteCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance);
	
	public void deleteCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances);

	public CustomerOpeningBalance getCustomerOpeningBalance(String finYear,
			long customerId);

}
