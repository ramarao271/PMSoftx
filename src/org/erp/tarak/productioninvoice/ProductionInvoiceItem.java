package org.erp.tarak.productioninvoice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.erp.tarak.product.Product;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "ProductionInvoiceItem")
public class ProductionInvoiceItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	/*@SequenceGenerator(name="my_seq", sequenceName="poijSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
*/	
	private int srNo;
	@Id
	@GenericGenerator(name = "productionInvoiceId", strategy = "org.erp.tarak.productioninvoice.ProductionInvoiceIdGenerator")
	@GeneratedValue(generator = "productionInvoiceId")
	private long productionInvoiceId;
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
	@Type(type="boolean")
	private boolean processed;
	private double returnedQty;
	private double returnedValue;	
	public int getSrNo() {
		return srNo;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
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

}
