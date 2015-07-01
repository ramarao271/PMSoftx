package org.erp.tarak.purchasereturn;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;

public class PurchaseReturnUtilities {

	public static PurchaseReturn getPurchaseReturnModel(
			PurchaseReturnService purchaseReturnService,
			long purchaseReturnId,String finYear) {
		PurchaseReturn purchaseReturn = purchaseReturnService
				.getPurchaseReturn(purchaseReturnId,finYear);
		return purchaseReturn;
	}

	public static List<PurchaseReturnItemBean> preparePurchaseReturnItemBeans(
			List<PurchaseReturnItem> purchaseReturnItems) {
		List<PurchaseReturnItemBean> purchaseReturnItemBeans = new LinkedList<PurchaseReturnItemBean>();
		for (PurchaseReturnItem purchaseReturnItem : purchaseReturnItems) {
			PurchaseReturnItemBean purchaseReturnItemBean = new PurchaseReturnItemBean();
			purchaseReturnItemBean
					.setFinYear(purchaseReturnItem.getFinYear());
			purchaseReturnItemBean.setDescription(purchaseReturnItem
					.getDescription());
			ProductBean pb = ProductUtilities
					.prepareProductBean(purchaseReturnItem.getProductId());
			purchaseReturnItemBean.setProductId(pb);
			purchaseReturnItemBean.setPurchaseReturnId(purchaseReturnItem
					.getPurchaseReturnId());
			purchaseReturnItemBean.setQuantity(purchaseReturnItem
					.getQuantity());
			purchaseReturnItemBean.setQuantityType(purchaseReturnItem
					.getQuantityType());
			purchaseReturnItemBean.setRate(purchaseReturnItem.getRate());
			purchaseReturnItemBean.setSrNo(purchaseReturnItem.getSrNo());
			purchaseReturnItemBean.setTotalCost(purchaseReturnItem
					.getTotalCost());
			purchaseReturnItemBean.setVariantCode(purchaseReturnItem.getVariantCode());
			purchaseReturnItemBean.setVariantId(purchaseReturnItem.getVariantId());
			purchaseReturnItemBeans.add(purchaseReturnItemBean);
		}

		return purchaseReturnItemBeans;
	}

