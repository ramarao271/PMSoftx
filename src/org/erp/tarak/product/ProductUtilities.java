package org.erp.tarak.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.erp.tarak.category.Category;
import org.erp.tarak.category.CategoryBean;
import org.erp.tarak.category.CategoryService;
import org.erp.tarak.category.CategoryUtilities;
import org.erp.tarak.deliverychallan.DeliveryChallan;
import org.erp.tarak.deliverychallan.DeliveryChallanBean;
import org.erp.tarak.deliverychallan.DeliveryChallanItem;
import org.erp.tarak.deliverychallan.DeliveryChallanItemBean;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.Measurement;
import org.erp.tarak.measurement.MeasurementBean;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.measurement.MeasurementUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItem;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItemBean;
import org.erp.tarak.purchaseorder.PurchaseOrder;
import org.erp.tarak.purchaseorder.PurchaseOrderBean;
import org.erp.tarak.purchaseorder.PurchaseOrderItem;
import org.erp.tarak.purchaseorder.PurchaseOrderItemBean;
import org.erp.tarak.purchaseorder.PurchaseOrderUtilities;
import org.erp.tarak.purchasereturn.PurchaseReturn;
import org.erp.tarak.purchasereturn.PurchaseReturnBean;
import org.erp.tarak.purchasereturn.PurchaseReturnItem;
import org.erp.tarak.purchasereturn.PurchaseReturnItemBean;
import org.erp.tarak.purchasereturn.PurchaseReturnUtilities;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceItem;
import org.erp.tarak.salesinvoice.SalesInvoiceItemBean;
import org.erp.tarak.salesorder.SalesOrder;
import org.erp.tarak.salesorder.SalesOrderBean;
import org.erp.tarak.salesorder.SalesOrderItem;
import org.erp.tarak.salesorder.SalesOrderItemBean;
import org.erp.tarak.salesorder.SalesOrderUtilities;
import org.erp.tarak.salesreturn.SalesReturn;
import org.erp.tarak.salesreturn.SalesReturnBean;
import org.erp.tarak.salesreturn.SalesReturnItem;
import org.erp.tarak.salesreturn.SalesReturnItemBean;
import org.erp.tarak.salesreturn.SalesReturnUtilities;
import org.erp.tarak.stage.Stage;
import org.erp.tarak.stage.StageBean;
import org.erp.tarak.stage.StageUtilities;
import org.erp.tarak.stageProperties.StagePropertiesService;
import org.erp.tarak.variant.Variant;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.variant.VariantService;
import org.erp.tarak.variant.VariantUtilities;
import org.erp.tarak.variantProperties.VariantPropertiesService;

public class ProductUtilities {

	static List<String> variantTypes = new LinkedList<String>();
	static List<String> productionTypes = new LinkedList<String>();

	static {
		variantTypes.add("Select Variant");
		variantTypes.add(ERPConstants.VT_COLOR);
		variantTypes.add(ERPConstants.VT_MATERIAL);
		variantTypes.add(ERPConstants.VT_SIZE);
		productionTypes.add(ERPConstants.PT_PURCHASED);
		productionTypes.add(ERPConstants.PT_OWN_PRODUCTION);
		productionTypes.add(ERPConstants.PT_RAW_MATERIAL);
	}

	public static List<ProductBean> prepareListofProductBean(
			List<Product> products) {
		List<ProductBean> beans = null;
		if (products != null && !products.isEmpty()) {
			beans = new ArrayList<ProductBean>();
			ProductBean bean = null;
			for (Product product : products) {
				bean = prepareProductBean(product);
				beans.add(bean);
			}
		}
		return beans;
	}

