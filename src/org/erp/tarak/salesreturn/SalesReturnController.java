package org.erp.tarak.salesreturn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceUtilities;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class SalesReturnController {

	@Autowired
	private SalesReturnService salesReturnService;

	@Autowired
	private SalesReturnItemService salesReturnItemService;

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
	private SalesInvoiceService salesInvoiceService;

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

	@RequestMapping(value = "/salesreturnItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("salesReturnBean") SalesReturnBean salesReturnBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<SalesReturnItemBean> salesReturnItemBeans = salesReturnBean
				.getSalesReturnItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (salesReturnItemBeans == null && size > 0) {
			SalesReturnItemBean bankAccountBean = new SalesReturnItemBean();
			salesReturnItemBeans = new LinkedList<SalesReturnItemBean>();
			salesReturnItemBeans.add(bankAccountBean);
			salesReturnBean
					.setSalesReturnItemBeans(salesReturnItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = salesReturnItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				SalesReturnItemBean bankAccountBean = new SalesReturnItemBean();
				salesReturnBean.getSalesReturnItemBeans().add(
						bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				salesReturnBean.getSalesReturnItemBeans().remove(
						salesReturnBean.getSalesReturnItemBeans()
								.size() - 1);
			}
		}
		salesReturnBean
				.setSalesReturnItemBeans(salesReturnItemBeans);
		model.put("salesReturnBean", salesReturnBean);
		model.put("operation", "Add");
		return new ModelAndView("salesReturn", model);
	}

	@RequestMapping(value = "/saveSalesReturn", method = RequestMethod.POST)
	public ModelAndView saveSalesReturn(
			@ModelAttribute("salesReturnBean") @Validated SalesReturnBean salesReturnBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("salesReturnBean", salesReturnBean);
			return new ModelAndView("salesReturn");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			SalesReturn salesReturn = SalesReturnUtilities
					.prepareSalesReturnModel(salesReturnBean, user,
							customerService, categoryService,
							measurementService,salesInvoiceService,variantService);
			SalesReturn savedSalesReturn=null;
			if(salesReturn.getSalesReturnId()>0)
			{
				savedSalesReturn=salesReturnService.getSalesReturn(salesReturn.getSalesReturnId(),user.getFinYear());
			}
			salesReturnService.addSalesReturn(salesReturn);
			ProductUtilities.updateProductDetails(salesReturnBean
					,productService,savedSalesReturn,ERPConstants.SALES_RETURN,ERPConstants.OP_CREATE);
			SalesInvoice salesInvoice=salesInvoiceService.getSalesInvoice(salesReturn.getSalesInvoice().getSalesInvoiceId(),user.getFinYear());
			double rtnAmt=0.0;
			double savedAmount=0;
			if(savedSalesReturn!=null)
				
			{
				rtnAmt=(salesInvoice.getAdjustedAmount()-salesReturn.getTotalCost()+savedSalesReturn.getTotalCost());
				savedAmount=savedSalesReturn.getTotalCost();
			}
			else
			{
				rtnAmt=salesInvoice.getTotalCost()-(salesInvoice.getAdjustedAmount()+salesReturn.getTotalCost());				
			}
			salesInvoice.setAdjustedAmount(rtnAmt);
			salesInvoice.setReturnAmount(salesReturn.getTotalCost());
			CustomerOpeningBalanceUtilities.updateCob(cobService, savedAmount, salesReturn.getTotalCost(), salesReturn.getCustomerId().getCustomerId(), salesReturn.getFinYear(),ERPConstants.SALES_RETURN);
			salesInvoiceService.addSalesInvoice(salesInvoice);
			model.addAttribute("message",
					"SalesReturn details saved successfully!");

			model.addAttribute("salesReturns", SalesReturnUtilities
					.prepareListofSalesReturnBean(
							salesReturnService.listSalesReturns(user.getFinYear()),
							customerService));
			
			return new ModelAndView("salesReturnList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/salesreturns", method = RequestMethod.GET)
	public ModelAndView listSalesReturns() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("salesReturns", SalesReturnUtilities
				.prepareListofSalesReturnBean(
						salesReturnService.listSalesReturns(user.getFinYear()),
						customerService));
		}return new ModelAndView("salesReturnList", model);
	}

	@RequestMapping(value = "/addSalesReturn", method = RequestMethod.GET)
	public ModelAndView addSalesReturn() {
		Map<String, Object> model = new HashMap<String, Object>();
		SalesReturnBean salesReturnBean = new SalesReturnBean();
		model.put("salesReturnBean", salesReturnBean);
		model.put("operation", "Add");
		return new ModelAndView("salesReturn", model);
	}

	@RequestMapping(value = "/salesreturn/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		SalesReturnBean salesReturnBean = new SalesReturnBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "salesreturn");
		model.put("salesReturnBean", salesReturnBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listSRCustomers", method = RequestMethod.GET)
	public @ResponseBody
	String customerList(HttpServletRequest request) {
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
	
	@RequestMapping(value = "/salesreturn/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category","salesreturn");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deleteSalesReturn", method = RequestMethod.GET)
	public ModelAndView deleteSalesReturn(
			@ModelAttribute("command") SalesReturnBean salesReturnBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		SalesReturn salesReturn = SalesReturnUtilities
				.getSalesReturnModel(salesReturnService,
						salesReturnBean.getSalesReturnId(),user.getFinYear());
		List<SalesReturnItem> pois = salesReturn
				.getSalesReturnItems();
		salesReturnService.deleteSalesReturn(salesReturn);
		salesReturnItemService.deleteSalesReturnItems(pois);
		ProductUtilities.updateProductDetails(salesReturnBean
				,productService,salesReturn,ERPConstants.SALES_RETURN,ERPConstants.OP_DELETE);
		SalesInvoice salesInvoice=salesInvoiceService.getSalesInvoice(salesReturn.getSalesInvoice().getSalesInvoiceId(),user.getFinYear());
		double rtnAmt=0.0;
		rtnAmt=-salesReturn.getTotalCost()+salesInvoice.getAdjustedAmount();
		salesInvoice.setAdjustedAmount(rtnAmt);
		salesInvoiceService.addSalesInvoice(salesInvoice);
		CustomerOpeningBalanceUtilities.updateCob(cobService,0, -salesReturn.getTotalCost(), salesReturn.getCustomerId().getCustomerId(), salesReturn.getFinYear(),ERPConstants.SALES_RETURN);
		
		model.put("salesReturn", null);
		model.put("salesReturns", SalesReturnUtilities
				.prepareListofSalesReturnBean(
						salesReturnService.listSalesReturns(user.getFinYear()),
						customerService));
		model.put("message",
				"Sales Return deleted successfully!");
		
		}return new ModelAndView("salesReturnList", model);
	}

	@RequestMapping(value = "/salesreturnupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updateSalesReturnItems(
			@ModelAttribute("command") SalesReturnBean salesReturnBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<SalesReturnItemBean> itr=salesReturnBean.getSalesReturnItemBeans().iterator();
		while(itr.hasNext())
		{
			SalesReturnItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("salesReturnBean",salesReturnBean);
		return new ModelAndView("salesReturn", model);
	}	
	
	
	@RequestMapping(value = "/editSalesReturn", method = RequestMethod.GET)
	public ModelAndView editSalesReturn(
			@ModelAttribute("command") SalesReturnBean salesReturnBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		model.put("salesReturnBean", SalesReturnUtilities
				.prepareSalesReturnBean(salesReturnService
						.getSalesReturn(salesReturnBean
								.getSalesReturnId(),user.getFinYear()), customerService));
		}model.put("operation", "Update");
		return new ModelAndView("salesReturn", model);
	}

	@RequestMapping(value = "/salesreturnDetails/{poid}", method = RequestMethod.POST)
	public ModelAndView setOrderDetails(
			@ModelAttribute("salesReturnBean") @Validated SalesReturnBean salesReturnBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			salesReturnBean
					.setSalesInvoiceBean(SalesInvoiceUtilities
							.prepareSalesInvoiceBean(SalesInvoiceUtilities
									.getSalesInvoiceModel(
											salesInvoiceService, poid,user.getFinYear())));
			salesReturnBean.setFinYear(user.getFinYear());
			salesReturnBean = ERPUtilities.setSIDetailsinSR(
					salesReturnBean, salesInvoiceService);
			model.put("salesReturnBean", salesReturnBean);
			model.put("salesInvoices", SalesInvoiceUtilities
					.prepareListofSalesInvoiceBean(
							salesInvoiceService.listSalesInvoices(user.getFinYear()),
							customerService));

			return new ModelAndView("salesReturn", model);
		} else {
			return new ModelAndView("error");// , model);
		}
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
