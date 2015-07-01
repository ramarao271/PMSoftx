package org.erp.tarak.balanceSheet;

import java.util.List;


public interface BalanceSheetService {

	public void addBalanceSheet(BalanceSheet balanceSheet);

	public List<BalanceSheet> listBalanceSheets();

	public BalanceSheet getBalanceSheet(long balanceSheetId);

	public void deleteBalanceSheet(BalanceSheet balanceSheet);

	public List<BalanceSheet> listPendingBalanceSheets();

	public List<BalanceSheet> listProcessedBalanceSheets();
}
