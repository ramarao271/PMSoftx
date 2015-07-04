package org.erp.tarak.salesorder;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerBean;


public class SalesOrderBean {

	private long salesOrderId;
	private String finYear;
	private Date salesOrderDate;
	private CustomerBean customerBean;
	private List<SalesOrderItemBean> salesOrderItemBeans;
	private double totalCost;
	private boolean processed;
	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}


	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<SalesOrderItemBean> getSalesOrderItemBeans() {
		return salesOrderItemBeans;
	}

	public void setSalesOrderItemBeans(
			List<SalesOrderItemBean> salesOrderItemBeans) {
		this.salesOrderItemBeans = salesOrderItemBeans;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
