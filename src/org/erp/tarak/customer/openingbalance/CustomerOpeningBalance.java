package org.erp.tarak.customer.openingbalance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COB")
public class CustomerOpeningBalance implements Serializable {
	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@SequenceGenerator(name="my_seq", sequenceName="CustomerOpeningBalanceSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	@Column(name = "COB_Id")
	private long customerOpeningBalanceId;
	private String financialYear;
	private long customerId;
	private double openingBalance;
	private double currentBalance;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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
	public long getCustomerOpeningBalanceId() {
		return customerOpeningBalanceId;
	}
	public void setCustomerOpeningBalanceId(long customerOpeningBalanceId) {
		this.customerOpeningBalanceId = customerOpeningBalanceId;
	}
	
}
