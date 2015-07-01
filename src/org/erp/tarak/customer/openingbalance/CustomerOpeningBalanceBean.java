package org.erp.tarak.customer.openingbalance;


public class CustomerOpeningBalanceBean {
	private long customerOpeningBalanceId;
	private String financialYear;
	private long customerId;
	private double openingBalance;
	private double currentBalance;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public long getCustomerOpeningBalanceId() {
		return customerOpeningBalanceId;
	}
	public void setCustomerOpeningBalanceId(long customerOpeningBalanceId) {
		this.customerOpeningBalanceId = customerOpeningBalanceId;
	}

}
