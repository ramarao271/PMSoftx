package org.erp.tarak.balanceSheet;

import java.util.List;


public interface BalanceSheetItemService {
	public void addBalanceSheetItem(BalanceSheetItem balanceSheetItem);

	public List<BalanceSheetItem> listBalanceSheetItems();

	public BalanceSheetItem getBalanceSheetItem(long balanceSheetItemId);

	public void deleteBalanceSheetItem(BalanceSheetItem balanceSheetItem);

	public void deleteBalanceSheetItems(List<BalanceSheetItem> pois);

}
