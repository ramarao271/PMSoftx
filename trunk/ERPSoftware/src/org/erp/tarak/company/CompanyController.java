package org.erp.tarak.company;

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
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private HttpSession session;


	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
	public ModelAndView saveCompany(
			@ModelAttribute("command") @Valid CompanyBean companyBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if(result.hasErrors())
		{
			model.put("companies", CompanyUtilities
					.prepareListofCompanyBean(companyService.listCategories()));
			return new ModelAndView("company", model);
		}
		if(companyBean.getCompanyId()==0)
		{
			Company company=companyService.getCompanyByName(companyBean.getCompanyName());
			if(company!=null && company.getCompanyId()>0)
			{
				model.put("message", "Company with name "+companyBean.getCompanyName()+" already exists!");
				model.put("companies", CompanyUtilities
						.prepareListofCompanyBean(companyService.listCategories()));
				return new ModelAndView("company", model);
			}
		}
		model.put("message", "Company saved successfully");
		Company company = CompanyUtilities.prepareCompanyModel(companyBean,companyService);
		companyService.addCompany(company);
		model.put("companies", CompanyUtilities
				.prepareListofCompanyBean(companyService.listCategories()));
		return new ModelAndView("company", model);
	}

	@RequestMapping(value = "/addCompany", method = RequestMethod.GET)
	public ModelAndView addCompany(
			@ModelAttribute("command") CompanyBean companyBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("companies", CompanyUtilities
				.prepareListofCompanyBean(companyService.listCategories()));
		model.put("operation", "Add");
		return new ModelAndView("company", model);
	}

	@RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
	public ModelAndView deleteCompany(
			@ModelAttribute("command") CompanyBean companyBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		companyService.deleteCompany(CompanyUtilities
				.prepareCompanyModel(companyBean));
		model.put("company", null);
		model.put("companies", CompanyUtilities
				.prepareListofCompanyBean(companyService.listCategories()));
		model.put("message", "Company deleted");
		return new ModelAndView("company", model);
	}

	@RequestMapping(value = "/editCompany", method = RequestMethod.GET)
	public ModelAndView editCompany(
			@ModelAttribute("command") CompanyBean companyBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("company", CompanyUtilities.prepareCompanyBean(companyService
				.getCompany(companyBean.getCompanyId())));
		model.put("companies", CompanyUtilities
				.prepareListofCompanyBean(companyService.listCategories()));
		model.put("operation", "Edit");
		return new ModelAndView("company", model);
	}

}
