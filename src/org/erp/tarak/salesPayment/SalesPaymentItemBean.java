package org.erp.tarak.salesPayment;

public class SalesPaymentItemBean {

	private int srNo;
	private long salesPaymentId;
	private String finYear;
	private long salesInvoiceId;
	private String salesInvoiceFinYear;
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

	public long getSalesPaymentId() {
		return salesPaymentId;
	}

	public void setSalesPaymentId(long salesPaymentId) {
		this.salesPaymentId = salesPaymentId;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public long getSalesInvoiceId() {
		return salesInvoiceId;
	}

	public void setSalesInvoiceId(long salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
	}

	public String getSalesInvoiceFinYear() {
		return salesInvoiceFinYear;
	}

	public void setSalesInvoiceFinYear(String salesInvoiceFinYear) {
		this.salesInvoiceFinYear = salesInvoiceFinYear;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
