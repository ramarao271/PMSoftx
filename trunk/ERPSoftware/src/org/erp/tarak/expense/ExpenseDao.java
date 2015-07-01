package org.erp.tarak.expense;

import java.util.List;


public interface ExpenseDao {
		
		public void addExpense(Expense expense);

		public List<Expense> listExpenses();
		
		public Expense getExpense(long expenseId);
		
		public void deleteExpense(Expense expense);

		public Expense getLastExpense();

		public Expense getExpenseByName(String expenseName);
	}
