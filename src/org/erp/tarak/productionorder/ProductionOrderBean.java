package org.erp.tarak.productionorder;

import java.util.Date;
import java.util.List;

import org.erp.tarak.worker.WorkerBean;


public class ProductionOrderBean {

	private long productionOrderId;
	private String finYear;
	private Date productionOrderDate;
	private WorkerBean workerBean;
	private List<ProductionOrderItemBean> productionOrderItemBeans;
	private double totalCost;
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

	public List<ProductionOrderItemBean> getProductionOrderItemBeans() {
		return productionOrderItemBeans;
	}

	public void setProductionOrderItemBeans(
			List<ProductionOrderItemBean> productionOrderItemBeans) {
		this.productionOrderItemBeans = productionOrderItemBeans;
	}

	public Date getProductionOrderDate() {
		return productionOrderDate;
	}

	public void setProductionOrderDate(Date productionOrderDate) {
		this.productionOrderDate = productionOrderDate;
	}

	public WorkerBean getWorkerBean() {
		return workerBean;
	}

	public void setWorkerBean(WorkerBean workerBean) {
		this.workerBean = workerBean;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