	public static ProductBean prepareProductBean(Product product) {
		ProductBean bean = new ProductBean();
		if (product.getCategory() != null) {
			CategoryBean categoryBean = CategoryUtilities
					.prepareCategoryBean(product.getCategory());
			bean.setCategoryBean(categoryBean);
		}
		if (product.getMeasurement() != null) {
			MeasurementBean measurementBean = MeasurementUtilities
					.prepareMeasurementBean(product.getMeasurement());
			bean.setMeasurementBean(measurementBean);
		}
		bean.setGroupId(product.getGroupId());
		bean.setProductCode(product.getProductCode());
		bean.setProductId(product.getProductId());
		bean.setProductName(product.getProductName());
		bean.setQuantity(product.getQuantity());
		bean.setCost(product.getCost());
		bean.setPrice(product.getPrice());
		bean.setPrice1(product.getPrice1());
		bean.setPrice2(product.getPrice2());
		bean.setPrice3(product.getPrice3());
		bean.setAllocated(product.getAllocated());
		bean.setOrdered(product.getOrdered());
		bean.setRequired(product.getRequired());
		bean.setSold(product.getSold());
		bean.setVariantType(product.getVariantType());
		bean.setDiscontinued(product.isDiscontinued());
		bean.setProductionType(product.getProductionType());
		bean.setImagePath(product.getImagePath());
		List<StageBean> stageBeans = new LinkedList<StageBean>();
		if (product.getStages() != null && product.getStages().size() > 0) {
			for (Stage stage : product.getStages()) {
				StageBean stageBean = StageUtilities.prepareStageBean(stage);
				stageBeans.add(stageBean);
			}
		}
		bean.setStageBeans(stageBeans);
		List<VariantBean> variantBeans = new LinkedList<VariantBean>();
		double variantQuantity = 0;
		for (Variant variant : product.getVariants()) {
			VariantBean variantBean = VariantUtilities
					.prepareVariantBean(variant);
			variantBeans.add(variantBean);
			variantQuantity += variantBean.getQuantity();
		}

		VariantBean variantBean = new VariantBean();
		variantBean.setVariantType(ERPConstants.UNASSIGNED);
		variantBean.setVariantName(ERPConstants.UNASSIGNED);
		variantBean.setQuantity(product.getQuantity() - variantQuantity);
		variantBean.setProductCode(product.getProductCode());
		variantBean.setAllocated(product.getAllocated());
		variantBean.setOrdered(product.getOrdered());
		variantBean.setRequired(product.getRequired());
		variantBean.setSold(product.getSold());
		variantBeans.add(variantBean);

		bean.setVariantBeans(variantBeans);
		return bean;
	}

	public static Product prepareProductModel(ProductBean productBean,
			CategoryService categoryService,
			MeasurementService measurementService, ProductService productService) {
		Product product = new Product();
		if (productBean.getCategoryBean() != null) {
			Category category = CategoryUtilities
					.prepareCategoryModel(productBean.getCategoryBean());
			category = categoryService.getCategory(category.getCategoryId());
			product.setCategory(category);
		}
		if (productBean.getMeasurementBean() != null) {
			Measurement measurement = MeasurementUtilities
					.prepareMeasurementModel(productBean.getMeasurementBean());
			measurement = measurementService.getMeasurement(measurement
					.getMeasurementId());
			product.setMeasurement(measurement);
		}
		String productCode = "";
		if (productBean.getProductId() > 0) {
			productCode = productBean.getProductCode();
		} else {
			productCode = getProductCode(productService, product.getCategory());
		}
		product.setProductCode(productCode);
		product.setVariantType(productBean.getVariantType());
		if (productBean.getVariantBeans() != null) {
			List<Variant> variants = new LinkedList<Variant>();
			int variantCount = 1;
			for (VariantBean variantBean : productBean.getVariantBeans()) {
				if (variantBean.getVariantName() != null
						&& !"".equals(variantBean.getVariantName())) {
					String variantCode = "";
					variantBean.setVariantType(product.getVariantType());
					if (variantBean.getVariantId() > 0) {
						variantCode = variantBean.getProductCode();
					} else {
						variantCode = getVariantCode(productCode, variantCount);
					}
					variantBean.setProductCode(variantCode);
					Variant variant = VariantUtilities
							.prepareVariantModel(variantBean);
					variants.add(variant);
					variantCount++;
				}
			}
			product.setVariants(variants);
		}
		product.setGroupId(productBean.getGroupId());
		product.setProductId(productBean.getProductId());
		product.setProductName(productBean.getProductName());
		product.setQuantity(productBean.getQuantity());
		product.setCost(productBean.getCost());
		product.setPrice(productBean.getPrice());
		product.setPrice1(productBean.getPrice1());
		product.setPrice2(productBean.getPrice2());
		product.setPrice3(productBean.getPrice3());
		product.setVariantType(productBean.getVariantType());
		product.setDiscontinued(productBean.isDiscontinued());
		product.setProductionType(productBean.getProductionType());
		product.setImagePath(productBean.getImagePath());
		List<Stage> stages = new ArrayList<Stage>();
		if (productBean.getStageBeans() != null && productBean.getStageBeans().size() > 0) {
			for (StageBean stageBean : productBean.getStageBeans()) {
				if(stageBean.getStageName()!=null && !"".equals(stageBean.getStageName()))
				{
					Stage stage = StageUtilities.prepareStageModel(stageBean);
					stages.add(stage);
				}
			}
		}
		product.setStages(stages);
		return product;
	}

	public static String getVariantCode(String productCode, int variantCount) {
		String variantCode = "";
		variantCode = ERPUtilities.fomatStringToN("" + variantCount, 2);
		variantCode = productCode + variantCode;
		return variantCode;
	}