	public static PurchaseReturnBean preparePurchaseReturnBean(
			PurchaseReturn po) {
		PurchaseReturnBean pob = new PurchaseReturnBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseReturnDate(po.getPurchaseReturnDate());
		pob.setPurchaseReturnId(po.getPurchaseReturnId());
		List<PurchaseReturnItemBean> poib = preparePurchaseReturnItemBeans(po
				.getPurchaseReturnItems());
		pob.setPurchaseReturnItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po.getSupplierId()));
		pob.setTotalCost(po.getTotalCost());
		PurchaseInvoiceBean purchaseInvoiceBean = PurchaseInvoiceUtilities
				.preparePurchaseInvoiceBean(po.getPurchaseInvoice());
		pob.setPurchaseInvoiceBean(purchaseInvoiceBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setLrNo(po.getLrNo());
		return pob;
	}
	
	public static PurchaseReturnBean preparePurchaseReturnBean(
			PurchaseReturn po, SupplierService SupplierService) {
		PurchaseReturnBean pob = new PurchaseReturnBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseReturnDate(po.getPurchaseReturnDate());
		pob.setPurchaseReturnId(po.getPurchaseReturnId());
		List<PurchaseReturnItemBean> poib = preparePurchaseReturnItemBeans(po
				.getPurchaseReturnItems());
		pob.setPurchaseReturnItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId().getSupplierId(), SupplierService));
		pob.setTotalCost(po.getTotalCost());
		PurchaseInvoiceBean purchaseInvoiceBean = PurchaseInvoiceUtilities
				.preparePurchaseInvoiceBean(po.getPurchaseInvoice());
		pob.setPurchaseInvoiceBean(purchaseInvoiceBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setLrNo(po.getLrNo());
		return pob;
	}

	public static PurchaseReturn preparePurchaseReturnModel(
			PurchaseReturnBean purchaseReturnBean, UserBean user,
			SupplierService SupplierService, CategoryService categoryService,
			MeasurementService measurementService,PurchaseInvoiceService purchaseInvoiceService, VariantService variantService) {
		PurchaseReturn purchaseReturn = new PurchaseReturn();
		purchaseReturn.setPurchaseReturnDate(purchaseReturnBean
				.getPurchaseReturnDate());
		purchaseReturn.setPurchaseReturnId(purchaseReturnBean
				.getPurchaseReturnId());
		purchaseReturn.setTotalCost(purchaseReturnBean.getTotalCost());
		purchaseReturn.setFinYear(user.getFinYear());
		List<PurchaseReturnItem> purchaseReturnItems = preparePurchaseReturnItems(
				purchaseReturnBean.getPurchaseReturnItemBeans(), user,
				categoryService, measurementService,variantService);
		purchaseReturn.setPurchaseReturnItems(purchaseReturnItems);
		PurchaseInvoice po = PurchaseInvoiceUtilities.getPurchaseInvoiceModel(purchaseInvoiceService, purchaseReturnBean.getPurchaseInvoiceBean().getPurchaseInvoiceId(),user.getFinYear());
		purchaseReturn.setPurchaseInvoice(po);
		purchaseReturn.setSupplierId(po.getSupplierId());
		purchaseReturn.setPaidAmount(purchaseReturnBean.getPaidAmount());
		purchaseReturn.setLrNo(purchaseReturnBean.getLrNo());
		return purchaseReturn;
	}

	public static List<PurchaseReturnItem> preparePurchaseReturnItems(
			List<PurchaseReturnItemBean> purchaseReturnItemBeans,
			UserBean user, CategoryService categoryService,
			MeasurementService measurementService, VariantService variantService) {
		List<PurchaseReturnItem> purchaseReturnItems = new LinkedList<PurchaseReturnItem>();
		for (PurchaseReturnItemBean purchaseReturnItemBean : purchaseReturnItemBeans) {
			PurchaseReturnItem purchaseReturnItem = new PurchaseReturnItem();
			purchaseReturnItem.setFinYear(user.getFinYear());
			purchaseReturnItem.setDescription(purchaseReturnItemBean
					.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					purchaseReturnItemBean.getProductId(), categoryService,
					measurementService);
			purchaseReturnItem.setProductId(pb);

			purchaseReturnItem.setPurchaseReturnId(purchaseReturnItemBean
					.getPurchaseReturnId());
			purchaseReturnItem.setQuantity(purchaseReturnItemBean
					.getQuantity());
			purchaseReturnItem.setQuantityType(purchaseReturnItemBean
					.getQuantityType());
			purchaseReturnItem.setRate(purchaseReturnItemBean.getRate());
			purchaseReturnItem.setSrNo(purchaseReturnItemBean.getSrNo());
			purchaseReturnItem.setTotalCost(purchaseReturnItemBean
					.getTotalCost());
			purchaseReturnItem.setVariantCode(purchaseReturnItemBean.getVariantCode());
			purchaseReturnItem.setVariantId(purchaseReturnItemBean.getVariantId());
			purchaseReturnItems.add(purchaseReturnItem);
		}

		return purchaseReturnItems;
	}

	public static List<PurchaseReturnBean> prepareListofPurchaseReturnBean(
			List<PurchaseReturn> purchaseReturns,
			SupplierService SupplierService) {
		List<PurchaseReturnBean> beans = new LinkedList<PurchaseReturnBean>();
		for (PurchaseReturn po : purchaseReturns) {
			PurchaseReturnBean pob = preparePurchaseReturnBean(po, SupplierService);
			beans.add(pob);
		}

		return beans;
	}
	public static List<PurchaseReturnBean> prepareListofPurchaseReturnBean(
			List<PurchaseReturn> purchaseReturns) {
		List<PurchaseReturnBean> beans = new LinkedList<PurchaseReturnBean>();
		for (PurchaseReturn po : purchaseReturns) {
			PurchaseReturnBean pob = preparePurchaseReturnBean(po);
			beans.add(pob);
		}

		return beans;
	}

	public static PurchaseReturn preparePurchaseReturnModel(
			PurchaseReturnBean purchaseReturnBean) {
		PurchaseReturn purchaseReturn=new PurchaseReturn();
		purchaseReturn.setPurchaseReturnDate(purchaseReturnBean
				.getPurchaseReturnDate());
		purchaseReturn.setPurchaseReturnId(purchaseReturnBean
				.getPurchaseReturnId());
		purchaseReturn.setTotalCost(purchaseReturnBean.getTotalCost());
		purchaseReturn.setFinYear(purchaseReturnBean.getFinYear());
		List<PurchaseReturnItem> purchaseReturnItems = preparePurchaseReturnItems(
				purchaseReturnBean.getPurchaseReturnItemBeans());
		purchaseReturn.setPurchaseReturnItems(purchaseReturnItems);
		purchaseReturn.setPurchaseInvoice(PurchaseInvoiceUtilities.preparePurchaseInvoiceModel(purchaseReturnBean.getPurchaseInvoiceBean()));
		purchaseReturn.setSupplierId(SupplierUtilities.prepareSupplierModel(purchaseReturnBean.getSupplierBean()));
		purchaseReturn.setPaidAmount(purchaseReturnBean.getPaidAmount());
		purchaseReturn.setLrNo(purchaseReturnBean.getLrNo());
		
		
		return purchaseReturn;
	}

	private static List<PurchaseReturnItem> preparePurchaseReturnItems(
			List<PurchaseReturnItemBean> purchaseReturnItemBeans) {
		List<PurchaseReturnItem> purchaseReturnItems = new LinkedList<PurchaseReturnItem>();
		for (PurchaseReturnItemBean purchaseReturnItemBean : purchaseReturnItemBeans) {
			PurchaseReturnItem purchaseReturnItem = new PurchaseReturnItem();
			purchaseReturnItem.setFinYear(purchaseReturnItemBean.getFinYear());
			purchaseReturnItem.setDescription(purchaseReturnItemBean
					.getDescription());
			purchaseReturnItem.setProductId(ProductUtilities.prepareProductModel(purchaseReturnItemBean.getProductId()));

			purchaseReturnItem.setPurchaseReturnId(purchaseReturnItemBean
					.getPurchaseReturnId());
			purchaseReturnItem.setQuantity(purchaseReturnItemBean
					.getQuantity());
			purchaseReturnItem.setQuantityType(purchaseReturnItemBean
					.getQuantityType());
			purchaseReturnItem.setRate(purchaseReturnItemBean.getRate());
			purchaseReturnItem.setSrNo(purchaseReturnItemBean.getSrNo());
			purchaseReturnItem.setTotalCost(purchaseReturnItemBean
					.getTotalCost());
			purchaseReturnItem.setVariantCode(purchaseReturnItemBean.getVariantCode());
			purchaseReturnItem.setVariantId(purchaseReturnItemBean.getVariantId());
			purchaseReturnItems.add(purchaseReturnItem);
		}

		return purchaseReturnItems;
	}

}
