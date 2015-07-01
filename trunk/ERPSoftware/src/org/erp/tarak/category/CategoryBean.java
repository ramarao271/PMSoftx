package org.erp.tarak.category;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryBean {
	private long categoryId;
	@NotEmpty(message="Category cannot be empty")
	private String categoryName;
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
