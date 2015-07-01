package org.erp.tarak.customer.openingbalance;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.library.ERPConstants;

public class CustomerOpeningBalanceUtilities {

	public static List<CustomerOpeningBalance> saveCustomerOpeningBalances(
			CustomerOpeningBalanceService customerOpeningBalanceService,
			List<CustomerOpeningBalance> customerOpeningBalances) {
		customerOpeningBalanceService.addCustomerOpeningBalances(customerOpeningBalances);
		return customerOpeningBalances;
	}

	public static List<CustomerOpeningBalance> populateCustomerOpeningBalances(
			List<CustomerOpeningBalanceBean> customerOpeningBalanceBeans) {
		List<CustomerOpeningBalance> customerOpeningBalances = new LinkedList<CustomerOpeningBalance>();
		if (customerOpeningBalanceBeans != null) {
			for (CustomerOpeningBalanceBean customerOpeningBalanceBean : customerOpeningBalanceBeans) {
				if (customerOpeningBalanceBean.getCustomerOpeningBalanceId() != 0
						&& !"".equals(customerOpeningBalanceBean.getCustomerOpeningBalanceId())) {
					CustomerOpeningBalance customerOpeningBalance = new CustomerOpeningBalance();
					customerOpeningBalance.setCurrentBalance(customerOpeningBalanceBean.getCurrentBalance());
					customerOpeningBalance.setCustomerId(customerOpeningBalanceBean.getCustomerId());
					customerOpeningBalance.setCustomerOpeningBalanceId(customerOpeningBalanceBean.getCustomerOpeningBalanceId());
					customerOpeningBalance.setFinancialYear(customerOpeningBalanceBean.getFinancialYear());
					customerOpeningBalance.setOpeningBalance(customerOpeningBalanceBean.getOpeningBalance());
					customerOpeningBalances.add(customerOpeningBalance);
				}
			}
		}
		return customerOpeningBalances;
	}

	public static List<CustomerOpeningBalanceBean> prepareCustomerOpeningBalanceBean(
			List<CustomerOpeningBalance> customerOpeningBalances) {
		List<CustomerOpeningBalanceBean> customerOpeningBalanceBeans = new LinkedList<CustomerOpeningBalanceBean>();
		Iterator<CustomerOpeningBalance> customerOpeningBalanceIterator = customerOpeningBalances.iterator();
		while (customerOpeningBalanceIterator.hasNext()) {
			CustomerOpeningBalance customerOpeningBalance = customerOpeningBalanceIterator.next();
			CustomerOpeningBalanceBean customerOpeningBalanceBean = new CustomerOpeningBalanceBean();
			customerOpeningBalanceBean.setCurrentBalance(customerOpeningBalance.getCurrentBalance());
			customerOpeningBalanceBean.setCustomerId(customerOpeningBalance.getCustomerId());
			customerOpeningBalanceBean.setCustomerOpeningBalanceId(customerOpeningBalance.getCustomerOpeningBalanceId());
			customerOpeningBalanceBean.setFinancialYear(customerOpeningBalance.getFinancialYear());
			customerOpeningBalanceBean.setOpeningBalance(customerOpeningBalance.getOpeningBalance());
			customerOpeningBalanceBeans.add(customerOpeningBalanceBean);
		}
		return customerOpeningBalanceBeans;
	}

	public static void updateCob(CustomerOpeningBalanceService cobService, double savedAmount, double currentAmount, long customerId, String finYear, String type) {
		double balance=0;
		if(savedAmount!=0)
		{
			balance=currentAmount-savedAmount;
		}
		else
		{
			balance=currentAmount;
		}
		CustomerOpeningBalance cob=cobService.getCustomerOpeningBalance(finYear,customerId);
		if(!ERPConstants.SALES_INVOICE.equals(type))
		{
			balance=-balance;
		}
		cob.setCurrentBalance(cob.getCurrentBalance()+balance);
		cobService.addCustomerOpeningBalance(cob);
	}

}
