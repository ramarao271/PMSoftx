package org.erp.tarak.purchaseinvoice;

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
import org.erp.tarak.deliverychallan.DeliveryChallanService;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseorder.PurchaseOrder;
import org.erp.tarak.purchaseorder.PurchaseOrderService;
import org.erp.tarak.purchaseorder.PurchaseOrderUtilities;
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceService;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceUtilities;
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
public class PurchaseInvoiceController {

	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	
	@Autowired
	private DeliveryChallanService deliveryChallanService;

	@Autowired
	private PurchaseInvoiceItemService purchaseInvoiceItemService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;

	@Autowired
	private VariantService variantService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierOpeningBalanceService cobService;

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

	@RequestMapping(value = "/purchaseinvoiceItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("purchaseInvoiceBean") PurchaseInvoiceBean purchaseInvoiceBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans = purchaseInvoiceBean
				.getPurchaseInvoiceItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (purchaseInvoiceItemBeans == null && size > 0) {
			PurchaseInvoiceItemBean bankAccountBean = new PurchaseInvoiceItemBean();
			purchaseInvoiceItemBeans = new LinkedList<PurchaseInvoiceItemBean>();
			purchaseInvoiceItemBeans.add(bankAccountBean);
			purchaseInvoiceBean.setPurchaseInvoiceItemBeans(purchaseInvoiceItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = purchaseInvoiceItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				PurchaseInvoiceItemBean bankAccountBean = new PurchaseInvoiceItemBean();
				purchaseInvoiceBean.getPurchaseInvoiceItemBeans()
						.add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				purchaseInvoiceBean.getPurchaseInvoiceItemBeans().remove(
						purchaseInvoiceBean.getPurchaseInvoiceItemBeans().size() - 1);
			}
		}
		purchaseInvoiceBean.setPurchaseInvoiceItemBeans(purchaseInvoiceItemBeans);
		model.put("purchaseInvoiceBean", purchaseInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseInvoice", model);
	}

	@RequestMapping(value = "/savePurchaseInvoice", method = RequestMethod.POST)
	public ModelAndView savePurchaseInvoice(
			@ModelAttribute("purchaseInvoiceBean") @Validated PurchaseInvoiceBean purchaseInvoiceBean,
			BindingResult result, Model model,
			@RequestParam(required = false, value = "Save") String saveFlag,
			@RequestParam(required = false, value = "SaveDC") String renewFlag) {

		if (result.hasErrors()) {
			model.addAttribute("purchaseInvoiceBean", purchaseInvoiceBean);
			return new ModelAndView("purchaseInvoice");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			PurchaseInvoice purchaseInvoice = PurchaseInvoiceUtilities
					.preparePurchaseInvoiceModel(purchaseInvoiceBean, user,
							supplierService, categoryService,
							measurementService, purchaseOrderService,
							variantService);
			PurchaseInvoice savedPurchaseInvoice = null;
			double savedAmount = 0;
			if (purchaseInvoice.getPurchaseInvoiceId() > 0) {
				savedPurchaseInvoice = purchaseInvoiceService
						.getPurchaseInvoice(purchaseInvoice.getPurchaseInvoiceId(),user.getFinYear());
				savedAmount = savedPurchaseInvoice.getTotalCost();
			}
			purchaseInvoiceService.addPurchaseInvoice(purchaseInvoice);
			SupplierOpeningBalanceUtilities.updateCob(cobService, savedAmount,
					purchaseInvoiceBean.getTotalCost(), purchaseInvoice
							.getSupplierId().getSupplierId(), purchaseInvoice
							.getFinYear(), ERPConstants.PURCHASE_INVOICE);
			ProductUtilities.updateProductDetails(purchaseInvoiceBean,
					productService, savedPurchaseInvoice,
					ERPConstants.PURCHASE_INVOICE, ERPConstants.OP_CREATE);
			PurchaseOrder purchaseOrder = purchaseOrderService
					.getPurchaseOrder(purchaseInvoice.getPurchaseOrder()
							.getPurchaseOrderId(),user.getFinYear());
			purchaseOrder.setProcessed(true);
			purchaseOrderService.addPurchaseOrder(purchaseOrder);
			if (saveFlag != null) {
				model.addAttribute("message",
						"PurchaseInvoice details saved successfully!");

				model.addAttribute("purchaseInvoices", PurchaseInvoiceUtilities
						.prepareListofPurchaseInvoiceBean(
								purchaseInvoiceService.listPurchaseInvoices(user.getFinYear()),
								supplierService));

				return new ModelAndView("purchaseInvoiceList");// , model);
			} else {
				return new ModelAndView(
						"redirect:../purchasePayment/listpurchaseInvoices/"
								+ purchaseInvoiceBean.getSupplierBean()
										.getSupplierId());
			}
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/purchaseinvoices", method = RequestMethod.GET)
	public ModelAndView listPurchaseInvoices() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("purchaseInvoices", PurchaseInvoiceUtilities
				.prepareListofPurchaseInvoiceBean(
						purchaseInvoiceService.listPurchaseInvoices(user.getFinYear()),
						supplierService));
		}
		return new ModelAndView("purchaseInvoiceList", model);
	}

	@RequestMapping(value = "/addPurchaseInvoice", method = RequestMethod.GET)
	public ModelAndView addPurchaseInvoice() {
		Map<String, Object> model = new HashMap<String, Object>();
		PurchaseInvoiceBean purchaseInvoiceBean = new PurchaseInvoiceBean();
		model.put("purchaseInvoiceBean", purchaseInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseInvoice", model);
	}

	@RequestMapping(value = "/purchaseinvoice/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		PurchaseInvoiceBean purchaseInvoiceBean = new PurchaseInvoiceBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "purchaseinvoice");
		model.put("purchaseInvoiceBean", purchaseInvoiceBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listPISuppliers", method = RequestMethod.GET)
	public @ResponseBody String supplierList(HttpServletRequest request) {
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

	@RequestMapping(value = "/purchaseinvoice/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "purchaseinvoice");
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/deletePurchaseInvoice", method = RequestMethod.GET)
	public ModelAndView editPurchaseInvoice(
			@ModelAttribute("command") PurchaseInvoiceBean purchaseInvoiceBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		PurchaseInvoice purchaseInvoice = PurchaseInvoiceUtilities.getPurchaseInvoiceModel(
				purchaseInvoiceService, purchaseInvoiceBean.getPurchaseInvoiceId(),user.getFinYear());
		List<PurchaseInvoiceItem> pois = purchaseInvoice.getPurchaseInvoiceItems();
		purchaseInvoiceService.deletePurchaseInvoice(purchaseInvoice);
		purchaseInvoiceItemService.deletePurchaseInvoiceItems(pois);
		ProductUtilities.updateProductDetails(purchaseInvoiceBean, productService,
				purchaseInvoice, ERPConstants.PURCHASE_INVOICE,
				ERPConstants.OP_DELETE);
		PurchaseOrder purchaseOrder = purchaseOrderService
				.getPurchaseOrder(purchaseInvoice.getPurchaseOrder()
						.getPurchaseOrderId(),user.getFinYear());
		purchaseOrder.setProcessed(false);
		purchaseOrderService.addPurchaseOrder(purchaseOrder);
		SupplierOpeningBalanceUtilities.updateCob(cobService, 0, -purchaseInvoice
				.getTotalCost(), purchaseInvoice.getSupplierId().getSupplierId(),
				purchaseInvoice.getFinYear(), ERPConstants.PURCHASE_INVOICE);
		
		model.put("purchaseInvoice", null);
		model.put("purchaseInvoices", PurchaseInvoiceUtilities
				.prepareListofPurchaseInvoiceBean(
						purchaseInvoiceService.listPurchaseInvoices(user.getFinYear()),
						supplierService));
		model.put("message", "Purchase Invoice deleted successfully!");

		}return new ModelAndView("purchaseInvoiceList", model);
	}

	@RequestMapping(value = "/editPurchaseInvoice", method = RequestMethod.GET)
	public ModelAndView deletePurchaseInvoice(
			@ModelAttribute("command") PurchaseInvoiceBean purchaseInvoiceBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("purchaseInvoiceBean", PurchaseInvoiceUtilities
				.preparePurchaseInvoiceBean(purchaseInvoiceService
						.getPurchaseInvoice(purchaseInvoiceBean.getPurchaseInvoiceId(),user.getFinYear()),
						supplierService));
		model.put("operation", "Update");
		}
		return new ModelAndView("purchaseInvoice", model);
	}

	@RequestMapping(value = "/purchaseinvoiceDetails/{poid}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView setOrderDetails(
			@ModelAttribute("purchaseInvoiceBean") @Validated PurchaseInvoiceBean purchaseInvoiceBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			purchaseInvoiceBean.setPurchaseOrderBean(PurchaseOrderUtilities
					.preparePurchaseOrderBean(PurchaseOrderUtilities
							.getPurchaseOrderModel(purchaseOrderService,
									poid,user.getFinYear())));
			purchaseInvoiceBean.setFinYear(user.getFinYear());
			purchaseInvoiceBean = ERPUtilities.setPODetailsinPI(purchaseInvoiceBean,purchaseOrderService);
			model.put("purchaseInvoiceBean", purchaseInvoiceBean);
			/*model.put("deliveryChallans", DeliveryChallanUtilities
					.prepareListofDeliveryChallanBean(
							deliveryChallanService.listDeliveryChallans(user.getFinYear()),
							supplierService));
*/
			return new ModelAndView("purchaseInvoice", model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/PurchaseInvoiceSelectionList", method = RequestMethod.GET)
	public ModelAndView PurchaseInvoicesSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("purchaseInvoices", PurchaseInvoiceUtilities
				.prepareListofPurchaseInvoiceBean(
						purchaseInvoiceService.listPurchaseInvoices(user.getFinYear()),
						supplierService));
		}
		return new ModelAndView("PurchaseInvoicesSelectionList", model);
	}
	
	@RequestMapping(value = "/purchaseinvoiceupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView deletePurchaseInvoiceItems(@ModelAttribute("purchaseInvoiceBean") @Validated PurchaseInvoiceBean purchaseInvoiceBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<PurchaseInvoiceItemBean> itr=purchaseInvoiceBean.getPurchaseInvoiceItemBeans().iterator();
		while(itr.hasNext())
		{
			PurchaseInvoiceItemBean dcib=itr.next();
			if(dcib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("purchaseInvoiceBean", purchaseInvoiceBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseInvoice", model);
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
