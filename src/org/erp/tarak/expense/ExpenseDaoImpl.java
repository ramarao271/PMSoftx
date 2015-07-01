package org.erp.tarak.expense;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("expenseDao")
public class ExpenseDaoImpl implements ExpenseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addExpense(Expense expense) {
		if (expense.getExpenseId() > 0) {
			sessionFactory.getCurrentSession().update(expense);
		} else {
			sessionFactory.getCurrentSession().save(expense);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Expense> listExpenses() {
		return (List<Expense>) sessionFactory.getCurrentSession()
				.createCriteria(Expense.class).list();
	}

	public Expense getExpense(long expenseId) {
		return (Expense) sessionFactory.getCurrentSession().get(
				Expense.class, expenseId);
	}

	public void deleteExpense(Expense expense) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Expense WHERE expenseId = "
								+ expense.getExpenseId()).executeUpdate();
	}

	@Override
	public Expense getLastExpense() {
		String hql = "from Expense where expenseId=(SELECT MAX(expenseId) from Expense)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Expense) results.get(0);
		}
		return null;
	}

	@Override
	public Expense getExpenseByName(String expenseName) {
		String hql = "from Expense where expenseName='"+expenseName+"')";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Expense) results.get(0);
		}
		return null;
		
	}

}
