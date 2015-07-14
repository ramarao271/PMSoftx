package org.erp.tarak.customer;

import java.util.List;

import org.erp.tarak.category.CategoryBean;

public class CustomerReport {
	private String customerName;
	private String companyName;
	private String companyBranch;
	private long customerId;
	private double totalPrice;
	private double noOfBills;
	private double avgTktPrice;
	private List<CategoryBean> categoryBeans;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<CategoryBean> getCategoryBeans() {
		return categoryBeans;
	}
	public void setCategoryBeans(List<CategoryBean> categoryBeans) {
		this.categoryBeans = categoryBeans;
	}

}
