package org.erp.tarak.productionorder;

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
import org.erp.tarak.worker.WorkerBean;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.rawMaterial.RawMaterialService;
import org.erp.tarak.rawMaterial.RawMaterialUtilities;
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
public class ProductionOrderController {

	@Autowired
	private ProductionOrderService productionOrderService;

	@Autowired
	private ProductionOrderItemService productionOrderItemService;

	@Autowired
	private RawMaterialService rawMaterialService;
	
	@Autowired
	private WorkerService workerService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;

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
	
	@RequestMapping(value = "/productionorderItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("productionOrderBean") ProductionOrderBean productionOrderBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<ProductionOrderItemBean> productionOrderItemBeans = productionOrderBean
				.getProductionOrderItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (productionOrderItemBeans == null && size > 0) {
			ProductionOrderItemBean bankAccountBean = new ProductionOrderItemBean();
			productionOrderItemBeans = new LinkedList<ProductionOrderItemBean>();
			productionOrderItemBeans.add(bankAccountBean);
			productionOrderBean.setProductionOrderItemBeans(productionOrderItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = productionOrderItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				ProductionOrderItemBean bankAccountBean = new ProductionOrderItemBean();
				productionOrderBean.getProductionOrderItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				productionOrderBean.getProductionOrderItemBeans().remove(
						productionOrderBean.getProductionOrderItemBeans().size() - 1);
			}
		}
		productionOrderBean.setProductionOrderItemBeans(productionOrderItemBeans);
		model.put("productionOrderBean", productionOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("productionOrder", model);
	}

	@RequestMapping(value = "/saveProductionOrder", method = RequestMethod.POST)
	public ModelAndView saveProductionOrder(
			@ModelAttribute("productionOrderBean") @Validated ProductionOrderBean productionOrderBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("productionOrderBean", productionOrderBean);
			return new ModelAndView("productionOrder");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			ProductionOrder productionOrder = ProductionOrderUtilities.prepareProductionOrderModel(
					productionOrderBean, user, workerService, categoryService,
					measurementService);
			ProductionOrder savedProductionOrder=null;
			if(productionOrderBean.getProductionOrderId()>0)
			{
				savedProductionOrder=ProductionOrderUtilities.getProductionOrderModel(productionOrderService, productionOrderBean.getProductionOrderId());
			}
			productionOrderService.addProductionOrder(productionOrder);
			
			RawMaterialUtilities.updateRawMaterialDetails(productionOrderBean,rawMaterialService,savedProductionOrder,ERPConstants.SALES_ORDER,ERPConstants.OP_CREATE);
			model.addAttribute("message",
					"ProductionOrder details saved successfully!");
			model.addAttribute("productionOrders", ProductionOrderUtilities
					.prepareListofProductionOrderBean(
							productionOrderService.listProductionOrders(),
							workerService));
			return new ModelAndView("productionOrderList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/ProductionOrderSelectionList", method = RequestMethod.GET)
	public ModelAndView ProductionOrdersSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionOrders", ProductionOrderUtilities
				.prepareListofProductionOrderBean(
						productionOrderService.listPendingProductionOrders(), workerService));
		return new ModelAndView("ProductionOrdersSelectionList", model);
	}

