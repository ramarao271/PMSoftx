package org.erp.tarak.variantProperties;

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
public class VariantPropertiesController {

	@Autowired
	private VariantPropertiesService variantPropertiesService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/saveVariantProperties", method = RequestMethod.POST)
	public ModelAndView saveVariantProperties(
			@ModelAttribute("command") @Valid VariantPropertiesBean variantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		boolean isVariantPropertiesExists=VariantPropertiesUtilities.isVariantPropertiesExists(variantPropertiesService,variantPropertiesBean.getVariantPropertiesName(),variantPropertiesBean.getVariantPropertiesType());
		if (result.hasErrors() || isVariantPropertiesExists) {
			model.put("variantPropertiess", VariantPropertiesUtilities
					.prepareListofVariantPropertiesBean(variantPropertiesService
							.listVariantPropertiess()));
			if(isVariantPropertiesExists)
			{
				model.put("message", "Variant Property with name "+variantPropertiesBean.getVariantPropertiesName()+" already exists");
			}
			return new ModelAndView("variantProperties", model);

		}
		
		VariantProperties variantProperties = VariantPropertiesUtilities
				.prepareVariantPropertiesModel(variantPropertiesBean);

		variantPropertiesService.addVariantProperties(variantProperties);
		model.put("message", "Variant Property saved successfully");
		model.put("variantPropertiess", VariantPropertiesUtilities
				.prepareListofVariantPropertiesBean(variantPropertiesService
						.listVariantPropertiess()));
		return new ModelAndView("variantProperties", model);
	}

	@RequestMapping(value = "/variantProperties", method = RequestMethod.GET)
	public ModelAndView addVariantProperties(
			@ModelAttribute("command") VariantPropertiesBean variantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("variantPropertiess", VariantPropertiesUtilities
				.prepareListofVariantPropertiesBean(variantPropertiesService
						.listVariantPropertiess()));
		model.put("operation", "Add");
		return new ModelAndView("variantProperties", model);
	}

	@RequestMapping(value = "/deleteVariantProperties", method = RequestMethod.GET)
	public ModelAndView deleteVariantProperties(
			@ModelAttribute("command") VariantPropertiesBean variantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		variantPropertiesService.deleteVariantProperties(VariantPropertiesUtilities
				.prepareVariantPropertiesModel(variantPropertiesBean));
		model.put("variantProperties", null);
		model.put("variantPropertiess", VariantPropertiesUtilities
				.prepareListofVariantPropertiesBean(variantPropertiesService
						.listVariantPropertiess()));
		model.put("message", "VariantProperties deleted");
		return new ModelAndView("variantProperties", model);
	}

	@RequestMapping(value = "/editVariantProperties", method = RequestMethod.GET)
	public ModelAndView editVariantProperties(
			@ModelAttribute("command") VariantPropertiesBean variantPropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("variantProperties", VariantPropertiesUtilities
				.prepareVariantPropertiesBean(variantPropertiesService
						.getVariantProperties(variantPropertiesBean.getVariantPropertiesId())));
		model.put("variantPropertiess", VariantPropertiesUtilities
				.prepareListofVariantPropertiesBean(variantPropertiesService
						.listVariantPropertiess()));
		model.put("operation", "Edit");
		return new ModelAndView("variantProperties", model);
	}

}
