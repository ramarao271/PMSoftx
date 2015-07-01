package org.erp.tarak.category;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Category")
public class Category  implements Serializable{
	
	private static final long serialVersionUID = -723583058586873479L;
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="categorySequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long categoryId; 
	
	@Column(name="Category_Name")
	private String categoryName;

	@GenericGenerator(name = "categoryCode", strategy = "org.erp.tarak.category.CategoryCodeGenerator")
	@GeneratedValue(generator = "categoryCode",strategy = GenerationType.AUTO)
	@Column(name="Category_Code")
	private String categoryCode;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
