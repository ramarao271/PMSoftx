package org.erp.tarak.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.product.ProductService;
import org.erp.tarak.salesinvoice.SalesInvoiceItemService;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.user.UserBean;
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
	private HttpSession session;
	
	@Autowired
	private SalesInvoiceItemService salesInvoiceItemService;
	
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
		}
		return new ModelAndView("categoryWiseReport", model);
	}

}
