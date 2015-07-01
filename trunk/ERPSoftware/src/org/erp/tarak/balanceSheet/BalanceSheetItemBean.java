package org.erp.tarak.balanceSheet;

import org.erp.tarak.expense.ExpenseBean;

public class BalanceSheetItemBean {

	private int srNo;
	private long balanceSheetId;
	private String finYear;
	private ExpenseBean expenseBeanId;
	private String description;
	private double rate;
	private double totalCost;

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public long getBalanceSheetId() {
		return balanceSheetId;
	}

	public void setBalanceSheetId(long balanceSheetId) {
		this.balanceSheetId = balanceSheetId;
	}

	public ExpenseBean getExpenseBeanId() {
		return expenseBeanId;
	}

	public void setExpenseBeanId(ExpenseBean expenseBeanId) {
		this.expenseBeanId = expenseBeanId;
	}
}
