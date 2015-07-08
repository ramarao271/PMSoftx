package org.erp.tarak.salesinvoice;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.deliverychallan.DeliveryChallanBean;


public class SalesInvoiceBean {

	private long salesInvoiceId;
	private DeliveryChallanBean deliveryChallanBean;
	private String finYear;
	private Date salesInvoiceDate;
	private CustomerBean customerBean;
	private List<SalesInvoiceItemBean> salesInvoiceItemBeans;
	private double invoiceAmount;
	private double discountPercent;
	private double discountedAmount;
	private double totalCost;
	private double paidAmount;
	private double adjustedAmount;
	private double returnAmount;
	private String lrNo;
	private boolean processed;
	private int noOfDays;
	public long getSalesInvoiceId() {
		return salesInvoiceId;
	}

	public void setSalesInvoiceId(long salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
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

	public List<SalesInvoiceItemBean> getSalesInvoiceItemBeans() {
		return salesInvoiceItemBeans;
	}

	public void setSalesInvoiceItemBeans(
			List<SalesInvoiceItemBean> salesInvoiceItemBeans) {
		this.salesInvoiceItemBeans = salesInvoiceItemBeans;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public Date getSalesInvoiceDate() {
		return salesInvoiceDate;
	}

	public void setSalesInvoiceDate(Date salesInvoiceDate) {
		this.salesInvoiceDate = salesInvoiceDate;
	}

	public DeliveryChallanBean getDeliveryChallanBean() {
		return deliveryChallanBean;
	}

	public void setDeliveryChallanBean(DeliveryChallanBean deliveryChallanBean) {
		this.deliveryChallanBean = deliveryChallanBean;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public double getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}

}
