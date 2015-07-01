package org.erp.tarak.salesreturn;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.product.Product;
import org.erp.tarak.product.ProductBean;
import org.erp.tarak.product.ProductUtilities;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.variant.VariantService;

public class SalesReturnUtilities {

	public static SalesReturn getSalesReturnModel(
			SalesReturnService salesReturnService,
			long salesReturnId,String finYear) {
		SalesReturn salesReturn = salesReturnService
				.getSalesReturn(salesReturnId,finYear);
		return salesReturn;
	}

	public static List<SalesReturnItemBean> prepareSalesReturnItemBeans(
			List<SalesReturnItem> salesReturnItems) {
		List<SalesReturnItemBean> salesReturnItemBeans = new LinkedList<SalesReturnItemBean>();
		for (SalesReturnItem salesReturnItem : salesReturnItems) {
			SalesReturnItemBean salesReturnItemBean = new SalesReturnItemBean();
			salesReturnItemBean
					.setFinYear(salesReturnItem.getFinYear());
			salesReturnItemBean.setDescription(salesReturnItem
					.getDescription());
			ProductBean pb = ProductUtilities
					.prepareProductBean(salesReturnItem.getProductId());
			salesReturnItemBean.setProductId(pb);
			salesReturnItemBean.setSalesReturnId(salesReturnItem
					.getSalesReturnId());
			salesReturnItemBean.setQuantity(salesReturnItem
					.getQuantity());
			salesReturnItemBean.setQuantityType(salesReturnItem
					.getQuantityType());
			salesReturnItemBean.setRate(salesReturnItem.getRate());
			salesReturnItemBean.setSrNo(salesReturnItem.getSrNo());
			salesReturnItemBean.setTotalCost(salesReturnItem
					.getTotalCost());
			salesReturnItemBean.setVariantCode(salesReturnItem.getVariantCode());
			salesReturnItemBean.setVariantId(salesReturnItem.getVariantId());
			salesReturnItemBeans.add(salesReturnItemBean);
		}

		return salesReturnItemBeans;
	}

