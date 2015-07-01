package org.erp.tarak.balanceSheet;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.expense.Expense;
import org.erp.tarak.expense.ExpenseBean;
import org.erp.tarak.expense.ExpenseUtilities;
import org.erp.tarak.user.UserBean;

public class BalanceSheetUtilities {

	public static BalanceSheet getBalanceSheetModel(
			BalanceSheetService balanceSheetService, long balanceSheetId) {
		BalanceSheet balanceSheet = balanceSheetService
				.getBalanceSheet(balanceSheetId);
		return balanceSheet;
	}

	public static List<BalanceSheetItemBean> prepareBalanceSheetItemBeans(
			List<BalanceSheetItem> balanceSheetItems) {
		List<BalanceSheetItemBean> balanceSheetItemBeans = new LinkedList<BalanceSheetItemBean>();
		for (BalanceSheetItem balanceSheetItem : balanceSheetItems) {
			BalanceSheetItemBean balanceSheetItemBean = new BalanceSheetItemBean();
			balanceSheetItemBean.setFinYear(balanceSheetItem.getFinYear());
			balanceSheetItemBean.setDescription(balanceSheetItem
					.getDescription());
			ExpenseBean pb = ExpenseUtilities
					.prepareExpenseBean(balanceSheetItem.getExpenseId());
			balanceSheetItemBean.setExpenseBeanId(pb);
			balanceSheetItemBean.setBalanceSheetId(balanceSheetItem
					.getBalanceSheetId());
			balanceSheetItemBean.setRate(balanceSheetItem.getRate());
			balanceSheetItemBean.setSrNo(balanceSheetItem.getSrNo());
			balanceSheetItemBeans.add(balanceSheetItemBean);
		}
		return balanceSheetItemBeans;
	}

	public static BalanceSheetBean prepareBalanceSheetBean(BalanceSheet po) {
		BalanceSheetBean pob = new BalanceSheetBean();
		pob.setFinYear(po.getFinYear());
		pob.setBalanceSheetDate(po.getBalanceSheetDate());
		pob.setBalanceSheetId(po.getBalanceSheetId());
		List<BalanceSheetItemBean> poib = prepareBalanceSheetItemBeans(po
				.getBalanceSheetItems());
		pob.setBalanceSheetItemBeans(poib);
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static BalanceSheet prepareBalanceSheetModel(
			BalanceSheetBean balanceSheetBean, UserBean user) {
		BalanceSheet balanceSheet = new BalanceSheet();
		balanceSheet
				.setBalanceSheetDate(balanceSheetBean.getBalanceSheetDate());
		balanceSheet.setBalanceSheetId(balanceSheetBean.getBalanceSheetId());
		balanceSheet.setTotalCost(balanceSheetBean.getTotalCost());
		balanceSheet.setFinYear(user.getFinYear());
		List<BalanceSheetItem> balanceSheetItems = prepareBalanceSheetItems(
				balanceSheetBean.getBalanceSheetItemBeans(),
				balanceSheetBean.getBalanceSheetId(), user);
		balanceSheet.setBalanceSheetItems(balanceSheetItems);
		balanceSheet.setProcessed(balanceSheetBean.isProcessed());
		return balanceSheet;
	}

	public static List<BalanceSheetItem> prepareBalanceSheetItems(
			List<BalanceSheetItemBean> balanceSheetItemBeans,
			long balanceSheetId, UserBean user) {
		List<BalanceSheetItem> balanceSheetItems = new LinkedList<BalanceSheetItem>();
		for (BalanceSheetItemBean balanceSheetItemBean : balanceSheetItemBeans) {
			BalanceSheetItem balanceSheetItem = new BalanceSheetItem();
			balanceSheetItem.setFinYear(user.getFinYear());
			balanceSheetItem.setDescription(balanceSheetItemBean
					.getDescription());
			Expense pb = ExpenseUtilities
					.prepareExpenseModel(balanceSheetItemBean
							.getExpenseBeanId());
			balanceSheetItem.setExpenseId(pb);
			balanceSheetItem.setBalanceSheetId(balanceSheetId);
			balanceSheetItem.setRate(balanceSheetItemBean.getRate());
			balanceSheetItem.setSrNo(balanceSheetItemBean.getSrNo());
			balanceSheetItems.add(balanceSheetItem);
		}

		return balanceSheetItems;
	}

	public static List<BalanceSheetBean> prepareListofBalanceSheetBean(
			List<BalanceSheet> balanceSheets) {
		List<BalanceSheetBean> beans = new LinkedList<BalanceSheetBean>();
		for (BalanceSheet po : balanceSheets) {
			BalanceSheetBean pob = prepareBalanceSheetBean(po);
			beans.add(pob);
		}
		return beans;
	}

	public static BalanceSheet prepareBalanceSheetModel(
			BalanceSheetBean balanceSheetBean) {
		BalanceSheet balanceSheet = new BalanceSheet();
		balanceSheet
				.setBalanceSheetDate(balanceSheetBean.getBalanceSheetDate());
		balanceSheet.setBalanceSheetId(balanceSheetBean.getBalanceSheetId());
		balanceSheet.setTotalCost(balanceSheetBean.getTotalCost());
		balanceSheet.setFinYear(balanceSheetBean.getFinYear());
		List<BalanceSheetItem> balanceSheetItems = prepareBalanceSheetItems(balanceSheetBean
				.getBalanceSheetItemBeans());
		balanceSheet.setBalanceSheetItems(balanceSheetItems);
		balanceSheet.setProcessed(balanceSheetBean.isProcessed());
		return balanceSheet;

	}

	private static List<BalanceSheetItem> prepareBalanceSheetItems(
			List<BalanceSheetItemBean> balanceSheetItemBeans) {
		List<BalanceSheetItem> balanceSheetItems = new LinkedList<BalanceSheetItem>();
		for (BalanceSheetItemBean balanceSheetItemBean : balanceSheetItemBeans) {
			BalanceSheetItem balanceSheetItem = new BalanceSheetItem();
			balanceSheetItem.setFinYear(balanceSheetItemBean.getFinYear());
			balanceSheetItem.setDescription(balanceSheetItemBean
					.getDescription());
			balanceSheetItem.setExpenseId(ExpenseUtilities
					.prepareExpenseModel(balanceSheetItemBean
							.getExpenseBeanId()));
			balanceSheetItem.setBalanceSheetId(balanceSheetItemBean
					.getBalanceSheetId());
			balanceSheetItem.setRate(balanceSheetItemBean.getRate());
			balanceSheetItem.setSrNo(balanceSheetItemBean.getSrNo());
			balanceSheetItems.add(balanceSheetItem);
		}
		return balanceSheetItems;
	}
}
