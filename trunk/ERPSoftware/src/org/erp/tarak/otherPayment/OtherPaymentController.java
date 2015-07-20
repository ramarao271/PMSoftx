package org.erp.tarak.otherPayment;

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
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.productioninvoice.ProductionInvoiceBean;
import org.erp.tarak.productioninvoice.ProductionInvoiceService;
import org.erp.tarak.productioninvoice.ProductionInvoiceUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.WorkerBean;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;
import org.erp.tarak.worker.openingbalance.WorkerOpeningBalanceService;
import org.erp.tarak.worker.openingbalance.WorkerOpeningBalanceUtilities;
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
public class OtherPaymentController {

	@Autowired
	private OtherPaymentService otherPaymentService;

	@Autowired
	private OtherPaymentItemService otherPaymentItemService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private WorkerService workerService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private ProductionInvoiceService productionInvoiceService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	@Autowired
	private WorkerOpeningBalanceService cobService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/otherPaymentItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("otherPaymentBean") OtherPaymentBean otherPaymentBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<OtherPaymentItemBean> otherPaymentItemBeans = otherPaymentBean
				.getOtherPaymentItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (otherPaymentItemBeans == null && size > 0) {
			OtherPaymentItemBean bankAccountBean = new OtherPaymentItemBean();
			otherPaymentItemBeans = new LinkedList<OtherPaymentItemBean>();
			otherPaymentItemBeans.add(bankAccountBean);
			otherPaymentBean.setOtherPaymentItemBeans(otherPaymentItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = otherPaymentItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				OtherPaymentItemBean bankAccountBean = new OtherPaymentItemBean();
				otherPaymentBean.getOtherPaymentItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				otherPaymentBean.getOtherPaymentItemBeans().remove(
						otherPaymentBean.getOtherPaymentItemBeans().size() - 1);
			}
		}
		otherPaymentBean.setOtherPaymentItemBeans(otherPaymentItemBeans);
		model.put("otherPaymentBean", otherPaymentBean);
		model.put("operation", "Add");
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		return new ModelAndView("otherPayment", model);
	}

	@RequestMapping(value = "/saveOtherPayment", method = RequestMethod.POST)
	public ModelAndView saveOtherPayment(
			@ModelAttribute("otherPaymentBean") @Validated OtherPaymentBean otherPaymentBean,
			BindingResult result, Model model) {
		model.addAttribute("pTypes", OtherPaymentUtilities.paymentModes);
		if (result.hasErrors()) {
			model.addAttribute("otherPaymentBean", otherPaymentBean);
			return new ModelAndView("otherPayment");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			OtherPaymentBean spBean=(OtherPaymentBean) session.getAttribute("spBean");
			OtherPayment otherPayment = OtherPaymentUtilities.prepareOtherPaymentModelFromSession(
					otherPaymentBean,spBean,user);
			OtherPayment savedOtherPayment=null;
			double savedAmount=0;
			if(otherPaymentBean.getOtherPaymentId()>0)
			{
				savedOtherPayment=OtherPaymentUtilities.getOtherPaymentModel(otherPaymentService, otherPaymentBean.getOtherPaymentId(),user.getFinYear());
				savedAmount=savedOtherPayment.getTotalCost();
			}
			otherPaymentService.addOtherPayment(otherPayment);
			OtherPaymentUtilities.updateProductionInvoiceAmount(otherPayment.getOtherPaymentItems(),savedOtherPayment, productionInvoiceService,ERPConstants.OP_CREATE,user.getFinYear());
			WorkerOpeningBalanceUtilities.updateCob(cobService, savedAmount, otherPayment.getTotalCost(), otherPayment.getWorkerId().getWorkerId(), otherPayment.getFinYear(),ERPConstants.SALES_PAYMENT);
			if(ERPConstants.PM_CASH.equals(otherPayment.getPaymentMode()))
			{
				CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
				companyBean.setBalance(companyBean.getBalance()+otherPayment.getTotalCost());
				companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
			}

			model.addAttribute("message",
					"OtherPayment details saved successfully!");
			model.addAttribute("otherPayments", OtherPaymentUtilities
					.prepareListofOtherPaymentBean(
							otherPaymentService.listOtherPayments(user.getFinYear()),
							workerService));
			return new ModelAndView("otherPaymentList");// , model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/OtherPaymentSelectionList", method = RequestMethod.GET)
	public ModelAndView OtherPaymentsSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		model.put("otherPayments", OtherPaymentUtilities
				.prepareListofOtherPaymentBean(
						otherPaymentService.listOtherPayments(user.getFinYear()), workerService));
		}return new ModelAndView("OtherPaymentsSelectionList", model);
	}

	@RequestMapping(value = "/otherPayments", method = RequestMethod.GET)
	public ModelAndView listOtherPayments() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("otherPayments", OtherPaymentUtilities
				.prepareListofOtherPaymentBean(
						otherPaymentService.listOtherPayments(user.getFinYear()), workerService));
		}return new ModelAndView("otherPaymentList", model);
	}

	@RequestMapping(value = "/addOtherPayment", method = RequestMethod.GET)
	public ModelAndView addOtherPayment() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		OtherPaymentBean otherPaymentBean = new OtherPaymentBean();
		model.put("otherPaymentBean", otherPaymentBean);
		model.put("operation", "Add");
		return new ModelAndView("otherPayment", model);
	}
	
	@RequestMapping(value = "/listproductionInvoices/{workerId}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView listProductionInvoices(@ModelAttribute("otherPaymentBean") @Validated OtherPaymentBean otherPaymentBean,
			BindingResult result, @PathVariable long workerId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		List<ProductionInvoiceBean> lsib=ProductionInvoiceUtilities.prepareListofProductionInvoiceBean(productionInvoiceService.listProductionInvoicesByWorker(workerId,user.getFinYear()));
		model.put("productionInvoiceBeans",lsib );
		otherPaymentBean.setWorkerBean(lsib.get(0).getWorkerBean());
		model.put("otherPaymentBean", otherPaymentBean);
		session.setAttribute("spBean", otherPaymentBean);
		}return new ModelAndView("otherPayment", model);
	}

	@RequestMapping(value = "/otherPayment/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		OtherPaymentBean otherPaymentBean = new OtherPaymentBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		model.put("category", "otherPayment");
		model.put("otherPaymentBean", otherPaymentBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/listOPWorkers", method = RequestMethod.GET)
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
			List<Tworker> sups = new LinkedList<Tworker>();
			for (WorkerBean workerBean : workers) {
				Tworker s = new Tworker(workerBean.getWorkerId(),
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

	@RequestMapping(value = "/otherPayment/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category","otherpayment");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/deleteOtherPayment", method = RequestMethod.GET)
	public ModelAndView editOtherPayment(
			@ModelAttribute("command") OtherPaymentBean otherPaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		OtherPayment otherPayment = OtherPaymentUtilities.getOtherPaymentModel(
				otherPaymentService, otherPaymentBean.getOtherPaymentId(),user.getFinYear());
		List<OtherPaymentItem> pois = otherPayment.getOtherPaymentItems();
		otherPaymentService.deleteOtherPayment(otherPayment);
		otherPaymentItemService.deleteOtherPaymentItems(pois);
		OtherPaymentUtilities.updateProductionInvoiceAmount(otherPayment.getOtherPaymentItems(),otherPayment, productionInvoiceService,ERPConstants.OP_DELETE,user.getFinYear());
		WorkerOpeningBalanceUtilities.updateCob(cobService, 0, -otherPayment.getTotalCost(), otherPayment.getWorkerId().getWorkerId(), otherPayment.getFinYear(),ERPConstants.SALES_PAYMENT);
		if(ERPConstants.PM_CASH.equals(otherPayment.getPaymentMode()))
		{
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			companyBean.setBalance(companyBean.getBalance()-otherPayment.getTotalCost());
			companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
		}
		
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		model.put("otherPayment", null);
		model.put("otherPayments", OtherPaymentUtilities
				.prepareListofOtherPaymentBean(
						otherPaymentService.listOtherPayments(user.getFinYear()), workerService));
		}
		return new ModelAndView("otherPaymentList", model);
	}

	@RequestMapping(value = "/editOtherPayment", method = RequestMethod.GET)
	public ModelAndView deleteOtherPayment(
			@ModelAttribute("command") OtherPaymentBean otherPaymentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
		model.put("pTypes", OtherPaymentUtilities.paymentModes);
		
		OtherPaymentBean spb=OtherPaymentUtilities.prepareOtherPaymentBean(
				otherPaymentService.getOtherPayment(otherPaymentBean
						.getOtherPaymentId(),user.getFinYear()), workerService);
		model.put("productionInvoiceBeans", ProductionInvoiceUtilities.prepareListofProductionInvoiceBean(productionInvoiceService.listProductionInvoicesByWorker(spb.getWorkerBean().getWorkerId(),user.getFinYear())));
		model.put("otherPaymentBean", spb);
		model.put("operation", "Update");
		}return new ModelAndView("otherPayment", model);
	}

	class Tworker {
		long workerId;
		String workerName;
		Tworker() {

		}

		Tworker(long workerId,String workerName) {
			this.workerId = workerId;
			this.workerName=workerName;
		}

	}

}
