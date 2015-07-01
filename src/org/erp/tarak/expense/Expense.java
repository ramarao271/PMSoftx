package org.erp.tarak.expense;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Expense")
public class Expense  implements Serializable{
	
	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="expenseSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long expenseId; 
	
	@Column(name="Expense_Name")
	private String expenseName;

	@GenericGenerator(name = "expenseCode", strategy = "org.erp.tarak.expense.ExpenseCodeGenerator")
	@GeneratedValue(generator = "expenseCode",strategy = GenerationType.AUTO)
	@Column(name="Expense_Code")
	private String expenseCode;
	
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
}
