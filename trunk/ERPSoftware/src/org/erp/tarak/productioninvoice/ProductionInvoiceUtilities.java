package org.erp.tarak.productioninvoice;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.deliverychallan.DeliveryChallanItem;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.productionorder.ProductionOrder;
import org.erp.tarak.productionorder.ProductionOrderService;
import org.erp.tarak.productionorder.ProductionOrderUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;

public class ProductionInvoiceUtilities {

	public static ProductionInvoice getProductionInvoiceModel(
			ProductionInvoiceService productionInvoiceService, long productionInvoiceId) {
		ProductionInvoice productionInvoice = productionInvoiceService.getProductionInvoice(productionInvoiceId);
		return productionInvoice;
	}

	public static List<ProductionInvoiceItemBean> prepareProductionInvoiceItemBeans(
			List<ProductionInvoiceItem> productionInvoiceItems) {
		List<ProductionInvoiceItemBean> productionInvoiceItemBeans = new LinkedList<ProductionInvoiceItemBean>();
		for (ProductionInvoiceItem productionInvoiceItem : productionInvoiceItems) {
			ProductionInvoiceItemBean productionInvoiceItemBean = new ProductionInvoiceItemBean();
			productionInvoiceItemBean.setFinYear(productionInvoiceItem.getFinYear());
			productionInvoiceItemBean.setDescription(productionInvoiceItem.getDescription());
			ProductBean pb = ProductUtilities.prepareProductBean(productionInvoiceItem
					.getProductId());
			productionInvoiceItemBean.setProductId(pb);;
			productionInvoiceItemBean
					.setProductionInvoiceId(productionInvoiceItem.getProductionInvoiceId());
			productionInvoiceItemBean.setQuantity(productionInvoiceItem.getQuantity());
			productionInvoiceItemBean
					.setQuantityType(productionInvoiceItem.getQuantityType());
			productionInvoiceItemBean.setRate(productionInvoiceItem.getRate());
			productionInvoiceItemBean.setSrNo(productionInvoiceItem.getSrNo());
			productionInvoiceItemBean.setTotalCost(productionInvoiceItem.getTotalCost());
			productionInvoiceItemBean.setVariantCode(productionInvoiceItem.getVariantCode());
			productionInvoiceItemBean.setVariantId(productionInvoiceItem.getVariantId());
			productionInvoiceItemBean.setProcessed(productionInvoiceItem.isProcessed());
			productionInvoiceItemBean.setReturnedQty(productionInvoiceItem.getReturnedQty());
			productionInvoiceItemBean.setReturnedValue(productionInvoiceItem.getReturnedValue());
			productionInvoiceItemBeans.add(productionInvoiceItemBean);
		}
		return productionInvoiceItemBeans;
	}

