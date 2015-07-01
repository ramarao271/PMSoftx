package org.erp.tarak.purchasePayment;

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

import org.erp.tarak.supplier.Supplier;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PurchasePayment")
public class PurchasePayment implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchasePaymentId", strategy = "org.erp.tarak.purchasePayment.PurchasePaymentIdGenerator")
	@GeneratedValue(generator = "purchasePaymentId")
	private long purchasePaymentId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "PP_DATE")
	private Date purchasePaymentDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Supplier_Id")
	private Supplier supplierId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PP_PPITEMS", inverseJoinColumns = {
			@JoinColumn(name = "PPITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PPITEMS_spId", referencedColumnName = "purchasePaymentId"),
			@JoinColumn(name = "PPITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PP_purchasePaymentId", referencedColumnName = "purchasePaymentId") }

	)
	private List<PurchasePaymentItem> purchasePaymentItems;

	private double totalPaid;
	
	private String paymentMode;
	
	private String paymentReference;
	
	private double advance;
	
	public long getPurchasePaymentId() {
		return purchasePaymentId;
	}

	public void setPurchasePaymentId(long purchasePaymentId) {
		this.purchasePaymentId = purchasePaymentId;
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

	public List<PurchasePaymentItem> getPurchasePaymentItems() {
		return purchasePaymentItems;
	}

	public void setPurchasePaymentItems(List<PurchasePaymentItem> purchasePaymentItems) {
		this.purchasePaymentItems = purchasePaymentItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPurchasePaymentDate() {
		return purchasePaymentDate;
	}

	public void setPurchasePaymentDate(Date purchasePaymentDate) {
		this.purchasePaymentDate = purchasePaymentDate;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId = supplierId;
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
