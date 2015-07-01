package org.erp.tarak.stage;

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
public class StageController {

	@Autowired
	private StageService stageService;
	
	@Autowired
	private HttpSession session;


	@RequestMapping(value = "/saveStage", method = RequestMethod.POST)
	public ModelAndView saveStage(
			@ModelAttribute("command") @Valid StageBean stageBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if(result.hasErrors())
		{
			model.put("categories", StageUtilities
					.prepareListofStageBean(stageService.listStages()));
			return new ModelAndView("stage", model);
		}
		if(stageBean.getStageId()==0)
		{
			Stage stage=stageService.getStageByName(stageBean.getStageName());
			if(stage!=null && stage.getStageId()>0)
			{
				model.put("message", "Stage with name "+stageBean.getStageName()+" already exists!");
				model.put("categories", StageUtilities
						.prepareListofStageBean(stageService.listStages()));
				return new ModelAndView("stage", model);
			}
		}
		model.put("message", "Stage saved successfully");
		Stage stage = StageUtilities.prepareStageModel(stageBean,stageService);
		stageService.addStage(stage);
		model.put("categories", StageUtilities
				.prepareListofStageBean(stageService.listStages()));
		return new ModelAndView("stage", model);
	}

	@RequestMapping(value = "/stage", method = RequestMethod.GET)
	public ModelAndView addStage(
			@ModelAttribute("command") StageBean stageBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("categories", StageUtilities
				.prepareListofStageBean(stageService.listStages()));
		model.put("operation", "Add");
		return new ModelAndView("stage", model);
	}

	@RequestMapping(value = "/deleteStage", method = RequestMethod.GET)
	public ModelAndView deleteStage(
			@ModelAttribute("command") StageBean stageBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		stageService.deleteStage(StageUtilities
				.prepareStageModel(stageBean));
		model.put("stage", null);
		model.put("categories", StageUtilities
				.prepareListofStageBean(stageService.listStages()));
		model.put("message", "Stage deleted");
		return new ModelAndView("stage", model);
	}

	@RequestMapping(value = "/editStage", method = RequestMethod.GET)
	public ModelAndView editStage(
			@ModelAttribute("command") StageBean stageBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("stage", StageUtilities.prepareStageBean(stageService
				.getStage(stageBean.getStageId())));
		model.put("categories", StageUtilities
				.prepareListofStageBean(stageService.listStages()));
		model.put("operation", "Edit");
		return new ModelAndView("stage", model);
	}

}
