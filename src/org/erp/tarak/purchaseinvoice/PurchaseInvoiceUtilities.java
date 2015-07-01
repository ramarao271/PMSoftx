package org.erp.tarak.purchaseinvoice;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseorder.PurchaseOrder;
import org.erp.tarak.purchaseorder.PurchaseOrderBean;
import org.erp.tarak.purchaseorder.PurchaseOrderService;
import org.erp.tarak.purchaseorder.PurchaseOrderUtilities;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;

public class PurchaseInvoiceUtilities {

	public static PurchaseInvoice getPurchaseInvoiceModel(
			PurchaseInvoiceService purchaseInvoiceService,
			long purchaseInvoiceId,String finYear) {
		PurchaseInvoice purchaseInvoice = purchaseInvoiceService
				.getPurchaseInvoice(purchaseInvoiceId,finYear);
		return purchaseInvoice;
	}

	public static List<PurchaseInvoiceItemBean> preparePurchaseInvoiceItemBeans(
			List<PurchaseInvoiceItem> purchaseInvoiceItems) {
		List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans = new LinkedList<PurchaseInvoiceItemBean>();
		for (PurchaseInvoiceItem purchaseInvoiceItem : purchaseInvoiceItems) {
			PurchaseInvoiceItemBean purchaseInvoiceItemBean = new PurchaseInvoiceItemBean();
			purchaseInvoiceItemBean
					.setFinYear(purchaseInvoiceItem.getFinYear());
			purchaseInvoiceItemBean.setDescription(purchaseInvoiceItem
					.getDescription());
			ProductBean pb = ProductUtilities
					.prepareProductBean(purchaseInvoiceItem.getProductId());
			purchaseInvoiceItemBean.setProductId(pb);
			purchaseInvoiceItemBean.setPurchaseInvoiceId(purchaseInvoiceItem
					.getPurchaseInvoiceId());
			purchaseInvoiceItemBean.setQuantity(purchaseInvoiceItem
					.getQuantity());
			purchaseInvoiceItemBean.setQuantityType(purchaseInvoiceItem
					.getQuantityType());
			purchaseInvoiceItemBean.setRate(purchaseInvoiceItem.getRate());
			purchaseInvoiceItemBean.setSrNo(purchaseInvoiceItem.getSrNo());
			purchaseInvoiceItemBean.setTotalCost(purchaseInvoiceItem
					.getTotalCost());
			purchaseInvoiceItemBean.setVariantCode(purchaseInvoiceItem.getVariantCode());
			purchaseInvoiceItemBean.setVariantId(purchaseInvoiceItem.getVariantId());
			purchaseInvoiceItemBeans.add(purchaseInvoiceItemBean);
		}

		return purchaseInvoiceItemBeans;
	}

