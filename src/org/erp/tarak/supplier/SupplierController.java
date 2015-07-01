package org.erp.tarak.supplier;

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
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalance;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceService;
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
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	/*@Autowired
	@Qualifier("supplierValidator")
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
	private SupplierOpeningBalanceService cobService;

	@RequestMapping(value = "/bankAccountssupplier/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("supplierBean") SupplierBean supplierBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		List<BankAccountBean> bankAccountBeans = supplierBean
				.getSupplierAccountsBeans();
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
			supplierBean.setSupplierAccountsBeans(bankAccountBeans);
			/*if (size > 1) {
				size = size - 1;
			}*/
		}
		listSize = bankAccountBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				BankAccountBean bankAccountBean = new BankAccountBean();
				supplierBean.getSupplierAccountsBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				supplierBean.getSupplierAccountsBeans().remove(
						supplierBean.getSupplierAccountsBeans().size() - 1);
			}
		}
		supplierBean.setSupplierAccountsBeans(bankAccountBeans);
		model.put("supplierBean", supplierBean);
		model.put("operation", "Add");
		return new ModelAndView("supplier", model);
	}

	@RequestMapping(value = "/contactpersonsupplier/{category}", method = RequestMethod.POST)
	public ModelAndView contactPersonModules(
			@ModelAttribute("supplierBean") SupplierBean supplierBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		List<ContactPersonBean> contactPersonBeans = supplierBean
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
			supplierBean.setContactPersonsBeans(contactPersonBeans);
		/*	if (size > 1) {
				size = size - 1;
			}*/
		}
		listSize = contactPersonBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				ContactPersonBean contactPersonBean = new ContactPersonBean();
				supplierBean.getContactPersonsBeans().add(contactPersonBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				supplierBean.getContactPersonsBeans().remove(
						supplierBean.getContactPersonsBeans().size() - 1);
			}
		}
		supplierBean.setContactPersonsBeans(contactPersonBeans);
		model.put("supplierBean", supplierBean);
		model.put("operation", "Add");
		return new ModelAndView("supplier", model);
	}

	@RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
	public ModelAndView saveSupplier(
			@ModelAttribute("supplierBean") @Validated SupplierBean supplierBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if (result.hasErrors()) {
			model.put("supplierBean", supplierBean);
			return new ModelAndView("supplier", model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		Supplier supplier = SupplierUtilities.prepareSupplierModel(supplierBean);
		SupplierOpeningBalance supplierOpeningBalance=null;
		if(supplier.getSupplierId()==0)
		{
			supplierOpeningBalance=new SupplierOpeningBalance();
			supplierOpeningBalance.setFinancialYear(user.getFinYear());
		}
		else
		{
			supplierOpeningBalance=cobService.getSupplierOpeningBalance(user.getFinYear(), supplierBean.getSupplierId());
		}
		supplierService.addSupplier(supplier);
		supplierOpeningBalance.setSupplierId(supplier.getSupplierId());
		supplierOpeningBalance.setOpeningBalance(supplier.getOpeningBalance());
		cobService.addSupplierOpeningBalance(supplierOpeningBalance);
		model.put("message", "Supplier details saved successfully!");
		model.put("suppliers",
				SupplierUtilities.prepareListofSupplierBeans(supplierService.listSuppliers()));
		}
		return new ModelAndView("supplierList", model);
	}

	@RequestMapping(value = "/listSupplier", method = RequestMethod.GET)
	public ModelAndView listSuppliers() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("suppliers",
				SupplierUtilities.prepareListofSupplierBeans(supplierService.listSuppliers()));
		return new ModelAndView("supplierList", model);
	}

	@RequestMapping(value = "/addSupplier", method = RequestMethod.GET)
	public ModelAndView addSupplier() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		SupplierBean supplierBean = new SupplierBean();
		AddressBean address = new AddressBean();
		supplierBean.setCompanyAddressBean(address);
		List<BankAccountBean> bankAccountBean = new LinkedList<BankAccountBean>();
		List<ContactPersonBean> contactPersonBean = new LinkedList<ContactPersonBean>();
		supplierBean.setSupplierAccountsBeans(bankAccountBean);
		supplierBean.setContactPersonsBeans(contactPersonBean);
		model.put("supplierBean", supplierBean);
		return new ModelAndView("supplier", model);
	}

	@RequestMapping(value = "/supplier/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		SupplierBean supplierBean = new SupplierBean();
		model.put("category", "supplier");
		model.put("supplierBean", supplierBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/supplier/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "supplier");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deleteSupplier", method = RequestMethod.GET)
	public ModelAndView deleteSupplier(
			@ModelAttribute("command") SupplierBean supplierBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		Supplier supplier = SupplierUtilities.getSupplierModel(supplierService,
				supplierBean.getSupplierId());
		Address address = supplier.getCompanyAddress();
		List<BankAccount> bankAccounts = supplier.getSupplierAccounts();
		List<ContactPerson> contactPersons = supplier.getContactPersons();
		supplierService.deleteSupplier(supplier);
		bankAccountService.deleteBankAccounts(bankAccounts);
		contactPersonService.deleteContactPersons(contactPersons);
		addressService.deleteAddress(address);
		model.put("supplier", null);
		model.put("suppliers",
				SupplierUtilities.prepareListofSupplierBeans(supplierService.listSuppliers()));
		return new ModelAndView("supplierList", model);
	}

	@RequestMapping(value = "/editSupplier", method = RequestMethod.GET)
	public ModelAndView editSupplier(
			@ModelAttribute("command") SupplierBean supplierBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("supplierBean", SupplierUtilities.prepareSupplierBean(supplierBean.getSupplierId(), supplierService));
		return new ModelAndView("supplier", model);
	}

	
}
