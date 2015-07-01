package org.erp.tarak.productioninvoice;

import java.util.Date;
import java.util.List;

import org.erp.tarak.productionorder.ProductionOrderBean;
import org.erp.tarak.worker.WorkerBean;


public class ProductionInvoiceBean {

	private long productionInvoiceId;
	private String finYear;
	private Date productionInvoiceDate;
	private WorkerBean workerBean;
	private List<ProductionInvoiceItemBean> productionInvoiceItemBeans;
	private double totalCost;
	private boolean processed;
	private ProductionOrderBean productionOrderBean;
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

	public List<ProductionInvoiceItemBean> getProductionInvoiceItemBeans() {
		return productionInvoiceItemBeans;
	}

	public void setProductionInvoiceItemBeans(
			List<ProductionInvoiceItemBean> productionInvoiceItemBeans) {
		this.productionInvoiceItemBeans = productionInvoiceItemBeans;
	}

	public Date getProductionInvoiceDate() {
		return productionInvoiceDate;
	}

	public void setProductionInvoiceDate(Date productionInvoiceDate) {
		this.productionInvoiceDate = productionInvoiceDate;
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

	public ProductionOrderBean getProductionOrderBean() {
		return productionOrderBean;
	}

	public void setProductionOrderBean(ProductionOrderBean productionOrderBean) {
		this.productionOrderBean = productionOrderBean;
	}

}
