package org.erp.tarak.salesPayment;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.erp.tarak.customer.Customer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SalesPayment")
public class SalesPayment implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "salesPaymentId", strategy = "org.erp.tarak.salesPayment.SalesPaymentIdGenerator")
	@GeneratedValue(generator = "salesPaymentId")
	private long salesPaymentId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "SP_DATE")
	private Date salesPaymentDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Customer_Id")
	private Customer customerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "SP_SPITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SPITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SPITEMS_spId", referencedColumnName = "salesPaymentId"),
			@JoinColumn(name = "SPITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SP_salesPaymentId", referencedColumnName = "salesPaymentId") }

	)
	private List<SalesPaymentItem> salesPaymentItems;

	private double totalPaid;
	
	private String paymentMode;
	
	private String paymentReference;
	
	private double advance;
	
	public long getSalesPaymentId() {
		return salesPaymentId;
	}

	public void setSalesPaymentId(long salesPaymentId) {
		this.salesPaymentId = salesPaymentId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public double getTotalCost() {
		return totalPaid;
	}

	public void setTotalCost(double totalCost) {
		this.totalPaid = totalCost;
	}

	public List<SalesPaymentItem> getSalesPaymentItems() {
		return salesPaymentItems;
	}

	public void setSalesPaymentItems(List<SalesPaymentItem> salesPaymentItems) {
		this.salesPaymentItems = salesPaymentItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSalesPaymentDate() {
		return salesPaymentDate;
	}

	public void setSalesPaymentDate(Date salesPaymentDate) {
		this.salesPaymentDate = salesPaymentDate;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public double getAdvance() {
		return advance;
	}

	public void setAdvance(double advance) {
		this.advance = advance;
	}

}
