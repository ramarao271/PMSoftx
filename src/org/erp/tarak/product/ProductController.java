package org.erp.tarak.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.category.CategoryBean;
import org.erp.tarak.category.CategoryService;
import org.erp.tarak.category.CategoryUtilities;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.salesinvoice.SalesInvoiceItemService;
import org.erp.tarak.stage.Stage;
import org.erp.tarak.stage.StageBean;
import org.erp.tarak.stageProperties.StagePropertiesService;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.variantProperties.VariantPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private StagePropertiesService stagePropertiesService;

	@Autowired
	private MeasurementService measurementService;

	@Autowired
	private HttpSession session;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private VariantPropertiesService variantPropertiesService;

	@Autowired
	private SalesInvoiceItemService salesInvoiceItemService;

	@RequestMapping(value = "/addImage", method = RequestMethod.POST)
	public ModelAndView addImage(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) throws Exception {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		MultipartFile file = productBean.getImage();
		if (null != file) {
			String fileName = file.getOriginalFilename();
			byte[] bytes = file.getBytes();

			// Creating the directory to store file
			File dir = new File(servletContext.getContextPath()
					+ File.separator + "tmpFiles");
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator
					+ fileName);
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			model.put("image", dir.getAbsolutePath() + File.separator
					+ fileName);
			productBean.setImagePath(dir.getAbsolutePath() + File.separator
					+ fileName);
		}

		model.put("productBean", productBean);
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(
			@ModelAttribute("productBean") @Valid ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("product", productBean);
		String variantType = productBean.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		boolean isProductExists = productBean.getProductId() == 0 ? ProductUtilities
				.isProductExists(productService, productBean.getProductName())
				: false;
		if (result.hasErrors() || isProductExists) {
			if (isProductExists) {
				model.put("message",
						"Product with name " + productBean.getProductName()
								+ " already exists");
			}
			return new ModelAndView("product", model);

		}
		int varQtyCount = 0;
		if (productBean.getVariantBeans() != null
				&& productBean.getVariantBeans().size() > 0) {
			for (VariantBean vb : productBean.getVariantBeans()) {
				varQtyCount += vb.getQuantity();
			}
		}
		if (varQtyCount > productBean.getQuantity()) {
			model.put("message",
					"Quantity entered in Variants doesnot match with total quantity");
			return new ModelAndView("product", model);

		}

		Product product = ProductUtilities.prepareProductModel(productBean,
				categoryService, measurementService, productService);
		if (product.getProductId() == 0) {
			Stage stage = new Stage();
			stage.setStageName("Stock");
			stage.setQuantity(product.getQuantity());
			List<Stage> stages = new LinkedList<Stage>();
			stages.add(stage);
			stages.addAll(product.getStages());
			product.setStages(stages);
		}
		ProductUtilities.saveProduct(product, productService);
		try {
			MultipartFile multipartFile = productBean.getImage();
			String fileExt = multipartFile.getOriginalFilename().substring(
					multipartFile.getOriginalFilename().lastIndexOf("."),
					multipartFile.getOriginalFilename().length());
			File dest = new File(servletContext.getRealPath("resources/images")
					+ File.separator + product.getProductCode() + fileExt);
			product.setImagePath("/ERPSoftware/resources/images"
					+ File.separator + product.getProductCode() + fileExt);
			multipartFile.transferTo(dest);
			ProductUtilities.saveProduct(product, productService);
		} catch (Exception e) {

		}
		model.put("message", "Product details saved successfully");
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		return new ModelAndView("productsList", model);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView listProducts() {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		return new ModelAndView("productsList", model);
	}

	@RequestMapping(value = "/productStage", method = RequestMethod.GET)
	public ModelAndView productStage() {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		model.put("stages", stagePropertiesService.listStagePropertiess());
		return new ModelAndView("productStage", model);
	}

	@RequestMapping(value = "/changeproductstage", method = RequestMethod.GET)
	public ModelAndView changeproductstage(
			@ModelAttribute("productBean") @Valid ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		Product product = ProductUtilities.prepareProductModel(productBean,
				categoryService, measurementService, productService);
		ProductUtilities.saveProduct(product, productService);
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		model.put("stages", stagePropertiesService.listStagePropertiess());
		return new ModelAndView("productStage", model);
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		productBean.setCategoryBean(categoryBean);
		VariantBean variantBean = new VariantBean();
		List<VariantBean> variantBeans = new LinkedList<VariantBean>();
		variantBeans.add(variantBean);
		productBean.setVariantBeans(variantBeans);
		String variantType = productBean.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		model.put("operation", "Add");
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = "/ProductSelectionList/{type}", method = RequestMethod.GET)
	public ModelAndView selectProduct(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result, @PathVariable String type) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		productBean.setCategoryBean(categoryBean);
		model.put("type", type);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		return new ModelAndView("ProductSelectionList", model);
	}

	@RequestMapping(value = "/ProductSelectionListType/{type}/{category}", method = RequestMethod.POST)
	public ModelAndView selectProductDisplay(@PathVariable long category,
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result, @PathVariable String type) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		productBean.setCategoryBean(categoryBean);
		model.put("type", type);
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService
						.listProductsbyCategory(category)));
		model.put("categoryId", category);
		return new ModelAndView("ProductSelectionList", model);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "product");
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/product/index", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "product");
		return new ModelAndView("index", model);

	}

	@RequestMapping(value = "/addVariant", method = RequestMethod.POST)
	public ModelAndView addVariant(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		VariantBean variantBean = new VariantBean();
		List<VariantBean> variantBeans = new LinkedList<VariantBean>();
		if (productBean.getVariantBeans() != null) {
			variantBeans.addAll(productBean.getVariantBeans());
		}
		variantBeans.add(variantBean);
		productBean.setVariantBeans(variantBeans);
		model.put("productBean", productBean);
		String variantType = productBean.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = "/addStage", method = RequestMethod.POST)
	public ModelAndView addStage(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		StageBean stageBean = new StageBean();
		List<StageBean> stageBeans = new LinkedList<StageBean>();
		if (productBean.getStageBeans() != null) {
			stageBeans.addAll(productBean.getStageBeans());
		}
		stageBeans.add(stageBean);
		productBean.setStageBeans(stageBeans);
		model.put("productBean", productBean);
		String variantType = productBean.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		model.put("sProperties", stagePropertiesService.listStagePropertiess());

		model.put("operation", "stage");
		return new ModelAndView("productStageEdit", model);
	}

	@RequestMapping(value = "/loadVariant/{variantType}", method = RequestMethod.POST)
	public ModelAndView loadVariant(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result, @PathVariable String variantType) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("productBean", productBean);
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		productService.deleteProduct(ProductUtilities.prepareProductModel(
				productBean, categoryService, measurementService));
		model.put("product", null);
		model.put("products", ProductUtilities
				.prepareListofProductBean(productService.listProducts()));
		return new ModelAndView("productsList", model);
	}

	@RequestMapping(value = "/deleteVariant/{variantId}", method = RequestMethod.POST)
	public ModelAndView deleteVariant(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result, @PathVariable long variantId) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		Iterator<VariantBean> vIterator = productBean.getVariantBeans()
				.iterator();
		while (vIterator.hasNext()) {
			VariantBean variantBean = vIterator.next();
			if (variantId == variantBean.getVariantId()) {
				vIterator.remove();
			}
		}
		String variantType = productBean.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = { "/editProduct" }, method = RequestMethod.GET)
	public ModelAndView editProduct(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		ProductBean pb = null;
		
			pb = ProductUtilities.prepareProductBean(productService
					.getProduct(productBean.getProductId()));
		Iterator<VariantBean> vi = pb.getVariantBeans().iterator();
		while (vi.hasNext()) {
			VariantBean vb = vi.next();
			if (vb.getVariantId() == 0) {
				vi.remove();
			}
		}
		model.put("productBean", pb);

		String variantType = pb.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		
			model.put("sProperties",
					stagePropertiesService.listStagePropertiess());
		return new ModelAndView("product", model);
	}

	@RequestMapping(value = { "/changeProductStageAction/{id}" }, method = RequestMethod.GET)
	public ModelAndView changeProductStageAction(
			@ModelAttribute("productBean") ProductBean productBean,
			BindingResult result, @PathVariable long id) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);

		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		ProductBean pb = null;
		if (id > 0) {
			pb = ProductUtilities.prepareProductBean(productService
					.getProduct(id));

		}
		model.put("productBean", pb);
		String variantType = pb.getVariantType();
		model.put("vProperties", variantPropertiesService
				.getVariantPropertiesByType(variantType));
		if (id > 0) {
			model.put("operation", "stage");
		}
		return new ModelAndView("productStageEdit", model);
	}

	@RequestMapping(value = "/saveProductStages/{id}", method = RequestMethod.POST)
	public ModelAndView saveProductStage(
			@ModelAttribute("productBean") @Valid ProductBean productBean,
			BindingResult result,@PathVariable long id) {
		Map<String, Object> model = ProductUtilities.getInputsInMap(
				productService, categoryService, measurementService,
				variantPropertiesService, stagePropertiesService);
		Product product=productService.getProduct(productBean.getProductId());
		List<StageBean> stageBeans=productBean.getStageBeans();
		if(stageBeans!=null && stageBeans.size()>0)
		{
			double value=0;
			for(StageBean stageBean: stageBeans)
			{
				if(id==stageBean.getStageId())
				{
					value=stageBean.getQuantity();
					break;
				}
			}
			boolean flag=false;
			for(Stage stage: product.getStages())
			{
				if(id==stage.getStageId())
				{
					stage.setQuantity(stage.getQuantity()-value);
					flag=true;
					continue;
				}
				if(flag)
				{
					stage.setQuantity(stage.getQuantity()+value);
					flag=false;
					break;
				}
			}
			productService.addProduct(product);
			ProductBean pb=ProductUtilities.prepareProductBean(product);
			model.put("productBean", pb);
		}
		return new ModelAndView("productStageEdit", model);
	}
	@RequestMapping(value = "/productWiseSales/{id}", method = RequestMethod.GET)
	public ModelAndView productWiseSales(@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			List<Object[]> objects = salesInvoiceItemService
					.listSalesInvoiceItemsByCategory(id, user.getFinYear());
			List<ProductBean> productBeans = new LinkedList<ProductBean>();
			for (Object[] obj : objects) {
				Product product = (Product) obj[0];
				String qty = (double) obj[1]+product.getMeasurement().getMeasurementName();
				double total = (double) obj[2];
				ProductBean productBean = ProductUtilities
						.prepareProductBean(product);
				productBean.setQty(qty);
				productBean.setTotalCost(total);
				productBeans.add(productBean);
			}
			model.put("products", productBeans);
		}
		return new ModelAndView("productWiseSales", model);

	}
}
