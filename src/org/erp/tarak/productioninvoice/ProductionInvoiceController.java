package org.erp.tarak.productioninvoice;

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
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.productionorder.ProductionOrderService;
import org.erp.tarak.productionorder.ProductionOrderUtilities;
import org.erp.tarak.rawMaterial.RawMaterialService;
import org.erp.tarak.rawMaterial.RawMaterialUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.WorkerBean;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;
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
public class ProductionInvoiceController {

	@Autowired
	private ProductionInvoiceService productionInvoiceService;

	@Autowired
	private ProductionOrderService productionOrderService;

	
	@Autowired
	private ProductionInvoiceItemService productionInvoiceItemService;

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
	
	@RequestMapping(value = "/productioninvoiceItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("productionInvoiceBean") ProductionInvoiceBean productionInvoiceBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		productionInvoiceBean
		.setProductionOrderBean(ProductionOrderUtilities
				.prepareProductionOrderBean(ProductionOrderUtilities
						.getProductionOrderModel(
								productionOrderService, productionInvoiceBean.getProductionOrderBean().getProductionOrderId())));
		List<ProductionInvoiceItemBean> productionInvoiceItemBeans = productionInvoiceBean
				.getProductionInvoiceItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (productionInvoiceItemBeans == null && size > 0) {
			ProductionInvoiceItemBean bankAccountBean = new ProductionInvoiceItemBean();
			productionInvoiceItemBeans = new LinkedList<ProductionInvoiceItemBean>();
			productionInvoiceItemBeans.add(bankAccountBean);
			productionInvoiceBean.setProductionInvoiceItemBeans(productionInvoiceItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = productionInvoiceItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				ProductionInvoiceItemBean bankAccountBean = new ProductionInvoiceItemBean();
				productionInvoiceBean.getProductionInvoiceItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				productionInvoiceBean.getProductionInvoiceItemBeans().remove(
						productionInvoiceBean.getProductionInvoiceItemBeans().size() - 1);
			}
		}
		productionInvoiceBean.setProductionInvoiceItemBeans(productionInvoiceItemBeans);
		model.put("productionInvoiceBean", productionInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("productionInvoice", model);
	}
	@RequestMapping(value = "/productioninvoices", method = RequestMethod.GET)
	public ModelAndView listProductionInvoices() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionInvoices", ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listProductionInvoices(),
						workerService));
		return new ModelAndView("productionInvoiceList", model);
	}

	@RequestMapping(value = "/saveProductionInvoice", method = RequestMethod.POST)
	public ModelAndView saveProductionInvoice(
			@ModelAttribute("productionInvoiceBean") @Validated ProductionInvoiceBean productionInvoiceBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("productionInvoiceBean", productionInvoiceBean);
			return new ModelAndView("productionInvoice");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			ProductionInvoice productionInvoice = ProductionInvoiceUtilities.prepareProductionInvoiceModel(
					productionInvoiceBean, user, workerService, categoryService,
					measurementService,productionOrderService);
			ProductionInvoice savedProductionInvoice=null;
			if(productionInvoiceBean.getProductionInvoiceId()>0)
			{
				savedProductionInvoice=ProductionInvoiceUtilities.getProductionInvoiceModel(productionInvoiceService, productionInvoiceBean.getProductionInvoiceId());
			}
			productionInvoiceService.addProductionInvoice(productionInvoice);
			
			RawMaterialUtilities.updateRawMaterialDetails(productionInvoiceBean,rawMaterialService,savedProductionInvoice,ERPConstants.SALES_ORDER,ERPConstants.OP_CREATE);
			model.addAttribute("message",
					"ProductionInvoice details saved successfully!");
			model.addAttribute("productionInvoices", ProductionInvoiceUtilities
					.prepareListofProductionInvoiceBean(
							productionInvoiceService.listProductionInvoices(),
							workerService));
			return new ModelAndView("productionInvoiceList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/ProductionInvoiceSelectionList", method = RequestMethod.GET)
	public ModelAndView ProductionInvoicesSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionInvoices", ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listPendingProductionInvoices(), workerService));
		return new ModelAndView("ProductionInvoicesSelectionList", model);
	}

	@RequestMapping(value = "/pendingProductioninvoices", method = RequestMethod.GET)
	public ModelAndView listPendingProductionInvoices() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PROCESSED);
		model.put("mode",ERPConstants.PENDING);
		model.put("productionInvoices", ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listPendingProductionInvoices(), workerService));
		return new ModelAndView("productionInvoiceList", model);
	}

	@RequestMapping(value = "/processedProductioninvoices", method = RequestMethod.GET)
	public ModelAndView listProcessedProductionInvoices() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PENDING);
		model.put("mode",ERPConstants.PROCESSED);
		model.put("productionInvoices", ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listProcessedProductionInvoices(), workerService));
		return new ModelAndView("productionInvoiceList", model);
	}
	@RequestMapping(value = "/addProductionInvoice", method = RequestMethod.GET)
	public ModelAndView addProductionInvoice() {
		Map<String, Object> model = new HashMap<String, Object>();
		ProductionInvoiceBean productionInvoiceBean = new ProductionInvoiceBean();
		model.put("productionInvoiceBean", productionInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("productionInvoice", model);
	}

	@RequestMapping(value = {"/productioninvoice/","/productioninvoice/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		ProductionInvoiceBean productionInvoiceBean = new ProductionInvoiceBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "productioninvoice");
		model.put("productionInvoiceBean", productionInvoiceBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listPIWorkers", method = RequestMethod.GET)
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

	@RequestMapping(value = "/deleteProductionInvoice", method = RequestMethod.GET)
	public ModelAndView editProductionInvoice(
			@ModelAttribute("command") ProductionInvoiceBean productionInvoiceBean,
			BindingResult result) {
		ProductionInvoice productionInvoice = ProductionInvoiceUtilities.getProductionInvoiceModel(
				productionInvoiceService, productionInvoiceBean.getProductionInvoiceId());
		List<ProductionInvoiceItem> pois = productionInvoice.getProductionInvoiceItems();
		productionInvoiceService.deleteProductionInvoice(productionInvoice);
		productionInvoiceItemService.deleteProductionInvoiceItems(pois);
		RawMaterialUtilities.updateRawMaterialDetails(productionInvoice,rawMaterialService,productionInvoice,ERPConstants.SALES_ORDER,ERPConstants.OP_DELETE);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productionInvoice", null);
		model.put("productionInvoices", ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listPendingProductionInvoices(), workerService));
		model.put("message",
				"ProductionInvoice deleted successfully!");
		return new ModelAndView("productionInvoiceList", model);
	}

	@RequestMapping(value = "/editProductionInvoice", method = RequestMethod.GET)
	public ModelAndView deleteProductionInvoice(
			@ModelAttribute("command") ProductionInvoiceBean productionInvoiceBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		productionInvoiceBean
		.setProductionOrderBean(ProductionOrderUtilities
				.prepareProductionOrderBean(ProductionOrderUtilities
						.getProductionOrderModel(
								productionOrderService, productionInvoiceBean.getProductionOrderBean().getProductionOrderId())));
		model.put("productionInvoiceBean", ProductionInvoiceUtilities.prepareProductionInvoiceBean(
				productionInvoiceService.getProductionInvoice(productionInvoiceBean
						.getProductionInvoiceId()), workerService));
		model.put("operation", "Update");
		return new ModelAndView("productionInvoice", model);
	}
	
	@RequestMapping(value = "/productioninvoiceupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updateProductionInvoiceItems(
			@ModelAttribute("command") ProductionInvoiceBean productionInvoiceBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<ProductionInvoiceItemBean> itr=productionInvoiceBean.getProductionInvoiceItemBeans().iterator();
		productionInvoiceBean
		.setProductionOrderBean(ProductionOrderUtilities
				.prepareProductionOrderBean(ProductionOrderUtilities
						.getProductionOrderModel(
								productionOrderService, productionInvoiceBean.getProductionOrderBean().getProductionOrderId())));

		while(itr.hasNext())
		{
			ProductionInvoiceItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("productionInvoiceBean",productionInvoiceBean);
		return new ModelAndView("productionInvoice", model);
	}
	@RequestMapping(value = "/updateProductionInvoice/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView updateProductionInvoice(
			@ModelAttribute("command") ProductionInvoiceBean productionInvoiceBean,
			BindingResult result,@PathVariable String type,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		ProductionInvoice productionInvoice=ProductionInvoiceUtilities.getProductionInvoiceModel(productionInvoiceService,id);
		List<ProductionInvoiceBean> productionInvoiceBeans=null;
		if(ERPConstants.PENDING.equals(type))
		{
			productionInvoice.setProcessed(false);
			productionInvoiceService.addProductionInvoice(productionInvoice);
			productionInvoiceBeans=ProductionInvoiceUtilities
				.prepareListofProductionInvoiceBean(
						productionInvoiceService.listProcessedProductionInvoices(), workerService);
		}
		else if(ERPConstants.PROCESSED.equals(type))
		{
			productionInvoice.setProcessed(true);
			productionInvoiceService.addProductionInvoice(productionInvoice);
			productionInvoiceBeans=ProductionInvoiceUtilities
					.prepareListofProductionInvoiceBean(
							productionInvoiceService.listPendingProductionInvoices(), workerService);
		}
		model.put("productionInvoices", productionInvoiceBeans);
		model.put("type", type);
		return new ModelAndView("productionInvoiceList", model);
	}

	@RequestMapping(value = "/productioninvoiceDetails/{poid}", method = RequestMethod.POST)
	public ModelAndView setOrderDetails(
			@ModelAttribute("productionInvoiceBean") @Validated ProductionInvoiceBean productionInvoiceBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			productionInvoiceBean
					.setProductionOrderBean(ProductionOrderUtilities
							.prepareProductionOrderBean(ProductionOrderUtilities
									.getProductionOrderModel(
											productionOrderService, poid)));
			productionInvoiceBean.setFinYear(user.getFinYear());
			WorkerBean workerBean=productionInvoiceBean.getProductionOrderBean().getWorkerBean();
			productionInvoiceBean.setWorkerBean(workerBean);
			//productionInvoiceBean = ERPUtilities.setPRODetailsinPRI(
				//	productionInvoiceBean, productionOrderService);
			model.put("productionInvoiceBean", productionInvoiceBean);
			return new ModelAndView("productionInvoice", model);
		} else {
			return new ModelAndView("error");// , model);
		}
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
