package org.erp.tarak.product;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.erp.tarak.category.CategoryBean;
import org.erp.tarak.measurement.MeasurementBean;
import org.erp.tarak.stage.StageBean;
import org.erp.tarak.variant.VariantBean;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class ProductBean {

	private long productId;
	@NotEmpty(message="Product Name cannot be empty")
	private String productName;
	private String productCode;
	private String groupId;
	@NotNull(message="Please create a category first")
	private CategoryBean categoryBean;
	@Min(value=0,message="should be Positive value")
	private double cost;
	@Min(value=0,message="should be Positive value")
	private double price;
	@Min(value=0,message="should be Positive value")
	private double price1;
	@Min(value=0,message="should be Positive value")
	private double price2;
	@Min(value=0,message="should be Positive value")
	private double price3;
	@Min(value=0,message="should be Positive value")
	private double quantity;
	private double allocated;
	private double sold;
	private double required;
	private double ordered;
	@NotNull(message="Please create a measurement first")
	private MeasurementBean measurementBean;
	private List<VariantBean> variantBeans;
	private String variantType;
	private boolean discontinued;
	private String productionType;
	private MultipartFile image;
	private String imagePath;
	private List <StageBean> stageBeans;
	private String qty;
	private double totalCost;
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public double getQuantity() {
		return quantity;
	}

	public CategoryBean getCategoryBean() {
		return categoryBean;
	}

	public void setCategoryBean(CategoryBean categoryBean) {
		this.categoryBean = categoryBean;
	}

	public MeasurementBean getMeasurementBean() {
		return measurementBean;
	}

	public void setMeasurementBean(MeasurementBean measurementBean) {
		this.measurementBean = measurementBean;
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

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice1() {
		return price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public double getPrice3() {
		return price3;
	}

	public void setPrice3(double price3) {
		this.price3 = price3;
	}

	public List<VariantBean> getVariantBeans() {
		return variantBeans;
	}

	public void setVariantBeans(List<VariantBean> variantBeans) {
		this.variantBeans = variantBeans;
	}

	public String getVariantType() {
		return variantType;
	}

	public void setVariantType(String variantType) {
		this.variantType = variantType;
	}

	public boolean isDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}

	public String getProductionType() {
		return productionType;
	}

	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<StageBean> getStageBeans() {
		return stageBeans;
	}

	public void setStageBeans(List<StageBean> stageBeans) {
		this.stageBeans = stageBeans;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
}
