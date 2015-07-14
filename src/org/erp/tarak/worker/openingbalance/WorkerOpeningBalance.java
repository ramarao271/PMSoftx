package org.erp.tarak.worker.openingbalance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "WKROB")
public class WorkerOpeningBalance implements Serializable {
	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@SequenceGenerator(name="my_seq", sequenceName="WorkerOpeningBalanceSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	@Column(name = "WKROB_Id")
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
