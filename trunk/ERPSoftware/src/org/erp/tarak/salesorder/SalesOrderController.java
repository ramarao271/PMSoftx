package org.erp.tarak.salesorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.Customer;
import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class SalesOrderController {

	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	private SalesOrderItemService salesOrderItemService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;

/*	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;
*/
	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping(value = "/salesorderItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("salesOrderBean") SalesOrderBean salesOrderBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<SalesOrderItemBean> salesOrderItemBeans = salesOrderBean
				.getSalesOrderItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (salesOrderItemBeans == null && size > 0) {
			SalesOrderItemBean bankAccountBean = new SalesOrderItemBean();
			salesOrderItemBeans = new LinkedList<SalesOrderItemBean>();
			salesOrderItemBeans.add(bankAccountBean);
			salesOrderBean.setSalesOrderItemBeans(salesOrderItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = salesOrderItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				SalesOrderItemBean bankAccountBean = new SalesOrderItemBean();
				salesOrderBean.getSalesOrderItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				salesOrderBean.getSalesOrderItemBeans().remove(
						salesOrderBean.getSalesOrderItemBeans().size() - 1);
			}
		}
		salesOrderBean.setSalesOrderItemBeans(salesOrderItemBeans);
		model.put("salesOrderBean", salesOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("salesOrder", model);
	}

	@RequestMapping(value = "/saveSalesOrder", method = RequestMethod.POST)
	public ModelAndView saveSalesOrder(
			@ModelAttribute("salesOrderBean") @Valid SalesOrderBean salesOrderBean,
			BindingResult result, Model model,@RequestParam(required=false , value = "Save") String saveFlag , @RequestParam(required=false , value = "SaveDC") String renewFlag) {

		if (result.hasErrors()) {
			model.addAttribute("salesOrderBean", salesOrderBean);
			return new ModelAndView("salesOrder");//, model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			SalesOrder salesOrder = SalesOrderUtilities.prepareSalesOrderModel(
					salesOrderBean, user, customerService, categoryService,
					measurementService);
			SalesOrder savedSalesOrder=null;
			if(salesOrderBean.getSalesOrderId()>0)
			{
				savedSalesOrder=SalesOrderUtilities.getSalesOrderModel(salesOrderService, salesOrderBean.getSalesOrderId(),user.getFinYear());
			}
			salesOrderService.addSalesOrder(salesOrder);
			ProductUtilities.updateProductDetails(salesOrderBean,productService,savedSalesOrder,ERPConstants.SALES_ORDER,ERPConstants.OP_CREATE);
			if(saveFlag!=null)
			{
				
				model.addAttribute("message",
						"SalesOrder details saved successfully!");
				model.addAttribute("salesOrders", SalesOrderUtilities
						.prepareListofSalesOrderBean(
								salesOrderService.listSalesOrders(user.getFinYear()),
								customerService));
				
				return new ModelAndView("salesOrderList");// , model);
			}
			else
			{
				return new ModelAndView("redirect:../deliverychallan/deliverychallanDetails/"+salesOrder.getSalesOrderId());
			}
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/SalesOrderSelectionList", method = RequestMethod.GET)
	public ModelAndView SalesOrdersSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			model.put("salesOrders", SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listPendingSalesOrders(user.getFinYear()), customerService));
		}
		return new ModelAndView("SalesOrdersSelectionList", model);
	}

	@RequestMapping(value = "/pendingSalesorders", method = RequestMethod.GET)
	public ModelAndView listPendingSalesOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("type", ERPConstants.PROCESSED);
		model.put("mode",ERPConstants.PENDING);
		model.put("salesOrders", SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listPendingSalesOrders(user.getFinYear()), customerService));
		}
		return new ModelAndView("salesOrderList", model);
	}

	@RequestMapping(value = "/processedSalesorders", method = RequestMethod.GET)
	public ModelAndView listProcessedSalesOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("type", ERPConstants.PENDING);
		model.put("mode",ERPConstants.PROCESSED);
		model.put("salesOrders", SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listProcessedSalesOrders(user.getFinYear()), customerService));
		}
		return new ModelAndView("salesOrderList", model);
	}
	@RequestMapping(value = "/addSalesOrder", method = RequestMethod.GET)
	public ModelAndView addSalesOrder() {
		Map<String, Object> model = new HashMap<String, Object>();
		SalesOrderBean salesOrderBean = new SalesOrderBean();
		model.put("salesOrderBean", salesOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("salesOrder", model);
	}

	@RequestMapping(value = {"/salesorder/","/salesorder/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		SalesOrderBean salesOrderBean = new SalesOrderBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "salesorder");
		model.put("salesOrderBean", salesOrderBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listSOCustomers", method = RequestMethod.GET)
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
			Set<supp> sups = new HashSet<supp>();
			for (CustomerBean customerBean : customers) {
				supp s = new supp(customerBean.getCustomerId(),
						customerBean.getCompanyBranch(),
						customerBean.getCompanyName());
				sups.add(s);
			}
			json = g.toJson(sups);

		} else if (search != null && !"".equals(search)) {
			List <Customer> customers=customerService
			.listCustomersbyCompanyNameRegex(search);
			Set<CustomerBean> customerSet=new LinkedHashSet<CustomerBean>();
			
			List<CustomerBean> customerBeans = CustomerUtilities
					.prepareListofCustomerBeans(customers);
			customerSet.addAll(customerBeans);
			json = g.toJson(customerSet);
			model.put("customers", json);

		}

		return json;
	}

	@RequestMapping(value = "/deleteSalesOrder", method = RequestMethod.GET)
	public ModelAndView editSalesOrder(
			@ModelAttribute("command") SalesOrderBean salesOrderBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		SalesOrder salesOrder = SalesOrderUtilities.getSalesOrderModel(
				salesOrderService, salesOrderBean.getSalesOrderId(),user.getFinYear());
		List<SalesOrderItem> pois = salesOrder.getSalesOrderItems();
		salesOrderService.deleteSalesOrder(salesOrder);
		salesOrderItemService.deleteSalesOrderItems(pois);
		ProductUtilities.updateProductDetails(salesOrder,productService,salesOrder,ERPConstants.SALES_ORDER,ERPConstants.OP_DELETE);
		model.put("salesOrder", null);
		model.put("salesOrders", SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listPendingSalesOrders(user.getFinYear()), customerService));
		model.put("message",
				"SalesOrder deleted successfully!");
		}return new ModelAndView("salesOrderList", model);
	}

	@RequestMapping(value = "/editSalesOrder", method = RequestMethod.GET)
	public ModelAndView deleteSalesOrder(
			@ModelAttribute("command") SalesOrderBean salesOrderBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		
		model.put("salesOrderBean", SalesOrderUtilities.prepareSalesOrderBean(
				salesOrderService.getSalesOrder(salesOrderBean
						.getSalesOrderId(),user.getFinYear()), customerService));
		model.put("operation", "Update");
		}
		return new ModelAndView("salesOrder", model);
	}
	
	@RequestMapping(value = "/salesorderupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updateSalesOrderItems(
			@ModelAttribute("command") SalesOrderBean salesOrderBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<SalesOrderItemBean> itr=salesOrderBean.getSalesOrderItemBeans().iterator();
		while(itr.hasNext())
		{
			SalesOrderItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("salesOrderBean",salesOrderBean);
		return new ModelAndView("salesOrder", model);
	}
	@RequestMapping(value = "/updateSalesOrder/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView updateSalesOrder(
			@ModelAttribute("command") SalesOrderBean salesOrderBean,
			BindingResult result,@PathVariable String type,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		SalesOrder salesOrder=SalesOrderUtilities.getSalesOrderModel(salesOrderService,id,user.getFinYear());
		List<SalesOrderBean> salesOrderBeans=null;
		if(ERPConstants.PENDING.equals(type))
		{
			salesOrder.setProcessed(false);
			salesOrderService.addSalesOrder(salesOrder);
			salesOrderBeans=SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listProcessedSalesOrders(user.getFinYear()), customerService);
		}
		else if(ERPConstants.PROCESSED.equals(type))
		{
			salesOrder.setProcessed(true);
			salesOrderService.addSalesOrder(salesOrder);
			salesOrderBeans=SalesOrderUtilities
					.prepareListofSalesOrderBean(
							salesOrderService.listPendingSalesOrders(user.getFinYear()), customerService);
		}
		model.put("salesOrders", salesOrderBeans);
		model.put("type", type);
		}return new ModelAndView("salesOrderList", model);
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
