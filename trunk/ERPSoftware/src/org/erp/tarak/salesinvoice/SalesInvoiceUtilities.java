package org.erp.tarak.salesinvoice;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.deliverychallan.DeliveryChallan;
import org.erp.tarak.deliverychallan.DeliveryChallanBean;
import org.erp.tarak.deliverychallan.DeliveryChallanService;
import org.erp.tarak.deliverychallan.DeliveryChallanUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;

public class SalesInvoiceUtilities {

	public static SalesInvoice getSalesInvoiceModel(
			SalesInvoiceService salesInvoiceService,
			long salesInvoiceId,String finYear) {
		SalesInvoice salesInvoice = salesInvoiceService
				.getSalesInvoice(salesInvoiceId,finYear);
		return salesInvoice;
	}

	public static List<SalesInvoiceItemBean> prepareSalesInvoiceItemBeans(
			List<SalesInvoiceItem> salesInvoiceItems) {
		List<SalesInvoiceItemBean> salesInvoiceItemBeans = new LinkedList<SalesInvoiceItemBean>();
		for (SalesInvoiceItem salesInvoiceItem : salesInvoiceItems) {
			SalesInvoiceItemBean salesInvoiceItemBean = new SalesInvoiceItemBean();
			salesInvoiceItemBean
					.setFinYear(salesInvoiceItem.getFinYear());
			salesInvoiceItemBean.setDescription(salesInvoiceItem
					.getDescription());
			ProductBean pb = ProductUtilities
					.prepareProductBean(salesInvoiceItem.getProductId());
			salesInvoiceItemBean.setProductId(pb);
			salesInvoiceItemBean.setSalesInvoiceId(salesInvoiceItem
					.getSalesInvoiceId());
			salesInvoiceItemBean.setQuantity(salesInvoiceItem
					.getQuantity());
			salesInvoiceItemBean.setQuantityType(salesInvoiceItem
					.getQuantityType());
			salesInvoiceItemBean.setRate(salesInvoiceItem.getRate());
			salesInvoiceItemBean.setSrNo(salesInvoiceItem.getSrNo());
			salesInvoiceItemBean.setTotalCost(salesInvoiceItem
					.getTotalCost());
			salesInvoiceItemBean.setVariantCode(salesInvoiceItem.getVariantCode());
			salesInvoiceItemBean.setVariantId(salesInvoiceItem.getVariantId());
			salesInvoiceItemBeans.add(salesInvoiceItemBean);
		}

		return salesInvoiceItemBeans;
	}

