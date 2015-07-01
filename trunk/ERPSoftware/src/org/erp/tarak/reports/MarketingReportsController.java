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
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalance;
import org.erp.tarak.customer.openingbalance.CustomerOpeningBalanceService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItemService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceUtilities;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceItemService;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceUtilities;
import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.supplier.SupplierBean;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalance;
import org.erp.tarak.supplier.openingbalance.SupplierOpeningBalanceService;
import org.erp.tarak.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MarketingReportsController {
	
	@Autowired
	private SalesInvoiceService salesInvoiceService;
	
	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SalesInvoiceItemService salesInvoiceItemService;
	
	@Autowired
	private PurchaseInvoiceItemService purchaseInvoiceItemService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SupplierOpeningBalanceService supplierOpeningBalanceService;
	
	@Autowired
	private CustomerOpeningBalanceService customerOpeningBalanceService;
	
	@RequestMapping(value = "/MR", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "marketingReports");
		return new ModelAndView("index", model);
	}
	@RequestMapping(value = "/customerWiseReport", method = RequestMethod.GET)
	public ModelAndView accountsReceivable() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<SalesInvoiceBean> lsib=SalesInvoiceUtilities.prepareListofSalesInvoiceBean(salesInvoiceService.listSalesInvoicesByCustomer(user.getFinYear()));
			model.put("salesInvoiceBeans",lsib );
		}
		return new ModelAndView("customerWiseReport", model);
	}
	@RequestMapping(value = "/customerSalesReport", method = RequestMethod.GET)
	public ModelAndView customerSalesReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<Long> customerIds=salesInvoiceService.listBilledCustomers(user.getFinYear());
			List<CustomerBean> customerBeans=new LinkedList<CustomerBean>();
			for(Long customerId: customerIds)
			{
				CustomerBean customerBean=CustomerUtilities.prepareCustomerBean(customerService.getCustomer(customerId));
				CustomerOpeningBalance cob=customerOpeningBalanceService.getCustomerOpeningBalance(user.getFinYear(), customerId);
				customerBean.setCurrentBalance(cob.getCurrentBalance());
				customerBeans.add(customerBean);
			}
			model.put("customers", customerBeans);
		}
		return new ModelAndView("customerSalesReport", model);
	}
	@RequestMapping(value = "/frequentlySoldItems/{customerId}", method = RequestMethod.GET)
	public ModelAndView frequentlySoldItems(@PathVariable long customerId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<Object[]> objects=salesInvoiceItemService.listFrequesntlyProductsByCustomer(customerId,user.getFinYear());
			List<ProductBean> productBeans=new LinkedList<ProductBean>();
			List<CustomerBean> customerBeans=new LinkedList<CustomerBean>();
			for(Object[] obj : objects)
			{
				CustomerBean customerBean=CustomerUtilities.prepareCustomerBean((Customer)obj[0]);
				customerBeans.add(customerBean);
				ProductBean productBean=ProductUtilities.prepareProductBean((Product)obj[1]);
				double count=(double)obj[2];
				productBean.setQty(count+"");
				productBeans.add(productBean);
			}
			model.put("customer",customerBeans);
			model.put("products",productBeans);
		}
		return new ModelAndView("frequentlyPurchasedItems", model);
	}
	
	@RequestMapping(value = "/supplierWiseReport", method = RequestMethod.GET)
	public ModelAndView accountsPayable() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<PurchaseInvoiceBean> lsib=PurchaseInvoiceUtilities.prepareListofPurchaseInvoiceBean(purchaseInvoiceService.listPurchaseInvoicesBySupplier(user.getFinYear()));
			model.put("purchaseInvoiceBeans",lsib );
		}
		return new ModelAndView("supplierWiseReport", model);
	}
	@RequestMapping(value = "/supplierPurchaseReport", method = RequestMethod.GET)
	public ModelAndView supplierPurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<Long> supplierIds=purchaseInvoiceService.listBilledSuppliers(user.getFinYear());
			List<SupplierBean> supplierBeans=new LinkedList<SupplierBean>();
			for(Long supplierId: supplierIds)
			{
				SupplierBean supplierBean=SupplierUtilities.prepareSupplierBean(supplierService.getSupplier(supplierId));
				SupplierOpeningBalance cob=supplierOpeningBalanceService.getSupplierOpeningBalance(user.getFinYear(), supplierId);
				supplierBean.setCurrentBalance(cob.getCurrentBalance());
				supplierBeans.add(supplierBean);
			}
			model.put("suppliers", supplierBeans);
		}
		return new ModelAndView("supplierPurchaseReport", model);
	}
	@RequestMapping(value = "/frequentlyPurchasedItems/{supplierId}", method = RequestMethod.GET)
	public ModelAndView frequentlyPurchasedItems(@PathVariable long supplierId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<Object[]> objects=purchaseInvoiceItemService.listFrequesntlyProductsBySupplier(supplierId,user.getFinYear());
			List<ProductBean> productBeans=new LinkedList<ProductBean>();
			List<SupplierBean> supplierBeans=new LinkedList<SupplierBean>();
			for(Object[] obj : objects)
			{
				SupplierBean supplierBean=SupplierUtilities.prepareSupplierBean((Supplier)obj[0]);
				supplierBeans.add(supplierBean);
				ProductBean productBean=ProductUtilities.prepareProductBean((Product)obj[1]);
				double count=(double)obj[2];
				productBean.setQty(count+"");
				productBeans.add(productBean);
			}
			model.put("supplier",supplierBeans);
			model.put("products",productBeans);
		}
		return new ModelAndView("frequentlyPurchasedItems", model);
	}	
}
