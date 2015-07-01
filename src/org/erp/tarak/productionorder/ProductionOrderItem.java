package org.erp.tarak.productionorder;

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

import org.erp.tarak.rawMaterial.RawMaterial;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "ProductionOrderItem")
public class ProductionOrderItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	/*@SequenceGenerator(name="my_seq", sequenceName="poijSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	*/
	private int srNo;
	@Id
	@GenericGenerator(name = "productionOrderId", strategy = "org.erp.tarak.productionorder.ProductionOrderIdGenerator")
	@GeneratedValue(generator = "productionOrderId")
	private long productionOrderId;
	@Id
	@Column(name="Financial_Year")
	private String finYear;
	@OneToOne
	@JoinColumn(name="RawMaterial_Id")
	private RawMaterial rawMaterialId;
	private String description;
	private double quantity;
	private String quantityType;
	private double rate;
	private double totalCost;
	private long variantId;
	private String variantCode;
	@Type(type="boolean")
	private boolean processed;

	public int getSrNo() {
		return srNo;
	}
	public RawMaterial getRawMaterialId() {
		return rawMaterialId;
	}
	public void setRawMaterialId(RawMaterial rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
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
	public long getProductionOrderId() {
		return productionOrderId;
	}
	public void setProductionOrderId(long productionOrderId) {
		this.productionOrderId = productionOrderId;
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


}
