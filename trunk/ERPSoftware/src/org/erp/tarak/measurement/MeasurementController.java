package org.erp.tarak.measurement;

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
public class MeasurementController {

	@Autowired
	private MeasurementService measurementService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/saveMeasurement", method = RequestMethod.POST)
	public ModelAndView saveMeasurement(
			@ModelAttribute("command") @Valid MeasurementBean measurementBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		boolean isMeasurementExists=MeasurementUtilities.isMeasurementExists(measurementService,measurementBean.getMeasurementName());
		if (result.hasErrors() || isMeasurementExists) {
			model.put("measurements", MeasurementUtilities
					.prepareListofMeasurementBean(measurementService
							.listMeasurements()));
			if(isMeasurementExists)
			{
				model.put("message", "Measurement with name "+measurementBean.getMeasurementName()+" already exists");
			}
			return new ModelAndView("measurement", model);

		}
		
		Measurement measurement = MeasurementUtilities
				.prepareMeasurementModel(measurementBean);

		measurementService.addMeasurement(measurement);
		model.put("message", "Measurement saved successfully");
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		return new ModelAndView("measurement", model);
	}

	@RequestMapping(value = "/measurement", method = RequestMethod.GET)
	public ModelAndView addMeasurement(
			@ModelAttribute("command") MeasurementBean measurementBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		model.put("operation", "Add");
		return new ModelAndView("measurement", model);
	}

	@RequestMapping(value = "/deleteMeasurement", method = RequestMethod.GET)
	public ModelAndView deleteMeasurement(
			@ModelAttribute("command") MeasurementBean measurementBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		measurementService.deleteMeasurement(MeasurementUtilities
				.prepareMeasurementModel(measurementBean));
		model.put("measurement", null);
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		model.put("message", "Measurement deleted");
		return new ModelAndView("measurement", model);
	}

	@RequestMapping(value = "/editMeasurement", method = RequestMethod.GET)
	public ModelAndView editMeasurement(
			@ModelAttribute("command") MeasurementBean measurementBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("measurement", MeasurementUtilities
				.prepareMeasurementBean(measurementService
						.getMeasurement(measurementBean.getMeasurementId())));
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		model.put("operation", "Edit");
		return new ModelAndView("measurement", model);
	}

}
