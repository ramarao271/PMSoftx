package org.erp.tarak.salesorder;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SalesOrder")
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "salesOrderId", strategy = "org.erp.tarak.salesorder.SalesOrderIdGenerator")
	@GeneratedValue(generator = "salesOrderId")
	private long salesOrderId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "SO_DATE")
	private Date salesOrderDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Customer_Id")
	private Customer customerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "SO_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SOITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SOITEMS_salesOrderId", referencedColumnName = "salesOrderId"),
			@JoinColumn(name = "SOITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SO_salesOrderId", referencedColumnName = "salesOrderId") }

	)
	private List<SalesOrderItem> salesOrderItems;

	private double totalCost;
	
	@Type(type="boolean")
	private boolean processed;
	
	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
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

	public List<SalesOrderItem> getSalesOrderItems() {
		return salesOrderItems;
	}

	public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
