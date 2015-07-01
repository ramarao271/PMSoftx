package org.erp.tarak.productionorder;

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

import org.erp.tarak.worker.Worker;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ProductionOrder")
public class ProductionOrder implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "productionOrderId", strategy = "org.erp.tarak.productionorder.ProductionOrderIdGenerator")
	@GeneratedValue(generator = "productionOrderId")
	private long productionOrderId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "POO_DATE")
	private Date productionOrderDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Worker_Id")
	private Worker workerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "POO_POOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "POOITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "POOITEMS_productionOrderId", referencedColumnName = "productionOrderId"),
			@JoinColumn(name = "POOITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "POO_productionOrderId", referencedColumnName = "productionOrderId") }

	)
	private List<ProductionOrderItem> productionOrderItems;

	private double totalCost;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(long productionOrderId) {
		this.productionOrderId = productionOrderId;
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

	public List<ProductionOrderItem> getProductionOrderItems() {
		return productionOrderItems;
	}

	public void setProductionOrderItems(List<ProductionOrderItem> productionOrderItems) {
		this.productionOrderItems = productionOrderItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getProductionOrderDate() {
		return productionOrderDate;
	}

	public void setProductionOrderDate(Date productionOrderDate) {
		this.productionOrderDate = productionOrderDate;
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

}
