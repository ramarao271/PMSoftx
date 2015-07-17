package org.erp.tarak.worker;

import java.util.Date;

public class WorkerTransaction {
	private long workerId;
	private long transactionId; 
	private Date date;
	private double orderCost;
	private double invoiceCost;
	private String transactionType;
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getOrderCost() {
		return orderCost;
	}
	public void setOrderCost(double orderCost) {
		this.orderCost = orderCost;
	}
	public double getInvoiceCost() {
		return invoiceCost;
	}
	public void setInvoiceCost(double invoiceCost) {
		this.invoiceCost = invoiceCost;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
}
