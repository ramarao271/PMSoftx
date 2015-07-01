package org.erp.tarak.deliverychallan;

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
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.salesorder.SalesOrder;
import org.erp.tarak.salesorder.SalesOrderService;
import org.erp.tarak.salesorder.SalesOrderUtilities;
import org.erp.tarak.shipper.ShipperBean;
import org.erp.tarak.shipper.ShipperService;
import org.erp.tarak.shipper.ShipperUtilities;
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
public class DeliveryChallanController {

	@Autowired
	private DeliveryChallanService deliveryChallanService;

	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	private DeliveryChallanItemService deliveryChallanItemService;

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
	private ShipperService shipperService;

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	List <String> deliveryTypes=new LinkedList<String>();
	
	public DeliveryChallanController() {
		deliveryTypes.add("Spot Delivery");
		deliveryTypes.add("Shipper");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/deliverychallanItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("deliveryChallanBean") DeliveryChallanBean deliveryChallanBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
		List<DeliveryChallanItemBean> deliveryChallanItemBeans = deliveryChallanBean
				.getDeliveryChallanItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (deliveryChallanItemBeans == null && size > 0) {
			DeliveryChallanItemBean bankAccountBean = new DeliveryChallanItemBean();
			deliveryChallanItemBeans = new LinkedList<DeliveryChallanItemBean>();
			deliveryChallanItemBeans.add(bankAccountBean);
			deliveryChallanBean
					.setDeliveryChallanItemBeans(deliveryChallanItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = deliveryChallanItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				DeliveryChallanItemBean bankAccountBean = new DeliveryChallanItemBean();
				deliveryChallanBean.getDeliveryChallanItemBeans().add(
						bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				deliveryChallanBean.getDeliveryChallanItemBeans().remove(
						deliveryChallanBean.getDeliveryChallanItemBeans()
								.size() - 1);
			}
		}
		deliveryChallanBean
				.setDeliveryChallanItemBeans(deliveryChallanItemBeans);
		model.put("deliveryChallanBean", deliveryChallanBean);
		model.put("operation", "Add");
		return new ModelAndView("deliveryChallan", model);
	}

	@RequestMapping(value = "/DeliveryChallanSelectionList", method = RequestMethod.GET)
	public ModelAndView DeliveryChallansSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("dTypes", deliveryTypes);
		model.put("deliveryChallans", DeliveryChallanUtilities
				.prepareListofDeliveryChallanBean(
						deliveryChallanService.listPendingDeliveryChallans(user.getFinYear()),
						customerService));
		}
		return new ModelAndView("DeliveryChallansSelectionList", model);
	}

	@RequestMapping(value = "/saveDeliveryChallan", method = RequestMethod.POST)
	public ModelAndView saveDeliveryChallan(
			@ModelAttribute("deliveryChallanBean") @Validated DeliveryChallanBean deliveryChallanBean,
			BindingResult result, Model model,@RequestParam(required=false , value = "Save") String saveFlag , @RequestParam(required=false , value = "SaveDC") String renewFlag) {
		if (result.hasErrors()) {
			model.addAttribute("deliveryChallanBean", deliveryChallanBean);
			return new ModelAndView("deliveryChallan");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			DeliveryChallan deliveryChallan = DeliveryChallanUtilities
					.prepareDeliveryChallanModel(deliveryChallanBean, user,
							customerService, categoryService,
							measurementService, salesOrderService, variantService, shipperService,user.getFinYear());
			DeliveryChallan savedDeliveryChallan=null;
			if(deliveryChallan.getDeliveryChallanId()>0)
			{
				savedDeliveryChallan=deliveryChallanService.getDeliveryChallan(deliveryChallan.getDeliveryChallanId(),user.getFinYear());
			}
			
			deliveryChallanService.addDeliveryChallan(deliveryChallan);
			ProductUtilities.updateProductDetails(deliveryChallanBean,productService,savedDeliveryChallan,ERPConstants.DELIVERY_CHALLAN,ERPConstants.OP_CREATE);
			SalesOrderUtilities.updateSalesOrderStatus(deliveryChallan.getSalesOrder().getSalesOrderId(),deliveryChallan.getDeliveryChallanItems(),salesOrderService,user.getFinYear());
			if(saveFlag!=null)
			{
				model.addAttribute("message",
						"DeliveryChallan details saved successfully!");

				model.addAttribute("deliveryChallans", DeliveryChallanUtilities
						.prepareListofDeliveryChallanBean(
								deliveryChallanService.listDeliveryChallans(user.getFinYear()),
								customerService));
				model.addAttribute("dTypes", deliveryTypes);
				return new ModelAndView("deliveryChallanList");// , model);
			}
			else
			{
				return new ModelAndView("redirect:../salesinvoice/salesinvoiceDetails/"+deliveryChallan.getDeliveryChallanId());
			}
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/pendingDeliverychallans", method = RequestMethod.GET)
	public ModelAndView listPendingDeliveryChallans() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("deliveryChallans", DeliveryChallanUtilities
				.prepareListofDeliveryChallanBean(
						deliveryChallanService.listPendingDeliveryChallans(user.getFinYear()), customerService));
		}return new ModelAndView("deliveryChallanList", model);
	}

	@RequestMapping(value = "/processedDeliverychallans", method = RequestMethod.GET)
	public ModelAndView listProcessedDeliveryChallans() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("deliveryChallans", DeliveryChallanUtilities
				.prepareListofDeliveryChallanBean(
						deliveryChallanService.listProcessedDeliveryChallans(user.getFinYear()), customerService));
		}
		return new ModelAndView("deliveryChallanList", model);
	}

	@RequestMapping(value = "/deliverychallanupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView deleteDeliveryChallanItems(@ModelAttribute("deliveryChallanBean") @Validated DeliveryChallanBean deliveryChallanBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
		Iterator<DeliveryChallanItemBean> itr=deliveryChallanBean.getDeliveryChallanItemBeans().iterator();
		while(itr.hasNext())
		{
			DeliveryChallanItemBean dcib=itr.next();
			if(dcib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("deliveryChallanBean", deliveryChallanBean);
		model.put("operation", "Add");
		return new ModelAndView("deliveryChallan", model);
	}	
	
	@RequestMapping(value = "/addDeliveryChallan", method = RequestMethod.GET)
	public ModelAndView addDeliveryChallan() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
		DeliveryChallanBean deliveryChallanBean = new DeliveryChallanBean();
		model.put("deliveryChallanBean", deliveryChallanBean);
		model.put("operation", "Add");
		return new ModelAndView("deliveryChallan", model);
	}

	@RequestMapping(value = {"/deliverychallan/","/deliverychallan/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		DeliveryChallanBean deliveryChallanBean = new DeliveryChallanBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
		model.put("category", "deliverychallan");
		model.put("deliveryChallanBean", deliveryChallanBean);
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/listDCCustomers", method = RequestMethod.GET)
	public @ResponseBody
	String customerList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
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

	@RequestMapping(value = "/listDCShippers", method = RequestMethod.GET)
	public @ResponseBody
	String shipperList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dTypes", deliveryTypes);
		String company = request.getParameter("company");
		String search = request.getParameter("name_startsWith");
		Gson g = new Gson();
		String json = "";
		if (company != null && !"".equals(company)) {
			List<ShipperBean> shippers = ShipperUtilities
					.prepareListofShipperBeans(shipperService
							.listShippersbyCompanyName(company));
			List<supp> sups = new LinkedList<supp>();
			for (ShipperBean shipperBean : shippers) {
				supp s = new supp(shipperBean.getShipperId(),
						shipperBean.getCompanyBranch(),
						shipperBean.getCompanyName());
				sups.add(s);
			}
			json = g.toJson(sups);

		} else if (search != null && !"".equals(search)) {
			List<ShipperBean> shippers = ShipperUtilities
					.prepareListofShipperBeans(shipperService
							.listShippersbyCompanyNameRegex(search));
			json = g.toJson(shippers);
			model.put("shippers", json);

		}

		return json;
	}
	
	@RequestMapping(value = "/deleteDeliveryChallan", method = RequestMethod.GET)
	public ModelAndView editDeliveryChallan(
			@ModelAttribute("command") DeliveryChallanBean deliveryChallanBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		DeliveryChallan deliveryChallan = DeliveryChallanUtilities
				.getDeliveryChallanModel(deliveryChallanService,
						deliveryChallanBean.getDeliveryChallanId(),user.getFinYear());
		List<DeliveryChallanItem> pois = deliveryChallan
				.getDeliveryChallanItems();
		deliveryChallanService.deleteDeliveryChallan(deliveryChallan);
		deliveryChallanItemService.deleteDeliveryChallanItems(pois);
		ProductUtilities.updateProductDetails(deliveryChallanBean,productService,deliveryChallan,ERPConstants.DELIVERY_CHALLAN,ERPConstants.OP_DELETE);
		SalesOrder salesOrder=salesOrderService.getSalesOrder(deliveryChallan.getSalesOrder().getSalesOrderId(),user.getFinYear());
		salesOrder.setProcessed(false);
		salesOrderService.addSalesOrder(salesOrder);
		
		model.put("dTypes", deliveryTypes);
		model.put("deliveryChallan", null);
		model.put("deliveryChallans", DeliveryChallanUtilities
				.prepareListofDeliveryChallanBean(
						deliveryChallanService.listDeliveryChallans(user.getFinYear()),
						customerService));
		model.put("message",
				"Delivery Challan deleted successfully!");
		}return new ModelAndView("deliveryChallanList", model);
	}

	@RequestMapping(value = "/editDeliveryChallan", method = RequestMethod.GET)
	public ModelAndView deleteDeliveryChallan(
			@ModelAttribute("command") DeliveryChallanBean deliveryChallanBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("dTypes", deliveryTypes);
		model.put("deliveryChallanBean", DeliveryChallanUtilities
				.prepareDeliveryChallanBean(deliveryChallanService
						.getDeliveryChallan(deliveryChallanBean
								.getDeliveryChallanId(),user.getFinYear()), customerService));
		model.put("operation", "Update");
		}return new ModelAndView("deliveryChallan", model);
	}

	@RequestMapping(value = "/deliverychallanDetails/{poid}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView setOrderDetails(
			@ModelAttribute("deliveryChallanBean") @Validated DeliveryChallanBean deliveryChallanBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dTypes", deliveryTypes);
			deliveryChallanBean.setSalesOrderBean(SalesOrderUtilities
					.prepareSalesOrderBean(SalesOrderUtilities
							.getSalesOrderModel(salesOrderService, poid,user.getFinYear())));
			deliveryChallanBean.setFinYear(user.getFinYear());
			deliveryChallanBean = ERPUtilities.setSODetailsinDC(
					deliveryChallanBean, salesOrderService);
			model.put("deliveryChallanBean", deliveryChallanBean);
			model.put("salesOrders", SalesOrderUtilities
					.prepareListofSalesOrderBean(
							salesOrderService.listSalesOrders(user.getFinYear()),
							customerService));

			return new ModelAndView("deliveryChallan", model);
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
