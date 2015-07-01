package org.erp.tarak.balanceSheet;

import java.util.Date;
import java.util.List;


public class BalanceSheetBean {

	private long balanceSheetId;
	private String finYear;
	private Date balanceSheetDate;
	private List<BalanceSheetItemBean> balanceSheetItemBeans;
	private double totalCost;
	private boolean processed;
	public long getBalanceSheetId() {
		return balanceSheetId;
	}

	public void setBalanceSheetId(long balanceSheetId) {
		this.balanceSheetId = balanceSheetId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}


	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<BalanceSheetItemBean> getBalanceSheetItemBeans() {
		return balanceSheetItemBeans;
	}

	public void setBalanceSheetItemBeans(
			List<BalanceSheetItemBean> balanceSheetItemBeans) {
		this.balanceSheetItemBeans = balanceSheetItemBeans;
	}

	public Date getBalanceSheetDate() {
		return balanceSheetDate;
	}

	public void setBalanceSheetDate(Date balanceSheetDate) {
		this.balanceSheetDate = balanceSheetDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
