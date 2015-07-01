package org.erp.tarak.salesPayment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.company.CompanyBean;
import org.erp.tarak.company.CompanyService;
import org.erp.tarak.company.CompanyUtilities;
import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceService;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceUtilities;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class SalesPaymentController {

	@Autowired
	private SalesPaymentService salesPaymentService;

	@Autowired
	private SalesPaymentItemService salesPaymentItemService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private SalesInvoiceService salesInvoiceService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	@Autowired
	private CustomerOpeningBalanceService cobService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/salesPaymentItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("salesPaymentBean") SalesPaymentBean salesPaymentBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<SalesPaymentItemBean> salesPaymentItemBeans = salesPaymentBean
				.getSalesPaymentItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (salesPaymentItemBeans == null && size > 0) {
			SalesPaymentItemBean bankAccountBean = new SalesPaymentItemBean();
			salesPaymentItemBeans = new LinkedList<SalesPaymentItemBean>();
			salesPaymentItemBeans.add(bankAccountBean);
			salesPaymentBean.setSalesPaymentItemBeans(salesPaymentItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = salesPaymentItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				SalesPaymentItemBean bankAccountBean = new SalesPaymentItemBean();
				salesPaymentBean.getSalesPaymentItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				salesPaymentBean.getSalesPaymentItemBeans().remove(
						salesPaymentBean.getSalesPaymentItemBeans().size() - 1);
			}
		}
		salesPaymentBean.setSalesPaymentItemBeans(salesPaymentItemBeans);
		model.put("salesPaymentBean", salesPaymentBean);
		model.put("operation", "Add");
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		return new ModelAndView("salesPayment", model);
	}

	@RequestMapping(value = "/saveSalesPayment", method = RequestMethod.POST)
	public ModelAndView saveSalesPayment(
			@ModelAttribute("salesPaymentBean") @Validated SalesPaymentBean salesPaymentBean,
			BindingResult result, Model model) {
		model.addAttribute("pTypes", SalesPaymentUtilities.paymentModes);
		if (result.hasErrors()) {
			model.addAttribute("salesPaymentBean", salesPaymentBean);
			return new ModelAndView("salesPayment");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			SalesPaymentBean spBean=(SalesPaymentBean) session.getAttribute("spBean");
			SalesPayment salesPayment = SalesPaymentUtilities.prepareSalesPaymentModelFromSession(
					salesPaymentBean,spBean,user);
			SalesPayment savedSalesPayment=null;
			double savedAmount=0;
			if(salesPaymentBean.getSalesPaymentId()>0)
			{
				savedSalesPayment=SalesPaymentUtilities.getSalesPaymentModel(salesPaymentService, salesPaymentBean.getSalesPaymentId(),user.getFinYear());
				savedAmount=savedSalesPayment.getTotalCost();
			}
			salesPaymentService.addSalesPayment(salesPayment);
			SalesPaymentUtilities.updateSalesInvoiceAmount(salesPayment.getSalesPaymentItems(),savedSalesPayment, salesInvoiceService,ERPConstants.OP_CREATE,user.getFinYear());
			CustomerOpeningBalanceUtilities.updateCob(cobService, savedAmount, salesPayment.getTotalCost(), salesPayment.getCustomerId().getCustomerId(), salesPayment.getFinYear(),ERPConstants.SALES_PAYMENT);
			if(ERPConstants.PM_CASH.equals(salesPayment.getPaymentMode()))
			{
				CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
				companyBean.setBalance(companyBean.getBalance()+salesPayment.getTotalCost());
				companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
			}

			model.addAttribute("message",
					"SalesPayment details saved successfully!");
			model.addAttribute("salesPayments", SalesPaymentUtilities
					.prepareListofSalesPaymentBean(
							salesPaymentService.listSalesPayments(user.getFinYear()),
							customerService));
			return new ModelAndView("salesPaymentList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/SalesPaymentSelectionList", method = RequestMethod.GET)
	public ModelAndView SalesPaymentsSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		model.put("salesPayments", SalesPaymentUtilities
				.prepareListofSalesPaymentBean(
						salesPaymentService.listSalesPayments(user.getFinYear()), customerService));
		}return new ModelAndView("SalesPaymentsSelectionList", model);
	}

	@RequestMapping(value = "/salesPayments", method = RequestMethod.GET)
	public ModelAndView listSalesPayments() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("salesPayments", SalesPaymentUtilities
				.prepareListofSalesPaymentBean(
						salesPaymentService.listSalesPayments(user.getFinYear()), customerService));
		}return new ModelAndView("salesPaymentList", model);
	}

	@RequestMapping(value = "/addSalesPayment", method = RequestMethod.GET)
	public ModelAndView addSalesPayment() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		SalesPaymentBean salesPaymentBean = new SalesPaymentBean();
		model.put("salesPaymentBean", salesPaymentBean);
		model.put("operation", "Add");
		return new ModelAndView("salesPayment", model);
	}
	
	@RequestMapping(value = "/listsalesInvoices/{customerId}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView listSalesInvoices(@ModelAttribute("salesPaymentBean") @Validated SalesPaymentBean salesPaymentBean,
			BindingResult result, @PathVariable long customerId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		List<SalesInvoiceBean> lsib=SalesInvoiceUtilities.prepareListofSalesInvoiceBean(salesInvoiceService.listSalesInvoicesByCustomer(customerId,user.getFinYear()));
		model.put("salesInvoiceBeans",lsib );
		salesPaymentBean.setCustomerBean(lsib.get(0).getCustomerBean());
		model.put("salesPaymentBean", salesPaymentBean);
		session.setAttribute("spBean", salesPaymentBean);
		}return new ModelAndView("salesPayment", model);
	}

	@RequestMapping(value = "/salesPayment/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		SalesPaymentBean salesPaymentBean = new SalesPaymentBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		model.put("category", "salesPayment");
		model.put("salesPaymentBean", salesPaymentBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listSPCustomers", method = RequestMethod.GET)
	public @ResponseBody
	String customerList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		String company = request.getParameter("company");
		String search = request.getParameter("name_startsWith");
		Gson g = new Gson();
		String json = "";
		if (company != null && !"".equals(company)) {
			List<CustomerBean> customers = CustomerUtilities
					.prepareListofCustomerBeans(customerService
							.listCustomersbyCompanyName(company));
			List<supp> sups = new LinkedList<supp>();
			for (CustomerBean customerBean : customers) {
				supp s = new supp(customerBean.getCustomerId(),
						customerBean.getCompanyBranch(),
						customerBean.getCompanyName());
				sups.add(s);
			}
			json = g.toJson(sups);

		} else if (search != null && !"".equals(search)) {
			List<CustomerBean> customers = CustomerUtilities
					.prepareListofCustomerBeans(customerService
							.listCustomersbyCompanyNameRegex(search));
			json = g.toJson(customers);
			model.put("customers", json);

		}

		return json;
	}

	@RequestMapping(value = "/salesPayment/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category","salespayment");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/deleteSalesPayment", method = RequestMethod.GET)
	public ModelAndView editSalesPayment(
			@ModelAttribute("command") SalesPaymentBean salesPaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		SalesPayment salesPayment = SalesPaymentUtilities.getSalesPaymentModel(
				salesPaymentService, salesPaymentBean.getSalesPaymentId(),user.getFinYear());
		List<SalesPaymentItem> pois = salesPayment.getSalesPaymentItems();
		salesPaymentService.deleteSalesPayment(salesPayment);
		salesPaymentItemService.deleteSalesPaymentItems(pois);
		SalesPaymentUtilities.updateSalesInvoiceAmount(salesPayment.getSalesPaymentItems(),salesPayment, salesInvoiceService,ERPConstants.OP_DELETE,user.getFinYear());
		CustomerOpeningBalanceUtilities.updateCob(cobService, 0, -salesPayment.getTotalCost(), salesPayment.getCustomerId().getCustomerId(), salesPayment.getFinYear(),ERPConstants.SALES_PAYMENT);
		if(ERPConstants.PM_CASH.equals(salesPayment.getPaymentMode()))
		{
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			companyBean.setBalance(companyBean.getBalance()-salesPayment.getTotalCost());
			companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
		}
		
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		model.put("salesPayment", null);
		model.put("salesPayments", SalesPaymentUtilities
				.prepareListofSalesPaymentBean(
						salesPaymentService.listSalesPayments(user.getFinYear()), customerService));
		}
		return new ModelAndView("salesPaymentList", model);
	}

	@RequestMapping(value = "/editSalesPayment", method = RequestMethod.GET)
	public ModelAndView deleteSalesPayment(
			@ModelAttribute("command") SalesPaymentBean salesPaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", SalesPaymentUtilities.paymentModes);
		
		SalesPaymentBean spb=SalesPaymentUtilities.prepareSalesPaymentBean(
				salesPaymentService.getSalesPayment(salesPaymentBean
						.getSalesPaymentId(),user.getFinYear()), customerService);
		model.put("salesInvoiceBeans", SalesInvoiceUtilities.prepareListofSalesInvoiceBean(salesInvoiceService.listSalesInvoicesByCustomer(spb.getCustomerBean().getCustomerId(),user.getFinYear())));
		model.put("salesPaymentBean", spb);
		model.put("operation", "Update");
		}return new ModelAndView("salesPayment", model);
	}

	class supp {
		long customerId;
		String companyBranch;
		String companyName;

		supp() {

		}

		supp(long customerId, String customerName, String companyName) {
			this.customerId = customerId;
			this.companyBranch = customerName;
			this.companyName = companyName;
		}

	}

}
