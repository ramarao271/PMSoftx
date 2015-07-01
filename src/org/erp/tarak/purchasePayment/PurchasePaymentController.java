package org.erp.tarak.purchasePayment;

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
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceService;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceUtilities;
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
public class PurchasePaymentController {

	@Autowired
	private PurchasePaymentService purchasePaymentService;

	@Autowired
	private PurchasePaymentItemService purchasePaymentItemService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	@Autowired
	private SupplierOpeningBalanceService cobService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/purchasePaymentItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("purchasePaymentBean") PurchasePaymentBean purchasePaymentBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<PurchasePaymentItemBean> purchasePaymentItemBeans = purchasePaymentBean
				.getPurchasePaymentItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (purchasePaymentItemBeans == null && size > 0) {
			PurchasePaymentItemBean bankAccountBean = new PurchasePaymentItemBean();
			purchasePaymentItemBeans = new LinkedList<PurchasePaymentItemBean>();
			purchasePaymentItemBeans.add(bankAccountBean);
			purchasePaymentBean.setPurchasePaymentItemBeans(purchasePaymentItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = purchasePaymentItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				PurchasePaymentItemBean bankAccountBean = new PurchasePaymentItemBean();
				purchasePaymentBean.getPurchasePaymentItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				purchasePaymentBean.getPurchasePaymentItemBeans().remove(
						purchasePaymentBean.getPurchasePaymentItemBeans().size() - 1);
			}
		}
		purchasePaymentBean.setPurchasePaymentItemBeans(purchasePaymentItemBeans);
		model.put("purchasePaymentBean", purchasePaymentBean);
		model.put("operation", "Add");
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		return new ModelAndView("purchasePayment", model);
	}

	@RequestMapping(value = "/savePurchasePayment", method = RequestMethod.POST)
	public ModelAndView savePurchasePayment(
			@ModelAttribute("purchasePaymentBean") @Validated PurchasePaymentBean purchasePaymentBean,
			BindingResult result, Model model) {
		model.addAttribute("pTypes", PurchasePaymentUtilities.paymentModes);
		if (result.hasErrors()) {
			model.addAttribute("purchasePaymentBean", purchasePaymentBean);
			return new ModelAndView("purchasePayment");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			PurchasePaymentBean spBean=(PurchasePaymentBean) session.getAttribute("spBean");
			PurchasePayment purchasePayment = PurchasePaymentUtilities.preparePurchasePaymentModelFromSession(
					purchasePaymentBean,spBean,user);
			PurchasePayment savedPurchasePayment=null;
			double savedAmount=0;
			if(purchasePaymentBean.getPurchasePaymentId()>0)
			{
				savedPurchasePayment=PurchasePaymentUtilities.getPurchasePaymentModel(purchasePaymentService, purchasePaymentBean.getPurchasePaymentId(),user.getFinYear());
				savedAmount=savedPurchasePayment.getTotalCost();
			}
			purchasePaymentService.addPurchasePayment(purchasePayment);
			PurchasePaymentUtilities.updatePurchaseInvoiceAmount(purchasePayment.getPurchasePaymentItems(),savedPurchasePayment, purchaseInvoiceService,ERPConstants.OP_CREATE,user.getFinYear());
			SupplierOpeningBalanceUtilities.updateCob(cobService, savedAmount, purchasePayment.getTotalCost(), purchasePayment.getSupplierId().getSupplierId(), purchasePayment.getFinYear(),ERPConstants.PURCHASE_PAYMENT);
			if(ERPConstants.PM_CASH.equals(purchasePayment.getPaymentMode()))
			{
				CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
				companyBean.setBalance(companyBean.getBalance()+purchasePayment.getTotalCost());
				companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
			}

			model.addAttribute("message",
					"PurchasePayment details saved successfully!");
			model.addAttribute("purchasePayments", PurchasePaymentUtilities
					.prepareListofPurchasePaymentBean(
							purchasePaymentService.listPurchasePayments(user.getFinYear()),
							supplierService));
			return new ModelAndView("purchasePaymentList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/PurchasePaymentSelectionList", method = RequestMethod.GET)
	public ModelAndView PurchasePaymentsSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		model.put("purchasePayments", PurchasePaymentUtilities
				.prepareListofPurchasePaymentBean(
						purchasePaymentService.listPurchasePayments(user.getFinYear()), supplierService));
		}return new ModelAndView("PurchasePaymentsSelectionList", model);
	}

	@RequestMapping(value = "/purchasePayments", method = RequestMethod.GET)
	public ModelAndView listPurchasePayments() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("purchasePayments", PurchasePaymentUtilities
				.prepareListofPurchasePaymentBean(
						purchasePaymentService.listPurchasePayments(user.getFinYear()), supplierService));
		}return new ModelAndView("purchasePaymentList", model);
	}

	@RequestMapping(value = "/addPurchasePayment", method = RequestMethod.GET)
	public ModelAndView addPurchasePayment() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		PurchasePaymentBean purchasePaymentBean = new PurchasePaymentBean();
		model.put("purchasePaymentBean", purchasePaymentBean);
		model.put("operation", "Add");
		return new ModelAndView("purchasePayment", model);
	}
	
	@RequestMapping(value = "/listpurchaseInvoices/{supplierId}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView listPurchaseInvoices(@ModelAttribute("purchasePaymentBean") @Validated PurchasePaymentBean purchasePaymentBean,
			BindingResult result, @PathVariable long supplierId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		List<PurchaseInvoiceBean> lsib=PurchaseInvoiceUtilities.prepareListofPurchaseInvoiceBean(purchaseInvoiceService.listPurchaseInvoicesBySupplier(supplierId,user.getFinYear()));
		model.put("purchaseInvoiceBeans",lsib );
		purchasePaymentBean.setSupplierBean(lsib.get(0).getSupplierBean());
		model.put("purchasePaymentBean", purchasePaymentBean);
		session.setAttribute("spBean", purchasePaymentBean);
		}return new ModelAndView("purchasePayment", model);
	}

	@RequestMapping(value = "/purchasePayment/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		PurchasePaymentBean purchasePaymentBean = new PurchasePaymentBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		model.put("category", "purchasePayment");
		model.put("purchasePaymentBean", purchasePaymentBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listPPSuppliers", method = RequestMethod.GET)
	public @ResponseBody
	String supplierList(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
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

	@RequestMapping(value = "/purchasePayment/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category","purchasepayment");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/deletePurchasePayment", method = RequestMethod.GET)
	public ModelAndView editPurchasePayment(
			@ModelAttribute("command") PurchasePaymentBean purchasePaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		PurchasePayment purchasePayment = PurchasePaymentUtilities.getPurchasePaymentModel(
				purchasePaymentService, purchasePaymentBean.getPurchasePaymentId(),user.getFinYear());
		List<PurchasePaymentItem> pois = purchasePayment.getPurchasePaymentItems();
		purchasePaymentService.deletePurchasePayment(purchasePayment);
		purchasePaymentItemService.deletePurchasePaymentItems(pois);
		PurchasePaymentUtilities.updatePurchaseInvoiceAmount(purchasePayment.getPurchasePaymentItems(),purchasePayment, purchaseInvoiceService,ERPConstants.OP_DELETE,user.getFinYear());
		SupplierOpeningBalanceUtilities.updateCob(cobService, 0, -purchasePayment.getTotalCost(), purchasePayment.getSupplierId().getSupplierId(), purchasePayment.getFinYear(),ERPConstants.PURCHASE_PAYMENT);
		if(ERPConstants.PM_CASH.equals(purchasePayment.getPaymentMode()))
		{
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			companyBean.setBalance(companyBean.getBalance()-purchasePayment.getTotalCost());
			companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
		}
		
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		model.put("purchasePayment", null);
		model.put("purchasePayments", PurchasePaymentUtilities
				.prepareListofPurchasePaymentBean(
						purchasePaymentService.listPurchasePayments(user.getFinYear()), supplierService));
		}
		return new ModelAndView("purchasePaymentList", model);
	}

	@RequestMapping(value = "/editPurchasePayment", method = RequestMethod.GET)
	public ModelAndView deletePurchasePayment(
			@ModelAttribute("command") PurchasePaymentBean purchasePaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", PurchasePaymentUtilities.paymentModes);
		
		PurchasePaymentBean spb=PurchasePaymentUtilities.preparePurchasePaymentBean(
				purchasePaymentService.getPurchasePayment(purchasePaymentBean
						.getPurchasePaymentId(),user.getFinYear()), supplierService);
		model.put("purchaseInvoiceBeans", PurchaseInvoiceUtilities.prepareListofPurchaseInvoiceBean(purchaseInvoiceService.listPurchaseInvoicesBySupplier(spb.getSupplierBean().getSupplierId(),user.getFinYear())));
		model.put("purchasePaymentBean", spb);
		model.put("operation", "Update");
		}return new ModelAndView("purchasePayment", model);
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
