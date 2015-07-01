package org.erp.tarak.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToolsController {
	@RequestMapping(value = "/tools/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "tools");
		return new ModelAndView("index", model);
	}


}
