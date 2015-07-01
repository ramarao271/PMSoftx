package org.erp.tarak.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("expenseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseDao expenseDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addExpense(Expense expense) {
		expenseDao.addExpense(expense);
	}
	
	public List<Expense> listExpenses() {
		return expenseDao.listExpenses();
	}

	public Expense getExpense(long expenseId) {
		return expenseDao.getExpense(expenseId);
	}
	
	public void deleteExpense(Expense expense) {
		expenseDao.deleteExpense(expense);
	}

	@Override
	public Expense getLastExpense() {
		return expenseDao.getLastExpense();
	}

	@Override
	public Expense getExpenseByName(String expenseName) {
		return expenseDao.getExpenseByName(expenseName);
	}

}
