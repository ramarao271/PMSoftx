package org.erp.tarak.purchaseorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
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
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PurchaseOrderItemService purchaseOrderItemService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SupplierService supplierService;

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

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping(value = "/purchaseorderItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("purchaseOrderBean") PurchaseOrderBean purchaseOrderBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<PurchaseOrderItemBean> purchaseOrderItemBeans = purchaseOrderBean
				.getPurchaseOrderItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (purchaseOrderItemBeans == null && size > 0) {
			PurchaseOrderItemBean bankAccountBean = new PurchaseOrderItemBean();
			purchaseOrderItemBeans = new LinkedList<PurchaseOrderItemBean>();
			purchaseOrderItemBeans.add(bankAccountBean);
			purchaseOrderBean.setPurchaseOrderItemBeans(purchaseOrderItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = purchaseOrderItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				PurchaseOrderItemBean bankAccountBean = new PurchaseOrderItemBean();
				purchaseOrderBean.getPurchaseOrderItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				purchaseOrderBean.getPurchaseOrderItemBeans().remove(
						purchaseOrderBean.getPurchaseOrderItemBeans().size() - 1);
			}
		}
		purchaseOrderBean.setPurchaseOrderItemBeans(purchaseOrderItemBeans);
		model.put("purchaseOrderBean", purchaseOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseOrder", model);
	}

	@RequestMapping(value = "/savePurchaseOrder", method = RequestMethod.POST)
	public ModelAndView savePurchaseOrder(
			@ModelAttribute("purchaseOrderBean") @Valid PurchaseOrderBean purchaseOrderBean,
			BindingResult result, Model model,@RequestParam(required=false , value = "Save") String saveFlag , @RequestParam(required=false , value = "SaveDC") String renewFlag) {

		if (result.hasErrors()) {
			model.addAttribute("purchaseOrderBean", purchaseOrderBean);
			return new ModelAndView("purchaseOrder");//, model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			PurchaseOrder purchaseOrder = PurchaseOrderUtilities.preparePurchaseOrderModel(
					purchaseOrderBean, user, supplierService, categoryService,
					measurementService);
			PurchaseOrder savedPurchaseOrder=null;
			if(purchaseOrderBean.getPurchaseOrderId()>0)
			{
				savedPurchaseOrder=PurchaseOrderUtilities.getPurchaseOrderModel(purchaseOrderService, purchaseOrderBean.getPurchaseOrderId(),user.getFinYear());
			}
			purchaseOrderService.addPurchaseOrder(purchaseOrder);
			ProductUtilities.updateProductDetails(purchaseOrderBean,productService,savedPurchaseOrder,ERPConstants.PURCHASE_ORDER,ERPConstants.OP_CREATE);
			if(saveFlag!=null)
			{
				
				model.addAttribute("message",
						"PurchaseOrder details saved successfully!");
				model.addAttribute("purchaseOrders", PurchaseOrderUtilities
						.prepareListofPurchaseOrderBean(
								purchaseOrderService.listPurchaseOrders(user.getFinYear()),
								supplierService));
				
				return new ModelAndView("purchaseOrderList");// , model);
			}
			else
			{
				return new ModelAndView("redirect:../purchaseinvoice/purchaseinvoiceDetails/"+purchaseOrder.getPurchaseOrderId());
			}
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/PurchaseOrderSelectionList", method = RequestMethod.GET)
	public ModelAndView PurchaseOrdersSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			model.put("purchaseOrders", PurchaseOrderUtilities
				.prepareListofPurchaseOrderBean(
						purchaseOrderService.listPendingPurchaseOrders(user.getFinYear()), supplierService));
		}
		return new ModelAndView("PurchaseOrdersSelectionList", model);
	}

	@RequestMapping(value = "/pendingPurchaseorders", method = RequestMethod.GET)
	public ModelAndView listPendingPurchaseOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("type", ERPConstants.PROCESSED);
		model.put("mode",ERPConstants.PENDING);
		model.put("purchaseOrders", PurchaseOrderUtilities
				.prepareListofPurchaseOrderBean(
						purchaseOrderService.listPendingPurchaseOrders(user.getFinYear()), supplierService));
		}
		return new ModelAndView("purchaseOrderList", model);
	}

	@RequestMapping(value = "/processedPurchaseorders", method = RequestMethod.GET)
	public ModelAndView listProcessedPurchaseOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("type", ERPConstants.PENDING);
		model.put("mode",ERPConstants.PROCESSED);
		model.put("purchaseOrders", PurchaseOrderUtilities
				.prepareListofPurchaseOrderBean(
						purchaseOrderService.listProcessedPurchaseOrders(user.getFinYear()), supplierService));
		}
		return new ModelAndView("purchaseOrderList", model);
	}
	@RequestMapping(value = "/addPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrder() {
		Map<String, Object> model = new HashMap<String, Object>();
		PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
		model.put("purchaseOrderBean", purchaseOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseOrder", model);
	}

	@RequestMapping(value = {"/purchaseorder/","/purchaseorder/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "purchaseorder");
		model.put("purchaseOrderBean", purchaseOrderBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listPOSuppliers", method = RequestMethod.GET)
	public @ResponseBody
	String supplierList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String company = request.getParameter("company");
		String search = request.getParameter("name_startsWith");
		Gson g = new Gson();
		String json = "";
		if (company != null && !"".equals(company)) {
			List<SupplierBean> suppliers = SupplierUtilities
					.prepareListofSupplierBeans(supplierService
							.listSuppliersbyCompanyName(company));
			List<supp> sups = new LinkedList<supp>();
			for (SupplierBean supplierBean : suppliers) {
				supp s = new supp(supplierBean.getSupplierId(),
						supplierBean.getCompanyBranch(),
						supplierBean.getCompanyName());
				sups.add(s);
			}
			json = g.toJson(sups);

		} else if (search != null && !"".equals(search)) {
			List<SupplierBean> suppliers = SupplierUtilities
					.prepareListofSupplierBeans(supplierService
							.listSuppliersbyCompanyNameRegex(search));
			json = g.toJson(suppliers);
			model.put("suppliers", json);

		}

		return json;
	}

	@RequestMapping(value = "/deletePurchaseOrder", method = RequestMethod.GET)
	public ModelAndView editPurchaseOrder(
			@ModelAttribute("command") PurchaseOrderBean purchaseOrderBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		PurchaseOrder purchaseOrder = PurchaseOrderUtilities.getPurchaseOrderModel(
				purchaseOrderService, purchaseOrderBean.getPurchaseOrderId(),user.getFinYear());
		List<PurchaseOrderItem> pois = purchaseOrder.getPurchaseOrderItems();
		purchaseOrderService.deletePurchaseOrder(purchaseOrder);
		purchaseOrderItemService.deletePurchaseOrderItems(pois);
		ProductUtilities.updateProductDetails(purchaseOrder,productService,purchaseOrder,ERPConstants.PURCHASE_ORDER,ERPConstants.OP_DELETE);
		model.put("purchaseOrder", null);
		model.put("purchaseOrders", PurchaseOrderUtilities
				.prepareListofPurchaseOrderBean(
						purchaseOrderService.listPendingPurchaseOrders(user.getFinYear()), supplierService));
		model.put("message",
				"PurchaseOrder deleted successfully!");
		}return new ModelAndView("purchaseOrderList", model);
	}

	@RequestMapping(value = "/editPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView deletePurchaseOrder(
			@ModelAttribute("command") PurchaseOrderBean purchaseOrderBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		
		model.put("purchaseOrderBean", PurchaseOrderUtilities.preparePurchaseOrderBean(
				purchaseOrderService.getPurchaseOrder(purchaseOrderBean
						.getPurchaseOrderId(),user.getFinYear()), supplierService));
		model.put("operation", "Update");
		}
		return new ModelAndView("purchaseOrder", model);
	}
	
	@RequestMapping(value = "/purchaseorderupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updatePurchaseOrderItems(
			@ModelAttribute("command") PurchaseOrderBean purchaseOrderBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<PurchaseOrderItemBean> itr=purchaseOrderBean.getPurchaseOrderItemBeans().iterator();
		while(itr.hasNext())
		{
			PurchaseOrderItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("purchaseOrderBean",purchaseOrderBean);
		return new ModelAndView("purchaseOrder", model);
	}
	@RequestMapping(value = "/updatePurchaseOrder/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView updatePurchaseOrder(
			@ModelAttribute("command") PurchaseOrderBean purchaseOrderBean,
			BindingResult result,@PathVariable String type,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		PurchaseOrder purchaseOrder=PurchaseOrderUtilities.getPurchaseOrderModel(purchaseOrderService,id,user.getFinYear());
		List<PurchaseOrderBean> purchaseOrderBeans=null;
		if(ERPConstants.PENDING.equals(type))
		{
			purchaseOrder.setProcessed(false);
			purchaseOrderService.addPurchaseOrder(purchaseOrder);
			purchaseOrderBeans=PurchaseOrderUtilities
				.prepareListofPurchaseOrderBean(
						purchaseOrderService.listProcessedPurchaseOrders(user.getFinYear()), supplierService);
		}
		else if(ERPConstants.PROCESSED.equals(type))
		{
			purchaseOrder.setProcessed(true);
			purchaseOrderService.addPurchaseOrder(purchaseOrder);
			purchaseOrderBeans=PurchaseOrderUtilities
					.prepareListofPurchaseOrderBean(
							purchaseOrderService.listPendingPurchaseOrders(user.getFinYear()), supplierService);
		}
		model.put("purchaseOrders", purchaseOrderBeans);
		model.put("type", type);
		}return new ModelAndView("purchaseOrderList", model);
	}
	
	class supp {
		long supplierId;
		String companyBranch;
		String companyName;

		supp() {
		}

		supp(long supplierId, String supplierName, String companyName) {
			this.supplierId = supplierId;
			this.companyBranch = supplierName;
			this.companyName = companyName;
		}
	}
}