	public static SalesInvoiceBean prepareSalesInvoiceBean(
			SalesInvoice po) {
		SalesInvoiceBean pob = new SalesInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesInvoiceDate(po.getSalesInvoiceDate());
		pob.setSalesInvoiceId(po.getSalesInvoiceId());
		List<SalesInvoiceItemBean> poib = prepareSalesInvoiceItemBeans(po
				.getSalesInvoiceItems());
		pob.setSalesInvoiceItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po.getCustomerId()));
		pob.setTotalCost(po.getTotalCost());
		DeliveryChallanBean deliveryChallanBean = DeliveryChallanUtilities
				.prepareDeliveryChallanBean(po.getDeliveryChallan());
		pob.setDeliveryChallanBean(deliveryChallanBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setReturnAmount(po.getReturnAmount());
		pob.setAdjustedAmount(po.getAdjustedAmount());
		pob.setLrNo(po.getLrNo());
		pob.setDiscountPercent(po.getDiscountPercent());
		pob.setDiscountedAmount(po.getDiscountedAmount());
		pob.setInvoiceAmount(po.getInvoiceAmount());
		pob.setProcessed(po.isProcessed());
		pob.setReturnAmount(po.getReturnAmount());
		return pob;
	}
	
	public static SalesInvoiceBean prepareSalesInvoiceBean(
			SalesInvoice po, CustomerService customerService) {
		SalesInvoiceBean pob = new SalesInvoiceBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesInvoiceDate(po.getSalesInvoiceDate());
		pob.setSalesInvoiceId(po.getSalesInvoiceId());
		List<SalesInvoiceItemBean> poib = prepareSalesInvoiceItemBeans(po
				.getSalesInvoiceItems());
		pob.setSalesInvoiceItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId().getCustomerId(), customerService));
		pob.setTotalCost(po.getTotalCost());
		DeliveryChallanBean deliveryChallanBean = DeliveryChallanUtilities
				.prepareDeliveryChallanBean(po.getDeliveryChallan());
		pob.setDeliveryChallanBean(deliveryChallanBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setReturnAmount(po.getReturnAmount());
		pob.setAdjustedAmount(po.getAdjustedAmount());
		pob.setLrNo(po.getLrNo());
		pob.setDiscountPercent(po.getDiscountPercent());
		pob.setDiscountedAmount(po.getDiscountedAmount());
		pob.setInvoiceAmount(po.getInvoiceAmount());
		pob.setProcessed(po.isProcessed());
		return pob;
	}

	public static SalesInvoice prepareSalesInvoiceModel(
			SalesInvoiceBean salesInvoiceBean, UserBean user,
			CustomerService customerService, CategoryService categoryService,
			MeasurementService measurementService,DeliveryChallanService deliveryChallanService, VariantService variantService) {
		SalesInvoice salesInvoice = new SalesInvoice();
		salesInvoice.setSalesInvoiceDate(salesInvoiceBean
				.getSalesInvoiceDate());
		salesInvoice.setSalesInvoiceId(salesInvoiceBean
				.getSalesInvoiceId());
		salesInvoice.setTotalCost(salesInvoiceBean.getTotalCost());
		salesInvoice.setFinYear(user.getFinYear());
		List<SalesInvoiceItem> salesInvoiceItems = prepareSalesInvoiceItems(
				salesInvoiceBean.getSalesInvoiceItemBeans(), user,
				categoryService, measurementService,variantService);
		salesInvoice.setSalesInvoiceItems(salesInvoiceItems);
		DeliveryChallan po = DeliveryChallanUtilities.getDeliveryChallanModel(deliveryChallanService, salesInvoiceBean.getDeliveryChallanBean().getDeliveryChallanId(),user.getFinYear());
		salesInvoice.setDeliveryChallan(po);
		salesInvoice.setCustomerId(po.getCustomerId());
		salesInvoice.setPaidAmount(salesInvoiceBean.getPaidAmount());
		salesInvoice.setLrNo(salesInvoiceBean.getLrNo());
		salesInvoice.setReturnAmount(salesInvoiceBean.getReturnAmount());
		salesInvoice.setAdjustedAmount(salesInvoiceBean.getAdjustedAmount());
		salesInvoice.setInvoiceAmount(salesInvoiceBean.getInvoiceAmount());
		salesInvoice.setDiscountPercent(salesInvoiceBean.getDiscountPercent());
		salesInvoice.setDiscountedAmount(salesInvoiceBean.getDiscountedAmount());
		salesInvoice.setProcessed(salesInvoiceBean.isProcessed());
		return salesInvoice;
	}

	public static List<SalesInvoiceItem> prepareSalesInvoiceItems(
			List<SalesInvoiceItemBean> salesInvoiceItemBeans,
			UserBean user, CategoryService categoryService,
			MeasurementService measurementService, VariantService variantService) {
		List<SalesInvoiceItem> salesInvoiceItems = new LinkedList<SalesInvoiceItem>();
		for (SalesInvoiceItemBean salesInvoiceItemBean : salesInvoiceItemBeans) {
			SalesInvoiceItem salesInvoiceItem = new SalesInvoiceItem();
			salesInvoiceItem.setFinYear(user.getFinYear());
			salesInvoiceItem.setDescription(salesInvoiceItemBean
					.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					salesInvoiceItemBean.getProductId(), categoryService,
					measurementService);
			salesInvoiceItem.setProductId(pb);

			salesInvoiceItem.setSalesInvoiceId(salesInvoiceItemBean
					.getSalesInvoiceId());
			salesInvoiceItem.setQuantity(salesInvoiceItemBean
					.getQuantity());
			salesInvoiceItem.setQuantityType(salesInvoiceItemBean
					.getQuantityType());
			salesInvoiceItem.setRate(salesInvoiceItemBean.getRate());
			salesInvoiceItem.setSrNo(salesInvoiceItemBean.getSrNo());
			salesInvoiceItem.setTotalCost(salesInvoiceItemBean
					.getTotalCost());
			salesInvoiceItem.setVariantCode(salesInvoiceItemBean.getVariantCode());
			salesInvoiceItem.setVariantId(salesInvoiceItemBean.getVariantId());
			salesInvoiceItems.add(salesInvoiceItem);
		}

		return salesInvoiceItems;
	}

	public static List<SalesInvoiceBean> prepareListofSalesInvoiceBean(
			List<SalesInvoice> salesInvoices,
			CustomerService customerService) {
		List<SalesInvoiceBean> beans = new LinkedList<SalesInvoiceBean>();
		for (SalesInvoice po : salesInvoices) {
			SalesInvoiceBean pob = prepareSalesInvoiceBean(po, customerService);
			beans.add(pob);
		}

		return beans;
	}
	public static List<SalesInvoiceBean> prepareListofSalesInvoiceBean(
			List<SalesInvoice> salesInvoices) {
		List<SalesInvoiceBean> beans = new LinkedList<SalesInvoiceBean>();
		for (SalesInvoice po : salesInvoices) {
			SalesInvoiceBean pob = prepareSalesInvoiceBean(po);
			beans.add(pob);
		}

		return beans;
	}

	public static SalesInvoice prepareSalesInvoiceModel(
			SalesInvoiceBean salesInvoiceBean) {
		SalesInvoice salesInvoice=new SalesInvoice();
		salesInvoice.setSalesInvoiceDate(salesInvoiceBean
				.getSalesInvoiceDate());
		salesInvoice.setSalesInvoiceId(salesInvoiceBean
				.getSalesInvoiceId());
		salesInvoice.setTotalCost(salesInvoiceBean.getTotalCost());
		salesInvoice.setFinYear(salesInvoiceBean.getFinYear());
		List<SalesInvoiceItem> salesInvoiceItems = prepareSalesInvoiceItems(
				salesInvoiceBean.getSalesInvoiceItemBeans());
		salesInvoice.setSalesInvoiceItems(salesInvoiceItems);
		salesInvoice.setDeliveryChallan(DeliveryChallanUtilities.prepareDeliveryChallanModel(salesInvoiceBean.getDeliveryChallanBean()));
		salesInvoice.setCustomerId(CustomerUtilities.prepareCustomerModel(salesInvoiceBean.getCustomerBean()));
		salesInvoice.setPaidAmount(salesInvoiceBean.getPaidAmount());
		salesInvoice.setLrNo(salesInvoiceBean.getLrNo());
		salesInvoice.setReturnAmount(salesInvoiceBean.getReturnAmount());
		salesInvoice.setAdjustedAmount(salesInvoiceBean.getAdjustedAmount());
		salesInvoice.setInvoiceAmount(salesInvoiceBean.getInvoiceAmount());
		salesInvoice.setDiscountPercent(salesInvoiceBean.getDiscountPercent());
		salesInvoice.setDiscountedAmount(salesInvoiceBean.getDiscountedAmount());
		salesInvoice.setProcessed(salesInvoiceBean.isProcessed());
		return salesInvoice;
	}

	private static List<SalesInvoiceItem> prepareSalesInvoiceItems(
			List<SalesInvoiceItemBean> salesInvoiceItemBeans) {
		List<SalesInvoiceItem> salesInvoiceItems = new LinkedList<SalesInvoiceItem>();
		for (SalesInvoiceItemBean salesInvoiceItemBean : salesInvoiceItemBeans) {
			SalesInvoiceItem salesInvoiceItem = new SalesInvoiceItem();
			salesInvoiceItem.setFinYear(salesInvoiceItemBean.getFinYear());
			salesInvoiceItem.setDescription(salesInvoiceItemBean
					.getDescription());
			salesInvoiceItem.setProductId(ProductUtilities.prepareProductModel(salesInvoiceItemBean.getProductId()));

			salesInvoiceItem.setSalesInvoiceId(salesInvoiceItemBean
					.getSalesInvoiceId());
			salesInvoiceItem.setQuantity(salesInvoiceItemBean
					.getQuantity());
			salesInvoiceItem.setQuantityType(salesInvoiceItemBean
					.getQuantityType());
			salesInvoiceItem.setRate(salesInvoiceItemBean.getRate());
			salesInvoiceItem.setSrNo(salesInvoiceItemBean.getSrNo());
			salesInvoiceItem.setTotalCost(salesInvoiceItemBean
					.getTotalCost());
			salesInvoiceItem.setVariantCode(salesInvoiceItemBean.getVariantCode());
			salesInvoiceItem.setVariantId(salesInvoiceItemBean.getVariantId());
			salesInvoiceItems.add(salesInvoiceItem);
		}

		return salesInvoiceItems;
	}

}
