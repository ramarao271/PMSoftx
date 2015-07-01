package org.erp.tarak.expense;

import org.hibernate.validator.constraints.NotEmpty;

public class ExpenseBean {
	private long expenseId;
	@NotEmpty(message="Expense cannot be empty")
	private String expenseName;
	private String expenseCode;
	private double amount;
	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(long expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseCode() {
		return expenseCode;
	}

	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
