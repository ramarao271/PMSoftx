package org.erp.tarak.productioninvoice;

import org.erp.tarak.product.ProductBean;

public class ProductionInvoiceItemBean {
	
	private int srNo;
	private long productionInvoiceId;
	private String finYear;
	private ProductBean productId;
	private String description;
	private double quantity;
	private String quantityType;
	private double rate;
	private double totalCost;
	private long variantId;
	private String variantCode;
	private boolean processed;
	private double returnedQty;
	private double returnedValue;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public long getProductionInvoiceId() {
		return productionInvoiceId;
	}
	public void setProductionInvoiceId(long productionInvoiceId) {
		this.productionInvoiceId = productionInvoiceId;
	}
	public long getVariantId() {
		return variantId;
	}
	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}
	public String getVariantCode() {
		return variantCode;
	}
	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public double getReturnedQty() {
		return returnedQty;
	}
	public void setReturnedQty(double returnedQty) {
		this.returnedQty = returnedQty;
	}
	public double getReturnedValue() {
		return returnedValue;
	}
	public void setReturnedValue(double returnedValue) {
		this.returnedValue = returnedValue;
	}
	public ProductBean getProductId() {
		return productId;
	}
	public void setProductId(ProductBean productId) {
		this.productId = productId;
	}
}
