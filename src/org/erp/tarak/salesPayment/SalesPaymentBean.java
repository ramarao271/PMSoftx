package org.erp.tarak.salesPayment;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerBean;


public class SalesPaymentBean {

	private long salesPaymentId;
	private String finYear;
	private Date salesPaymentDate;
	private CustomerBean customerBean;
	private List<SalesPaymentItemBean> salesPaymentItemBeans;
	private double totalPaid;
	private String paymentMode;
	private String paymentReference; 
	private double advance;

	public long getSalesPaymentId() {
		return salesPaymentId;
	}

	public void setSalesPaymentId(long salesPaymentId) {
		this.salesPaymentId = salesPaymentId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}


	public double getTotalCost() {
		return totalPaid;
	}

	public void setTotalCost(double totalCost) {
		this.totalPaid = totalCost;
	}

	public List<SalesPaymentItemBean> getSalesPaymentItemBeans() {
		return salesPaymentItemBeans;
	}

	public void setSalesPaymentItemBeans(
			List<SalesPaymentItemBean> salesPaymentItemBeans) {
		this.salesPaymentItemBeans = salesPaymentItemBeans;
	}

	public Date getSalesPaymentDate() {
		return salesPaymentDate;
	}

	public void setSalesPaymentDate(Date salesPaymentDate) {
		this.salesPaymentDate = salesPaymentDate;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public double getAdvance() {
		return advance;
	}

	public void setAdvance(double advance) {
		this.advance = advance;
	}

}
