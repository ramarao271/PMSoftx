package org.erp.tarak.balanceSheet;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.erp.tarak.expense.Expense;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "BalanceSheetItem")
public class BalanceSheetItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	private int srNo;
	@Id
	@GenericGenerator(name = "balanceSheetId", strategy = "org.erp.tarak.balanceSheet.BalanceSheetIdGenerator")
	@GeneratedValue(generator = "balanceSheetId")
	private long balanceSheetId;
	@Id
	@Column(name="Financial_Year")
	private String finYear;
	@OneToOne
	@JoinColumn(name="Expense_Id")
	private Expense expenseId;
	private String description;
	private double rate;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public long getBalanceSheetId() {
		return balanceSheetId;
	}
	public void setBalanceSheetId(long balanceSheetId) {
		this.balanceSheetId = balanceSheetId;
	}
	public Expense getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(Expense expenseId) {
		this.expenseId = expenseId;
	}
}
