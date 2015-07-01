package org.erp.tarak.purchasereturn;

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
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceService;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceUtilities;
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
public class PurchaseReturnController {

	@Autowired
	private PurchaseReturnService purchaseReturnService;

	@Autowired
	private PurchaseReturnItemService purchaseReturnItemService;

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
	private PurchaseInvoiceService purchaseInvoiceService;

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

	@RequestMapping(value = "/purchasereturnItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("purchaseReturnBean") PurchaseReturnBean purchaseReturnBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<PurchaseReturnItemBean> purchaseReturnItemBeans = purchaseReturnBean
				.getPurchaseReturnItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (purchaseReturnItemBeans == null && size > 0) {
			PurchaseReturnItemBean bankAccountBean = new PurchaseReturnItemBean();
			purchaseReturnItemBeans = new LinkedList<PurchaseReturnItemBean>();
			purchaseReturnItemBeans.add(bankAccountBean);
			purchaseReturnBean
					.setPurchaseReturnItemBeans(purchaseReturnItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = purchaseReturnItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				PurchaseReturnItemBean bankAccountBean = new PurchaseReturnItemBean();
				purchaseReturnBean.getPurchaseReturnItemBeans().add(
						bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				purchaseReturnBean.getPurchaseReturnItemBeans().remove(
						purchaseReturnBean.getPurchaseReturnItemBeans()
								.size() - 1);
			}
		}
		purchaseReturnBean
				.setPurchaseReturnItemBeans(purchaseReturnItemBeans);
		model.put("purchaseReturnBean", purchaseReturnBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseReturn", model);
	}

	@RequestMapping(value = "/savePurchaseReturn", method = RequestMethod.POST)
	public ModelAndView savePurchaseReturn(
			@ModelAttribute("purchaseReturnBean") @Validated PurchaseReturnBean purchaseReturnBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("purchaseReturnBean", purchaseReturnBean);
			return new ModelAndView("purchaseReturn");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			PurchaseReturn purchaseReturn = PurchaseReturnUtilities
					.preparePurchaseReturnModel(purchaseReturnBean, user,
							supplierService, categoryService,
							measurementService,purchaseInvoiceService,variantService);
			PurchaseReturn savedPurchaseReturn=null;
			if(purchaseReturn.getPurchaseReturnId()>0)
			{
				savedPurchaseReturn=purchaseReturnService.getPurchaseReturn(purchaseReturn.getPurchaseReturnId(),user.getFinYear());
			}
			purchaseReturnService.addPurchaseReturn(purchaseReturn);
			ProductUtilities.updateProductDetails(purchaseReturnBean
					,productService,savedPurchaseReturn,ERPConstants.PURCHASE_RETURN,ERPConstants.OP_CREATE);
			PurchaseInvoice purchaseInvoice=purchaseInvoiceService.getPurchaseInvoice(purchaseReturn.getPurchaseInvoice().getPurchaseInvoiceId(),user.getFinYear());
			double rtnAmt=0.0;
			double savedAmount=0;
			if(savedPurchaseReturn!=null)
				
			{
				rtnAmt=(purchaseInvoice.getReturnAmount()-purchaseReturn.getTotalCost()+savedPurchaseReturn.getTotalCost());
				savedAmount=savedPurchaseReturn.getTotalCost();
			}
			else
			{
				rtnAmt=purchaseInvoice.getTotalCost()-(purchaseInvoice.getReturnAmount()+purchaseReturn.getTotalCost());				
			}
			purchaseInvoice.setReturnAmount(rtnAmt);
			SupplierOpeningBalanceUtilities.updateCob(cobService, savedAmount, purchaseReturn.getTotalCost(), purchaseReturn.getSupplierId().getSupplierId(), purchaseReturn.getFinYear(),ERPConstants.PURCHASE_RETURN);
			purchaseInvoiceService.addPurchaseInvoice(purchaseInvoice);
			model.addAttribute("message",
					"PurchaseReturn details saved successfully!");

			model.addAttribute("purchaseReturns", PurchaseReturnUtilities
					.prepareListofPurchaseReturnBean(
							purchaseReturnService.listPurchaseReturns(user.getFinYear()),
							supplierService));
			
			return new ModelAndView("purchaseReturnList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/purchasereturns", method = RequestMethod.GET)
	public ModelAndView listPurchaseReturns() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("purchaseReturns", PurchaseReturnUtilities
				.prepareListofPurchaseReturnBean(
						purchaseReturnService.listPurchaseReturns(user.getFinYear()),
						supplierService));
		}return new ModelAndView("purchaseReturnList", model);
	}

	@RequestMapping(value = "/addPurchaseReturn", method = RequestMethod.GET)
	public ModelAndView addPurchaseReturn() {
		Map<String, Object> model = new HashMap<String, Object>();
		PurchaseReturnBean purchaseReturnBean = new PurchaseReturnBean();
		model.put("purchaseReturnBean", purchaseReturnBean);
		model.put("operation", "Add");
		return new ModelAndView("purchaseReturn", model);
	}

	@RequestMapping(value = "/purchasereturn/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		PurchaseReturnBean purchaseReturnBean = new PurchaseReturnBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "purchasereturn");
		model.put("purchaseReturnBean", purchaseReturnBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listPRSuppliers", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/purchasereturn/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category","purchasereturn");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deletePurchaseReturn", method = RequestMethod.GET)
	public ModelAndView deletePurchaseReturn(
			@ModelAttribute("command") PurchaseReturnBean purchaseReturnBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		PurchaseReturn purchaseReturn = PurchaseReturnUtilities
				.getPurchaseReturnModel(purchaseReturnService,
						purchaseReturnBean.getPurchaseReturnId(),user.getFinYear());
		List<PurchaseReturnItem> pois = purchaseReturn
				.getPurchaseReturnItems();
		purchaseReturnService.deletePurchaseReturn(purchaseReturn);
		purchaseReturnItemService.deletePurchaseReturnItems(pois);
		ProductUtilities.updateProductDetails(purchaseReturnBean
				,productService,purchaseReturn,ERPConstants.PURCHASE_RETURN,ERPConstants.OP_DELETE);
		PurchaseInvoice purchaseInvoice=purchaseInvoiceService.getPurchaseInvoice(purchaseReturn.getPurchaseInvoice().getPurchaseInvoiceId(),user.getFinYear());
		double rtnAmt=0.0;
		rtnAmt=-purchaseReturn.getTotalCost()+purchaseInvoice.getReturnAmount();
		purchaseInvoice.setReturnAmount(rtnAmt);
		purchaseInvoiceService.addPurchaseInvoice(purchaseInvoice);
		SupplierOpeningBalanceUtilities.updateCob(cobService,0, -purchaseReturn.getTotalCost(), purchaseReturn.getSupplierId().getSupplierId(), purchaseReturn.getFinYear(),ERPConstants.PURCHASE_RETURN);
		
		model.put("purchaseReturn", null);
		model.put("purchaseReturns", PurchaseReturnUtilities
				.prepareListofPurchaseReturnBean(
						purchaseReturnService.listPurchaseReturns(user.getFinYear()),
						supplierService));
		model.put("message",
				"Purchase Return deleted successfully!");
		
		}return new ModelAndView("purchaseReturnList", model);
	}

	@RequestMapping(value = "/purchasereturnupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updatePurchaseReturnItems(
			@ModelAttribute("command") PurchaseReturnBean purchaseReturnBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<PurchaseReturnItemBean> itr=purchaseReturnBean.getPurchaseReturnItemBeans().iterator();
		while(itr.hasNext())
		{
			PurchaseReturnItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("purchaseReturnBean",purchaseReturnBean);
		return new ModelAndView("purchaseReturn", model);
	}	
	
	
	@RequestMapping(value = "/editPurchaseReturn", method = RequestMethod.GET)
	public ModelAndView editPurchaseReturn(
			@ModelAttribute("command") PurchaseReturnBean purchaseReturnBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		model.put("purchaseReturnBean", PurchaseReturnUtilities
				.preparePurchaseReturnBean(purchaseReturnService
						.getPurchaseReturn(purchaseReturnBean
								.getPurchaseReturnId(),user.getFinYear()), supplierService));
		}model.put("operation", "Update");
		return new ModelAndView("purchaseReturn", model);
	}

	@RequestMapping(value = "/purchasereturnDetails/{poid}", method = RequestMethod.POST)
	public ModelAndView setOrderDetails(
			@ModelAttribute("purchaseReturnBean") @Validated PurchaseReturnBean purchaseReturnBean,
			BindingResult result, @PathVariable long poid) {
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			Map<String, Object> model = new HashMap<String, Object>();
			purchaseReturnBean
					.setPurchaseInvoiceBean(PurchaseInvoiceUtilities
							.preparePurchaseInvoiceBean(PurchaseInvoiceUtilities
									.getPurchaseInvoiceModel(
											purchaseInvoiceService, poid,user.getFinYear())));
			purchaseReturnBean.setFinYear(user.getFinYear());
			purchaseReturnBean = ERPUtilities.setPIDetailsinPR(
					purchaseReturnBean, purchaseInvoiceService);
			model.put("purchaseReturnBean", purchaseReturnBean);
			model.put("purchaseInvoices", PurchaseInvoiceUtilities
					.prepareListofPurchaseInvoiceBean(
							purchaseInvoiceService.listPurchaseInvoices(user.getFinYear()),
							supplierService));

			return new ModelAndView("purchaseReturn", model);
		} else {
			return new ModelAndView("error");// , model);
		}
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
