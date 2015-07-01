package org.erp.tarak.purchaseorder;

import java.util.Date;
import java.util.List;

import org.erp.tarak.supplier.SupplierBean;
import org.hibernate.validator.constraints.NotEmpty;


public class PurchaseOrderBean {

	private long purchaseOrderId;
	private String finYear;
	@NotEmpty(message="Date should not be empty")
	private Date purchaseOrderDate;
	private SupplierBean supplierBean;
	private List<PurchaseOrderItemBean> purchaseOrderItemBeans;
	private double totalCost;
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

	public List<PurchaseOrderItemBean> getPurchaseOrderItemBeans() {
		return purchaseOrderItemBeans;
	}

	public void setPurchaseOrderItemBeans(
			List<PurchaseOrderItemBean> purchaseOrderItemBeans) {
		this.purchaseOrderItemBeans = purchaseOrderItemBeans;
	}

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public SupplierBean getSupplierBean() {
		return supplierBean;
	}

	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
