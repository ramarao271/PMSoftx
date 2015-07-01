package org.erp.tarak.purchaseinvoice;

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

import org.erp.tarak.purchaseorder.PurchaseOrder;
import org.erp.tarak.supplier.Supplier;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PurchaseInvoice")
public class PurchaseInvoice implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseInvoiceId", strategy = "org.erp.tarak.purchaseinvoice.PurchaseInvoiceIdGenerator")
	@GeneratedValue(generator = "purchaseInvoiceId")
	private long purchaseInvoiceId;
	
	private String finYear;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name="PI_DATE")
	private Date purchaseInvoiceDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "supplier_Id")
	private Supplier supplierId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PO_Id")
	private PurchaseOrder purchaseOrder;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PI_POITEMS", inverseJoinColumns = {
			@JoinColumn(name = "PIITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PIITEMS_purchaseInvoiceId", referencedColumnName = "purchaseInvoiceId"),
			@JoinColumn(name = "PIITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PI_purchaseInvoiceId", referencedColumnName = "purchaseInvoiceId") }

	)
	private List<PurchaseInvoiceItem> purchaseInvoiceItems;
	
	private String lrNo;

	private double invoiceAmount;
	
	private double discountPercent;
	
	private double discountedAmount;
	
	private double totalCost;
	
	private double paidAmount;
	
	private double returnAmount;
	@Type(type="boolean")
	private boolean processed;
	
	public long getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(long purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId = supplierId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<PurchaseInvoiceItem> getPurchaseInvoiceItems() {
		return purchaseInvoiceItems;
	}

	public void setPurchaseInvoiceItems(List<PurchaseInvoiceItem> purchaseInvoiceItems) {
		this.purchaseInvoiceItems = purchaseInvoiceItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPurchaseInvoiceDate() {
		return purchaseInvoiceDate;
	}

	public void setPurchaseInvoiceDate(Date purchaseInvoiceDate) {
		this.purchaseInvoiceDate = purchaseInvoiceDate;
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

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
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

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

}
