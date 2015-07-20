package org.erp.tarak.otherPayment;

public class OtherPaymentItemBean {

	private int srNo;
	private long otherPaymentId;
	private String finYear;
	private long productionInvoiceId;
	private String productionInvoiceFinYear;
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

	public long getOtherPaymentId() {
		return otherPaymentId;
	}

	public void setOtherPaymentId(long otherPaymentId) {
		this.otherPaymentId = otherPaymentId;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public long getProductionInvoiceId() {
		return productionInvoiceId;
	}

	public void setProductionInvoiceId(long productionInvoiceId) {
		this.productionInvoiceId = productionInvoiceId;
	}

	public String getProductionInvoiceFinYear() {
		return productionInvoiceFinYear;
	}

	public void setProductionInvoiceFinYear(String productionInvoiceFinYear) {
		this.productionInvoiceFinYear = productionInvoiceFinYear;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
