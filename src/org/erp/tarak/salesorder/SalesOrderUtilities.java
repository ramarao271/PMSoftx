package org.erp.tarak.salesorder;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.Customer;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.deliverychallan.DeliveryChallanItem;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.user.UserBean;

public class SalesOrderUtilities {

	public static SalesOrder getSalesOrderModel(
			SalesOrderService salesOrderService, long salesOrderId,String finYear) {
		SalesOrder salesOrder = salesOrderService.getSalesOrder(salesOrderId,finYear);
		return salesOrder;
	}

	public static List<SalesOrderItemBean> prepareSalesOrderItemBeans(
			List<SalesOrderItem> salesOrderItems) {
		List<SalesOrderItemBean> salesOrderItemBeans = new LinkedList<SalesOrderItemBean>();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();
			salesOrderItemBean.setFinYear(salesOrderItem.getFinYear());
			salesOrderItemBean.setDescription(salesOrderItem.getDescription());
			ProductBean pb = ProductUtilities.prepareProductBean(salesOrderItem
					.getProductId());
			salesOrderItemBean.setProductId(pb);
			salesOrderItemBean
					.setSalesOrderId(salesOrderItem.getSalesOrderId());
			salesOrderItemBean.setQuantity(salesOrderItem.getQuantity());
			salesOrderItemBean
					.setQuantityType(salesOrderItem.getQuantityType());
			salesOrderItemBean.setRate(salesOrderItem.getRate());
			salesOrderItemBean.setSrNo(salesOrderItem.getSrNo());
			salesOrderItemBean.setTotalCost(salesOrderItem.getTotalCost());
			salesOrderItemBean.setVariantCode(salesOrderItem.getVariantCode());
			salesOrderItemBean.setVariantId(salesOrderItem.getVariantId());
			salesOrderItemBean.setProcessed(salesOrderItem.isProcessed());
			salesOrderItemBeans.add(salesOrderItemBean);
		}
		return salesOrderItemBeans;
	}

	public static SalesOrderBean prepareSalesOrderBean(SalesOrder po,
			CustomerService customerService) {
		SalesOrderBean pob = new SalesOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesOrderDate(po.getSalesOrderDate());
		pob.setSalesOrderId(po.getSalesOrderId());
		List<SalesOrderItemBean> poib = prepareSalesOrderItemBeans(po
				.getSalesOrderItems());
		pob.setSalesOrderItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId().getCustomerId(), customerService));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static SalesOrderBean prepareSalesOrderBean(SalesOrder po) {
		SalesOrderBean pob = new SalesOrderBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesOrderDate(po.getSalesOrderDate());
		pob.setSalesOrderId(po.getSalesOrderId());
		List<SalesOrderItemBean> poib = prepareSalesOrderItemBeans(po
				.getSalesOrderItems());
		pob.setSalesOrderItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static SalesOrder prepareSalesOrderModel(
			SalesOrderBean salesOrderBean, UserBean user,
			CustomerService customerService, CategoryService categoryService,
			MeasurementService measurementService) {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setSalesOrderDate(salesOrderBean.getSalesOrderDate());
		salesOrder.setSalesOrderId(salesOrderBean.getSalesOrderId());
		salesOrder.setTotalCost(salesOrderBean.getTotalCost());
		salesOrder.setFinYear(user.getFinYear());
		List<SalesOrderItem> salesOrderItems = prepareSalesOrderItems(
				salesOrderBean.getSalesOrderItemBeans(),salesOrderBean.getSalesOrderId(), user, categoryService,
				measurementService);
		salesOrder.setSalesOrderItems(salesOrderItems);
		Customer customer = CustomerUtilities.getCustomerModel(customerService,
				salesOrderBean.getCustomerBean().getCustomerId());
		salesOrder.setCustomerId(customer);
		salesOrder.setProcessed(salesOrderBean.isProcessed());
		return salesOrder;
	}

	public static List<SalesOrderItem> prepareSalesOrderItems(
			List<SalesOrderItemBean> salesOrderItemBeans, long salesOrderId, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService ) {
		List<SalesOrderItem> salesOrderItems = new LinkedList<SalesOrderItem>();
		for (SalesOrderItemBean salesOrderItemBean : salesOrderItemBeans) {
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			salesOrderItem.setFinYear(user.getFinYear());
			salesOrderItem.setDescription(salesOrderItemBean.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					salesOrderItemBean.getProductId(), categoryService,
					measurementService);
			salesOrderItem.setProductId(pb);
			salesOrderItem
					.setSalesOrderId(salesOrderId);
			salesOrderItem.setQuantity(salesOrderItemBean.getQuantity());
			salesOrderItem
					.setQuantityType(salesOrderItemBean.getQuantityType());
			salesOrderItem.setRate(salesOrderItemBean.getRate());
			salesOrderItem.setSrNo(salesOrderItemBean.getSrNo());
			salesOrderItem.setTotalCost(salesOrderItemBean.getTotalCost());
			salesOrderItem.setVariantCode(salesOrderItemBean.getVariantCode());
			salesOrderItem.setVariantId(salesOrderItemBean.getVariantId());
			salesOrderItems.add(salesOrderItem);
		}

		return salesOrderItems;
	}

	public static List<SalesOrderBean> prepareListofSalesOrderBean(
			List<SalesOrder> salesOrders, CustomerService customerService) {
		List<SalesOrderBean> beans = new LinkedList<SalesOrderBean>();
		for (SalesOrder po : salesOrders) {
			SalesOrderBean pob = prepareSalesOrderBean(po);
			beans.add(pob);
		}
		return beans;
	}

	public static SalesOrder prepareSalesOrderModel(
			SalesOrderBean salesOrderBean) {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setSalesOrderDate(salesOrderBean.getSalesOrderDate());
		salesOrder.setSalesOrderId(salesOrderBean.getSalesOrderId());
		salesOrder.setTotalCost(salesOrderBean.getTotalCost());
		salesOrder.setFinYear(salesOrderBean.getFinYear());
		List<SalesOrderItem> salesOrderItems = prepareSalesOrderItems(
				salesOrderBean.getSalesOrderItemBeans());
		salesOrder.setSalesOrderItems(salesOrderItems);
		salesOrder.setCustomerId(CustomerUtilities.prepareCustomerModel(salesOrderBean.getCustomerBean()));
		salesOrder.setProcessed(salesOrderBean.isProcessed());
		return salesOrder;
		
	}

	private static List<SalesOrderItem> prepareSalesOrderItems(
			List<SalesOrderItemBean> salesOrderItemBeans) {
		List<SalesOrderItem> salesOrderItems = new LinkedList<SalesOrderItem>();
		for (SalesOrderItemBean salesOrderItemBean : salesOrderItemBeans) {
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			salesOrderItem.setFinYear(salesOrderItemBean.getFinYear());
			salesOrderItem.setDescription(salesOrderItemBean.getDescription());
			salesOrderItem.setProductId(ProductUtilities.prepareProductModel(salesOrderItemBean.getProductId()));
			salesOrderItem
					.setSalesOrderId(salesOrderItemBean.getSalesOrderId());
			salesOrderItem.setQuantity(salesOrderItemBean.getQuantity());
			salesOrderItem
					.setQuantityType(salesOrderItemBean.getQuantityType());
			salesOrderItem.setRate(salesOrderItemBean.getRate());
			salesOrderItem.setSrNo(salesOrderItemBean.getSrNo());
			salesOrderItem.setTotalCost(salesOrderItemBean.getTotalCost());
			salesOrderItem.setVariantCode(salesOrderItemBean.getVariantCode());
			salesOrderItem.setVariantId(salesOrderItemBean.getVariantId());
			salesOrderItems.add(salesOrderItem);
		}
		return salesOrderItems;
	}

	public static void updateSalesOrderStatus(long salesOrderId,
			List<DeliveryChallanItem> deliveryChallanItems,SalesOrderService salesOrderService,String finYear) {
		SalesOrder salesOrder=salesOrderService.getSalesOrder(salesOrderId,finYear);
		if(deliveryChallanItems!=null && deliveryChallanItems.size()>0)
		{
			if(salesOrder.getSalesOrderItems()!=null && salesOrder.getSalesOrderItems().size()>0)
			{
				int count=0;
				for(SalesOrderItem salesOrderItem: salesOrder.getSalesOrderItems())
				{
					boolean processed=false;
					for(DeliveryChallanItem deliveryChallanItem: deliveryChallanItems)
					{
						if(salesOrderItem.getVariantCode().equals(deliveryChallanItem.getVariantCode()) && salesOrderItem.getQuantity()==deliveryChallanItem.getQuantity())
						{
							count++;
							salesOrderItem.setProcessed(true);
							processed=true;
							break;
						}
					}
				}
				boolean processed=true;
				for(SalesOrderItem soi: salesOrder.getSalesOrderItems())
				{
					if(!soi.isProcessed())
					{
						processed=false;
					}
				}
				if(count==salesOrder.getSalesOrderItems().size() || processed)
				{
					salesOrder.setProcessed(true);
				}
				salesOrderService.addSalesOrder(salesOrder);			
			}
		}
	}
}
