package org.erp.tarak.shipper.openingbalance;


public class ShipperOpeningBalanceBean {
	private long shipperOpeningBalanceId;
	private String financialYear;
	private long shipperId;
	private double openingBalance;
	private double currentBalance;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public long getShipperId() {
		return shipperId;
	}
	public void setShipperId(long shipperId) {
		this.shipperId = shipperId;
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
	public long getShipperOpeningBalanceId() {
		return shipperOpeningBalanceId;
	}
	public void setShipperOpeningBalanceId(long shipperOpeningBalanceId) {
		this.shipperOpeningBalanceId = shipperOpeningBalanceId;
	}

}