	private static String getProductCode(ProductService productService,
			Category category) {
		Product product = productService.getLastProductByCategory(category
				.getCategoryId());
		String productCode = "";
		if (product != null) {
			productCode = ERPUtilities.incrementCode(product.getProductCode());
			productCode = ERPUtilities.fomatStringToN(productCode, 6);
		} else {
			productCode = category.getCategoryCode() + "001";
		}
		return productCode;
	}

	public static void updateProductModel(ProductBean pb,
			CategoryService categoryService,
			MeasurementService measurementService,
			VariantService variantService, ProductService productService) {
		Product product = ProductUtilities.prepareProductModel(pb,
				categoryService, measurementService, productService);
		productService.addProduct(product);
	}

	public static Product getProductModel(long productId,
			ProductService productService) {
		Product product = productService.getProduct(productId);
		return product;
	}

	public static void saveProduct(Product product,
			ProductService productService) {
		productService.addProduct(product);
	}

	public static Product prepareProductModel(ProductBean productBean,
			CategoryService categoryService,
			MeasurementService measurementService) {
		Product product = new Product();
		if (productBean.getCategoryBean() != null) {
			Category category = CategoryUtilities
					.prepareCategoryModel(productBean.getCategoryBean());
			category = categoryService.getCategory(category.getCategoryId());
			product.setCategory(category);
		}
		if (productBean.getMeasurementBean() != null) {
			Measurement measurement = MeasurementUtilities
					.prepareMeasurementModel(productBean.getMeasurementBean());
			measurement = measurementService.getMeasurement(measurement
					.getMeasurementId());
			product.setMeasurement(measurement);
		}
		product.setVariants(prepareVariantProperties(productBean,
				product.getVariantType()));
		product.setGroupId(productBean.getGroupId());
		product.setProductCode(productBean.getProductCode());
		product.setProductId(productBean.getProductId());
		product.setProductName(productBean.getProductName());
		product.setQuantity(productBean.getQuantity());
		product.setCost(productBean.getCost());
		product.setPrice(productBean.getPrice());
		product.setPrice1(productBean.getPrice1());
		product.setPrice2(productBean.getPrice2());
		product.setPrice3(productBean.getPrice3());
		product.setVariantType(productBean.getVariantType());
		product.setDiscontinued(productBean.isDiscontinued());
		product.setProductionType(productBean.getProductionType());
		product.setImagePath(productBean.getImagePath());
		List<Stage> stages = new LinkedList<Stage>();
		if (productBean.getStageBeans() != null && productBean.getStageBeans().size() > 0) {
			for (StageBean stageBean : productBean.getStageBeans()) {
				Stage stage = StageUtilities.prepareStageModel(stageBean);
				stages.add(stage);
			}
		}
		product.setStages(stages);
		return product;
	}

	private static List<Variant> prepareVariantProperties(
			ProductBean productBean, String variantType) {
		if (productBean.getVariantBeans() != null) {
			List<Variant> variants = new LinkedList<Variant>();
			for (VariantBean variantBean : productBean.getVariantBeans()) {
				if (variantBean.getVariantName() != null
						&& !"".equals(variantBean.getVariantName())) {
					Variant variant = VariantUtilities
							.prepareVariantModel(variantBean);
					variant.setVariantType(variantType);
					variants.add(variant);
				}
			}
			return variants;
		}
		return null;
	}

