package org.erp.tarak.reports;

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
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesorder.SalesOrderService;
import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FinanceReportsController {
	
	@Autowired
	private SalesInvoiceService salesInvoiceService;

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
				customer.setTotalAmount((double)obj[1]);
				customer.setReturnAmount((double)obj[2]);
				customer.setPaidAmount((double)obj[3]);
				double balance=(double)obj[2]>0?(double)obj[2]-(double)obj[3]:(double)obj[1]-(double)obj[3];
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
				supplier.setTotalAmount((double)obj[1]);
				supplier.setReturnAmount((double)obj[2]);
				supplier.setPaidAmount((double)obj[3]);
				double balance=(double)obj[2]>0?(double)obj[2]-(double)obj[3]:(double)obj[1]-(double)obj[3];
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

}
