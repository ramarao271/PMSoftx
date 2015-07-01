package org.erp.tarak.productionorder;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.deliverychallan.DeliveryChallanItem;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.rawMaterial.RawMaterial;
import org.erp.tarak.rawMaterial.RawMaterialBean;
import org.erp.tarak.rawMaterial.RawMaterialUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.Worker;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;

public class ProductionOrderUtilities {

	public static ProductionOrder getProductionOrderModel(
			ProductionOrderService productionOrderService, long productionOrderId) {
		ProductionOrder productionOrder = productionOrderService.getProductionOrder(productionOrderId);
		return productionOrder;
	}

	public static List<ProductionOrderItemBean> prepareProductionOrderItemBeans(
			List<ProductionOrderItem> productionOrderItems) {
		List<ProductionOrderItemBean> productionOrderItemBeans = new LinkedList<ProductionOrderItemBean>();
		for (ProductionOrderItem productionOrderItem : productionOrderItems) {
			ProductionOrderItemBean productionOrderItemBean = new ProductionOrderItemBean();
			productionOrderItemBean.setFinYear(productionOrderItem.getFinYear());
			productionOrderItemBean.setDescription(productionOrderItem.getDescription());
			RawMaterialBean pb = RawMaterialUtilities.prepareRawMaterialBean(productionOrderItem
					.getRawMaterialId());
			productionOrderItemBean.setRawMaterialBeanId(pb);;
			productionOrderItemBean
					.setProductionOrderId(productionOrderItem.getProductionOrderId());
			productionOrderItemBean.setQuantity(productionOrderItem.getQuantity());
			productionOrderItemBean
					.setQuantityType(productionOrderItem.getQuantityType());
			productionOrderItemBean.setRate(productionOrderItem.getRate());
			productionOrderItemBean.setSrNo(productionOrderItem.getSrNo());
			productionOrderItemBean.setTotalCost(productionOrderItem.getTotalCost());
			productionOrderItemBean.setVariantCode(productionOrderItem.getVariantCode());
			productionOrderItemBean.setVariantId(productionOrderItem.getVariantId());
			productionOrderItemBean.setProcessed(productionOrderItem.isProcessed());
			productionOrderItemBeans.add(productionOrderItemBean);
		}
		return productionOrderItemBeans;
	}

