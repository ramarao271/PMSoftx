package org.erp.tarak.productioninvoice;

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

import org.erp.tarak.productionorder.ProductionOrder;
import org.erp.tarak.worker.Worker;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ProductionInvoice")
public class ProductionInvoice implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "productionInvoiceId", strategy = "org.erp.tarak.productioninvoice.ProductionInvoiceIdGenerator")
	@GeneratedValue(generator = "productionInvoiceId")
	private long productionInvoiceId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "POI_DATE")
	private Date productionInvoiceDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Worker_Id")
	private Worker workerId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PO_Id")
	private ProductionOrder productionOrder;
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "POI_POIITEMS", inverseJoinColumns = {
			@JoinColumn(name = "POIITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "POIITEMS_productionInvoiceId", referencedColumnName = "productionInvoiceId"),
			@JoinColumn(name = "POIITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "POI_productionInvoiceId", referencedColumnName = "productionInvoiceId") }

	)
	private List<ProductionInvoiceItem> productionInvoiceItems;

	private double totalCost;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getProductionInvoiceId() {
		return productionInvoiceId;
	}

	public void setProductionInvoiceId(long productionInvoiceId) {
		this.productionInvoiceId = productionInvoiceId;
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

	public List<ProductionInvoiceItem> getProductionInvoiceItems() {
		return productionInvoiceItems;
	}

	public void setProductionInvoiceItems(List<ProductionInvoiceItem> productionInvoiceItems) {
		this.productionInvoiceItems = productionInvoiceItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getProductionInvoiceDate() {
		return productionInvoiceDate;
	}

	public void setProductionInvoiceDate(Date productionInvoiceDate) {
		this.productionInvoiceDate = productionInvoiceDate;
	}

	public Worker getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Worker workerId) {
		this.workerId = workerId;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

}
