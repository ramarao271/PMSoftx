package org.erp.tarak.category;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.library.ERPUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private HttpSession session;


	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public ModelAndView saveCategory(
			@ModelAttribute("command") @Valid CategoryBean categoryBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if(result.hasErrors())
		{
			model.put("categories", CategoryUtilities
					.prepareListofCategoryBean(categoryService.listCategories()));
			return new ModelAndView("category", model);
		}
		if(categoryBean.getCategoryId()==0)
		{
			Category category=categoryService.getCategoryByName(categoryBean.getCategoryName());
			if(category!=null && category.getCategoryId()>0)
			{
				model.put("message", "Category with name "+categoryBean.getCategoryName()+" already exists!");
				model.put("categories", CategoryUtilities
						.prepareListofCategoryBean(categoryService.listCategories()));
				return new ModelAndView("category", model);
			}
		}
		model.put("message", "Category saved successfully");
		Category category = CategoryUtilities.prepareCategoryModel(categoryBean,categoryService);
		categoryService.addCategory(category);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		return new ModelAndView("category", model);
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView addCategory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		model.put("operation", "Add");
		return new ModelAndView("category", model);
	}

	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public ModelAndView deleteCategory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		categoryService.deleteCategory(CategoryUtilities
				.prepareCategoryModel(categoryBean));
		model.put("category", null);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		model.put("message", "Category deleted");
		return new ModelAndView("category", model);
	}

	@RequestMapping(value = "/editCategory", method = RequestMethod.GET)
	public ModelAndView editCategory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", CategoryUtilities.prepareCategoryBean(categoryService
				.getCategory(categoryBean.getCategoryId())));
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		model.put("operation", "Edit");
		return new ModelAndView("category", model);
	}

}