	public static ProductionOrderBean prepareProductionOrderBean(ProductionOrder po,
			WorkerService workerService) {
		ProductionOrderBean pob = new ProductionOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setProductionOrderDate(po.getProductionOrderDate());
		pob.setProductionOrderId(po.getProductionOrderId());
		List<ProductionOrderItemBean> poib = prepareProductionOrderItemBeans(po
				.getProductionOrderItems());
		pob.setProductionOrderItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId().getWorkerId(), workerService));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static ProductionOrderBean prepareProductionOrderBean(ProductionOrder po) {
		ProductionOrderBean pob = new ProductionOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setProductionOrderDate(po.getProductionOrderDate());
		pob.setProductionOrderId(po.getProductionOrderId());
		List<ProductionOrderItemBean> poib = prepareProductionOrderItemBeans(po
				.getProductionOrderItems());
		pob.setProductionOrderItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static ProductionOrder prepareProductionOrderModel(
			ProductionOrderBean productionOrderBean, UserBean user,
			WorkerService workerService, CategoryService categoryService,
			MeasurementService measurementService) {
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.setProductionOrderDate(productionOrderBean.getProductionOrderDate());
		productionOrder.setProductionOrderId(productionOrderBean.getProductionOrderId());
		productionOrder.setTotalCost(productionOrderBean.getTotalCost());
		productionOrder.setFinYear(user.getFinYear());
		List<ProductionOrderItem> productionOrderItems = prepareProductionOrderItems(
				productionOrderBean.getProductionOrderItemBeans(),productionOrderBean.getProductionOrderId(), user, categoryService,
				measurementService);
		productionOrder.setProductionOrderItems(productionOrderItems);
		Worker worker = WorkerUtilities.getWorkerModel(workerService,
				productionOrderBean.getWorkerBean().getWorkerId());
		productionOrder.setWorkerId(worker);
		productionOrder.setProcessed(productionOrderBean.isProcessed());
		return productionOrder;
	}

	public static List<ProductionOrderItem> prepareProductionOrderItems(
			List<ProductionOrderItemBean> productionOrderItemBeans, long productionOrderId, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService ) {
		List<ProductionOrderItem> productionOrderItems = new LinkedList<ProductionOrderItem>();
		for (ProductionOrderItemBean productionOrderItemBean : productionOrderItemBeans) {
			ProductionOrderItem productionOrderItem = new ProductionOrderItem();
			productionOrderItem.setFinYear(user.getFinYear());
			productionOrderItem.setDescription(productionOrderItemBean.getDescription());
			RawMaterial pb = RawMaterialUtilities.prepareRawMaterialModel(
					productionOrderItemBean.getRawMaterialBeanId(), categoryService,
					measurementService);
			productionOrderItem.setRawMaterialId(pb);;
			productionOrderItem
					.setProductionOrderId(productionOrderId);
			productionOrderItem.setQuantity(productionOrderItemBean.getQuantity());
			productionOrderItem
					.setQuantityType(productionOrderItemBean.getQuantityType());
			productionOrderItem.setRate(productionOrderItemBean.getRate());
			productionOrderItem.setSrNo(productionOrderItemBean.getSrNo());
			productionOrderItem.setTotalCost(productionOrderItemBean.getTotalCost());
			productionOrderItem.setVariantCode(productionOrderItemBean.getVariantCode());
			productionOrderItem.setVariantId(productionOrderItemBean.getVariantId());
			productionOrderItems.add(productionOrderItem);
		}

		return productionOrderItems;
	}

	public static List<ProductionOrderBean> prepareListofProductionOrderBean(
			List<ProductionOrder> productionOrders, WorkerService workerService) {
		List<ProductionOrderBean> beans = new LinkedList<ProductionOrderBean>();
		for (ProductionOrder po : productionOrders) {
			ProductionOrderBean pob = prepareProductionOrderBean(po);
			beans.add(pob);
		}
		return beans;
	}

	public static ProductionOrder prepareProductionOrderModel(
			ProductionOrderBean productionOrderBean) {
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.setProductionOrderDate(productionOrderBean.getProductionOrderDate());
		productionOrder.setProductionOrderId(productionOrderBean.getProductionOrderId());
		productionOrder.setTotalCost(productionOrderBean.getTotalCost());
		productionOrder.setFinYear(productionOrderBean.getFinYear());
		List<ProductionOrderItem> productionOrderItems = prepareProductionOrderItems(
				productionOrderBean.getProductionOrderItemBeans());
		productionOrder.setProductionOrderItems(productionOrderItems);
		productionOrder.setWorkerId(WorkerUtilities.prepareWorkerModel(productionOrderBean.getWorkerBean()));
		productionOrder.setProcessed(productionOrderBean.isProcessed());
		return productionOrder;
		
	}

	private static List<ProductionOrderItem> prepareProductionOrderItems(
			List<ProductionOrderItemBean> productionOrderItemBeans) {
		List<ProductionOrderItem> productionOrderItems = new LinkedList<ProductionOrderItem>();
		for (ProductionOrderItemBean productionOrderItemBean : productionOrderItemBeans) {
			ProductionOrderItem productionOrderItem = new ProductionOrderItem();
			productionOrderItem.setFinYear(productionOrderItemBean.getFinYear());
			productionOrderItem.setDescription(productionOrderItemBean.getDescription());
			productionOrderItem.setRawMaterialId(RawMaterialUtilities.prepareRawMaterialModel(productionOrderItemBean.getRawMaterialBeanId()));
			productionOrderItem
					.setProductionOrderId(productionOrderItemBean.getProductionOrderId());
			productionOrderItem.setQuantity(productionOrderItemBean.getQuantity());
			productionOrderItem
					.setQuantityType(productionOrderItemBean.getQuantityType());
			productionOrderItem.setRate(productionOrderItemBean.getRate());
			productionOrderItem.setSrNo(productionOrderItemBean.getSrNo());
			productionOrderItem.setTotalCost(productionOrderItemBean.getTotalCost());
			productionOrderItem.setVariantCode(productionOrderItemBean.getVariantCode());
			productionOrderItem.setVariantId(productionOrderItemBean.getVariantId());
			productionOrderItems.add(productionOrderItem);
		}
		return productionOrderItems;
	}

	public static void updateProductionOrderStatus(long productionOrderId,
			List<DeliveryChallanItem> deliveryChallanItems,ProductionOrderService productionOrderService) {
		ProductionOrder productionOrder=productionOrderService.getProductionOrder(productionOrderId);
		if(deliveryChallanItems!=null && deliveryChallanItems.size()>0)
		{
			if(productionOrder.getProductionOrderItems()!=null && productionOrder.getProductionOrderItems().size()>0)
			{
				int count=0;
				for(ProductionOrderItem productionOrderItem: productionOrder.getProductionOrderItems())
				{
					boolean processed=false;
					for(DeliveryChallanItem deliveryChallanItem: deliveryChallanItems)
					{
						if(productionOrderItem.getVariantCode().equals(deliveryChallanItem.getVariantCode()) && productionOrderItem.getQuantity()==deliveryChallanItem.getQuantity())
						{
							count++;
							productionOrderItem.setProcessed(true);
							processed=true;
							break;
						}
					}
					if(!processed)
					{
						productionOrderItem.setProcessed(false);
					}
				}
				boolean processed=true;
				for(ProductionOrderItem soi: productionOrder.getProductionOrderItems())
				{
					if(!soi.isProcessed())
					{
						processed=false;
					}
				}
				if(count==productionOrder.getProductionOrderItems().size() || processed)
				{
					productionOrder.setProcessed(true);
				}
				productionOrderService.addProductionOrder(productionOrder);			
			}
		}
	}
}
