package org.erp.tarak.salesPayment;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.customer.CustomerService;
import org.erp.tarak.customer.CustomerUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesinvoice.SalesInvoiceUtilities;
import org.erp.tarak.user.UserBean;

public class SalesPaymentUtilities {

	public static List <String> paymentModes=new LinkedList<String>();
	static
	{
		paymentModes.add(ERPConstants.PM_CASH);
		paymentModes.add(ERPConstants.PM_CHEQUE);
		paymentModes.add(ERPConstants.PM_ONLINE_TRANSFER);
	}
	public static SalesPayment getSalesPaymentModel(
			SalesPaymentService salesPaymentService, long salesPaymentId,String finYear) {
		SalesPayment salesPayment = salesPaymentService
				.getSalesPayment(salesPaymentId,finYear);
		return salesPayment;
	}

	public static List<SalesPaymentItemBean> prepareSalesPaymentItemBeans(
			List<SalesPaymentItem> salesPaymentItems) {
		List<SalesPaymentItemBean> salesPaymentItemBeans = new LinkedList<SalesPaymentItemBean>();
		for (SalesPaymentItem salesPaymentItem : salesPaymentItems) {
			SalesPaymentItemBean salesPaymentItemBean = new SalesPaymentItemBean();
			salesPaymentItemBean.setFinYear(salesPaymentItem.getFinYear());
			salesPaymentItemBean.setSalesPaymentId(salesPaymentItem
					.getSalesPaymentId());
			salesPaymentItemBean.setSalesInvoiceId(salesPaymentItem.getSalesInvoiceId());
			salesPaymentItemBean.setSrNo(salesPaymentItem.getSrNo());
			salesPaymentItemBean.setPaid(salesPaymentItem.getPaid());
			salesPaymentItemBean.setProcessed(salesPaymentItem.isProcessed());
			salesPaymentItemBeans.add(salesPaymentItemBean);
		}

		return salesPaymentItemBeans;
	}