	public static PurchaseInvoiceBean preparePurchaseInvoiceBean(
			PurchaseInvoice po) {
		PurchaseInvoiceBean pob = new PurchaseInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseInvoiceDate(po.getPurchaseInvoiceDate());
		pob.setPurchaseInvoiceId(po.getPurchaseInvoiceId());
		List<PurchaseInvoiceItemBean> poib = preparePurchaseInvoiceItemBeans(po
				.getPurchaseInvoiceItems());
		pob.setPurchaseInvoiceItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po.getSupplierId()));
		pob.setTotalCost(po.getTotalCost());
		PurchaseOrderBean purchaseOrderBean=PurchaseOrderUtilities.preparePurchaseOrderBean(po.getPurchaseOrder());
		pob.setPurchaseOrderBean(purchaseOrderBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setReturnAmount(po.getReturnAmount());
		pob.setLrNo(po.getLrNo());
		pob.setDiscountPercent(po.getDiscountPercent());
		pob.setDiscountedAmount(po.getDiscountedAmount());
		pob.setInvoiceAmount(po.getInvoiceAmount());
		pob.setProcessed(po.isProcessed());
		return pob;
	}
	
	public static PurchaseInvoiceBean preparePurchaseInvoiceBean(
			PurchaseInvoice po, SupplierService supplierService) {
		PurchaseInvoiceBean pob = new PurchaseInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseInvoiceDate(po.getPurchaseInvoiceDate());
		pob.setPurchaseInvoiceId(po.getPurchaseInvoiceId());
		List<PurchaseInvoiceItemBean> poib = preparePurchaseInvoiceItemBeans(po
				.getPurchaseInvoiceItems());
		pob.setPurchaseInvoiceItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId().getSupplierId(), supplierService));
		pob.setTotalCost(po.getTotalCost());
		PurchaseOrderBean purchaseOrderBean=PurchaseOrderUtilities.preparePurchaseOrderBean(po.getPurchaseOrder());
		pob.setPaidAmount(po.getPaidAmount());
		pob.setReturnAmount(po.getReturnAmount());
		pob.setLrNo(po.getLrNo());
		pob.setDiscountPercent(po.getDiscountPercent());
		pob.setDiscountedAmount(po.getDiscountedAmount());
		pob.setInvoiceAmount(po.getInvoiceAmount());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static PurchaseInvoice preparePurchaseInvoiceModel(
			PurchaseInvoiceBean purchaseInvoiceBean, UserBean user,
			SupplierService supplierService, CategoryService categoryService,
			MeasurementService measurementService,PurchaseOrderService purchaseOrderService, VariantService variantService) {
		PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
		purchaseInvoice.setPurchaseInvoiceDate(purchaseInvoiceBean
				.getPurchaseInvoiceDate());
		purchaseInvoice.setPurchaseInvoiceId(purchaseInvoiceBean
				.getPurchaseInvoiceId());
		purchaseInvoice.setTotalCost(purchaseInvoiceBean.getTotalCost());
		purchaseInvoice.setFinYear(user.getFinYear());
		List<PurchaseInvoiceItem> purchaseInvoiceItems = preparePurchaseInvoiceItems(
				purchaseInvoiceBean.getPurchaseInvoiceItemBeans(), user,
				categoryService, measurementService,variantService);
		purchaseInvoice.setPurchaseInvoiceItems(purchaseInvoiceItems);
		PurchaseOrder po=PurchaseOrderUtilities.getPurchaseOrderModel(purchaseOrderService, purchaseInvoiceBean.getPurchaseOrderBean().getPurchaseOrderId(), user.getFinYear());
		purchaseInvoice.setPurchaseOrder(po);
		purchaseInvoice.setSupplierId(po.getSupplierId());
		purchaseInvoice.setPaidAmount(purchaseInvoiceBean.getPaidAmount());
		purchaseInvoice.setLrNo(purchaseInvoiceBean.getLrNo());
		purchaseInvoice.setReturnAmount(purchaseInvoiceBean.getReturnAmount());
		purchaseInvoice.setInvoiceAmount(purchaseInvoiceBean.getInvoiceAmount());
		purchaseInvoice.setDiscountPercent(purchaseInvoiceBean.getDiscountPercent());
		purchaseInvoice.setDiscountedAmount(purchaseInvoiceBean.getDiscountedAmount());
		purchaseInvoice.setProcessed(purchaseInvoiceBean.isProcessed());
		return purchaseInvoice;
	}

	public static List<PurchaseInvoiceItem> preparePurchaseInvoiceItems(
			List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans,
			UserBean user, CategoryService categoryService,
			MeasurementService measurementService, VariantService variantService) {
		List<PurchaseInvoiceItem> purchaseInvoiceItems = new LinkedList<PurchaseInvoiceItem>();
		for (PurchaseInvoiceItemBean purchaseInvoiceItemBean : purchaseInvoiceItemBeans) {
			PurchaseInvoiceItem purchaseInvoiceItem = new PurchaseInvoiceItem();
			purchaseInvoiceItem.setFinYear(user.getFinYear());
			purchaseInvoiceItem.setDescription(purchaseInvoiceItemBean
					.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					purchaseInvoiceItemBean.getProductId(), categoryService,
					measurementService);
			purchaseInvoiceItem.setProductId(pb);

			purchaseInvoiceItem.setPurchaseInvoiceId(purchaseInvoiceItemBean
					.getPurchaseInvoiceId());
			purchaseInvoiceItem.setQuantity(purchaseInvoiceItemBean
					.getQuantity());
			purchaseInvoiceItem.setQuantityType(purchaseInvoiceItemBean
					.getQuantityType());
			purchaseInvoiceItem.setRate(purchaseInvoiceItemBean.getRate());
			purchaseInvoiceItem.setSrNo(purchaseInvoiceItemBean.getSrNo());
			purchaseInvoiceItem.setTotalCost(purchaseInvoiceItemBean
					.getTotalCost());
			purchaseInvoiceItem.setVariantCode(purchaseInvoiceItemBean.getVariantCode());
			purchaseInvoiceItem.setVariantId(purchaseInvoiceItemBean.getVariantId());
			purchaseInvoiceItems.add(purchaseInvoiceItem);
		}

		return purchaseInvoiceItems;
	}

	public static List<PurchaseInvoiceBean> prepareListofPurchaseInvoiceBean(
			List<PurchaseInvoice> purchaseInvoices,
			SupplierService supplierService) {
		List<PurchaseInvoiceBean> beans = new LinkedList<PurchaseInvoiceBean>();
		for (PurchaseInvoice po : purchaseInvoices) {
			PurchaseInvoiceBean pob = preparePurchaseInvoiceBean(po, supplierService);
			beans.add(pob);
		}

		return beans;
	}
	public static List<PurchaseInvoiceBean> prepareListofPurchaseInvoiceBean(
			List<PurchaseInvoice> purchaseInvoices) {
		List<PurchaseInvoiceBean> beans = new LinkedList<PurchaseInvoiceBean>();
		for (PurchaseInvoice po : purchaseInvoices) {
			PurchaseInvoiceBean pob = preparePurchaseInvoiceBean(po);
			beans.add(pob);
		}

		return beans;
	}

	public static PurchaseInvoice preparePurchaseInvoiceModel(
			PurchaseInvoiceBean purchaseInvoiceBean) {
		PurchaseInvoice purchaseInvoice=new PurchaseInvoice();
		purchaseInvoice.setPurchaseInvoiceDate(purchaseInvoiceBean
				.getPurchaseInvoiceDate());
		purchaseInvoice.setPurchaseInvoiceId(purchaseInvoiceBean
				.getPurchaseInvoiceId());
		purchaseInvoice.setTotalCost(purchaseInvoiceBean.getTotalCost());
		purchaseInvoice.setFinYear(purchaseInvoiceBean.getFinYear());
		List<PurchaseInvoiceItem> purchaseInvoiceItems = preparePurchaseInvoiceItems(
				purchaseInvoiceBean.getPurchaseInvoiceItemBeans());
		purchaseInvoice.setPurchaseInvoiceItems(purchaseInvoiceItems);
		purchaseInvoice.setPurchaseOrder(PurchaseOrderUtilities.preparePurchaseOrderModel(purchaseInvoiceBean.getPurchaseOrderBean()));
		purchaseInvoice.setSupplierId(SupplierUtilities.prepareSupplierModel(purchaseInvoiceBean.getSupplierBean()));
		purchaseInvoice.setPaidAmount(purchaseInvoiceBean.getPaidAmount());
		purchaseInvoice.setLrNo(purchaseInvoiceBean.getLrNo());
		purchaseInvoice.setReturnAmount(purchaseInvoiceBean.getReturnAmount());
		purchaseInvoice.setInvoiceAmount(purchaseInvoiceBean.getInvoiceAmount());
		purchaseInvoice.setDiscountPercent(purchaseInvoiceBean.getDiscountPercent());
		purchaseInvoice.setDiscountedAmount(purchaseInvoiceBean.getDiscountedAmount());
		purchaseInvoice.setProcessed(purchaseInvoiceBean.isProcessed());
		
		return purchaseInvoice;
	}

	private static List<PurchaseInvoiceItem> preparePurchaseInvoiceItems(
			List<PurchaseInvoiceItemBean> purchaseInvoiceItemBeans) {
		List<PurchaseInvoiceItem> purchaseInvoiceItems = new LinkedList<PurchaseInvoiceItem>();
		for (PurchaseInvoiceItemBean purchaseInvoiceItemBean : purchaseInvoiceItemBeans) {
			PurchaseInvoiceItem purchaseInvoiceItem = new PurchaseInvoiceItem();
			purchaseInvoiceItem.setFinYear(purchaseInvoiceItemBean.getFinYear());
			purchaseInvoiceItem.setDescription(purchaseInvoiceItemBean
					.getDescription());
			purchaseInvoiceItem.setProductId(ProductUtilities.prepareProductModel(purchaseInvoiceItemBean.getProductId()));

			purchaseInvoiceItem.setPurchaseInvoiceId(purchaseInvoiceItemBean
					.getPurchaseInvoiceId());
			purchaseInvoiceItem.setQuantity(purchaseInvoiceItemBean
					.getQuantity());
			purchaseInvoiceItem.setQuantityType(purchaseInvoiceItemBean
					.getQuantityType());
			purchaseInvoiceItem.setRate(purchaseInvoiceItemBean.getRate());
			purchaseInvoiceItem.setSrNo(purchaseInvoiceItemBean.getSrNo());
			purchaseInvoiceItem.setTotalCost(purchaseInvoiceItemBean
					.getTotalCost());
			purchaseInvoiceItem.setVariantCode(purchaseInvoiceItemBean.getVariantCode());
			purchaseInvoiceItem.setVariantId(purchaseInvoiceItemBean.getVariantId());
			purchaseInvoiceItems.add(purchaseInvoiceItem);
		}

		return purchaseInvoiceItems;
	}

}
