package org.erp.tarak.deliverychallan;

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
import org.erp.tarak.salesorder.SalesOrder;
import org.erp.tarak.shipper.Shipper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "DeliveryChallan")
public class DeliveryChallan implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "deliveryChallanId", strategy = "org.erp.tarak.deliverychallan.DeliveryChallanIdGenerator")
	@GeneratedValue(generator = "deliveryChallanId")
	private long deliveryChallanId;

	private String finYear;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "DC_DATE")
	private Date deliveryChallanDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_Id")
	private Customer customerId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SO_Id")
	private SalesOrder salesOrder;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Shipper_Id")
	private Shipper shipper;
	
	@Column(name="Booked_To")
	private String bookedAddress;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "DC_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "DCITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "DCITEMS_deliveryChallanId", referencedColumnName = "deliveryChallanId"),
			@JoinColumn(name = "DCITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "DC_deliveryChallanId", referencedColumnName = "deliveryChallanId") }

	)
	private List<DeliveryChallanItem> deliveryChallanItems;

	private double totalCost;

	private String deliveryType;
	

	@Type(type="boolean")
	private boolean processed;

	private double shippingCost;
	
	public long getDeliveryChallanId() {
		return deliveryChallanId;
	}

	public void setDeliveryChallanId(long deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
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

	public List<DeliveryChallanItem> getDeliveryChallanItems() {
		return deliveryChallanItems;
	}

	public void setDeliveryChallanItems(
			List<DeliveryChallanItem> deliveryChallanItems) {
		this.deliveryChallanItems = deliveryChallanItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getDeliveryChallanDate() {
		return deliveryChallanDate;
	}

	public void setDeliveryChallanDate(Date deliveryChallanDate) {
		this.deliveryChallanDate = deliveryChallanDate;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Shipper getShipper() {
		return shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}

	public String getBookedAddress() {
		return bookedAddress;
	}

	public void setBookedAddress(String bookedAddress) {
		this.bookedAddress = bookedAddress;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

}
