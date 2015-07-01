package org.erp.tarak.deliverychallan;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.salesorder.SalesOrder;
import org.erp.tarak.salesorder.SalesOrderBean;
import org.erp.tarak.salesorder.SalesOrderService;
import org.erp.tarak.salesorder.SalesOrderUtilities;
import org.erp.tarak.shipper.Shipper;
import org.erp.tarak.shipper.ShipperBean;
import org.erp.tarak.shipper.ShipperService;
import org.erp.tarak.shipper.ShipperUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;

public class DeliveryChallanUtilities {

	public static DeliveryChallan getDeliveryChallanModel(
			DeliveryChallanService deliveryChallanService,
			long deliveryChallanId,String finYear) {
		DeliveryChallan deliveryChallan = deliveryChallanService
				.getDeliveryChallan(deliveryChallanId,finYear);
		return deliveryChallan;
	}

	public static List<DeliveryChallanItemBean> prepareDeliveryChallanItemBeans(
			List<DeliveryChallanItem> deliveryChallanItems) {
		List<DeliveryChallanItemBean> deliveryChallanItemBeans = new LinkedList<DeliveryChallanItemBean>();
		for (DeliveryChallanItem deliveryChallanItem : deliveryChallanItems) {
			DeliveryChallanItemBean deliveryChallanItemBean = new DeliveryChallanItemBean();
			deliveryChallanItemBean
					.setFinYear(deliveryChallanItem.getFinYear());
			deliveryChallanItemBean.setDescription(deliveryChallanItem
					.getDescription());

			ProductBean pb = ProductUtilities
					.prepareProductBean(deliveryChallanItem.getProductId());
			deliveryChallanItemBean.setProductId(pb);
			deliveryChallanItemBean.setDeliveryChallanId(deliveryChallanItem
					.getDeliveryChallanId());
			deliveryChallanItemBean.setQuantity(deliveryChallanItem
					.getQuantity());
			deliveryChallanItemBean.setQuantityType(deliveryChallanItem
					.getQuantityType());
			deliveryChallanItemBean.setRate(deliveryChallanItem.getRate());
			deliveryChallanItemBean.setSrNo(deliveryChallanItem.getSrNo());
			deliveryChallanItemBean.setTotalCost(deliveryChallanItem
					.getTotalCost());
			deliveryChallanItemBean.setVariantId(deliveryChallanItem.getVariantId());
			deliveryChallanItemBean.setVariantCode(deliveryChallanItem.getVariantCode());
			deliveryChallanItemBean.setPackageList(deliveryChallanItem.getPackageList());
			deliveryChallanItemBeans.add(deliveryChallanItemBean);
		}

		return deliveryChallanItemBeans;
	}

