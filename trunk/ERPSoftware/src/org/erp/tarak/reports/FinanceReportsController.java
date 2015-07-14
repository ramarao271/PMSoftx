package org.erp.tarak.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.customer.Customer;
import org.erp.tarak.customer.CustomerBean;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.deliverychallan.DeliveryChallanService;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.product.ProductReport;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceItemService;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesorder.SalesOrderService;
import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FinanceReportsController {
	
	@Autowired
	private SalesInvoiceService salesInvoiceService;

	@Autowired
	private SalesInvoiceItemService salesInvoiceItemService;
	
	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;

	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeliveryChallanService deliveryChallanService;

	@Autowired
	private HttpSession session;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping(value = "/FR", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "financialReports");
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/ordersToShip", method = RequestMethod.GET)
	public ModelAndView ordersToShip() {
		/*Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			model.put("salesOrders", SalesOrderUtilities
				.prepareListofSalesOrderBean(
						salesOrderService.listPendingSalesOrders(user.getFinYear()), customerService));
		}*/
		return new ModelAndView("redirect:../salesorder/pendingSalesorders.html");
	}
	
	@RequestMapping(value = "/ordersToInvoice", method = RequestMethod.GET)
	public ModelAndView ordersToInvoice() {
		/*Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			model.put("deliveryChallans", DeliveryChallanUtilities
				.prepareListofDeliveryChallanBean(
						deliveryChallanService.listPendingDeliveryChallans(user.getFinYear()), customerService));
		}
		*/
		return new ModelAndView("redirect:../deliverychallan/pendingDeliverychallans.html");
	}
	
	@RequestMapping(value = "/accountsReceivable", method = RequestMethod.GET)
	public ModelAndView accountsReceivable() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<CustomerBean> customerBeans=new LinkedList<CustomerBean>();
			List<Object[]> lsib=salesInvoiceService.listPendingSalesInvoicesByCustomer(user.getFinYear());
			for(Object[] obj: lsib)
			{
				CustomerBean customer=CustomerUtilities.prepareCustomerBean((Customer)obj[0]);
				customer.setTotalAmount(ERPUtilities.round((double)obj[1],2));
				customer.setReturnAmount(ERPUtilities.round((double)obj[2],2));
				customer.setAdjustedAmount(ERPUtilities.round((double)obj[3],2));
				customer.setPaidAmount(ERPUtilities.round((double)obj[4],2));
				double balance=(double)obj[2]>0?(double)obj[3]-(double)obj[4]:(double)obj[1]-(double)obj[4];
				balance=ERPUtilities.round(balance, 2);
				customer.setCurrentBalance(balance);
				customerBeans.add(customer);
				/*Calendar cal=Calendar.getInstance();
				Date dt=cal.getTime();
				int days=dt.compareTo(sib.getSalesInvoiceDate());
				sib.setNoOfDays(days);*/
			}
			model.put("customers", customerBeans);
		}	
		
		return new ModelAndView("accountsReceivable", model);
	}
	@RequestMapping(value = "/accountsPayable", method = RequestMethod.GET)
	public ModelAndView accountsPayable() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<SupplierBean> supplierBeans=new LinkedList<SupplierBean>();
			List<Object[]> lsib=purchaseInvoiceService.listPendingPurchaseInvoicesBySupplier(user.getFinYear());
			for(Object[] obj: lsib)
			{
				SupplierBean supplier=SupplierUtilities.prepareSupplierBean((Supplier)obj[0]);
				supplier.setTotalAmount(ERPUtilities.round((double)obj[1],2));
				supplier.setReturnAmount(ERPUtilities.round((double)obj[2],2));
				supplier.setAdjustedAmount(ERPUtilities.round((double)obj[3],2));
				supplier.setPaidAmount(ERPUtilities.round((double)obj[4],2));
				double balance=(double)obj[2]>0?(double)obj[3]-(double)obj[4]:(double)obj[1]-(double)obj[4];
				balance=ERPUtilities.round(balance, 2);
				supplier.setCurrentBalance(balance);
				supplierBeans.add(supplier);
				/*Calendar cal=Calendar.getInstance();
				Date dt=cal.getTime();
				int days=dt.compareTo(sib.getSalesInvoiceDate());
				sib.setNoOfDays(days);*/
			}
			model.put("suppliers", supplierBeans);
		}	
		
		return new ModelAndView("accountsPayable", model);
	}
	
	@RequestMapping(value = "/salesReport", method = RequestMethod.GET)
	public ModelAndView salesReport(@ModelAttribute("reportForm") ReportForm reportForm) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			Calendar c = Calendar.getInstance();
			Date d=c.getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String date=df.format(d);
			List<Object[]> obj=salesInvoiceItemService.listSalesReportByCategoryWise(user.getFinYear(),Calendar.DATE,date);
			List<ProductReport> prList=ReportUtilities.populateProductSaleEntries(obj,"Sales");
			model.put("mode", date);
			model.put("products", prList);
		}
		return new ModelAndView("salesReport",model);
	}
	
	@RequestMapping(value = "/salesReportAction", method = RequestMethod.POST)
	public ModelAndView salesReportAction(@ModelAttribute("reportForm") ReportForm reportForm) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			int type=0;
			if("Daily".equals(reportForm.getDurationType()))
			{
				type=Calendar.DATE;
			}
			else
			{
				type=Calendar.MONTH;
			}
			List<Object[]> obj=salesInvoiceItemService.listSalesReportByCategoryWise(user.getFinYear(),type,reportForm.getFromDate(),reportForm.getToDate());
			List<ProductReport> prList=ReportUtilities.populateProductSaleEntries(obj,"Sales");
			List<ProductReport> prList1=new ArrayList<ProductReport>();
			Date d=null;
			int month=0;
			for(ProductReport pp: prList)
			{
				if(type==Calendar.DATE)
				{
					if(d==null || d.compareTo(pp.getDate())!=0)
					{
						ProductReport pr=new ProductReport();
						pr.setProductName(pp.getDate()+"");
						prList1.add(pr);
						d=pp.getDate();
					}
					prList1.add(pp);
				}
				else if(type==Calendar.MONTH)
				{
					if(month==0 || month!=pp.getMonth())
					{
						ProductReport pr=new ProductReport();
						pr.setProductName(pp.getMonth()+"");
						prList1.add(pr);
						month=pp.getMonth();
					}
					prList1.add(pp);
				}
			}
			String date=reportForm.getFromDate()+" to "+reportForm.getToDate();
			model.put("mode", date);
			model.put("products", prList1);
		}
		return new ModelAndView("salesReport",model);
	}

	@RequestMapping(value = "/lostSalesReport", method = RequestMethod.GET)
	public ModelAndView lostSalesReport(@ModelAttribute("reportForm") ReportForm reportForm) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			Calendar c = Calendar.getInstance();
			Date d=c.getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String date=df.format(d);
			List<Object[]> obj=salesInvoiceItemService.listSalesReportByCategoryWise(user.getFinYear(),Calendar.DATE,date);
			List<ProductReport> prList=ReportUtilities.populateProductSaleEntries(obj,"Sales");
			model.put("mode", date);
			model.put("products", prList);
		}
		return new ModelAndView("lostSalesReport",model);
	}
	
	@RequestMapping(value = "/profitReport", method = RequestMethod.GET)
	public ModelAndView profitReport(@ModelAttribute("reportForm") ReportForm reportForm) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			Calendar c = Calendar.getInstance();
			Date d=c.getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String date=df.format(d);
			List<Object[]> obj=salesInvoiceItemService.getProfitReportByCategoryWise(user.getFinYear(),Calendar.DATE,date);
			List<ProductReport> prList=ReportUtilities.populateProductSaleEntries(obj,"Profit");
			model.put("mode", date);
			model.put("products", prList);
		}
		return new ModelAndView("profitReport",model);
	}
}
