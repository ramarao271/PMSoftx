package org.erp.tarak.supplier.openingbalance;


public class SupplierOpeningBalanceBean {
	private long supplierOpeningBalanceId;
	private String financialYear;
	private long supplierId;
	private double openingBalance;
	private double currentBalance;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
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
	public long getSupplierOpeningBalanceId() {
		return supplierOpeningBalanceId;
	}
	public void setSupplierOpeningBalanceId(long supplierOpeningBalanceId) {
		this.supplierOpeningBalanceId = supplierOpeningBalanceId;
	}

}
