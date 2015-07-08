package org.erp.tarak.salesinvoice;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.erp.tarak.customer.Customer;
import org.erp.tarak.deliverychallan.DeliveryChallan;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SalesInvoice")
public class SalesInvoice implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "salesInvoiceId", strategy = "org.erp.tarak.salesinvoice.SalesInvoiceIdGenerator")
	@GeneratedValue(generator = "salesInvoiceId")
	private long salesInvoiceId;
	
	private String finYear;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name="SI_DATE")
	private Date salesInvoiceDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_Id")
	private Customer customerId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SO_Id")
	private DeliveryChallan deliveryChallan;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "SI_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SIITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SIITEMS_salesInvoiceId", referencedColumnName = "salesInvoiceId"),
			@JoinColumn(name = "SIITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SI_salesInvoiceId", referencedColumnName = "salesInvoiceId") }

	)
	private List<SalesInvoiceItem> salesInvoiceItems;
	
	private String lrNo;

	private double invoiceAmount;
	
	private double discountPercent;
	
	private double discountedAmount;
	
	private double totalCost;
	
	private double paidAmount;
	
	private double adjustedAmount;
	
	private double returnAmount;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getSalesInvoiceId() {
		return salesInvoiceId;
	}

	public void setSalesInvoiceId(long salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
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

	public List<SalesInvoiceItem> getSalesInvoiceItems() {
		return salesInvoiceItems;
	}

	public void setSalesInvoiceItems(List<SalesInvoiceItem> salesInvoiceItems) {
		this.salesInvoiceItems = salesInvoiceItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSalesInvoiceDate() {
		return salesInvoiceDate;
	}

	public void setSalesInvoiceDate(Date salesInvoiceDate) {
		this.salesInvoiceDate = salesInvoiceDate;
	}

	public DeliveryChallan getDeliveryChallan() {
		return deliveryChallan;
	}

	public void setDeliveryChallan(DeliveryChallan deliveryChallan) {
		this.deliveryChallan = deliveryChallan;
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

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public double getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}

}
