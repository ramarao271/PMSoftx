package org.erp.tarak.purchasePayment;

import java.util.Date;
import java.util.List;

import org.erp.tarak.supplier.SupplierBean;


public class PurchasePaymentBean {

	private long purchasePaymentId;
	private String finYear;
	private Date purchasePaymentDate;
	private SupplierBean supplierBean;
	private List<PurchasePaymentItemBean> purchasePaymentItemBeans;
	private double totalPaid;
	private String paymentMode;
	private String paymentReference; 
	private double advance;

	public long getPurchasePaymentId() {
		return purchasePaymentId;
	}

	public void setPurchasePaymentId(long purchasePaymentId) {
		this.purchasePaymentId = purchasePaymentId;
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

	public List<PurchasePaymentItemBean> getPurchasePaymentItemBeans() {
		return purchasePaymentItemBeans;
	}

	public void setPurchasePaymentItemBeans(
			List<PurchasePaymentItemBean> purchasePaymentItemBeans) {
		this.purchasePaymentItemBeans = purchasePaymentItemBeans;
	}

	public Date getPurchasePaymentDate() {
		return purchasePaymentDate;
	}

	public void setPurchasePaymentDate(Date purchasePaymentDate) {
		this.purchasePaymentDate = purchasePaymentDate;
	}

	public SupplierBean getSupplierBean() {
		return supplierBean;
	}

	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
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
