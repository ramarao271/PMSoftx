package org.erp.tarak.subVariantProperties;

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
public class SubVariantPropertiesController {

	@Autowired
	private SubVariantPropertiesService subVariantPropertiesService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/saveSubVariantProperties", method = RequestMethod.POST)
	public ModelAndView saveSubVariantProperties(
			@ModelAttribute("command") @Valid SubVariantPropertiesBean subVariantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		boolean isSubVariantPropertiesExists=SubVariantPropertiesUtilities.isSubVariantPropertiesExists(subVariantPropertiesService,subVariantPropertiesBean.getSubVariantPropertiesName());
		if (result.hasErrors() || isSubVariantPropertiesExists) {
			model.put("subVariantPropertiess", SubVariantPropertiesUtilities
					.prepareListofSubVariantPropertiesBean(subVariantPropertiesService
							.listSubVariantPropertiess()));
			if(isSubVariantPropertiesExists)
			{
				model.put("message", "Variant Property with name "+subVariantPropertiesBean.getSubVariantPropertiesName()+" already exists");
			}
			return new ModelAndView("subVariantProperties", model);

		}
		
		SubVariantProperties subVariantProperties = SubVariantPropertiesUtilities
				.prepareSubVariantPropertiesModel(subVariantPropertiesBean);

		subVariantPropertiesService.addSubVariantProperties(subVariantProperties);
		model.put("message", "Variant Property saved successfully");
		model.put("subVariantPropertiess", SubVariantPropertiesUtilities
				.prepareListofSubVariantPropertiesBean(subVariantPropertiesService
						.listSubVariantPropertiess()));
		return new ModelAndView("subVariantProperties", model);
	}

	@RequestMapping(value = "/subVariantProperties", method = RequestMethod.GET)
	public ModelAndView addSubVariantProperties(
			@ModelAttribute("command") SubVariantPropertiesBean subVariantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("subVariantPropertiess", SubVariantPropertiesUtilities
				.prepareListofSubVariantPropertiesBean(subVariantPropertiesService
						.listSubVariantPropertiess()));
		model.put("operation", "Add");
		return new ModelAndView("subVariantProperties", model);
	}

	@RequestMapping(value = "/deleteSubVariantProperties", method = RequestMethod.GET)
	public ModelAndView deleteSubVariantProperties(
			@ModelAttribute("command") SubVariantPropertiesBean subVariantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		subVariantPropertiesService.deleteSubVariantProperties(SubVariantPropertiesUtilities
				.prepareSubVariantPropertiesModel(subVariantPropertiesBean));
		model.put("subVariantProperties", null);
		model.put("subVariantPropertiess", SubVariantPropertiesUtilities
				.prepareListofSubVariantPropertiesBean(subVariantPropertiesService
						.listSubVariantPropertiess()));
		model.put("message", "SubVariantProperties deleted");
		return new ModelAndView("subVariantProperties", model);
	}

	@RequestMapping(value = "/editSubVariantProperties", method = RequestMethod.GET)
	public ModelAndView editSubVariantProperties(
			@ModelAttribute("command") SubVariantPropertiesBean subVariantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("subVariantProperties", SubVariantPropertiesUtilities
				.prepareSubVariantPropertiesBean(subVariantPropertiesService
						.getSubVariantProperties(subVariantPropertiesBean.getSubVariantPropertiesId())));
		model.put("subVariantPropertiess", SubVariantPropertiesUtilities
				.prepareListofSubVariantPropertiesBean(subVariantPropertiesService
						.listSubVariantPropertiess()));
		model.put("operation", "Edit");
		return new ModelAndView("subVariantProperties", model);
	}

}