	public static ProductionInvoiceBean prepareProductionInvoiceBean(ProductionInvoice po,
			WorkerService workerService) {
		ProductionInvoiceBean pob = new ProductionInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setProductionInvoiceDate(po.getProductionInvoiceDate());
		pob.setProductionInvoiceId(po.getProductionInvoiceId());
		List<ProductionInvoiceItemBean> poib = prepareProductionInvoiceItemBeans(po
				.getProductionInvoiceItems());
		pob.setProductionInvoiceItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId().getWorkerId(), workerService));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		pob.setProductionOrderBean(ProductionOrderUtilities.prepareProductionOrderBean(po.getProductionOrder()));
		return pob;
	}

	public static ProductionInvoiceBean prepareProductionInvoiceBean(ProductionInvoice po) {
		ProductionInvoiceBean pob = new ProductionInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setProductionInvoiceDate(po.getProductionInvoiceDate());
		pob.setProductionInvoiceId(po.getProductionInvoiceId());
		List<ProductionInvoiceItemBean> poib = prepareProductionInvoiceItemBeans(po
				.getProductionInvoiceItems());
		pob.setProductionInvoiceItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		pob.setProductionOrderBean(ProductionOrderUtilities.prepareProductionOrderBean(po.getProductionOrder()));
		return pob;
	}

	public static ProductionInvoice prepareProductionInvoiceModel(
			ProductionInvoiceBean productionInvoiceBean, UserBean user,
			WorkerService workerService, CategoryService categoryService,
			MeasurementService measurementService,ProductionOrderService productionOrderService) {
		ProductionInvoice productionInvoice = new ProductionInvoice();
		productionInvoice.setProductionInvoiceDate(productionInvoiceBean.getProductionInvoiceDate());
		productionInvoice.setProductionInvoiceId(productionInvoiceBean.getProductionInvoiceId());
		productionInvoice.setTotalCost(productionInvoiceBean.getTotalCost());
		productionInvoice.setFinYear(user.getFinYear());
		List<ProductionInvoiceItem> productionInvoiceItems = prepareProductionInvoiceItems(
				productionInvoiceBean.getProductionInvoiceItemBeans(),productionInvoiceBean.getProductionInvoiceId(), user, categoryService,
				measurementService);
		productionInvoice.setProductionInvoiceItems(productionInvoiceItems);
		/*Worker worker = WorkerUtilities.getWorkerModel(workerService,
				productionInvoiceBean.getWorkerBean().getWorkerId());
		*/
		productionInvoice.setProcessed(productionInvoiceBean.isProcessed());
		ProductionOrder po=ProductionOrderUtilities.getProductionOrderModel(productionOrderService, productionInvoiceBean.getProductionOrderBean().getProductionOrderId());
		productionInvoice.setProductionOrder(po);
		productionInvoice.setWorkerId(po.getWorkerId());
		return productionInvoice;
	}

	public static List<ProductionInvoiceItem> prepareProductionInvoiceItems(
			List<ProductionInvoiceItemBean> productionInvoiceItemBeans, long productionInvoiceId, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService ) {
		List<ProductionInvoiceItem> productionInvoiceItems = new LinkedList<ProductionInvoiceItem>();
		for (ProductionInvoiceItemBean productionInvoiceItemBean : productionInvoiceItemBeans) {
			ProductionInvoiceItem productionInvoiceItem = new ProductionInvoiceItem();
			productionInvoiceItem.setFinYear(user.getFinYear());
			productionInvoiceItem.setDescription(productionInvoiceItemBean.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					productionInvoiceItemBean.getProductId(), categoryService,
					measurementService);
			productionInvoiceItem.setProductId(pb);;
			productionInvoiceItem
					.setProductionInvoiceId(productionInvoiceId);
			productionInvoiceItem.setQuantity(productionInvoiceItemBean.getQuantity());
			productionInvoiceItem
					.setQuantityType(productionInvoiceItemBean.getQuantityType());
			productionInvoiceItem.setRate(productionInvoiceItemBean.getRate());
			productionInvoiceItem.setSrNo(productionInvoiceItemBean.getSrNo());
			productionInvoiceItem.setTotalCost(productionInvoiceItemBean.getTotalCost());
			productionInvoiceItem.setVariantCode(productionInvoiceItemBean.getVariantCode());
			productionInvoiceItem.setVariantId(productionInvoiceItemBean.getVariantId());
			productionInvoiceItem.setReturnedQty(productionInvoiceItemBean.getReturnedQty());
			productionInvoiceItem.setReturnedValue(productionInvoiceItemBean.getReturnedValue());
			productionInvoiceItems.add(productionInvoiceItem);
		}

		return productionInvoiceItems;
	}

	public static List<ProductionInvoiceBean> prepareListofProductionInvoiceBean(
			List<ProductionInvoice> productionInvoices, WorkerService workerService) {
		List<ProductionInvoiceBean> beans = new LinkedList<ProductionInvoiceBean>();
		for (ProductionInvoice po : productionInvoices) {
			ProductionInvoiceBean pob = prepareProductionInvoiceBean(po);
			beans.add(pob);
		}
		return beans;
	}

	public static ProductionInvoice prepareProductionInvoiceModel(
			ProductionInvoiceBean productionInvoiceBean) {
		ProductionInvoice productionInvoice = new ProductionInvoice();
		productionInvoice.setProductionInvoiceDate(productionInvoiceBean.getProductionInvoiceDate());
		productionInvoice.setProductionInvoiceId(productionInvoiceBean.getProductionInvoiceId());
		productionInvoice.setTotalCost(productionInvoiceBean.getTotalCost());
		productionInvoice.setFinYear(productionInvoiceBean.getFinYear());
		List<ProductionInvoiceItem> productionInvoiceItems = prepareProductionInvoiceItems(
				productionInvoiceBean.getProductionInvoiceItemBeans());
		productionInvoice.setProductionInvoiceItems(productionInvoiceItems);
		productionInvoice.setWorkerId(WorkerUtilities.prepareWorkerModel(productionInvoiceBean.getWorkerBean()));
		productionInvoice.setProcessed(productionInvoiceBean.isProcessed());
		productionInvoice.setProductionOrder(ProductionOrderUtilities.prepareProductionOrderModel(productionInvoiceBean.getProductionOrderBean()));
		return productionInvoice;
		
	}

	private static List<ProductionInvoiceItem> prepareProductionInvoiceItems(
			List<ProductionInvoiceItemBean> productionInvoiceItemBeans) {
		List<ProductionInvoiceItem> productionInvoiceItems = new LinkedList<ProductionInvoiceItem>();
		for (ProductionInvoiceItemBean productionInvoiceItemBean : productionInvoiceItemBeans) {
			ProductionInvoiceItem productionInvoiceItem = new ProductionInvoiceItem();
			productionInvoiceItem.setFinYear(productionInvoiceItemBean.getFinYear());
			productionInvoiceItem.setDescription(productionInvoiceItemBean.getDescription());
			productionInvoiceItem.setProductId(ProductUtilities.prepareProductModel(productionInvoiceItemBean.getProductId()));
			productionInvoiceItem
					.setProductionInvoiceId(productionInvoiceItemBean.getProductionInvoiceId());
			productionInvoiceItem.setQuantity(productionInvoiceItemBean.getQuantity());
			productionInvoiceItem
					.setQuantityType(productionInvoiceItemBean.getQuantityType());
			productionInvoiceItem.setRate(productionInvoiceItemBean.getRate());
			productionInvoiceItem.setSrNo(productionInvoiceItemBean.getSrNo());
			productionInvoiceItem.setTotalCost(productionInvoiceItemBean.getTotalCost());
			productionInvoiceItem.setVariantCode(productionInvoiceItemBean.getVariantCode());
			productionInvoiceItem.setVariantId(productionInvoiceItemBean.getVariantId());
			productionInvoiceItem.setReturnedQty(productionInvoiceItemBean.getReturnedQty());
			productionInvoiceItem.setReturnedValue(productionInvoiceItemBean.getReturnedValue());
			productionInvoiceItems.add(productionInvoiceItem);
		}
		return productionInvoiceItems;
	}

	public static void updateProductionInvoiceStatus(long productionInvoiceId,
			List<DeliveryChallanItem> deliveryChallanItems,ProductionInvoiceService productionInvoiceService) {
		ProductionInvoice productionInvoice=productionInvoiceService.getProductionInvoice(productionInvoiceId);
		if(deliveryChallanItems!=null && deliveryChallanItems.size()>0)
		{
			if(productionInvoice.getProductionInvoiceItems()!=null && productionInvoice.getProductionInvoiceItems().size()>0)
			{
				int count=0;
				for(ProductionInvoiceItem productionInvoiceItem: productionInvoice.getProductionInvoiceItems())
				{
					boolean processed=false;
					for(DeliveryChallanItem deliveryChallanItem: deliveryChallanItems)
					{
						if(productionInvoiceItem.getVariantCode().equals(deliveryChallanItem.getVariantCode()) && productionInvoiceItem.getQuantity()==deliveryChallanItem.getQuantity())
						{
							count++;
							productionInvoiceItem.setProcessed(true);
							processed=true;
							break;
						}
					}
					if(!processed)
					{
						productionInvoiceItem.setProcessed(false);
					}
				}
				boolean processed=true;
				for(ProductionInvoiceItem soi: productionInvoice.getProductionInvoiceItems())
				{
					if(!soi.isProcessed())
					{
						processed=false;
					}
				}
				if(count==productionInvoice.getProductionInvoiceItems().size() || processed)
				{
					productionInvoice.setProcessed(true);
				}
				productionInvoiceService.addProductionInvoice(productionInvoice);			
			}
		}
	}
}
