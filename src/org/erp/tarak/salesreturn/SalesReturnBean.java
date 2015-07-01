package org.erp.tarak.salesreturn;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;


public class SalesReturnBean {

	private long salesReturnId;
	private SalesInvoiceBean salesInvoiceBean;
	private String finYear;
	private Date salesReturnDate;
	private CustomerBean customerBean;
	private List<SalesReturnItemBean> salesReturnItemBeans;
	private double totalCost;
	private double paidAmount;
	private String lrNo;
	public long getSalesReturnId() {
		return salesReturnId;
	}

	public void setSalesReturnId(long salesReturnId) {
		this.salesReturnId = salesReturnId;
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

	public List<SalesReturnItemBean> getSalesReturnItemBeans() {
		return salesReturnItemBeans;
	}

	public void setSalesReturnItemBeans(
			List<SalesReturnItemBean> salesReturnItemBeans) {
		this.salesReturnItemBeans = salesReturnItemBeans;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public Date getSalesReturnDate() {
		return salesReturnDate;
	}

	public void setSalesReturnDate(Date salesReturnDate) {
		this.salesReturnDate = salesReturnDate;
	}

	public SalesInvoiceBean getSalesInvoiceBean() {
		return salesInvoiceBean;
	}

	public void setSalesInvoiceBean(SalesInvoiceBean salesInvoiceBean) {
		this.salesInvoiceBean = salesInvoiceBean;
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

}
