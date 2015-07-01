package org.erp.tarak.salesreturn;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.erp.tarak.product.Product;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "SalesReturnItem")
public class SalesReturnItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	private int srNo;
	@Id
	@GenericGenerator(name = "salesReturnId", strategy = "org.erp.tarak.salesreturn.SalesReturnIdGenerator")
	@GeneratedValue(generator = "salesReturnId")
	private long salesReturnId;
	@Id
	@Column(name="Financial_Year")
	private String finYear;
	@OneToOne
	@JoinColumn(name="Product_Id")
	private Product productId;
	private String description;
	private double quantity;
	private String quantityType;
	private double rate;
	private double totalCost;
	private long variantId;
	private String variantCode;
	
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
	public long getSalesReturnId() {
		return salesReturnId;
	}
	public void setSalesReturnId(long salesReturnId) {
		this.salesReturnId = salesReturnId;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
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


}