	@RequestMapping(value = "/pendingProductionorders", method = RequestMethod.GET)
	public ModelAndView listPendingProductionOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PROCESSED);
		model.put("mode",ERPConstants.PENDING);
		model.put("productionOrders", ProductionOrderUtilities
				.prepareListofProductionOrderBean(
						productionOrderService.listPendingProductionOrders(), workerService));
		return new ModelAndView("productionOrderList", model);
	}

	@RequestMapping(value = "/processedProductionorders", method = RequestMethod.GET)
	public ModelAndView listProcessedProductionOrders() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PENDING);
		model.put("mode",ERPConstants.PROCESSED);
		model.put("productionOrders", ProductionOrderUtilities
				.prepareListofProductionOrderBean(
						productionOrderService.listProcessedProductionOrders(), workerService));
		return new ModelAndView("productionOrderList", model);
	}
	@RequestMapping(value = "/addProductionOrder", method = RequestMethod.GET)
	public ModelAndView addProductionOrder() {
		Map<String, Object> model = new HashMap<String, Object>();
		ProductionOrderBean productionOrderBean = new ProductionOrderBean();
		model.put("productionOrderBean", productionOrderBean);
		model.put("operation", "Add");
		return new ModelAndView("productionOrder", model);
	}

	@RequestMapping(value = {"/productionorder/","/productionorder/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		ProductionOrderBean productionOrderBean = new ProductionOrderBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "productionorder");
		model.put("productionOrderBean", productionOrderBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listSOWorkers", method = RequestMethod.GET)
	public @ResponseBody
	String workerList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String company = request.getParameter("company");
		String search = request.getParameter("name_startsWith");
		Gson g = new Gson();
		String json = "";
		if (company != null && !"".equals(company)) {
			List<WorkerBean> workers = WorkerUtilities
					.prepareListofWorkerBeans(workerService
							.listWorkersbyCompanyName(company));
			List<supp> sups = new LinkedList<supp>();
			for (WorkerBean workerBean : workers) {
				supp s = new supp(workerBean.getWorkerId(),
						workerBean.getWorkerName());
				sups.add(s);
			}
			json = g.toJson(sups);

		} else if (search != null && !"".equals(search)) {
			List<WorkerBean> workers = WorkerUtilities
					.prepareListofWorkerBeans(workerService
							.listWorkersbyCompanyNameRegex(search));
			json = g.toJson(workers);
			model.put("workers", json);

		}

		return json;
	}

	@RequestMapping(value = "/deleteProductionOrder", method = RequestMethod.GET)
	public ModelAndView editProductionOrder(
			@ModelAttribute("command") ProductionOrderBean productionOrderBean,
			BindingResult result) {
		ProductionOrder productionOrder = ProductionOrderUtilities.getProductionOrderModel(
				productionOrderService, productionOrderBean.getProductionOrderId());
		List<ProductionOrderItem> pois = productionOrder.getProductionOrderItems();
		productionOrderService.deleteProductionOrder(productionOrder);
		productionOrderItemService.deleteProductionOrderItems(pois);
		RawMaterialUtilities.updateRawMaterialDetails(productionOrder,rawMaterialService,productionOrder,ERPConstants.SALES_ORDER,ERPConstants.OP_DELETE);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionOrder", null);
		model.put("productionOrders", ProductionOrderUtilities
				.prepareListofProductionOrderBean(
						productionOrderService.listPendingProductionOrders(), workerService));
		model.put("message",
				"ProductionOrder deleted successfully!");
		return new ModelAndView("productionOrderList", model);
	}

	@RequestMapping(value = "/editProductionOrder", method = RequestMethod.GET)
	public ModelAndView deleteProductionOrder(
			@ModelAttribute("command") ProductionOrderBean productionOrderBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionOrderBean", ProductionOrderUtilities.prepareProductionOrderBean(
				productionOrderService.getProductionOrder(productionOrderBean
						.getProductionOrderId()), workerService));
		model.put("operation", "Update");
		return new ModelAndView("productionOrder", model);
	}
	
	@RequestMapping(value = "/productionorderupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updateProductionOrderItems(
			@ModelAttribute("command") ProductionOrderBean productionOrderBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<ProductionOrderItemBean> itr=productionOrderBean.getProductionOrderItemBeans().iterator();
		while(itr.hasNext())
		{
			ProductionOrderItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("productionOrderBean",productionOrderBean);
		return new ModelAndView("productionOrder", model);
	}
	@RequestMapping(value = "/updateProductionOrder/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView updateProductionOrder(
			@ModelAttribute("command") ProductionOrderBean productionOrderBean,
			BindingResult result,@PathVariable String type,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		ProductionOrder productionOrder=ProductionOrderUtilities.getProductionOrderModel(productionOrderService,id);
		List<ProductionOrderBean> productionOrderBeans=null;
		if(ERPConstants.PENDING.equals(type))
		{
			productionOrder.setProcessed(false);
			productionOrderService.addProductionOrder(productionOrder);
			productionOrderBeans=ProductionOrderUtilities
				.prepareListofProductionOrderBean(
						productionOrderService.listProcessedProductionOrders(), workerService);
		}
		else if(ERPConstants.PROCESSED.equals(type))
		{
			productionOrder.setProcessed(true);
			productionOrderService.addProductionOrder(productionOrder);
			productionOrderBeans=ProductionOrderUtilities
					.prepareListofProductionOrderBean(
							productionOrderService.listPendingProductionOrders(), workerService);
		}
		model.put("productionOrders", productionOrderBeans);
		model.put("type", type);
		return new ModelAndView("productionOrderList", model);
	}
	
	class supp {
		long workerId;
		String workerName;
		supp() {
		}

		supp(long workerId, String workerName) {
			this.workerId = workerId;
			this.workerName = workerName;
		}
	}
}
