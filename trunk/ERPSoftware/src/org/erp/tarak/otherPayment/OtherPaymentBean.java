package org.erp.tarak.otherPayment;

import java.util.Date;
import java.util.List;

import org.erp.tarak.worker.WorkerBean;


public class OtherPaymentBean {

	private long otherPaymentId;
	private String finYear;
	private Date otherPaymentDate;
	private WorkerBean workerBean;
	private List<OtherPaymentItemBean> otherPaymentItemBeans;
	private double totalPaid;
	private String paymentMode;
	private String paymentReference; 
	private double advance;
	private String payTo;

	public long getOtherPaymentId() {
		return otherPaymentId;
	}

	public void setOtherPaymentId(long otherPaymentId) {
		this.otherPaymentId = otherPaymentId;
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

	public List<OtherPaymentItemBean> getOtherPaymentItemBeans() {
		return otherPaymentItemBeans;
	}

	public void setOtherPaymentItemBeans(
			List<OtherPaymentItemBean> otherPaymentItemBeans) {
		this.otherPaymentItemBeans = otherPaymentItemBeans;
	}

	public Date getOtherPaymentDate() {
		return otherPaymentDate;
	}

	public void setOtherPaymentDate(Date otherPaymentDate) {
		this.otherPaymentDate = otherPaymentDate;
	}

	public WorkerBean getWorkerBean() {
		return workerBean;
	}

	public void setWorkerBean(WorkerBean workerBean) {
		this.workerBean = workerBean;
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

	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

}
