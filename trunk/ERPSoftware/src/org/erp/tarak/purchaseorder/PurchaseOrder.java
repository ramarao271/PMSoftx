package org.erp.tarak.purchaseorder;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PurchaseOrder")
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseOrderId", strategy = "org.erp.tarak.purchaseorder.PurchaseOrderIdGenerator")
	@GeneratedValue(generator = "purchaseOrderId")
	private long purchaseOrderId;
	@Id
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "PO_DATE")
	private Date purchaseOrderDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Supplier_Id")
	private Supplier supplierId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PO_POITEMS", inverseJoinColumns = {
			@JoinColumn(name = "POITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "POITEMS_purchaseOrderId", referencedColumnName = "purchaseOrderId"),
			@JoinColumn(name = "POITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PO_purchaseOrderId", referencedColumnName = "purchaseOrderId"),@JoinColumn(name = "PO_POFinYear", referencedColumnName = "finYear") }

	)
	private List<PurchaseOrderItem> purchaseOrderItems;

	private double totalCost;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
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

	public List<PurchaseOrderItem> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	public void setPurchaseOrderItems(List<PurchaseOrderItem> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId = supplierId;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