	public static DeliveryChallanBean prepareDeliveryChallanBean(
			DeliveryChallan po, CustomerService customerService) {
		DeliveryChallanBean pob = new DeliveryChallanBean();
		pob.setFinYear(po.getFinYear());
		pob.setDeliveryChallanDate(po.getDeliveryChallanDate());
		pob.setDeliveryChallanId(po.getDeliveryChallanId());
		List<DeliveryChallanItemBean> poib = prepareDeliveryChallanItemBeans(po
				.getDeliveryChallanItems());
		pob.setDeliveryChallanItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId().getCustomerId(), customerService));
		pob.setTotalCost(po.getTotalCost());
		SalesOrderBean salesOrderBean = SalesOrderUtilities
				.prepareSalesOrderBean(po.getSalesOrder());
		pob.setSalesOrderBean(salesOrderBean);
		pob.setBookedAddress(po.getBookedAddress());
		ShipperBean shipperBean=ShipperUtilities.prepareShipperBean(po.getShipper());
		pob.setShipperBean(shipperBean);
		pob.setDeliveryType(po.getDeliveryType());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static DeliveryChallan prepareDeliveryChallanModel(
			DeliveryChallanBean deliveryChallanBean, UserBean user,
			CustomerService customerService, CategoryService categoryService,
			MeasurementService measurementService,
			SalesOrderService salesOrderService, VariantService variantService, ShipperService shipperService,String finYear) {
		DeliveryChallan deliveryChallan = new DeliveryChallan();
		deliveryChallan.setDeliveryChallanDate(deliveryChallanBean
				.getDeliveryChallanDate());
		deliveryChallan.setDeliveryChallanId(deliveryChallanBean
				.getDeliveryChallanId());
		deliveryChallan.setTotalCost(deliveryChallanBean.getTotalCost());
		deliveryChallan.setFinYear(user.getFinYear());
		List<DeliveryChallanItem> deliveryChallanItems = prepareDeliveryChallanItems(
				deliveryChallanBean.getDeliveryChallanItemBeans(), user,
				categoryService, measurementService,variantService);
		deliveryChallan.setDeliveryChallanItems(deliveryChallanItems);
		SalesOrder po = SalesOrderUtilities.getSalesOrderModel(
				salesOrderService, deliveryChallanBean.getSalesOrderBean()
						.getSalesOrderId(),finYear);
		deliveryChallan.setSalesOrder(po);
		deliveryChallan.setCustomerId(po.getCustomerId());
		deliveryChallan.setBookedAddress(deliveryChallanBean.getBookedAddress());
		Shipper shipper=ShipperUtilities.getShipperModel(shipperService, deliveryChallanBean.getShipperBean().getShipperId());
		deliveryChallan.setShipper(shipper);
		deliveryChallan.setDeliveryType(deliveryChallanBean.getDeliveryType());
		deliveryChallan.setProcessed(deliveryChallanBean.isProcessed());
		return deliveryChallan;
	}

	public static List<DeliveryChallanItem> prepareDeliveryChallanItems(
			List<DeliveryChallanItemBean> deliveryChallanItemBeans,
			UserBean user, CategoryService categoryService,
			MeasurementService measurementService, VariantService variantService) {
		List<DeliveryChallanItem> deliveryChallanItems = new LinkedList<DeliveryChallanItem>();
		for (DeliveryChallanItemBean deliveryChallanItemBean : deliveryChallanItemBeans) {
			DeliveryChallanItem deliveryChallanItem = new DeliveryChallanItem();
			deliveryChallanItem.setFinYear(user.getFinYear());
			deliveryChallanItem.setDescription(deliveryChallanItemBean
					.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					deliveryChallanItemBean.getProductId(), categoryService,
					measurementService);
			deliveryChallanItem.setProductId(pb);

			deliveryChallanItem.setDeliveryChallanId(deliveryChallanItemBean
					.getDeliveryChallanId());
			deliveryChallanItem.setQuantity(deliveryChallanItemBean
					.getQuantity());
			deliveryChallanItem.setQuantityType(deliveryChallanItemBean
					.getQuantityType());
			deliveryChallanItem.setRate(deliveryChallanItemBean.getRate());
			deliveryChallanItem.setSrNo(deliveryChallanItemBean.getSrNo());
			deliveryChallanItem.setTotalCost(deliveryChallanItemBean
					.getTotalCost());
			deliveryChallanItem.setVariantId(deliveryChallanItemBean.getVariantId());
			deliveryChallanItem.setVariantCode(deliveryChallanItemBean.getVariantCode());
			deliveryChallanItem.setPackageList(deliveryChallanItemBean.getPackageList());
			deliveryChallanItems.add(deliveryChallanItem);
		}

		return deliveryChallanItems;
	}

	public static List<DeliveryChallanBean> prepareListofDeliveryChallanBean(
			List<DeliveryChallan> deliveryChallans,
			CustomerService customerService) {
		List<DeliveryChallanBean> beans = new LinkedList<DeliveryChallanBean>();
		for (DeliveryChallan dc : deliveryChallans) {
			DeliveryChallanBean pob = prepareDeliveryChallanBean(dc);
			beans.add(pob);
		}

		return beans;
	}
	public static DeliveryChallanBean prepareDeliveryChallanBean(DeliveryChallan po) {
		DeliveryChallanBean pob = new DeliveryChallanBean();
		pob.setFinYear(po.getFinYear());
		pob.setDeliveryChallanDate(po.getDeliveryChallanDate());
		pob.setDeliveryChallanId(po.getDeliveryChallanId());
		List<DeliveryChallanItemBean> poib = prepareDeliveryChallanItemBeans(po
				.getDeliveryChallanItems());
		pob.setDeliveryChallanItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setBookedAddress(po.getBookedAddress());
		ShipperBean shipperBean=ShipperUtilities.prepareShipperBean(po.getShipper());
		pob.setShipperBean(shipperBean);
		pob.setDeliveryType(po.getDeliveryType());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static DeliveryChallan prepareDeliveryChallanModel(
			DeliveryChallanBean deliveryChallanBean) {
		// TODO Auto-generated method stub
		return null;
	}
}
