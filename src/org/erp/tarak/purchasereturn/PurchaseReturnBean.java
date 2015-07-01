package org.erp.tarak.purchasereturn;

import java.util.Date;
import java.util.List;

import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;


public class PurchaseReturnBean {

	private long purchaseReturnId;
	private PurchaseInvoiceBean purchaseInvoiceBean;
	private String finYear;
	private Date purchaseReturnDate;
	private SupplierBean supplierBean;
	private List<PurchaseReturnItemBean> purchaseReturnItemBeans;
	private double totalCost;
	private double paidAmount;
	private String lrNo;
	public long getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId(long purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
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

	public List<PurchaseReturnItemBean> getPurchaseReturnItemBeans() {
		return purchaseReturnItemBeans;
	}

	public void setPurchaseReturnItemBeans(
			List<PurchaseReturnItemBean> purchaseReturnItemBeans) {
		this.purchaseReturnItemBeans = purchaseReturnItemBeans;
	}

	public SupplierBean getSupplierBean() {
		return supplierBean;
	}

	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
	}

	public Date getPurchaseReturnDate() {
		return purchaseReturnDate;
	}

	public void setPurchaseReturnDate(Date purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	public PurchaseInvoiceBean getPurchaseInvoiceBean() {
		return purchaseInvoiceBean;
	}

	public void setPurchaseInvoiceBean(PurchaseInvoiceBean purchaseInvoiceBean) {
		this.purchaseInvoiceBean = purchaseInvoiceBean;
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
