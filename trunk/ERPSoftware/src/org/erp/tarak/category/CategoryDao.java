package org.erp.tarak.category;

import java.util.List;


public interface CategoryDao {
		
		public void addCategory(Category category);

		public List<Category> listCategories();
		
		public Category getCategory(long categoryId);
		
		public void deleteCategory(Category category);

		public Category getLastCategory();

		public Category getCategoryByName(String categoryName);
	}
