package org.erp.tarak.purchaseorder;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItem;
import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.user.UserBean;

public class PurchaseOrderUtilities {

	public static PurchaseOrder getPurchaseOrderModel(
			PurchaseOrderService purchaseOrderService, long purchaseOrderId,String finYear) {
		PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrder(purchaseOrderId,finYear);
		return purchaseOrder;
	}

	public static List<PurchaseOrderItemBean> preparePurchaseOrderItemBeans(
			List<PurchaseOrderItem> purchaseOrderItems) {
		List<PurchaseOrderItemBean> purchaseOrderItemBeans = new LinkedList<PurchaseOrderItemBean>();
		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
			PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();
			purchaseOrderItemBean.setFinYear(purchaseOrderItem.getFinYear());
			purchaseOrderItemBean.setDescription(purchaseOrderItem.getDescription());
			ProductBean pb = ProductUtilities.prepareProductBean(purchaseOrderItem
					.getProductId());
			purchaseOrderItemBean.setProductId(pb);
			purchaseOrderItemBean
					.setPurchaseOrderId(purchaseOrderItem.getPurchaseOrderId());
			purchaseOrderItemBean.setQuantity(purchaseOrderItem.getQuantity());
			purchaseOrderItemBean
					.setQuantityType(purchaseOrderItem.getQuantityType());
			purchaseOrderItemBean.setRate(purchaseOrderItem.getRate());
			purchaseOrderItemBean.setSrNo(purchaseOrderItem.getSrNo());
			purchaseOrderItemBean.setTotalCost(purchaseOrderItem.getTotalCost());
			purchaseOrderItemBean.setVariantCode(purchaseOrderItem.getVariantCode());
			purchaseOrderItemBean.setVariantId(purchaseOrderItem.getVariantId());
			purchaseOrderItemBean.setProcessed(purchaseOrderItem.isProcessed());
			purchaseOrderItemBeans.add(purchaseOrderItemBean);
		}
		return purchaseOrderItemBeans;
	}

	public static PurchaseOrderBean preparePurchaseOrderBean(PurchaseOrder po,
			SupplierService supplierService) {
		PurchaseOrderBean pob = new PurchaseOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseOrderDate(po.getPurchaseOrderDate());
		pob.setPurchaseOrderId(po.getPurchaseOrderId());
		List<PurchaseOrderItemBean> poib = preparePurchaseOrderItemBeans(po
				.getPurchaseOrderItems());
		pob.setPurchaseOrderItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId().getSupplierId(), supplierService));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static PurchaseOrderBean preparePurchaseOrderBean(PurchaseOrder po) {
		PurchaseOrderBean pob = new PurchaseOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchaseOrderDate(po.getPurchaseOrderDate());
		pob.setPurchaseOrderId(po.getPurchaseOrderId());
		List<PurchaseOrderItemBean> poib = preparePurchaseOrderItemBeans(po
				.getPurchaseOrderItems());
		pob.setPurchaseOrderItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static PurchaseOrder preparePurchaseOrderModel(
			PurchaseOrderBean purchaseOrderBean, UserBean user,
			SupplierService supplierService, CategoryService categoryService,
			MeasurementService measurementService) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPurchaseOrderDate(purchaseOrderBean.getPurchaseOrderDate());
		purchaseOrder.setPurchaseOrderId(purchaseOrderBean.getPurchaseOrderId());
		purchaseOrder.setTotalCost(purchaseOrderBean.getTotalCost());
		purchaseOrder.setFinYear(user.getFinYear());
		List<PurchaseOrderItem> purchaseOrderItems = preparePurchaseOrderItems(
				purchaseOrderBean.getPurchaseOrderItemBeans(),purchaseOrderBean.getPurchaseOrderId(), user, categoryService,
				measurementService);
		purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
		Supplier supplier = SupplierUtilities.getSupplierModel(supplierService,
				purchaseOrderBean.getSupplierBean().getSupplierId());
		purchaseOrder.setSupplierId(supplier);
		purchaseOrder.setProcessed(purchaseOrderBean.isProcessed());
		return purchaseOrder;
	}

	public static List<PurchaseOrderItem> preparePurchaseOrderItems(
			List<PurchaseOrderItemBean> purchaseOrderItemBeans, long purchaseOrderId, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService ) {
		List<PurchaseOrderItem> purchaseOrderItems = new LinkedList<PurchaseOrderItem>();
		for (PurchaseOrderItemBean purchaseOrderItemBean : purchaseOrderItemBeans) {
			PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
			purchaseOrderItem.setFinYear(user.getFinYear());
			purchaseOrderItem.setDescription(purchaseOrderItemBean.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					purchaseOrderItemBean.getProductId(), categoryService,
					measurementService);
			purchaseOrderItem.setProductId(pb);
			purchaseOrderItem
					.setPurchaseOrderId(purchaseOrderId);
			purchaseOrderItem.setQuantity(purchaseOrderItemBean.getQuantity());
			purchaseOrderItem
					.setQuantityType(purchaseOrderItemBean.getQuantityType());
			purchaseOrderItem.setRate(purchaseOrderItemBean.getRate());
			purchaseOrderItem.setSrNo(purchaseOrderItemBean.getSrNo());
			purchaseOrderItem.setTotalCost(purchaseOrderItemBean.getTotalCost());
			purchaseOrderItem.setVariantCode(purchaseOrderItemBean.getVariantCode());
			purchaseOrderItem.setVariantId(purchaseOrderItemBean.getVariantId());
			purchaseOrderItems.add(purchaseOrderItem);
		}

		return purchaseOrderItems;
	}

	public static List<PurchaseOrderBean> prepareListofPurchaseOrderBean(
			List<PurchaseOrder> purchaseOrders, SupplierService supplierService) {
		List<PurchaseOrderBean> beans = new LinkedList<PurchaseOrderBean>();
		for (PurchaseOrder po : purchaseOrders) {
			PurchaseOrderBean pob = preparePurchaseOrderBean(po);
			beans.add(pob);
		}
		return beans;
	}

	public static PurchaseOrder preparePurchaseOrderModel(
			PurchaseOrderBean purchaseOrderBean) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPurchaseOrderDate(purchaseOrderBean.getPurchaseOrderDate());
		purchaseOrder.setPurchaseOrderId(purchaseOrderBean.getPurchaseOrderId());
		purchaseOrder.setTotalCost(purchaseOrderBean.getTotalCost());
		purchaseOrder.setFinYear(purchaseOrderBean.getFinYear());
		List<PurchaseOrderItem> purchaseOrderItems = preparePurchaseOrderItems(
				purchaseOrderBean.getPurchaseOrderItemBeans());
		purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
		purchaseOrder.setSupplierId(SupplierUtilities.prepareSupplierModel(purchaseOrderBean.getSupplierBean()));
		purchaseOrder.setProcessed(purchaseOrderBean.isProcessed());
		return purchaseOrder;
		
	}

	private static List<PurchaseOrderItem> preparePurchaseOrderItems(
			List<PurchaseOrderItemBean> purchaseOrderItemBeans) {
		List<PurchaseOrderItem> purchaseOrderItems = new LinkedList<PurchaseOrderItem>();
		for (PurchaseOrderItemBean purchaseOrderItemBean : purchaseOrderItemBeans) {
			PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
			purchaseOrderItem.setFinYear(purchaseOrderItemBean.getFinYear());
			purchaseOrderItem.setDescription(purchaseOrderItemBean.getDescription());
			purchaseOrderItem.setProductId(ProductUtilities.prepareProductModel(purchaseOrderItemBean.getProductId()));
			purchaseOrderItem
					.setPurchaseOrderId(purchaseOrderItemBean.getPurchaseOrderId());
			purchaseOrderItem.setQuantity(purchaseOrderItemBean.getQuantity());
			purchaseOrderItem
					.setQuantityType(purchaseOrderItemBean.getQuantityType());
			purchaseOrderItem.setRate(purchaseOrderItemBean.getRate());
			purchaseOrderItem.setSrNo(purchaseOrderItemBean.getSrNo());
			purchaseOrderItem.setTotalCost(purchaseOrderItemBean.getTotalCost());
			purchaseOrderItem.setVariantCode(purchaseOrderItemBean.getVariantCode());
			purchaseOrderItem.setVariantId(purchaseOrderItemBean.getVariantId());
			purchaseOrderItems.add(purchaseOrderItem);
		}
		return purchaseOrderItems;
	}

	public static void updatePurchaseOrderStatus(long purchaseOrderId,
			List<PurchaseInvoiceItem> purchaseInvoiceItems,PurchaseOrderService purchaseOrderService,String finYear) {
		PurchaseOrder purchaseOrder=purchaseOrderService.getPurchaseOrder(purchaseOrderId,finYear);
		if(purchaseInvoiceItems!=null && purchaseInvoiceItems.size()>0)
		{
			if(purchaseOrder.getPurchaseOrderItems()!=null && purchaseOrder.getPurchaseOrderItems().size()>0)
			{
				int count=0;
				for(PurchaseOrderItem purchaseOrderItem: purchaseOrder.getPurchaseOrderItems())
				{
					boolean processed=false;
					for(PurchaseInvoiceItem purchaseInvoiceItem: purchaseInvoiceItems)
					{
						if(purchaseOrderItem.getVariantCode().equals(purchaseInvoiceItem.getVariantCode()) && purchaseOrderItem.getQuantity()==purchaseInvoiceItem.getQuantity())
						{
							count++;
							purchaseOrderItem.setProcessed(true);
							processed=true;
							break;
						}
					}
				}
				boolean processed=true;
				for(PurchaseOrderItem soi: purchaseOrder.getPurchaseOrderItems())
				{
					if(!soi.isProcessed())
					{
						processed=false;
					}
				}
				if(count==purchaseOrder.getPurchaseOrderItems().size() || processed)
				{
					purchaseOrder.setProcessed(true);
				}
				purchaseOrderService.addPurchaseOrder(purchaseOrder);			
			}
		}
	}
}
