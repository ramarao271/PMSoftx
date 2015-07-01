package org.erp.tarak.variant;

public class VariantBean {
	private long variantId;
	private String productCode;
	private String variantName;
	private String variantType;
	private double quantity;
	private double allocated;
	private double sold;
	private double required;
	private double ordered;
	private double cost;

	public long getVariantId() {
		return variantId;
	}
	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getVariantName() {
		return variantName;
	}
	public void setVariantName(String variantName) {
		this.variantName = variantName;
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
	public String getVariantType() {
		return variantType;
	}
	public void setVariantType(String variantType) {
		this.variantType = variantType;
	}
	public double getAllocated() {
		return allocated;
	}
	public void setAllocated(double allocated) {
		this.allocated = allocated;
	}
	public double getSold() {
		return sold;
	}
	public void setSold(double sold) {
		this.sold = sold;
	}
	public double getRequired() {
		return required;
	}
	public void setRequired(double required) {
		this.required = required;
	}
	public double getOrdered() {
		return ordered;
	}
	public void setOrdered(double ordered) {
		this.ordered = ordered;
	}
	
}
