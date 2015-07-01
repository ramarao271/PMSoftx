package org.erp.tarak.customer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.address.Address;
import org.erp.tarak.address.AddressBean;
import org.erp.tarak.address.AddressService;
import org.erp.tarak.bankaccount.BankAccount;
import org.erp.tarak.bankaccount.BankAccountBean;
import org.erp.tarak.bankaccount.BankAccountService;
import org.erp.tarak.contactperson.ContactPerson;
import org.erp.tarak.contactperson.ContactPersonBean;
import org.erp.tarak.contactperson.ContactPersonService;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalance;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceService;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/*@Autowired
	@Qualifier("customerValidator")
	private Validator validator;*/

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	/*@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}*/
	@Autowired
	private HttpSession session;

	@Autowired
	private AddressService addressService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private ContactPersonService contactPersonService;
	@Autowired
	private CustomerOpeningBalanceService cobService;

	@RequestMapping(value = "/bankAccountscustomer/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("customerBean") CustomerBean customerBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		List<BankAccountBean> bankAccountBeans = customerBean
				.getCustomerAccountsBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (bankAccountBeans == null) {
			BankAccountBean bankAccountBean = new BankAccountBean();
			bankAccountBeans = new LinkedList<BankAccountBean>();
			bankAccountBeans.add(bankAccountBean);
			customerBean.setCustomerAccountsBeans(bankAccountBeans);
			/*if (size > 1) {
				size = size - 1;
			}*/
		}
		listSize = bankAccountBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				BankAccountBean bankAccountBean = new BankAccountBean();
				customerBean.getCustomerAccountsBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				customerBean.getCustomerAccountsBeans().remove(
						customerBean.getCustomerAccountsBeans().size() - 1);
			}
		}
		customerBean.setCustomerAccountsBeans(bankAccountBeans);
		model.put("customerBean", customerBean);
		model.put("operation", "Add");
		return new ModelAndView("customer", model);
	}

	@RequestMapping(value = "/contactpersoncustomer/{category}", method = RequestMethod.POST)
	public ModelAndView contactPersonModules(
			@ModelAttribute("customerBean") CustomerBean customerBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		List<ContactPersonBean> contactPersonBeans = customerBean
				.getContactPersonsBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (contactPersonBeans == null) {
			ContactPersonBean contactPersonBean = new ContactPersonBean();
			contactPersonBeans = new LinkedList<ContactPersonBean>();
			contactPersonBeans.add(contactPersonBean);
			customerBean.setContactPersonsBeans(contactPersonBeans);
		/*	if (size > 1) {
				size = size - 1;
			}*/
		}
		listSize = contactPersonBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				ContactPersonBean contactPersonBean = new ContactPersonBean();
				customerBean.getContactPersonsBeans().add(contactPersonBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				customerBean.getContactPersonsBeans().remove(
						customerBean.getContactPersonsBeans().size() - 1);
			}
		}
		customerBean.setContactPersonsBeans(contactPersonBeans);
		model.put("customerBean", customerBean);
		model.put("operation", "Add");
		return new ModelAndView("customer", model);
	}

	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public ModelAndView saveCustomer(
			@ModelAttribute("customerBean") @Validated CustomerBean customerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if (result.hasErrors()) {
			model.put("customerBean", customerBean);
			return new ModelAndView("customer", model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		Customer customer = CustomerUtilities.prepareCustomerModel(customerBean);
		CustomerOpeningBalance customerOpeningBalance=null;
		if(customer.getCustomerId()==0)
		{
			customerOpeningBalance=new CustomerOpeningBalance();
			customerOpeningBalance.setFinancialYear(user.getFinYear());
		}
		else
		{
			customerOpeningBalance=cobService.getCustomerOpeningBalance(user.getFinYear(), customerBean.getCustomerId());
		}
		customerService.addCustomer(customer);
		customerOpeningBalance.setCustomerId(customer.getCustomerId());
		customerOpeningBalance.setOpeningBalance(customer.getOpeningBalance());
		cobService.addCustomerOpeningBalance(customerOpeningBalance);
		model.put("message", "Customer details saved successfully!");
		model.put("customers",
				CustomerUtilities.prepareListofCustomerBeans(customerService.listCustomers()));
		}
		return new ModelAndView("customerList", model);
	}

	@RequestMapping(value = "/listCustomer", method = RequestMethod.GET)
	public ModelAndView listCustomers() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("customers",
				CustomerUtilities.prepareListofCustomerBeans(customerService.listCustomers()));
		return new ModelAndView("customerList", model);
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
	public ModelAndView addCustomer() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CustomerBean customerBean = new CustomerBean();
		AddressBean address = new AddressBean();
		customerBean.setCompanyAddressBean(address);
		List<BankAccountBean> bankAccountBean = new LinkedList<BankAccountBean>();
		List<ContactPersonBean> contactPersonBean = new LinkedList<ContactPersonBean>();
		customerBean.setCustomerAccountsBeans(bankAccountBean);
		customerBean.setContactPersonsBeans(contactPersonBean);
		model.put("customerBean", customerBean);
		return new ModelAndView("customer", model);
	}

	@RequestMapping(value = "/customer/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CustomerBean customerBean = new CustomerBean();
		model.put("category", "customer");
		model.put("customerBean", customerBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/customer/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "customer");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
	public ModelAndView deleteCustomer(
			@ModelAttribute("command") CustomerBean customerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		Customer customer = CustomerUtilities.getCustomerModel(customerService,
				customerBean.getCustomerId());
		Address address = customer.getCompanyAddress();
		List<BankAccount> bankAccounts = customer.getCustomerAccounts();
		List<ContactPerson> contactPersons = customer.getContactPersons();
		customerService.deleteCustomer(customer);
		bankAccountService.deleteBankAccounts(bankAccounts);
		contactPersonService.deleteContactPersons(contactPersons);
		addressService.deleteAddress(address);
		model.put("customer", null);
		model.put("customers",
				CustomerUtilities.prepareListofCustomerBeans(customerService.listCustomers()));
		return new ModelAndView("customerList", model);
	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
	public ModelAndView editCustomer(
			@ModelAttribute("command") CustomerBean customerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("customerBean", CustomerUtilities.prepareCustomerBean(customerBean.getCustomerId(), customerService));
		return new ModelAndView("customer", model);
	}

	
}
