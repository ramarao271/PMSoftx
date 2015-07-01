package org.erp.tarak.balanceSheet;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "BalanceSheet")
public class BalanceSheet implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "balanceSheetId", strategy = "org.erp.tarak.balanceSheet.BalanceSheetIdGenerator")
	@GeneratedValue(generator = "balanceSheetId")
	private long balanceSheetId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "BS_DATE")
	private Date balanceSheetDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "BS_BSITEMS", inverseJoinColumns = {
			@JoinColumn(name = "BSITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "BSITEMS_balanceSheetId", referencedColumnName = "balanceSheetId"),
			@JoinColumn(name = "BSITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "BS_balanceSheetId", referencedColumnName = "balanceSheetId") }

	)
	private List<BalanceSheetItem> balanceSheetItems;

	private double totalCost;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getBalanceSheetId() {
		return balanceSheetId;
	}

	public void setBalanceSheetId(long balanceSheetId) {
		this.balanceSheetId = balanceSheetId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<BalanceSheetItem> getBalanceSheetItems() {
		return balanceSheetItems;
	}

	public void setBalanceSheetItems(List<BalanceSheetItem> balanceSheetItems) {
		this.balanceSheetItems = balanceSheetItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getBalanceSheetDate() {
		return balanceSheetDate;
	}

	public void setBalanceSheetDate(Date balanceSheetDate) {
		this.balanceSheetDate = balanceSheetDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
