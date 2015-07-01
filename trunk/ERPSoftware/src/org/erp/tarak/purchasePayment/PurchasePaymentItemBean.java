package org.erp.tarak.purchasePayment;

public class PurchasePaymentItemBean {

	private int srNo;
	private long purchasePaymentId;
	private String finYear;
	private long purchaseInvoiceId;
	private String purchaseInvoiceFinYear;
	private double paid;
	private boolean processed;
	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public long getPurchasePaymentId() {
		return purchasePaymentId;
	}

	public void setPurchasePaymentId(long purchasePaymentId) {
		this.purchasePaymentId = purchasePaymentId;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public long getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(long purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
	}

	public String getPurchaseInvoiceFinYear() {
		return purchaseInvoiceFinYear;
	}

	public void setPurchaseInvoiceFinYear(String purchaseInvoiceFinYear) {
		this.purchaseInvoiceFinYear = purchaseInvoiceFinYear;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
