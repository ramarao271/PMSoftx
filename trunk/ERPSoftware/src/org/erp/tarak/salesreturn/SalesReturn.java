package org.erp.tarak.salesreturn;

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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.erp.tarak.customer.Customer;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SalesReturn")
public class SalesReturn implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "salesReturnId", strategy = "org.erp.tarak.salesreturn.SalesReturnIdGenerator")
	@GeneratedValue(generator = "salesReturnId")
	private long salesReturnId;
	@Id
	private String finYear;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name="SR_DATE")
	private Date salesReturnDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_Id")
	private Customer customerId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({@JoinColumn(name = "SI_ID", referencedColumnName = "salesInvoiceId"),@JoinColumn(name = "SI_FINYEAR", referencedColumnName = "finYear")})
	private SalesInvoice salesInvoice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "SR_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SRITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SRITEMS_srId", referencedColumnName = "salesReturnId"),
			@JoinColumn(name = "SRITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SR_salesReturnId", referencedColumnName = "salesReturnId"),@JoinColumn(name = "SR_SRFinYear", referencedColumnName = "finYear") }

	)
	private List<SalesReturnItem> salesReturnItems;
	
	private String lrNo;

	private double totalCost;
	
	private double paidAmount;

	public long getSalesReturnId() {
		return salesReturnId;
	}

	public void setSalesReturnId(long salesReturnId) {
		this.salesReturnId = salesReturnId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<SalesReturnItem> getSalesReturnItems() {
		return salesReturnItems;
	}

	public void setSalesReturnItems(List<SalesReturnItem> salesReturnItems) {
		this.salesReturnItems = salesReturnItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSalesReturnDate() {
		return salesReturnDate;
	}

	public void setSalesReturnDate(Date salesReturnDate) {
		this.salesReturnDate = salesReturnDate;
	}

	public SalesInvoice getSalesInvoice() {
		return salesInvoice;
	}

	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

}
