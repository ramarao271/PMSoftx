package org.erp.tarak.rawMaterial;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.erp.tarak.category.Category;
import org.erp.tarak.measurement.Measurement;
import org.erp.tarak.stage.Stage;
import org.erp.tarak.variant.Variant;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name="RawMaterial")
public class RawMaterial implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="rawMaterialSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	@Column(name = "RawMaterial_Id")
	private long rawMaterialId;
	
	@Column(name="RawMaterial_Name")
	private String rawMaterialName;
	
	@GenericGenerator(name = "rawMaterialCode", strategy = "org.erp.tarak.rawMaterial.RawMaterialCodeGenerator")
	@GeneratedValue(generator = "rawMaterialCode")
	@Column(name="RawMaterial_Code")
	private String rawMaterialCode;
	
	@Column(name="Group_Id")
	private String groupId;

	@ManyToOne
	@JoinColumn(name="Category_Id")
	private Category category;
	
	@Column(name="Cost")
	private double cost;
	
	@Column(name="Price")
	private double price;
	
	/*@Column(name="Price1")
	private double price1;
	
	@Column(name="Price2")
	private double price2;
	
	@Column(name="Price3")
	private double price3;
	
	*/@Column(name="Quantity")
	private double quantity;
	
	@Column(name="Allocated")
	private double allocated;
	
	@Column(name="Sold")
	private double sold;
	
	@Column(name="Required")
	private double required;
	
	@Column(name="Ordered")
	private double ordered;
	
	@ManyToOne
	@JoinColumn(name="Quantity_Type")
	private Measurement measurement;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Fetch(value=FetchMode.SELECT)
	private List<Variant> variants;
	
	@Type(type="boolean")
	private boolean discontinued;
	
	private String variantType; 
	
	private String rawMaterialionType;
	
	private String imagePath;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Fetch(value=FetchMode.SELECT)
	private List <Stage> stages;
	
	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	public String getRawMaterialCode() {
		return rawMaterialCode;
	}

	public void setRawMaterialCode(String rawMaterialCode) {
		this.rawMaterialCode = rawMaterialCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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

	public long getRawMaterialId() {
		return rawMaterialId;
	}

	public void setRawMaterialId(long rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
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

/*	public double getPrice1() {
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
	}*/

	public List<Variant> getVariants() {
		return variants;
	}

	public void setVariants(List<Variant> variants) {
		this.variants = variants;
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

	public String getRawMaterialionType() {
		return rawMaterialionType;
	}

	public void setRawMaterialionType(String rawMaterialionType) {
		this.rawMaterialionType = rawMaterialionType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

}
