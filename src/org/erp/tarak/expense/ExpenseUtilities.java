package org.erp.tarak.expense;

import java.util.ArrayList;
import java.util.List;

import org.erp.tarak.library.ERPUtilities;

public class ExpenseUtilities {

	public static List<ExpenseBean> prepareListofExpenseBean(
			List<Expense> expenses) {
		List<ExpenseBean> beans = new ArrayList<ExpenseBean>();
		for (Expense expense : expenses) {
			ExpenseBean expenseBean = new ExpenseBean();
			expenseBean.setExpenseId(expense.getExpenseId());
			expenseBean.setExpenseName(expense.getExpenseName());
			expenseBean.setExpenseCode(expense.getExpenseCode());
			beans.add(expenseBean);
		}
		return beans;
	}

	public static Expense prepareExpenseModel(ExpenseBean expenseBean,
			ExpenseService expenseService) {
		Expense expense = new Expense();
		expense.setExpenseId(expenseBean.getExpenseId());
		expense.setExpenseName(expenseBean.getExpenseName());
		if (expenseBean.getExpenseId() > 0) {
			expense.setExpenseCode(expenseBean.getExpenseCode());
		} else {
			String expenseCode = getExpenseCode(expenseService);
			expense.setExpenseCode(expenseCode);
		}

		return expense;
	}

	public static Expense prepareExpenseModel(ExpenseBean expenseBean) {
		Expense expense = new Expense();
		expense.setExpenseId(expenseBean.getExpenseId());
		expense.setExpenseName(expenseBean.getExpenseName());
		expense.setExpenseCode(expenseBean.getExpenseCode());
		return expense;
	}

	private static String getExpenseCode(ExpenseService expenseService) {
		Expense expense = expenseService.getLastExpense();
		String expenseCode = "";
		if (expense != null) {
			expenseCode = ERPUtilities.incrementCode(expense.getExpenseCode());
			expenseCode = ERPUtilities.fomatStringToN(expenseCode, 3);
		} else {
			expenseCode = "0";
		}
		return expenseCode;
	}

	public static ExpenseBean prepareExpenseBean(Expense expense) {
		ExpenseBean bean = new ExpenseBean();
		bean.setExpenseId(expense.getExpenseId());
		bean.setExpenseName(expense.getExpenseName());
		bean.setExpenseCode(expense.getExpenseCode());
		return bean;
	}

}
