package org.erp.tarak.salesinvoice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceService;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceUtilities;
import org.erp.tarak.deliverychallan.DeliveryChallan;
import org.erp.tarak.deliverychallan.DeliveryChallanService;
import org.erp.tarak.deliverychallan.DeliveryChallanUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class SalesInvoiceController {

	@Autowired
	private SalesInvoiceService salesInvoiceService;

	@Autowired
	private DeliveryChallanService deliveryChallanService;

	@Autowired
	private SalesInvoiceItemService salesInvoiceItemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;

	@Autowired
	private VariantService variantService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerOpeningBalanceService cobService;

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/salesinvoiceItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("salesInvoiceBean") SalesInvoiceBean salesInvoiceBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<SalesInvoiceItemBean> salesInvoiceItemBeans = salesInvoiceBean
				.getSalesInvoiceItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (salesInvoiceItemBeans == null && size > 0) {
			SalesInvoiceItemBean bankAccountBean = new SalesInvoiceItemBean();
			salesInvoiceItemBeans = new LinkedList<SalesInvoiceItemBean>();
			salesInvoiceItemBeans.add(bankAccountBean);
			salesInvoiceBean.setSalesInvoiceItemBeans(salesInvoiceItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = salesInvoiceItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				SalesInvoiceItemBean bankAccountBean = new SalesInvoiceItemBean();
				salesInvoiceBean.getSalesInvoiceItemBeans()
						.add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				salesInvoiceBean.getSalesInvoiceItemBeans().remove(
						salesInvoiceBean.getSalesInvoiceItemBeans().size() - 1);
			}
		}
		salesInvoiceBean.setSalesInvoiceItemBeans(salesInvoiceItemBeans);
		model.put("salesInvoiceBean", salesInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("salesInvoice", model);
	}

	@RequestMapping(value = "/saveSalesInvoice", method = RequestMethod.POST)
	public ModelAndView saveSalesInvoice(
			@ModelAttribute("salesInvoiceBean") @Validated SalesInvoiceBean salesInvoiceBean,
			BindingResult result, Model model,
			@RequestParam(required = false, value = "Save") String saveFlag,
			@RequestParam(required = false, value = "SaveDC") String renewFlag) {

		if (result.hasErrors()) {
			model.addAttribute("salesInvoiceBean", salesInvoiceBean);
			return new ModelAndView("salesInvoice");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			SalesInvoice salesInvoice = SalesInvoiceUtilities
					.prepareSalesInvoiceModel(salesInvoiceBean, user,
							customerService, categoryService,
							measurementService, deliveryChallanService,
							variantService);
			SalesInvoice savedSalesInvoice = null;
			double savedAmount = 0;
			if (salesInvoice.getSalesInvoiceId() > 0) {
				savedSalesInvoice = salesInvoiceService
						.getSalesInvoice(salesInvoice.getSalesInvoiceId(),user.getFinYear());
				savedAmount = savedSalesInvoice.getTotalCost();
			}
			salesInvoiceService.addSalesInvoice(salesInvoice);
			CustomerOpeningBalanceUtilities.updateCob(cobService, savedAmount,
					salesInvoiceBean.getTotalCost(), salesInvoice
							.getCustomerId().getCustomerId(), salesInvoice
							.getFinYear(), ERPConstants.SALES_INVOICE);
			ProductUtilities.updateProductDetails(salesInvoiceBean,
					productService, savedSalesInvoice,
					ERPConstants.SALES_INVOICE, ERPConstants.OP_CREATE);
			DeliveryChallan deliveryChallan = deliveryChallanService
					.getDeliveryChallan(salesInvoice.getDeliveryChallan()
							.getDeliveryChallanId(),user.getFinYear());
			deliveryChallan.setProcessed(true);
			deliveryChallanService.addDeliveryChallan(deliveryChallan);
			if (saveFlag != null) {
				model.addAttribute("message",
						"SalesInvoice details saved successfully!");

				model.addAttribute("salesInvoices", SalesInvoiceUtilities
						.prepareListofSalesInvoiceBean(
								salesInvoiceService.listSalesInvoices(user.getFinYear()),
								customerService));

				return new ModelAndView("salesInvoiceList");// , model);
			} else {
				return new ModelAndView(
						"redirect:../salesPayment/listsalesInvoices/"
								+ deliveryChallan.getCustomerId()
										.getCustomerId());
			}
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/salesinvoices", method = RequestMethod.GET)
	public ModelAndView listSalesInvoices() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("salesInvoices", SalesInvoiceUtilities
				.prepareListofSalesInvoiceBean(
						salesInvoiceService.listSalesInvoices(user.getFinYear()),
						customerService));
		}
		return new ModelAndView("salesInvoiceList", model);
	}

	@RequestMapping(value = "/addSalesInvoice", method = RequestMethod.GET)
	public ModelAndView addSalesInvoice() {
		Map<String, Object> model = new HashMap<String, Object>();
		SalesInvoiceBean salesInvoiceBean = new SalesInvoiceBean();
		model.put("salesInvoiceBean", salesInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("salesInvoice", model);
	}

	@RequestMapping(value = "/salesinvoice/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		SalesInvoiceBean salesInvoiceBean = new SalesInvoiceBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "salesinvoice");
		model.put("salesInvoiceBean", salesInvoiceBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listSICustomers", method = RequestMethod.GET)
	public @ResponseBody String customerList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
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

	@RequestMapping(value = "/salesinvoice/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "salesinvoice");
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/deleteSalesInvoice", method = RequestMethod.GET)
	public ModelAndView editSalesInvoice(
			@ModelAttribute("command") SalesInvoiceBean salesInvoiceBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		SalesInvoice salesInvoice = SalesInvoiceUtilities.getSalesInvoiceModel(
				salesInvoiceService, salesInvoiceBean.getSalesInvoiceId(),user.getFinYear());
		List<SalesInvoiceItem> pois = salesInvoice.getSalesInvoiceItems();
		salesInvoiceService.deleteSalesInvoice(salesInvoice);
		salesInvoiceItemService.deleteSalesInvoiceItems(pois);
		ProductUtilities.updateProductDetails(salesInvoiceBean, productService,
				salesInvoice, ERPConstants.SALES_INVOICE,
				ERPConstants.OP_DELETE);
		DeliveryChallan deliveryChallan = deliveryChallanService
				.getDeliveryChallan(salesInvoice.getDeliveryChallan()
						.getDeliveryChallanId(),user.getFinYear());
		deliveryChallan.setProcessed(false);
		deliveryChallanService.addDeliveryChallan(deliveryChallan);
		CustomerOpeningBalanceUtilities.updateCob(cobService, 0, -salesInvoice
				.getTotalCost(), salesInvoice.getCustomerId().getCustomerId(),
				salesInvoice.getFinYear(), ERPConstants.SALES_INVOICE);
		
		model.put("salesInvoice", null);
		model.put("salesInvoices", SalesInvoiceUtilities
				.prepareListofSalesInvoiceBean(
						salesInvoiceService.listSalesInvoices(user.getFinYear()),
						customerService));
		model.put("message", "Sales Invoice deleted successfully!");

		}return new ModelAndView("salesInvoiceList", model);
	}

	@RequestMapping(value = "/editSalesInvoice", method = RequestMethod.GET)
	public ModelAndView deleteSalesInvoice(
			@ModelAttribute("command") SalesInvoiceBean salesInvoiceBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("salesInvoiceBean", SalesInvoiceUtilities
				.prepareSalesInvoiceBean(salesInvoiceService
						.getSalesInvoice(salesInvoiceBean.getSalesInvoiceId(),user.getFinYear()),
						customerService));
		model.put("operation", "Update");
		}
		return new ModelAndView("salesInvoice", model);
	}

	@RequestMapping(value = "/salesinvoiceDetails/{poid}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView setOrderDetails(
			@ModelAttribute("salesInvoiceBean") @Validated SalesInvoiceBean salesInvoiceBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			salesInvoiceBean.setDeliveryChallanBean(DeliveryChallanUtilities
					.prepareDeliveryChallanBean(DeliveryChallanUtilities
							.getDeliveryChallanModel(deliveryChallanService,
									poid,user.getFinYear())));
			salesInvoiceBean.setFinYear(user.getFinYear());
			salesInvoiceBean = ERPUtilities.setDCDetailsinSI(salesInvoiceBean,
					deliveryChallanService);
			model.put("salesInvoiceBean", salesInvoiceBean);
			model.put("deliveryChallans", DeliveryChallanUtilities
					.prepareListofDeliveryChallanBean(
							deliveryChallanService.listDeliveryChallans(user.getFinYear()),
							customerService));

			return new ModelAndView("salesInvoice", model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/SalesInvoiceSelectionList", method = RequestMethod.GET)
	public ModelAndView SalesInvoicesSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("salesInvoices", SalesInvoiceUtilities
				.prepareListofSalesInvoiceBean(
						salesInvoiceService.listSalesInvoices(user.getFinYear()),
						customerService));
		}
		return new ModelAndView("SalesInvoicesSelectionList", model);
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
