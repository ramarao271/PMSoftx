package org.erp.tarak.customer;

public class CustomerReport {
	private String customerName;
	private String companyBranch;
	private long customerId;
	private double totalPrice;
	private double noOfBills;
	private double avgTktPrice;
	
	public double getAvgTktPrice() {
		return avgTktPrice;
	}
	public void setAvgTktPrice(double avgTktPrice) {
		this.avgTktPrice = avgTktPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getNoOfBills() {
		return noOfBills;
	}
	public void setNoOfBills(double noOfBills) {
		this.noOfBills = noOfBills;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCompanyBranch() {
		return companyBranch;
	}
	public void setCompanyBranch(String companyBranch) {
		this.companyBranch = companyBranch;
	}
	

}
