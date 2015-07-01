package org.erp.tarak.stageProperties;

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
public class StagePropertiesController {

	@Autowired
	private StagePropertiesService stagePropertiesService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/saveStageProperties", method = RequestMethod.POST)
	public ModelAndView saveStageProperties(
			@ModelAttribute("command") @Valid StagePropertiesBean stagePropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		boolean isStagePropertiesExists=StagePropertiesUtilities.isStagePropertiesExists(stagePropertiesService,stagePropertiesBean.getStagePropertiesName());
		if (result.hasErrors() || isStagePropertiesExists) {
			model.put("stagePropertiess", StagePropertiesUtilities
					.prepareListofStagePropertiesBean(stagePropertiesService
							.listStagePropertiess()));
			if(isStagePropertiesExists)
			{
				model.put("message", "Stage with name "+stagePropertiesBean.getStagePropertiesName()+" already exists");
			}
			return new ModelAndView("stageProperties", model);

		}
		
		StageProperties stageProperties = StagePropertiesUtilities
				.prepareStagePropertiesModel(stagePropertiesBean);

		stagePropertiesService.addStageProperties(stageProperties);
		model.put("message", "Stage saved successfully");
		model.put("stagePropertiess", StagePropertiesUtilities
				.prepareListofStagePropertiesBean(stagePropertiesService
						.listStagePropertiess()));
		return new ModelAndView("stageProperties", model);
	}

	@RequestMapping(value = "/stageProperties", method = RequestMethod.GET)
	public ModelAndView addStageProperties(
			@ModelAttribute("command") StagePropertiesBean stagePropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("stagePropertiess", StagePropertiesUtilities
				.prepareListofStagePropertiesBean(stagePropertiesService
						.listStagePropertiess()));
		model.put("operation", "Add");
		return new ModelAndView("stageProperties", model);
	}

	@RequestMapping(value = "/deleteStageProperties", method = RequestMethod.GET)
	public ModelAndView deleteStageProperties(
			@ModelAttribute("command") StagePropertiesBean stagePropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		stagePropertiesService.deleteStageProperties(StagePropertiesUtilities
				.prepareStagePropertiesModel(stagePropertiesBean));
		model.put("stageProperties", null);
		model.put("stagePropertiess", StagePropertiesUtilities
				.prepareListofStagePropertiesBean(stagePropertiesService
						.listStagePropertiess()));
		model.put("message", "Stage deleted");
		return new ModelAndView("stageProperties", model);
	}

	@RequestMapping(value = "/editStageProperties", method = RequestMethod.GET)
	public ModelAndView editStageProperties(
			@ModelAttribute("command") StagePropertiesBean stagePropertiesBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("stageProperties", StagePropertiesUtilities
				.prepareStagePropertiesBean(stagePropertiesService
						.getStageProperties(stagePropertiesBean.getStagePropertiesId())));
		model.put("stagePropertiess", StagePropertiesUtilities
				.prepareListofStagePropertiesBean(stagePropertiesService
						.listStagePropertiess()));
		model.put("operation", "Edit");
		return new ModelAndView("stageProperties", model);
	}

}
