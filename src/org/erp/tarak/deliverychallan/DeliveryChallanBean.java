package org.erp.tarak.deliverychallan;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.salesorder.SalesOrderBean;
import org.erp.tarak.shipper.ShipperBean;


public class DeliveryChallanBean {

	private long deliveryChallanId;
	private SalesOrderBean salesOrderBean;
	private String finYear;
	private Date deliveryChallanDate;
	private CustomerBean customerBean;
	private ShipperBean shipperBean;
	private String bookedAddress;
	private List<DeliveryChallanItemBean> deliveryChallanItemBeans;
	private double totalCost;
	private String deliveryType;
	private boolean processed;
	
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


	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<DeliveryChallanItemBean> getDeliveryChallanItemBeans() {
		return deliveryChallanItemBeans;
	}

	public void setDeliveryChallanItemBeans(
			List<DeliveryChallanItemBean> deliveryChallanItemBeans) {
		this.deliveryChallanItemBeans = deliveryChallanItemBeans;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public Date getDeliveryChallanDate() {
		return deliveryChallanDate;
	}

	public void setDeliveryChallanDate(Date deliveryChallanDate) {
		this.deliveryChallanDate = deliveryChallanDate;
	}

	public SalesOrderBean getSalesOrderBean() {
		return salesOrderBean;
	}

	public void setSalesOrderBean(SalesOrderBean salesOrderBean) {
		this.salesOrderBean = salesOrderBean;
	}

	public ShipperBean getShipperBean() {
		return shipperBean;
	}

	public void setShipperBean(ShipperBean shipperBean) {
		this.shipperBean = shipperBean;
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

}
