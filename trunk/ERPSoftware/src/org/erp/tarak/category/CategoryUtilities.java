package org.erp.tarak.category;

import java.util.ArrayList;
import java.util.List;

import org.erp.tarak.library.ERPUtilities;


public class CategoryUtilities {

	public static List<CategoryBean> prepareListofCategoryBean(
			List<Category> categories) {
		List<CategoryBean> beans = new ArrayList<CategoryBean>();
		for (Category category : categories) {
			CategoryBean categoryBean = new CategoryBean();
			categoryBean.setCategoryId(category.getCategoryId());
			categoryBean.setCategoryName(category.getCategoryName());
			categoryBean.setCategoryCode(category.getCategoryCode());
			beans.add(categoryBean);
		}
		return beans;
	}

	public static Category prepareCategoryModel(CategoryBean categoryBean,CategoryService categoryService) {
		Category category = new Category();
		category.setCategoryId(categoryBean.getCategoryId());
		category.setCategoryName(categoryBean.getCategoryName());
		if(categoryBean.getCategoryId()>0)
		{
			category.setCategoryCode(categoryBean.getCategoryCode());
		}
		else
		{
			String categoryCode=getCategoryCode(categoryService);
			category.setCategoryCode(categoryCode);
		}
		
		return category;
	}

	public static Category prepareCategoryModel(CategoryBean categoryBean) {
		Category category = new Category();
		category.setCategoryId(categoryBean.getCategoryId());
		category.setCategoryName(categoryBean.getCategoryName());
		category.setCategoryCode(categoryBean.getCategoryCode());
		return category;
	}

	
	
	
	private static String getCategoryCode(CategoryService categoryService) {
		Category category=categoryService.getLastCategory();
		String categoryCode="";
		if(category!=null)
		{
			categoryCode=ERPUtilities.incrementCode(category.getCategoryCode());
			categoryCode=ERPUtilities.fomatStringToN(categoryCode,3);
		}
		else
		{
			categoryCode="000";
		}
		return categoryCode;
	}

	public static CategoryBean prepareCategoryBean(Category category) {
		CategoryBean bean = new CategoryBean();
		bean.setCategoryId(category.getCategoryId());
		bean.setCategoryName(category.getCategoryName());
		bean.setCategoryCode(category.getCategoryCode());
		return bean;
	}


}
