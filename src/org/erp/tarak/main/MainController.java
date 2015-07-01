package org.erp.tarak.main;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.company.Company;
import org.erp.tarak.company.CompanyService;
import org.erp.tarak.company.CompanyUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.user.User;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class MainController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	protected ModelAndView authenticateUser(
			@ModelAttribute("command") UserBean userBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			User user = userService.getUser(userBean.getUsername());
			if (user.getPassword() != null || "".equals(user.getPassword())) {
				if (user.getPassword().equals(userBean.getPassword())) {
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH)+1;
					String finYear = "";
					if (month >= 4) {
						finYear = year + "-" + (year + 1);
					} else {
						finYear = (year - 1) + "-" + year;
					}
					userBean.setFinYear(finYear);
					model.put("userBean", userBean);
					session.setAttribute("user", userBean);
					Company company=companyService.getCompany(1);
					if(company!=null)
					{
						session.setAttribute("company", CompanyUtilities.prepareCompanyBean(company));
					}
					return new ModelAndView("home", model);
				} else {
					UserBean userBean1 = new UserBean();
					model.put("userBean", userBean1);
					model.put("errorMessage", "Please enter correct Password");
					return new ModelAndView("index", model);
				}
			} else {
				UserBean userBean1 = new UserBean();
				model.put("userBean", userBean1);
				model.put("errorMessage",
						"Please enter correct Username/Password");
				return new ModelAndView("index", model);
			}
		} catch (Exception e) {
			UserBean userBean1 = new UserBean();
			model.put("userBean", userBean1);
			model.put("errorMessage", "Please enter correct Username/Password");
			return new ModelAndView("index", model);
		}
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	protected ModelAndView createUserLoad(
			@ModelAttribute("command") UserBean userBean, BindingResult result) {
		/*if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}*/
		return new ModelAndView("createUser");// , model);
	}

	@RequestMapping(value = "/createUserDb", method = RequestMethod.POST)
	protected ModelAndView createUser(
			@ModelAttribute("command") UserBean userBean, BindingResult result) {
		/*if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}*/
		User user = prepareModel(userBean);
		userService.addUser(user);
		return new ModelAndView("redirect:/main/");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	protected ModelAndView loadLoginindex(Map<String, Object> model) {
		if (ERPUtilities.isValidUser(session)) {
			return new ModelAndView("home", model);
		}
		
		UserBean userBean = new UserBean();
		model.put("userBean", userBean);
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected ModelAndView loadLogin(Map<String, Object> model) {
		if (ERPUtilities.isValidUser(session)) {
			return new ModelAndView("home", model);
		}
		UserBean userBean = new UserBean();
		model.put("userBean", userBean);
		return new ModelAndView("index", model);
	}


	
	@RequestMapping(value = "/links/{category}", method = RequestMethod.GET)
	protected ModelAndView loadHomeLinks(@PathVariable String category,
			Map<String, Object> model) {
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		@SuppressWarnings("unchecked")
		Map<String, String> cats = (Map<String, String>) ERPConstants.linkMap
				.get(category);
		model.put("entries", cats);
		return new ModelAndView("links", model);
	}

	@RequestMapping(value = "/ERPSoftware/resources/autocomplete/images/{d}", method = RequestMethod.GET)
	protected ModelAndView autoComp(@PathVariable String d,
			Map<String, Object> model) {
		String x = "/ERPSoftware/resources/autocomplete/images/" + d;
		return new ModelAndView(x);
	}

	@RequestMapping(value = "/links", method = RequestMethod.GET)
	protected ModelAndView loadHomeLinks(Map<String, Object> model) {
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		return new ModelAndView("links", model);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	protected ModelAndView loadHomeHello(Map<String, Object> model) {
		return new ModelAndView("hello", model);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	protected ModelAndView logout(Map<String, Object> model) {
		session.removeAttribute("user");
		session.invalidate();
		model.put("userBean", new UserBean());
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/financial", method = RequestMethod.GET)
	protected ModelAndView loadFinYear(Map<String, Object> model) {
		return new ModelAndView("financial", model);
	}

	@RequestMapping(value = "/transactionForm", method = RequestMethod.GET)
	protected ModelAndView transactionForm(Map<String, Object> model) {
		return new ModelAndView("transactionForm", model);
	}
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	protected ModelAndView transaction(Map<String, Object> model) {
		return new ModelAndView("transaction", model);
	}
	
	
	@RequestMapping(value = "/year/{year}", method = RequestMethod.GET)
	protected ModelAndView setFinYear(@PathVariable String year,Map<String, Object> model) {
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		UserBean user=(UserBean) session.getAttribute("user");
		user.setFinYear(year);
		session.setAttribute("user",user);
		model.put("userBean",user);
		return new ModelAndView("home", model);
	}

	
	private User prepareModel(UserBean userBean) {
		User user = new User();
		user.setCategory(userBean.getCategory());
		user.setPassword(userBean.getPassword());
		user.setSecurityAnswer(userBean.getSecurityAnswer());
		user.setSecurityQuestion(userBean.getSecurityQuestion());
		user.setUsername(userBean.getUsername());
		return user;

	}

}
