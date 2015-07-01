package org.erp.tarak.purchasereturn;

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

import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PurchaseReturn")
public class PurchaseReturn implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseReturnId", strategy = "org.erp.tarak.purchasereturn.PurchaseReturnIdGenerator")
	@GeneratedValue(generator = "purchaseReturnId")
	private long purchaseReturnId;
	
	private String finYear;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name="PR_DATE")
	private Date purchaseReturnDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "supplier_Id")
	private Supplier supplierId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PO_Id")
	private PurchaseInvoice purchaseInvoice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PR_POITEMS", inverseJoinColumns = {
			@JoinColumn(name = "PRITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PRITEMS_srId", referencedColumnName = "purchaseReturnId"),
			@JoinColumn(name = "PRITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PR_purchaseReturnId", referencedColumnName = "purchaseReturnId") }

	)
	private List<PurchaseReturnItem> purchaseReturnItems;
	
	private String lrNo;

	private double totalCost;
	
	private double paidAmount;

	public long getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId(long purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
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

	public List<PurchaseReturnItem> getPurchaseReturnItems() {
		return purchaseReturnItems;
	}

	public void setPurchaseReturnItems(List<PurchaseReturnItem> purchaseReturnItems) {
		this.purchaseReturnItems = purchaseReturnItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPurchaseReturnDate() {
		return purchaseReturnDate;
	}

	public void setPurchaseReturnDate(Date purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	public PurchaseInvoice getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
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
