package org.erp.tarak.company;

import org.hibernate.validator.constraints.NotEmpty;

public class CompanyBean {
	private long companyId;
	@NotEmpty(message="Company cannot be empty")
	private String companyName;
	private String companyCode;
private double balance;
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
