package org.erp.tarak.worker.openingbalance;


public class WorkerOpeningBalanceBean {
	private long workerOpeningBalanceId;
	private String financialYear;
	private long workerId;
	private double openingBalance;
	private double currentBalance;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public long getWorkerOpeningBalanceId() {
		return workerOpeningBalanceId;
	}
	public void setWorkerOpeningBalanceId(long workerOpeningBalanceId) {
		this.workerOpeningBalanceId = workerOpeningBalanceId;
	}

}
