package org.erp.tarak.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("categoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCategory(Category category) {
		categoryDao.addCategory(category);
	}
	
	public List<Category> listCategories() {
		return categoryDao.listCategories();
	}

	public Category getCategory(long categoryId) {
		return categoryDao.getCategory(categoryId);
	}
	
	public void deleteCategory(Category category) {
		categoryDao.deleteCategory(category);
	}

	@Override
	public Category getLastCategory() {
		return categoryDao.getLastCategory();
	}

	@Override
	public Category getCategoryByName(String categoryName) {
		return categoryDao.getCategoryByName(categoryName);
	}

}
