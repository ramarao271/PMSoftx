package org.erp.tarak.product;

import java.util.Date;

public class ProductReport {

	private String productName;
	private String categoryName;
	private double quantity;
	private double rate;
	private Date date;
	private String month;
	private double totalCost;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
