package org.erp.tarak.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItemService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceItemService;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.variant.VariantReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductReportsController {
	
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

	@RequestMapping(value = "/PR", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "productReports");
		return new ModelAndView("index", model);
	}
	@RequestMapping(value = "/categoryWiseReport", method = RequestMethod.GET)
	public ModelAndView categoryWiseReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<CategoryReport> crs=salesInvoiceItemService.getSalesReportByCategory(user.getFinYear());
			model.put("cats",crs );
			model.put("operation", "Sales");
		}
		return new ModelAndView("categoryWiseReport", model);
	}

	@RequestMapping(value = "/categoryWisePurchaseReport", method = RequestMethod.GET)
	public ModelAndView categoryWisePurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<CategoryReport> crs=purchaseInvoiceItemService.getPurchaseReportByCategory(user.getFinYear());
			model.put("cats",crs );
			model.put("operation", "Purchase");
		}
		return new ModelAndView("categoryWiseReport", model);
	}

	
	@RequestMapping(value = "/variantWiseReport", method = RequestMethod.GET)
	public ModelAndView variantWiseReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<VariantReport> crs=salesInvoiceItemService.getSalesReportByVariant(user.getFinYear());
			model.put("cats",crs );
			model.put("operation", "Sales");
		}
		return new ModelAndView("variantWiseReport", model);
	}
	
	@RequestMapping(value = "/variantWisePurchaseReport", method = RequestMethod.GET)
	public ModelAndView variantWisePurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null)
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<VariantReport> crs=purchaseInvoiceItemService.getPurchaseReportByVariant(user.getFinYear());
			model.put("cats",crs );
			model.put("operation", "Purchase");
		}
		return new ModelAndView("variantWiseReport", model);
	}
	
	
	@RequestMapping(value = "/frequentProduct", method = RequestMethod.GET)
	public ModelAndView frequentProduct() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ProductBean> products=ProductUtilities.prepareListofProductBean(productService.listProductsBySold());
		Iterator<ProductBean> iter=products.iterator();
		while(iter.hasNext())
		{
			ProductBean pb=iter.next();
			double qty=0;
			for(VariantBean vb: pb.getVariantBeans())
			{
				qty+=vb.getSold();
			}
			if(qty>0)
			{
				pb.setSoldVariants(qty);
			}
			else
			{
				iter.remove();
			}
			
		}
		model.put("products",products);
		return new ModelAndView("frequentProduct", model);
	}

}
