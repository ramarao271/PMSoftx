package org.erp.tarak.balanceSheet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.company.CompanyBean;
import org.erp.tarak.company.CompanyService;
import org.erp.tarak.company.CompanyUtilities;
import org.erp.tarak.expense.ExpenseService;
import org.erp.tarak.expense.ExpenseUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.purchasePayment.PurchasePayment;
import org.erp.tarak.purchasePayment.PurchasePaymentService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchasereturn.PurchaseReturn;
import org.erp.tarak.purchasereturn.PurchaseReturnService;
import org.erp.tarak.salesPayment.SalesPayment;
import org.erp.tarak.salesPayment.SalesPaymentService;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesreturn.SalesReturn;
import org.erp.tarak.salesreturn.SalesReturnService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BalanceSheetController {

	@Autowired
	private BalanceSheetService balanceSheetService;
	
	@Autowired
	private SalesInvoiceService salesInvoiceService;

	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;

	@Autowired
	private SalesPaymentService salesPaymentService;

	@Autowired
	private PurchasePaymentService purchasePaymentService;
	
	@Autowired
	private SalesReturnService salesReturnService;

	@Autowired
	private PurchaseReturnService purchaseReturnService;
	
	@Autowired
	private BalanceSheetItemService balanceSheetItemService;

	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private CompanyService companyService;
	
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
	
	@RequestMapping(value = "/balanceSheetItemModules/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("balanceSheetBean") BalanceSheetBean balanceSheetBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<BalanceSheetItemBean> balanceSheetItemBeans = balanceSheetBean
				.getBalanceSheetItemBeans();
		int size = 0;
		int listSize = 0;
		try {
			size = Integer.parseInt(category);
		} catch (Exception e) {
		}
		if (balanceSheetItemBeans == null && size > 0) {
			BalanceSheetItemBean bankAccountBean = new BalanceSheetItemBean();
			balanceSheetItemBeans = new LinkedList<BalanceSheetItemBean>();
			balanceSheetItemBeans.add(bankAccountBean);
			balanceSheetBean.setBalanceSheetItemBeans(balanceSheetItemBeans);
			if (size > 1) {
				size = size - 1;
			}
		}
		listSize = balanceSheetItemBeans.size();
		int diff = size - listSize;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				BalanceSheetItemBean bankAccountBean = new BalanceSheetItemBean();
				balanceSheetBean.getBalanceSheetItemBeans().add(bankAccountBean);
			}
		} else {
			for (int i = diff; i < 0; i++) {
				balanceSheetBean.getBalanceSheetItemBeans().remove(
						balanceSheetBean.getBalanceSheetItemBeans().size() - 1);
			}
		}
		balanceSheetBean.setBalanceSheetItemBeans(balanceSheetItemBeans);
		model.put("balanceSheetBean", balanceSheetBean);
		model.put("operation", "Add");
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		if (session.getAttribute("company") != null) {
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			model.put("balance", companyBean.getBalance());
		}
		return new ModelAndView("balanceSheet", model);
	}

	@RequestMapping(value = "/saveBalanceSheet", method = RequestMethod.POST)
	public ModelAndView saveBalanceSheet(
			@ModelAttribute("balanceSheetBean") @Validated BalanceSheetBean balanceSheetBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("balanceSheetBean", balanceSheetBean);
			return new ModelAndView("balanceSheet");// , model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			BalanceSheet balanceSheet = BalanceSheetUtilities.prepareBalanceSheetModel(
					balanceSheetBean, user);
			BalanceSheet savedBalanceSheet=null;
			if(balanceSheetBean.getBalanceSheetId()>0)
			{
				savedBalanceSheet=BalanceSheetUtilities.getBalanceSheetModel(balanceSheetService, balanceSheetBean.getBalanceSheetId());
			}
			balanceSheetService.addBalanceSheet(balanceSheet);
			
			model.addAttribute("message",
					"BalanceSheet details saved successfully!");
			model.addAttribute("balanceSheets", BalanceSheetUtilities
					.prepareListofBalanceSheetBean(
							balanceSheetService.listPendingBalanceSheets()));
			return new ModelAndView("balanceSheetList");//, model);
		} else {
			return new ModelAndView("error");// , model);
		}
	}

	@RequestMapping(value = "/BalanceSheetSelectionList", method = RequestMethod.GET)
	public ModelAndView BalanceSheetsSelectionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balanceSheets", BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listPendingBalanceSheets()));
		return new ModelAndView("BalanceSheetsSelectionList", model);
	}

	@RequestMapping(value = "/pendingBalanceSheets", method = RequestMethod.GET)
	public ModelAndView listPendingBalanceSheets() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PROCESSED);
		model.put("mode",ERPConstants.PENDING);
		model.put("balanceSheets", BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listPendingBalanceSheets()));
		return new ModelAndView("balanceSheetList", model);
	}

	@RequestMapping(value = "/processedBalanceSheets", method = RequestMethod.GET)
	public ModelAndView listProcessedBalanceSheets() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", ERPConstants.PENDING);
		model.put("mode",ERPConstants.PROCESSED);
		model.put("balanceSheets", BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listProcessedBalanceSheets()));
		return new ModelAndView("balanceSheetList", model);
	}
	@RequestMapping(value = "/addBalanceSheet", method = RequestMethod.GET)
	public ModelAndView addBalanceSheet() {
		Map<String, Object> model = new HashMap<String, Object>();
		BalanceSheetBean balanceSheetBean = new BalanceSheetBean();
		model.put("balanceSheetBean", balanceSheetBean);
		model.put("operation", "Add");
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		if (session.getAttribute("company") != null) {
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			model.put("balance", companyBean.getBalance());
		}
		return new ModelAndView("balanceSheet", model);
	}

	@RequestMapping(value = {"/balanceSheet/","/balancesheet/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		BalanceSheetBean balanceSheetBean = new BalanceSheetBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "balanceSheet");
		model.put("balanceSheetBean", balanceSheetBean);
		return new ModelAndView("index", model);
	}

	
	@RequestMapping(value = "/deleteBalanceSheet", method = RequestMethod.GET)
	public ModelAndView editBalanceSheet(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result) {
		BalanceSheet balanceSheet = BalanceSheetUtilities.getBalanceSheetModel(
				balanceSheetService, balanceSheetBean.getBalanceSheetId());
		List<BalanceSheetItem> pois = balanceSheet.getBalanceSheetItems();
		balanceSheetService.deleteBalanceSheet(balanceSheet);
		balanceSheetItemService.deleteBalanceSheetItems(pois);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balanceSheet", null);
		model.put("balanceSheets", BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listPendingBalanceSheets()));
		model.put("message",
				"BalanceSheet deleted successfully!");
		return new ModelAndView("balanceSheetList", model);
	}

	@RequestMapping(value = "/editBalanceSheet", method = RequestMethod.GET)
	public ModelAndView deleteBalanceSheet(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balanceSheetBean", BalanceSheetUtilities.prepareBalanceSheetBean(
				balanceSheetService.getBalanceSheet(balanceSheetBean
						.getBalanceSheetId())));
		model.put("operation", "Update");
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		if (session.getAttribute("company") != null) {
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			model.put("balance", companyBean.getBalance());
		}
		return new ModelAndView("balanceSheet", model);
	}
	
	@RequestMapping(value = "/balancesheetupdateItems/{srNo}", method = RequestMethod.POST)
	public ModelAndView updateBalanceSheetItems(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result,@PathVariable long srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		Iterator<BalanceSheetItemBean> itr=balanceSheetBean.getBalanceSheetItemBeans().iterator();
		while(itr.hasNext())
		{
			BalanceSheetItemBean soib=itr.next();
			if(soib.getSrNo()==srNo)
			{
				itr.remove();
			}
		}
		model.put("balanceSheetBean",balanceSheetBean);
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		if (session.getAttribute("company") != null) {
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			model.put("balance", companyBean.getBalance());
		}
		return new ModelAndView("balanceSheet", model);
	}
	@RequestMapping(value = "/updateBalanceSheet/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView updateBalanceSheetStatus(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result,@PathVariable String type,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		BalanceSheet balanceSheet=BalanceSheetUtilities.getBalanceSheetModel(balanceSheetService,id);
		List<BalanceSheetBean> balanceSheetBeans=null;
		if(ERPConstants.PENDING.equals(type))
		{
			balanceSheetService.addBalanceSheet(balanceSheet);
			balanceSheetBeans=BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listProcessedBalanceSheets());
		}
		else if(ERPConstants.PROCESSED.equals(type))
		{
			balanceSheetService.addBalanceSheet(balanceSheet);
			balanceSheetBeans=BalanceSheetUtilities
					.prepareListofBalanceSheetBean(
							balanceSheetService.listPendingBalanceSheets());
		}
		model.put("balanceSheets", balanceSheetBeans);
		model.put("type", type);
		return new ModelAndView("balanceSheetList", model);
	}
	
	@RequestMapping(value = "/updateBalanceSheet/{id}", method = RequestMethod.GET)
	public ModelAndView updateBalanceSheet(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		BalanceSheet balanceSheet=BalanceSheetUtilities.getBalanceSheetModel(balanceSheetService,id);
		if (session.getAttribute("company") != null) {
			CompanyBean companyBean=(CompanyBean)session.getAttribute("company");
			companyBean.setBalance(companyBean.getBalance()-balanceSheet.getTotalCost());
			companyService.addCompany(CompanyUtilities.prepareCompanyModel(companyBean));
			session.setAttribute("company", companyBean);
			balanceSheet.setProcessed(true);
			balanceSheetService.addBalanceSheet(balanceSheet);
		}
		model.put("balanceSheets", BalanceSheetUtilities
				.prepareListofBalanceSheetBean(
						balanceSheetService.listPendingBalanceSheets()));
		return new ModelAndView("balanceSheetList", model);
	}
	
	@RequestMapping(value = "/viewBalanceSheet", method = RequestMethod.GET)
	public ModelAndView viewBalanceSheet(
			@ModelAttribute("command") BalanceSheetBean balanceSheetBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		BalanceSheetBean bsb=BalanceSheetUtilities.prepareBalanceSheetBean(
				balanceSheetService.getBalanceSheet(balanceSheetBean
						.getBalanceSheetId()));
		List<SalesInvoice> salesInvoices=salesInvoiceService.listSalesInvoicesByDate(bsb.getBalanceSheetDate());
		List<SalesPayment> salesPayments=salesPaymentService.listSalesInvoicesByDate(bsb.getBalanceSheetDate());
		List<SalesReturn> salesReturns=salesReturnService.listSalesReturnsByDate(bsb.getBalanceSheetDate());
		List<PurchaseInvoice> purchaseInvoices=purchaseInvoiceService.listPurchaseInvoicesByDate(bsb.getBalanceSheetDate());
		List<PurchasePayment> purchasePayments=purchasePaymentService.listPurchasePaymentsByDate(bsb.getBalanceSheetDate());
		List<PurchaseReturn> purchaseReturns=purchaseReturnService.listPurchaseReturns(bsb.getBalanceSheetDate());
		List<BalanceSheetItemBean> bsibs=new LinkedList<BalanceSheetItemBean>();
		bsibs.addAll(bsb.getBalanceSheetItemBeans());
		int srno=bsb.getBalanceSheetItemBeans().size();
		for(SalesInvoice si: salesInvoices)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Sales Invoice-"+si.getSalesInvoiceId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		for(PurchaseInvoice si: purchaseInvoices)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Purchase Invoice-"+si.getPurchaseInvoiceId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(-si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		for(SalesPayment si: salesPayments)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Sales Payment-"+si.getSalesPaymentId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		for(PurchasePayment si: purchasePayments)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Purchase Payment-"+si.getPurchasePaymentId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(-si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		for(SalesReturn si: salesReturns)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Sales Return-"+si.getSalesReturnId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(-si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		for(PurchaseReturn si: purchaseReturns)
		{
			BalanceSheetItemBean bsib=new BalanceSheetItemBean();
			bsib.setBalanceSheetId(bsb.getBalanceSheetId());
			bsib.setDescription("Purchase Return-"+si.getPurchaseReturnId());
			bsib.setFinYear(bsb.getFinYear());
			bsib.setRate(+si.getTotalCost());
			bsib.setSrNo(++srno);
			bsibs.add(bsib);
		}
		bsb.setBalanceSheetItemBeans(bsibs);
		model.put("balanceSheetBean", bsb);
		return new ModelAndView("balanceSheetView", model);
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
