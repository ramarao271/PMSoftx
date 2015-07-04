package org.erp.tarak.product;

import org.erp.tarak.subVariantProperties.SubVariantPropertiesBean;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.worker.WorkerBean;

public class SubVariantBean {
	
	private ProductBean productBean;
	private VariantBean variantBean;
	private SubVariantPropertiesBean subVariantPropertiesBean;
	private String newVariantName; 
	private double quantity;
	private double cost;
	private WorkerBean workerBean;

	public ProductBean getProductBean() {
		return productBean;
	}
	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}
	public VariantBean getVariantBean() {
		return variantBean;
	}
	public void setVariantBean(VariantBean variantBean) {
		this.variantBean = variantBean;
	}
	public SubVariantPropertiesBean getSubVariantPropertiesBean() {
		return subVariantPropertiesBean;
	}
	public void setSubVariantPropertiesBean(SubVariantPropertiesBean subVariantPropertiesBean) {
		this.subVariantPropertiesBean = subVariantPropertiesBean;
	}
	public WorkerBean getWorkerBean() {
		return workerBean;
	}
	public void setWorkerBean(WorkerBean workerBean) {
		this.workerBean = workerBean;
	}
	public String getNewVariantName() {
		return newVariantName;
	}
	public void setNewVariantName(String newVariantName) {
		this.newVariantName = newVariantName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