	public static boolean isProductExists(ProductService productService,
			String productName) {
		Product product = productService.getProductByName(productName);
		if (product != null && product.getProductId() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Variant getVariantById(Product product, long variantId) {

		for (Variant variant : product.getVariants()) {
			if (variantId == variant.getVariantId()) {
				return variant;
			}
		}
		return null;
	}

	public static Product updateVariantById(Product product, long variantId,
			double quantity, String type) {
		if (variantId == 0) {
			switch (type) {
			case ERPConstants.SALES_ORDER:
				product.setRequired(product.getRequired() + quantity);
				break;
			case ERPConstants.DELIVERY_CHALLAN:
				product.setRequired(product.getRequired() - quantity);
				product.setAllocated(product.getAllocated() + quantity);
				product.setQuantity(product.getQuantity() - quantity);
				break;
			case ERPConstants.SALES_INVOICE:
				product.setAllocated(product.getAllocated() - quantity);
				product.setSold(product.getSold() + quantity);
				break;
			case ERPConstants.PURCHASE_ORDER:
				product.setOrdered(product.getOrdered() + quantity);
				break;
			case ERPConstants.PURCHASE_INVOICE:
				product.setOrdered(product.getOrdered() - quantity);
				product.setQuantity(product.getQuantity() + quantity);
				break;
			case ERPConstants.SALES_RETURN:
				product.setSold(product.getSold() - quantity);
				product.setQuantity(product.getQuantity() + quantity);
				break;
			case ERPConstants.PURCHASE_RETURN:
				product.setQuantity(product.getQuantity() - quantity);
				break;
			}
			return product;
		}

		Iterator<Variant> iterator = product.getVariants().iterator();
		while (iterator.hasNext()) {
			Variant variant = iterator.next();
			if (variantId == variant.getVariantId()) {
				switch (type) {
				case ERPConstants.SALES_ORDER:
					variant.setRequired(variant.getRequired() + quantity);
					break;
				case ERPConstants.DELIVERY_CHALLAN:
					variant.setRequired(variant.getRequired() - quantity);
					variant.setAllocated(variant.getAllocated() + quantity);
					variant.setQuantity(variant.getQuantity() - quantity);
					product.setQuantity(product.getQuantity() - quantity);
					break;
				case ERPConstants.SALES_INVOICE:
					variant.setAllocated(variant.getAllocated() - quantity);
					variant.setSold(variant.getSold() + quantity);
					break;
				case ERPConstants.PURCHASE_ORDER:
					variant.setOrdered(variant.getOrdered() + quantity);
					break;
				case ERPConstants.PURCHASE_INVOICE:
					variant.setOrdered(variant.getOrdered() - quantity);
					variant.setQuantity(variant.getQuantity() + quantity);
					product.setQuantity(product.getQuantity() + quantity);
					break;
				case ERPConstants.SALES_RETURN:
					variant.setSold(variant.getSold() - quantity);
					variant.setQuantity(variant.getQuantity() + quantity);
					product.setQuantity(product.getQuantity() + quantity);
					break;
				case ERPConstants.PURCHASE_RETURN:
					variant.setQuantity(variant.getQuantity() - quantity);
					product.setQuantity(product.getQuantity() - quantity);
					break;
				}
				break;
			}
		}
		return product;
	}

	public static void updateRemovedItems(Object savedobject, Object object,
			ProductService productService, String type) {
		switch (type) {
		case ERPConstants.SALES_ORDER:
			SalesOrder so = (SalesOrder) savedobject;
			SalesOrderBean salesOrderBean = (SalesOrderBean) object;
			List<SalesOrderItemBean> ibList = SalesOrderUtilities
					.prepareSalesOrderItemBeans(so.getSalesOrderItems());
			Iterator<SalesOrderItemBean> iter = ibList.iterator();
			while (iter.hasNext()) {
				boolean flag = false;
				SalesOrderItemBean salesOrderItemBean = (SalesOrderItemBean) iter
						.next();
				for (SalesOrderItemBean newSalesOrderItemBean : salesOrderBean
						.getSalesOrderItemBeans()) {
					if (salesOrderItemBean.getSrNo() == newSalesOrderItemBean
							.getSrNo()) {
						flag = true;
						break;
					}
				}
				if (flag == true) {
					iter.remove();
				}
			}
			if (ibList.size() > 0) {
				for (SalesOrderItemBean soib : ibList) {
					updateProductDetailModel(
							soib.getProductId().getProductId(), productService,
							soib.getVariantId(), -soib.getQuantity(), type);
				}
			}
			break;
		case ERPConstants.PURCHASE_ORDER:
			PurchaseOrder po = (PurchaseOrder) savedobject;
			PurchaseOrderBean purchaseOrderBean = (PurchaseOrderBean) object;
			List<PurchaseOrderItemBean> ibpList = PurchaseOrderUtilities
					.preparePurchaseOrderItemBeans(po.getPurchaseOrderItems());
			Iterator<PurchaseOrderItemBean> iterp = ibpList.iterator();
			while (iterp.hasNext()) {
				boolean flag = false;
				PurchaseOrderItemBean purchaseOrderItemBean = (PurchaseOrderItemBean) iterp
						.next();
				for (PurchaseOrderItemBean newPurchaseOrderItemBean : purchaseOrderBean
						.getPurchaseOrderItemBeans()) {
					if (purchaseOrderItemBean.getSrNo() == newPurchaseOrderItemBean
							.getSrNo()) {
						flag = true;
						break;
					}
				}
				if (flag == true) {
					iterp.remove();
				}
			}
			if (ibpList.size() > 0) {
				for (PurchaseOrderItemBean soib : ibpList) {
					updateProductDetailModel(
							soib.getProductId().getProductId(), productService,
							soib.getVariantId(), -soib.getQuantity(), type);
				}
			}
			break;
		case ERPConstants.SALES_RETURN:
			SalesReturn sr = (SalesReturn) savedobject;
			SalesReturnBean salesReturnBean = (SalesReturnBean) object;
			List<SalesReturnItemBean> ibrList = SalesReturnUtilities
					.prepareSalesReturnItemBeans(sr.getSalesReturnItems());
			Iterator<SalesReturnItemBean> iterr = ibrList.iterator();
			while (iterr.hasNext()) {
				boolean flag = false;
				SalesReturnItemBean salesReturnItemBean = (SalesReturnItemBean) iterr
						.next();
				for (SalesReturnItemBean newSalesReturnItemBean : salesReturnBean
						.getSalesReturnItemBeans()) {
					if (salesReturnItemBean.getSrNo() == newSalesReturnItemBean
							.getSrNo()) {
						flag = true;
						break;
					}
				}
				if (flag == true) {
					iterr.remove();
				}
			}
			if (ibrList.size() > 0) {
				for (SalesReturnItemBean soib : ibrList) {
					updateProductDetailModel(
							soib.getProductId().getProductId(), productService,
							soib.getVariantId(), -soib.getQuantity(), type);
				}
			}
			break;
		case ERPConstants.PURCHASE_RETURN:
			PurchaseReturn pr = (PurchaseReturn) savedobject;
			PurchaseReturnBean purchaseReturnBean = (PurchaseReturnBean) object;
			List<PurchaseReturnItemBean> ibprList = PurchaseReturnUtilities
					.preparePurchaseReturnItemBeans(pr.getPurchaseReturnItems());
			Iterator<PurchaseReturnItemBean> iterpr = ibprList.iterator();
			while (iterpr.hasNext()) {
				boolean flag = false;
				PurchaseReturnItemBean purchaseReturnItemBean = (PurchaseReturnItemBean) iterpr
						.next();
				for (PurchaseReturnItemBean newPurchaseReturnItemBean : purchaseReturnBean
						.getPurchaseReturnItemBeans()) {
					if (purchaseReturnItemBean.getSrNo() == newPurchaseReturnItemBean
							.getSrNo()) {
						flag = true;
						break;
					}
				}
				if (flag == true) {
					iterpr.remove();
				}
			}
			if (ibprList.size() > 0) {
				for (PurchaseReturnItemBean soib : ibprList) {
					updateProductDetailModel(
							soib.getProductId().getProductId(), productService,
							soib.getVariantId(), -soib.getQuantity(), type);
				}
			}

		}
	}

	public static void updateProductDetails(Object item,
			ProductService productService, Object savedobject, String type,
			String operation) {
		if (item != null) {
			double newValue = 0;
			long productId = 0;
			long variantId = 0;
			switch (type) {
			case ERPConstants.SALES_ORDER:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					SalesOrderBean salesOrderBean = (SalesOrderBean) item;
					for (SalesOrderItemBean salesOrderItemBean : salesOrderBean
							.getSalesOrderItemBeans()) {
						productId = salesOrderItemBean.getProductId()
								.getProductId();
						newValue = salesOrderItemBean.getQuantity();
						variantId = salesOrderItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									salesOrderItemBean.getSrNo(), type);
							if (obj != null) {
								SalesOrderItem salesOrderItem1 = (SalesOrderItem) obj;
								if (salesOrderItem1 != null) {
									newValue = -salesOrderItem1.getQuantity()
											+ salesOrderItemBean.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
					if (savedobject != null) {
						updateRemovedItems(savedobject, salesOrderBean,
								productService, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					SalesOrder salesOrder = (SalesOrder) savedobject;
					List<SalesOrderItem> list = salesOrder.getSalesOrderItems();
					for (SalesOrderItem salesOrderItem : list) {
						productId = salesOrderItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								salesOrderItem.getVariantId(),
								-salesOrderItem.getQuantity(), type);
					}
				}
				break;
			case ERPConstants.DELIVERY_CHALLAN:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					DeliveryChallanBean deliveryChallanBean = (DeliveryChallanBean) item;
					for (DeliveryChallanItemBean deliveryChallanItemBean : deliveryChallanBean
							.getDeliveryChallanItemBeans()) {
						productId = deliveryChallanItemBean.getProductId()
								.getProductId();
						newValue = deliveryChallanItemBean.getQuantity();
						variantId = deliveryChallanItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									deliveryChallanItemBean.getSrNo(), type);
							if (obj != null) {
								DeliveryChallanItem deliveryChallanItem1 = (DeliveryChallanItem) obj;
								if (deliveryChallanItem1 != null) {
									newValue = -deliveryChallanItem1
											.getQuantity()
											+ deliveryChallanItemBean
													.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					DeliveryChallan deliveryChallan = (DeliveryChallan) savedobject;
					List<DeliveryChallanItem> list = deliveryChallan
							.getDeliveryChallanItems();
					for (DeliveryChallanItem deliveryChallanItem : list) {
						productId = deliveryChallanItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								deliveryChallanItem.getVariantId(),
								-deliveryChallanItem.getQuantity(), type);
					}
				}
				break;
			case ERPConstants.SALES_INVOICE:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					SalesInvoiceBean salesInvoiceBean = (SalesInvoiceBean) item;
					for (SalesInvoiceItemBean salesInvoiceItemBean : salesInvoiceBean
							.getSalesInvoiceItemBeans()) {
						productId = salesInvoiceItemBean.getProductId()
								.getProductId();
						newValue = salesInvoiceItemBean.getQuantity();
						variantId = salesInvoiceItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									salesInvoiceItemBean.getSrNo(), type);
							if (obj != null) {
								SalesInvoiceItem salesInvoiceItem1 = (SalesInvoiceItem) obj;
								if (salesInvoiceItem1 != null) {
									newValue = -salesInvoiceItem1.getQuantity()
											+ salesInvoiceItemBean
													.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					SalesInvoice salesInvoice = (SalesInvoice) savedobject;
					List<SalesInvoiceItem> list = salesInvoice
							.getSalesInvoiceItems();
					for (SalesInvoiceItem salesInvoiceItem : list) {
						productId = salesInvoiceItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								salesInvoiceItem.getVariantId(),
								-salesInvoiceItem.getQuantity(), type);
					}
				}
				break;
			case ERPConstants.PURCHASE_ORDER:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					PurchaseOrderBean purchaseOrderBean = (PurchaseOrderBean) item;
					for (PurchaseOrderItemBean purchaseOrderItemBean : purchaseOrderBean
							.getPurchaseOrderItemBeans()) {
						productId = purchaseOrderItemBean.getProductId()
								.getProductId();
						newValue = purchaseOrderItemBean.getQuantity();
						variantId = purchaseOrderItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									purchaseOrderItemBean.getSrNo(), type);
							if (obj != null) {
								PurchaseOrderItem purchaseOrderItem1 = (PurchaseOrderItem) obj;
								if (purchaseOrderItem1 != null) {
									newValue = -purchaseOrderItem1
											.getQuantity()
											+ purchaseOrderItemBean
													.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
					if (savedobject != null) {
						updateRemovedItems(savedobject, purchaseOrderBean,
								productService, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					PurchaseOrder purchaseOrder = (PurchaseOrder) savedobject;
					List<PurchaseOrderItem> list = purchaseOrder
							.getPurchaseOrderItems();
					for (PurchaseOrderItem purchaseOrderItem : list) {
						productId = purchaseOrderItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								purchaseOrderItem.getVariantId(),
								-purchaseOrderItem.getQuantity(), type);
					}
				}
				break;
			case ERPConstants.PURCHASE_INVOICE:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					PurchaseInvoiceBean purchaseInvoiceBean = (PurchaseInvoiceBean) item;
					for (PurchaseInvoiceItemBean purchaseInvoiceItemBean : purchaseInvoiceBean
							.getPurchaseInvoiceItemBeans()) {
						productId = purchaseInvoiceItemBean.getProductId()
								.getProductId();
						newValue = purchaseInvoiceItemBean.getQuantity();
						variantId = purchaseInvoiceItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									purchaseInvoiceItemBean.getSrNo(), type);
							if (obj != null) {
								PurchaseInvoiceItem purchaseInvoiceItem1 = (PurchaseInvoiceItem) obj;
								if (purchaseInvoiceItem1 != null) {
									newValue = -purchaseInvoiceItem1
											.getQuantity()
											+ purchaseInvoiceItemBean
													.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					PurchaseInvoice purchaseInvoice = (PurchaseInvoice) savedobject;
					List<PurchaseInvoiceItem> list = purchaseInvoice
							.getPurchaseInvoiceItems();
					for (PurchaseInvoiceItem purchaseInvoiceItem : list) {
						productId = purchaseInvoiceItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								purchaseInvoiceItem.getVariantId(),
								-purchaseInvoiceItem.getQuantity(), type);
					}
				}
				break;
			case ERPConstants.SALES_RETURN:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					SalesReturnBean salesReturnBean = (SalesReturnBean) item;
					for (SalesReturnItemBean salesReturnItemBean : salesReturnBean
							.getSalesReturnItemBeans()) {
						productId = salesReturnItemBean.getProductId()
								.getProductId();
						newValue = salesReturnItemBean.getQuantity();
						variantId = salesReturnItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									salesReturnItemBean.getSrNo(), type);
							if (obj != null) {
								SalesReturnItem salesReturnItem1 = (SalesReturnItem) obj;
								if (salesReturnItem1 != null) {
									newValue = -salesReturnItem1.getQuantity()
											+ salesReturnItemBean.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
					if (savedobject != null) {
						updateRemovedItems(savedobject, salesReturnBean,
								productService, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					SalesReturn salesReturn = (SalesReturn) savedobject;
					List<SalesReturnItem> list = salesReturn
							.getSalesReturnItems();
					for (SalesReturnItem salesReturnItem : list) {
						productId = salesReturnItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								salesReturnItem.getVariantId(),
								-salesReturnItem.getQuantity(), type);
					}
				}
				break;

			case ERPConstants.PURCHASE_RETURN:
				if (operation.equals(ERPConstants.OP_CREATE)
						|| operation.equals(ERPConstants.OP_EDIT)) {
					PurchaseReturnBean purchaseReturnBean = (PurchaseReturnBean) item;
					for (PurchaseReturnItemBean purchaseReturnItemBean : purchaseReturnBean
							.getPurchaseReturnItemBeans()) {
						productId = purchaseReturnItemBean.getProductId()
								.getProductId();
						newValue = purchaseReturnItemBean.getQuantity();
						variantId = purchaseReturnItemBean.getVariantId();
						if (savedobject != null) {
							Object obj = checkProductSaved(savedobject,
									productId, variantId,
									purchaseReturnItemBean.getSrNo(), type);
							if (obj != null) {
								PurchaseReturnItem purchaseReturnItem1 = (PurchaseReturnItem) obj;
								if (purchaseReturnItem1 != null) {
									newValue = -purchaseReturnItem1
											.getQuantity()
											+ purchaseReturnItemBean
													.getQuantity();
								}
							}
						}
						updateProductDetailModel(productId, productService,
								variantId, newValue, type);
					}
					if (savedobject != null) {
						updateRemovedItems(savedobject, purchaseReturnBean,
								productService, type);
					}
				} else if (operation.equals(ERPConstants.OP_DELETE)) {
					PurchaseReturn purchaseReturn = (PurchaseReturn) savedobject;
					List<PurchaseReturnItem> list = purchaseReturn
							.getPurchaseReturnItems();
					for (PurchaseReturnItem purchaseReturnItem : list) {
						productId = purchaseReturnItem.getProductId()
								.getProductId();
						updateProductDetailModel(productId, productService,
								purchaseReturnItem.getVariantId(),
								-purchaseReturnItem.getQuantity(), type);
					}
				}
				break;

			}
		}

	}

	private static void updateProductDetailModel(long productId,
			ProductService productService, long variantId, double newValue,
			String type) {
		Product product = ProductUtilities.getProductModel(productId,
				productService);
		product = ProductUtilities.updateVariantById(product, variantId,
				newValue, type);
		ProductUtilities.saveProduct(product, productService);

	}

	private static Object checkProductSaved(Object savedObject, long productId,
			long variantId, int srNo, String type) {
		if (savedObject != null) {
			switch (type) {
			case ERPConstants.SALES_ORDER:
				SalesOrder savedSalesOrder = (SalesOrder) savedObject;
				List<SalesOrderItem> savedSalesOrderItems = savedSalesOrder
						.getSalesOrderItems();
				for (SalesOrderItem salesOrderItem : savedSalesOrderItems) {
					if (productId == salesOrderItem.getProductId()
							.getProductId()
							&& variantId == salesOrderItem.getVariantId()
							&& srNo == salesOrderItem.getSrNo()) {
						return salesOrderItem;
					}
				}
				break;
			case ERPConstants.DELIVERY_CHALLAN:
				DeliveryChallan savedDeliveryChallan = (DeliveryChallan) savedObject;
				List<DeliveryChallanItem> savedDeliveryChallanItems = savedDeliveryChallan
						.getDeliveryChallanItems();
				for (DeliveryChallanItem deliveryChallanItem : savedDeliveryChallanItems) {
					if (productId == deliveryChallanItem.getProductId()
							.getProductId()
							&& variantId == deliveryChallanItem.getVariantId()
							&& srNo == deliveryChallanItem.getSrNo()) {
						return deliveryChallanItem;
					}
				}
				break;
			case ERPConstants.SALES_INVOICE:
				SalesInvoice savedSalesInvoice = (SalesInvoice) savedObject;
				List<SalesInvoiceItem> savedSalesInvoiceItems = savedSalesInvoice
						.getSalesInvoiceItems();
				for (SalesInvoiceItem salesInvoiceItem : savedSalesInvoiceItems) {
					if (productId == salesInvoiceItem.getProductId()
							.getProductId()
							&& variantId == salesInvoiceItem.getVariantId()
							&& srNo == salesInvoiceItem.getSrNo()) {
						return salesInvoiceItem;
					}
				}
				break;
			case ERPConstants.PURCHASE_ORDER:
				PurchaseOrder savedPurchaseOrder = (PurchaseOrder) savedObject;
				List<PurchaseOrderItem> savedPurchaseOrderItems = savedPurchaseOrder
						.getPurchaseOrderItems();
				for (PurchaseOrderItem purchaseOrderItem : savedPurchaseOrderItems) {
					if (productId == purchaseOrderItem.getProductId()
							.getProductId()
							&& variantId == purchaseOrderItem.getVariantId()
							&& srNo == purchaseOrderItem.getSrNo()) {
						return purchaseOrderItem;
					}
				}
				break;
			case ERPConstants.PURCHASE_INVOICE:
				PurchaseInvoice savedPurchaseInvoice = (PurchaseInvoice) savedObject;
				List<PurchaseInvoiceItem> savedPurchaseInvoiceItems = savedPurchaseInvoice
						.getPurchaseInvoiceItems();
				for (PurchaseInvoiceItem purchaseInvoiceItem : savedPurchaseInvoiceItems) {
					if (productId == purchaseInvoiceItem.getProductId()
							.getProductId()
							&& variantId == purchaseInvoiceItem.getVariantId()
							&& srNo == purchaseInvoiceItem.getSrNo()) {
						return purchaseInvoiceItem;
					}
				}
				break;
			case ERPConstants.SALES_RETURN:
				SalesReturn savedSalesReturn = (SalesReturn) savedObject;
				List<SalesReturnItem> savedSalesReturnItems = savedSalesReturn
						.getSalesReturnItems();
				for (SalesReturnItem salesReturnItem : savedSalesReturnItems) {
					if (productId == salesReturnItem.getProductId()
							.getProductId()
							&& variantId == salesReturnItem.getVariantId()
							&& srNo == salesReturnItem.getSrNo()) {
						return salesReturnItem;
					}
				}
				break;
			case ERPConstants.PURCHASE_RETURN:
				PurchaseReturn savedPurchaseReturn = (PurchaseReturn) savedObject;
				List<PurchaseReturnItem> savedPurchaseReturnItems = savedPurchaseReturn
						.getPurchaseReturnItems();
				for (PurchaseReturnItem purchaseReturnItem : savedPurchaseReturnItems) {
					if (productId == purchaseReturnItem.getProductId()
							.getProductId()
							&& variantId == purchaseReturnItem.getVariantId()
							&& srNo == purchaseReturnItem.getSrNo()) {
						return purchaseReturnItem;
					}
				}

			}
		}
		return null;
	}

	public static Map<String, Object> getInputsInMap(
			ProductService productService, CategoryService categoryService,
			MeasurementService measurementService,
			VariantPropertiesService variantPropertiesService, StagePropertiesService stagePropertiesService) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("variantTypes", variantTypes);
		model.put("pTypes", productionTypes);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		model.put("stages", stagePropertiesService.listStagePropertiess());
		return model;

	}

	public static Product prepareProductModel(ProductBean productBean) {
		Product product = new Product();
		product.setAllocated(productBean.getAllocated());
		product.setCategory(CategoryUtilities.prepareCategoryModel(productBean
				.getCategoryBean()));
		product.setCost(productBean.getCost());
		product.setDiscontinued(productBean.isDiscontinued());
		product.setGroupId(productBean.getGroupId());
		product.setImagePath(productBean.getImagePath());
		product.setMeasurement(MeasurementUtilities
				.prepareMeasurementModel(productBean.getMeasurementBean()));
		product.setOrdered(productBean.getOrdered());
		product.setPrice(productBean.getPrice());
		product.setPrice1(productBean.getPrice1());
		product.setPrice2(productBean.getPrice2());
		product.setPrice3(productBean.getPrice3());
		product.setProductCode(productBean.getProductCode());
		product.setProductId(productBean.getProductId());
		product.setProductionType(productBean.getProductionType());
		product.setProductName(productBean.getProductName());
		product.setQuantity(product.getQuantity());
		product.setRequired(productBean.getRequired());
		product.setSold(productBean.getSold());
		product.setVariantType(productBean.getVariantType());
		product.setVariants(prepareVariantProperties(productBean,
				product.getVariantType()));
		List<Stage> stages = new LinkedList<Stage>();
		if (productBean.getStageBeans() != null && productBean.getStageBeans().size() > 0) {
			for (StageBean stageBean : productBean.getStageBeans()) {
				Stage stage = StageUtilities.prepareStageModel(stageBean);
				stages.add(stage);
			}
		}
		product.setStages(stages);
		return product;
	}
}
