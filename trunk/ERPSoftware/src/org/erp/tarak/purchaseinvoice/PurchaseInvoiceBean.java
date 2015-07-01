package org.erp.tarak.purchaseinvoice;

import java.util.Date;
import java.util.List;

import org.erp.tarak.purchaseorder.PurchaseOrderBean;
import org.erp.tarak.supplier.SupplierBean;


public class PurchaseInvoiceBean {

	private long purchaseInvoiceId;
	private PurchaseOrderBean purchaseOrderBean;
	private String finYear;
	private Date purchaseInvoiceDate;
	private SupplierBean supplierBean;
	private List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans;
	private double invoiceAmount;
	private double discountPercent;
	private double discountedAmount;
	private double totalCost;
	private double paidAmount;
	private double returnAmount;
	private String lrNo;
	private boolean processed;
	private int noOfDays;
	public long getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(long purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
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

	public List<PurchaseInvoiceItemBean> getPurchaseInvoiceItemBeans() {
		return purchaseInvoiceItemBeans;
	}

	public void setPurchaseInvoiceItemBeans(
			List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans) {
		this.purchaseInvoiceItemBeans = purchaseInvoiceItemBeans;
	}

	public SupplierBean getSupplierBean() {
		return supplierBean;
	}

	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
	}

	public Date getPurchaseInvoiceDate() {
		return purchaseInvoiceDate;
	}

	public void setPurchaseInvoiceDate(Date purchaseInvoiceDate) {
		this.purchaseInvoiceDate = purchaseInvoiceDate;
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

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
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

	public PurchaseOrderBean getPurchaseOrderBean() {
		return purchaseOrderBean;
	}

	public void setPurchaseOrderBean(PurchaseOrderBean purchaseOrderBean) {
		this.purchaseOrderBean = purchaseOrderBean;
	}

}