	public static SalesPaymentBean prepareSalesPaymentBean(SalesPayment po,
			CustomerService customerService) {
		SalesPaymentBean pob = new SalesPaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesPaymentDate(po.getSalesPaymentDate());
		pob.setSalesPaymentId(po.getSalesPaymentId());
		List<SalesPaymentItemBean> poib = prepareSalesPaymentItemBeans(po
				.getSalesPaymentItems());
		pob.setSalesPaymentItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId().getCustomerId(), customerService));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static SalesPaymentBean prepareSalesPaymentBean(SalesPayment po) {
		SalesPaymentBean pob = new SalesPaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setSalesPaymentDate(po.getSalesPaymentDate());
		pob.setSalesPaymentId(po.getSalesPaymentId());
		List<SalesPaymentItemBean> poib = prepareSalesPaymentItemBeans(po
				.getSalesPaymentItems());
		pob.setSalesPaymentItemBeans(poib);
		pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
				.getCustomerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static SalesPayment prepareSalesPaymentModel(
			SalesPaymentBean salesPaymentBean, UserBean user,
			CustomerService customerService, CategoryService categoryService,
			MeasurementService measurementService, SalesInvoiceService salesInvoiceService) {
		SalesPayment salesPayment = new SalesPayment();
		salesPayment
				.setSalesPaymentDate(salesPaymentBean.getSalesPaymentDate());
		salesPayment.setSalesPaymentId(salesPaymentBean.getSalesPaymentId());
		salesPayment.setTotalCost(salesPaymentBean.getTotalCost());
		salesPayment.setFinYear(user.getFinYear());
		List<SalesPaymentItem> salesPaymentItems = prepareSalesPaymentItems(
				salesPaymentBean.getSalesPaymentItemBeans(), user,
				categoryService, measurementService,salesInvoiceService);
		salesPayment.setSalesPaymentItems(salesPaymentItems);
		salesPayment.setCustomerId(customerService.getCustomer(salesPayment.getCustomerId().getCustomerId()));
		salesPayment.setPaymentMode(salesPaymentBean.getPaymentMode());
		salesPayment.setPaymentReference(salesPaymentBean.getPaymentReference());
		salesPayment.setAdvance(salesPaymentBean.getAdvance());
		return salesPayment;
	}

	public static List<SalesPaymentItem> prepareSalesPaymentItems(
			List<SalesPaymentItemBean> salesPaymentItemBeans, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService,SalesInvoiceService salesInvoiceService) {
		List<SalesPaymentItem> salesPaymentItems = new LinkedList<SalesPaymentItem>();
		for (SalesPaymentItemBean salesPaymentItemBean : salesPaymentItemBeans) {
			SalesPaymentItem salesPaymentItem = new SalesPaymentItem();
			salesPaymentItem.setFinYear(user.getFinYear());
			salesPaymentItem.setSalesPaymentId(salesPaymentItemBean
					.getSalesPaymentId());
			salesPaymentItem.setSrNo(salesPaymentItemBean.getSrNo());
			salesPaymentItem.setSalesInvoiceId(salesPaymentItemBean.getSalesInvoiceId());
			salesPaymentItem.setPaid(salesPaymentItemBean.getPaid());
			salesPaymentItem.setProcessed(salesPaymentItemBean.isProcessed());
			salesPaymentItems.add(salesPaymentItem);
		}

		return salesPaymentItems;
	}

	public static List<SalesPaymentBean> prepareListofSalesPaymentBean(
			List<SalesPayment> salesPayments, CustomerService customerService) {
		List<SalesPaymentBean> beans = new LinkedList<SalesPaymentBean>();
		for (SalesPayment po : salesPayments) {
			SalesPaymentBean pob = new SalesPaymentBean();
			pob.setFinYear(po.getFinYear());
			pob.setSalesPaymentDate(po.getSalesPaymentDate());
			pob.setSalesPaymentId(po.getSalesPaymentId());
			List<SalesPaymentItemBean> poib = prepareSalesPaymentItemBeans(po
					.getSalesPaymentItems());
			pob.setSalesPaymentItemBeans(poib);
			pob.setCustomerBean(CustomerUtilities.prepareCustomerBean(po
					.getCustomerId()));
			pob.setTotalCost(po.getTotalCost());
			pob.setPaymentMode(po.getPaymentMode());
			pob.setPaymentReference(po.getPaymentReference());
			pob.setAdvance(po.getAdvance());
			beans.add(pob);
		}

		return beans;
	}

	public static List<SalesPaymentItemBean> prepareSalesPaymentItemBeansFromInvoices(
			List<SalesInvoiceBean> salesInvoiceBeans) {
		List <SalesPaymentItemBean> salesPaymentItemBeans=new LinkedList<SalesPaymentItemBean>();
		for(SalesInvoiceBean salesInvoiceBean: salesInvoiceBeans)
		{
			SalesPaymentItemBean salesPaymentItemBean=prepareSalesPaymentItemBeanFromInvoice(salesInvoiceBean);
			salesPaymentItemBeans.add(salesPaymentItemBean);
		}
		return salesPaymentItemBeans;
	}

	private static SalesPaymentItemBean prepareSalesPaymentItemBeanFromInvoice(
			SalesInvoiceBean salesInvoiceBean) {
		SalesPaymentItemBean salesPaymentItemBean=new SalesPaymentItemBean();
		return salesPaymentItemBean;
	}
	public static SalesPaymentBean prepareSPBeanWithBill(SalesPaymentBean salesPaymentBean, SalesInvoiceService salesInvoiceService, CustomerService customerService,String finYear)
	{
		List<SalesInvoice> salesInvoices=salesInvoiceService.listSalesInvoicesByCustomer(salesPaymentBean.getCustomerBean().getCustomerId(),finYear);
		List<SalesInvoiceBean> salesInvoiceBeans=SalesInvoiceUtilities.prepareListofSalesInvoiceBean(salesInvoices);
		List<SalesPaymentItemBean> salesPaymentItemBeans=SalesPaymentUtilities.prepareSalesPaymentItemBeansFromInvoices(salesInvoiceBeans);
		salesPaymentBean.setSalesPaymentItemBeans(salesPaymentItemBeans);
		return salesPaymentBean;
	}

	public static void updateSalesInvoiceAmount(
			List<SalesPaymentItem> salesPaymentItems, SalesPayment savedSalesPayment,SalesInvoiceService salesInvoiceService, String operation,String finYear) {
		if(salesPaymentItems!=null && salesPaymentItems.size()>0)
		{
			for(SalesPaymentItem salesPaymentItem: salesPaymentItems)
			{
				SalesInvoice salesInvoice=salesInvoiceService.getSalesInvoice(salesPaymentItem.getSalesInvoiceId(),finYear);
				double newAmount=salesPaymentItem.getPaid();
				if(operation.equals(ERPConstants.OP_CREATE))
				{
					if(savedSalesPayment!=null)
					{
						newAmount=salesPaymentItem.getPaid()-getAmountFromSaved(savedSalesPayment,salesPaymentItem.getSrNo());
					}
				}
				else if(operation.equals(ERPConstants.OP_DELETE))
				{
					newAmount=-salesPaymentItem.getPaid();
				}
				salesInvoice.setPaidAmount(salesInvoice.getPaidAmount()+newAmount);
				if((salesInvoice.getReturnAmount()>0 && salesInvoice.getReturnAmount()-salesInvoice.getPaidAmount()==0) || salesInvoice.getTotalCost()-salesInvoice.getPaidAmount()==0)
				{
					salesInvoice.setProcessed(true);
				}
				else
				{
					salesInvoice.setProcessed(salesPaymentItem.isProcessed());
				}
				salesInvoiceService.addSalesInvoice(salesInvoice);
			}
		}
	}

	private static double getAmountFromSaved(SalesPayment savedSalesPayment,
			int srNo) {
		if(savedSalesPayment.getSalesPaymentItems()!=null && savedSalesPayment.getSalesPaymentItems().size()>0)
		{
			for(SalesPaymentItem salesPaymentItem: savedSalesPayment.getSalesPaymentItems())
			{
				if(srNo==salesPaymentItem.getSrNo())
				{
					return salesPaymentItem.getPaid();
				}
			}
		}
		return 0;
	}

	public static SalesPayment prepareSalesPaymentModelFromSession(
			SalesPaymentBean salesPaymentBean, SalesPaymentBean s1pBean, UserBean user) {
		salesPaymentBean.setCustomerBean(s1pBean.getCustomerBean());
		salesPaymentBean.setFinYear(user.getFinYear());
		List<SalesPaymentItemBean> salesPaymentItemBeans=prepareSalesPaymentItemBeansFromSession(salesPaymentBean.getSalesPaymentItemBeans(),salesPaymentBean.getSalesPaymentItemBeans(),user);
		salesPaymentBean.setSalesPaymentItemBeans(salesPaymentItemBeans);
		SalesPayment salesPayment=prepareSalesPaymentModel(salesPaymentBean);
		return salesPayment;
	}

	private static SalesPayment prepareSalesPaymentModel(
			SalesPaymentBean salesPaymentBean) {
		SalesPayment salesPayment=new SalesPayment();
		salesPayment.setSalesPaymentDate(salesPaymentBean.getSalesPaymentDate());
		salesPayment.setSalesPaymentId(salesPaymentBean.getSalesPaymentId());
		salesPayment.setTotalCost(salesPaymentBean.getTotalCost());
		salesPayment.setFinYear(salesPaymentBean.getFinYear());
		List<SalesPaymentItem> salesPaymentItems = prepareSalesPaymentItems(
		salesPaymentBean.getSalesPaymentItemBeans());
		salesPayment.setSalesPaymentItems(salesPaymentItems);
		salesPayment.setCustomerId(CustomerUtilities.prepareCustomerModel(salesPaymentBean.getCustomerBean()));
		salesPayment.setPaymentMode(salesPaymentBean.getPaymentMode());
		salesPayment.setPaymentReference(salesPaymentBean.getPaymentReference());
		salesPayment.setAdvance(salesPaymentBean.getAdvance());
		return salesPayment;
	}

	private static List<SalesPaymentItem> prepareSalesPaymentItems(
			List<SalesPaymentItemBean> salesPaymentItemBeans) {
		List <SalesPaymentItem> salesPaymentItems=new LinkedList<SalesPaymentItem>();
		for(SalesPaymentItemBean salesPaymentItemBean: salesPaymentItemBeans)
		{
			SalesPaymentItem salesPaymentItem=new SalesPaymentItem();
			salesPaymentItem.setFinYear(salesPaymentItemBean.getFinYear());
			salesPaymentItem.setPaid(salesPaymentItemBean.getPaid());
			salesPaymentItem.setSalesInvoiceId(salesPaymentItemBean.getSalesInvoiceId());
			salesPaymentItem.setSrNo(salesPaymentItemBean.getSrNo());
			salesPaymentItem.setProcessed(salesPaymentItemBean.isProcessed());
			salesPaymentItems.add(salesPaymentItem);
		}
		return salesPaymentItems;
	}

	private static List<SalesPaymentItemBean> prepareSalesPaymentItemBeansFromSession(
			List<SalesPaymentItemBean> salesPaymentItemBeans, List<SalesPaymentItemBean> spItemBeans, UserBean user) {
		for(SalesPaymentItemBean salesPaymentItemBean :salesPaymentItemBeans)
		{
			salesPaymentItemBean.setFinYear(user.getFinYear());
			for(SalesPaymentItemBean spItemBean: spItemBeans)
			{
				if(spItemBean.getSalesInvoiceId()==salesPaymentItemBean.getSalesInvoiceId())
				{
					salesPaymentItemBean.setSalesInvoiceId(spItemBean.getSalesInvoiceId());
				}
			}
		}
		return salesPaymentItemBeans;
	}
	
}