	public static SalesReturnBean prepareSalesReturnBean(
			SalesReturn po) {
		SalesReturnBean pob = new SalesReturnBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesReturnDate(po.getSalesReturnDate());
		pob.setSalesReturnId(po.getSalesReturnId());
		List<SalesReturnItemBean> poib = prepareSalesReturnItemBeans(po
				.getSalesReturnItems());
		pob.setSalesReturnItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po.getCustomerId()));
		pob.setTotalCost(po.getTotalCost());
		SalesInvoiceBean salesInvoiceBean = SalesInvoiceUtilities
				.prepareSalesInvoiceBean(po.getSalesInvoice());
		pob.setSalesInvoiceBean(salesInvoiceBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setLrNo(po.getLrNo());
		return pob;
	}
	
	public static SalesReturnBean prepareSalesReturnBean(
			SalesReturn po, CustomerService CustomerService) {
		SalesReturnBean pob = new SalesReturnBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesReturnDate(po.getSalesReturnDate());
		pob.setSalesReturnId(po.getSalesReturnId());
		List<SalesReturnItemBean> poib = prepareSalesReturnItemBeans(po
				.getSalesReturnItems());
		pob.setSalesReturnItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId().getCustomerId(), CustomerService));
		pob.setTotalCost(po.getTotalCost());
		SalesInvoiceBean salesInvoiceBean = SalesInvoiceUtilities
				.prepareSalesInvoiceBean(po.getSalesInvoice());
		pob.setSalesInvoiceBean(salesInvoiceBean);
		pob.setPaidAmount(po.getPaidAmount());
		pob.setLrNo(po.getLrNo());
		return pob;
	}

	public static SalesReturn prepareSalesReturnModel(
			SalesReturnBean salesReturnBean, UserBean user,
			CustomerService CustomerService, CategoryService categoryService,
			MeasurementService measurementService,SalesInvoiceService salesInvoiceService, VariantService variantService) {
		SalesReturn salesReturn = new SalesReturn();
		salesReturn.setSalesReturnDate(salesReturnBean
				.getSalesReturnDate());
		salesReturn.setSalesReturnId(salesReturnBean
				.getSalesReturnId());
		salesReturn.setTotalCost(salesReturnBean.getTotalCost());
		salesReturn.setFinYear(user.getFinYear());
		List<SalesReturnItem> salesReturnItems = prepareSalesReturnItems(
				salesReturnBean.getSalesReturnItemBeans(), user,
				categoryService, measurementService,variantService);
		salesReturn.setSalesReturnItems(salesReturnItems);
		SalesInvoice po = SalesInvoiceUtilities.getSalesInvoiceModel(salesInvoiceService, salesReturnBean.getSalesInvoiceBean().getSalesInvoiceId(),user.getFinYear());
		salesReturn.setSalesInvoice(po);
		salesReturn.setCustomerId(po.getCustomerId());
		salesReturn.setPaidAmount(salesReturnBean.getPaidAmount());
		salesReturn.setLrNo(salesReturnBean.getLrNo());
		return salesReturn;
	}

	public static List<SalesReturnItem> prepareSalesReturnItems(
			List<SalesReturnItemBean> salesReturnItemBeans,
			UserBean user, CategoryService categoryService,
			MeasurementService measurementService, VariantService variantService) {
		List<SalesReturnItem> salesReturnItems = new LinkedList<SalesReturnItem>();
		for (SalesReturnItemBean salesReturnItemBean : salesReturnItemBeans) {
			SalesReturnItem salesReturnItem = new SalesReturnItem();
			salesReturnItem.setFinYear(user.getFinYear());
			salesReturnItem.setDescription(salesReturnItemBean
					.getDescription());
			Product pb = ProductUtilities.prepareProductModel(
					salesReturnItemBean.getProductId(), categoryService,
					measurementService);
			salesReturnItem.setProductId(pb);

			salesReturnItem.setSalesReturnId(salesReturnItemBean
					.getSalesReturnId());
			salesReturnItem.setQuantity(salesReturnItemBean
					.getQuantity());
			salesReturnItem.setQuantityType(salesReturnItemBean
					.getQuantityType());
			salesReturnItem.setRate(salesReturnItemBean.getRate());
			salesReturnItem.setSrNo(salesReturnItemBean.getSrNo());
			salesReturnItem.setTotalCost(salesReturnItemBean
					.getTotalCost());
			salesReturnItem.setVariantCode(salesReturnItemBean.getVariantCode());
			salesReturnItem.setVariantId(salesReturnItemBean.getVariantId());
			salesReturnItems.add(salesReturnItem);
		}

		return salesReturnItems;
	}

	public static List<SalesReturnBean> prepareListofSalesReturnBean(
			List<SalesReturn> salesReturns,
			CustomerService CustomerService) {
		List<SalesReturnBean> beans = new LinkedList<SalesReturnBean>();
		for (SalesReturn po : salesReturns) {
			SalesReturnBean pob = prepareSalesReturnBean(po, CustomerService);
			beans.add(pob);
		}

		return beans;
	}
	public static List<SalesReturnBean> prepareListofSalesReturnBean(
			List<SalesReturn> salesReturns) {
		List<SalesReturnBean> beans = new LinkedList<SalesReturnBean>();
		for (SalesReturn po : salesReturns) {
			SalesReturnBean pob = prepareSalesReturnBean(po);
			beans.add(pob);
		}

		return beans;
	}

	public static SalesReturn prepareSalesReturnModel(
			SalesReturnBean salesReturnBean) {
		SalesReturn salesReturn=new SalesReturn();
		salesReturn.setSalesReturnDate(salesReturnBean
				.getSalesReturnDate());
		salesReturn.setSalesReturnId(salesReturnBean
				.getSalesReturnId());
		salesReturn.setTotalCost(salesReturnBean.getTotalCost());
		salesReturn.setFinYear(salesReturnBean.getFinYear());
		List<SalesReturnItem> salesReturnItems = prepareSalesReturnItems(
				salesReturnBean.getSalesReturnItemBeans());
		salesReturn.setSalesReturnItems(salesReturnItems);
		salesReturn.setSalesInvoice(SalesInvoiceUtilities.prepareSalesInvoiceModel(salesReturnBean.getSalesInvoiceBean()));
		salesReturn.setCustomerId(CustomerUtilities.prepareCustomerModel(salesReturnBean.getCustomerBean()));
		salesReturn.setPaidAmount(salesReturnBean.getPaidAmount());
		salesReturn.setLrNo(salesReturnBean.getLrNo());
		
		
		return salesReturn;
	}

	private static List<SalesReturnItem> prepareSalesReturnItems(
			List<SalesReturnItemBean> salesReturnItemBeans) {
		List<SalesReturnItem> salesReturnItems = new LinkedList<SalesReturnItem>();
		for (SalesReturnItemBean salesReturnItemBean : salesReturnItemBeans) {
			SalesReturnItem salesReturnItem = new SalesReturnItem();
			salesReturnItem.setFinYear(salesReturnItemBean.getFinYear());
			salesReturnItem.setDescription(salesReturnItemBean
					.getDescription());
			salesReturnItem.setProductId(ProductUtilities.prepareProductModel(salesReturnItemBean.getProductId()));

			salesReturnItem.setSalesReturnId(salesReturnItemBean
					.getSalesReturnId());
			salesReturnItem.setQuantity(salesReturnItemBean
					.getQuantity());
			salesReturnItem.setQuantityType(salesReturnItemBean
					.getQuantityType());
			salesReturnItem.setRate(salesReturnItemBean.getRate());
			salesReturnItem.setSrNo(salesReturnItemBean.getSrNo());
			salesReturnItem.setTotalCost(salesReturnItemBean
					.getTotalCost());
			salesReturnItem.setVariantCode(salesReturnItemBean.getVariantCode());
			salesReturnItem.setVariantId(salesReturnItemBean.getVariantId());
			salesReturnItems.add(salesReturnItem);
		}

		return salesReturnItems;
	}

}
