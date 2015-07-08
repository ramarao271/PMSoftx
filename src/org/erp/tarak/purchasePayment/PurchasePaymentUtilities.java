package org.erp.tarak.purchasePayment;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.supplier.SupplierService;
import org.erp.tarak.supplier.SupplierUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoice;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceUtilities;
import org.erp.tarak.user.UserBean;

public class PurchasePaymentUtilities {

	public static List <String> paymentModes=new LinkedList<String>();
	static
	{
		paymentModes.add(ERPConstants.PM_CASH);
		paymentModes.add(ERPConstants.PM_CHEQUE);
		paymentModes.add(ERPConstants.PM_ONLINE_TRANSFER);
	}
	public static PurchasePayment getPurchasePaymentModel(
			PurchasePaymentService purchasePaymentService, long purchasePaymentId,String finYear) {
		PurchasePayment purchasePayment = purchasePaymentService
				.getPurchasePayment(purchasePaymentId,finYear);
		return purchasePayment;
	}

	public static List<PurchasePaymentItemBean> preparePurchasePaymentItemBeans(
			List<PurchasePaymentItem> purchasePaymentItems) {
		List<PurchasePaymentItemBean> purchasePaymentItemBeans = new LinkedList<PurchasePaymentItemBean>();
		for (PurchasePaymentItem purchasePaymentItem : purchasePaymentItems) {
			PurchasePaymentItemBean purchasePaymentItemBean = new PurchasePaymentItemBean();
			purchasePaymentItemBean.setFinYear(purchasePaymentItem.getFinYear());
			purchasePaymentItemBean.setPurchasePaymentId(purchasePaymentItem
					.getPurchasePaymentId());
			purchasePaymentItemBean.setPurchaseInvoiceId(purchasePaymentItem.getPurchaseInvoiceId());
			purchasePaymentItemBean.setSrNo(purchasePaymentItem.getSrNo());
			purchasePaymentItemBean.setPaid(purchasePaymentItem.getPaid());
			purchasePaymentItemBean.setProcessed(purchasePaymentItem.isProcessed());
			purchasePaymentItemBeans.add(purchasePaymentItemBean);
		}

		return purchasePaymentItemBeans;
	}

	public static PurchasePaymentBean preparePurchasePaymentBean(PurchasePayment po,
			SupplierService supplierService) {
		PurchasePaymentBean pob = new PurchasePaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchasePaymentDate(po.getPurchasePaymentDate());
		pob.setPurchasePaymentId(po.getPurchasePaymentId());
		List<PurchasePaymentItemBean> poib = preparePurchasePaymentItemBeans(po
				.getPurchasePaymentItems());
		pob.setPurchasePaymentItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId().getSupplierId(), supplierService));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static PurchasePaymentBean preparePurchasePaymentBean(PurchasePayment po) {
		PurchasePaymentBean pob = new PurchasePaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setPurchasePaymentDate(po.getPurchasePaymentDate());
		pob.setPurchasePaymentId(po.getPurchasePaymentId());
		List<PurchasePaymentItemBean> poib = preparePurchasePaymentItemBeans(po
				.getPurchasePaymentItems());
		pob.setPurchasePaymentItemBeans(poib);
		pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
				.getSupplierId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static PurchasePayment preparePurchasePaymentModel(
			PurchasePaymentBean purchasePaymentBean, UserBean user,
			SupplierService supplierService, CategoryService categoryService,
			MeasurementService measurementService, PurchaseInvoiceService purchaseInvoiceService) {
		PurchasePayment purchasePayment = new PurchasePayment();
		purchasePayment
				.setPurchasePaymentDate(purchasePaymentBean.getPurchasePaymentDate());
		purchasePayment.setPurchasePaymentId(purchasePaymentBean.getPurchasePaymentId());
		purchasePayment.setTotalCost(purchasePaymentBean.getTotalCost());
		purchasePayment.setFinYear(user.getFinYear());
		List<PurchasePaymentItem> purchasePaymentItems = preparePurchasePaymentItems(
				purchasePaymentBean.getPurchasePaymentItemBeans(), user,
				categoryService, measurementService,purchaseInvoiceService);
		purchasePayment.setPurchasePaymentItems(purchasePaymentItems);
		purchasePayment.setSupplierId(supplierService.getSupplier(purchasePayment.getSupplierId().getSupplierId()));
		purchasePayment.setPaymentMode(purchasePaymentBean.getPaymentMode());
		purchasePayment.setPaymentReference(purchasePaymentBean.getPaymentReference());
		purchasePayment.setAdvance(purchasePaymentBean.getAdvance());
		return purchasePayment;
	}

	public static List<PurchasePaymentItem> preparePurchasePaymentItems(
			List<PurchasePaymentItemBean> purchasePaymentItemBeans, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService,PurchaseInvoiceService purchaseInvoiceService) {
		List<PurchasePaymentItem> purchasePaymentItems = new LinkedList<PurchasePaymentItem>();
		for (PurchasePaymentItemBean purchasePaymentItemBean : purchasePaymentItemBeans) {
			PurchasePaymentItem purchasePaymentItem = new PurchasePaymentItem();
			purchasePaymentItem.setFinYear(user.getFinYear());
			purchasePaymentItem.setPurchasePaymentId(purchasePaymentItemBean
					.getPurchasePaymentId());
			purchasePaymentItem.setSrNo(purchasePaymentItemBean.getSrNo());
			purchasePaymentItem.setPurchaseInvoiceId(purchasePaymentItemBean.getPurchaseInvoiceId());
			purchasePaymentItem.setPaid(purchasePaymentItemBean.getPaid());
			purchasePaymentItem.setProcessed(purchasePaymentItemBean.isProcessed());
			purchasePaymentItems.add(purchasePaymentItem);
		}

		return purchasePaymentItems;
	}

	public static List<PurchasePaymentBean> prepareListofPurchasePaymentBean(
			List<PurchasePayment> purchasePayments, SupplierService supplierService) {
		List<PurchasePaymentBean> beans = new LinkedList<PurchasePaymentBean>();
		for (PurchasePayment po : purchasePayments) {
			PurchasePaymentBean pob = new PurchasePaymentBean();
			pob.setFinYear(po.getFinYear());
			pob.setPurchasePaymentDate(po.getPurchasePaymentDate());
			pob.setPurchasePaymentId(po.getPurchasePaymentId());
			List<PurchasePaymentItemBean> poib = preparePurchasePaymentItemBeans(po
					.getPurchasePaymentItems());
			pob.setPurchasePaymentItemBeans(poib);
			pob.setSupplierBean(SupplierUtilities.prepareSupplierBean(po
					.getSupplierId()));
			pob.setTotalCost(po.getTotalCost());
			pob.setPaymentMode(po.getPaymentMode());
			pob.setPaymentReference(po.getPaymentReference());
			pob.setAdvance(po.getAdvance());
			beans.add(pob);
		}

		return beans;
	}

	public static List<PurchasePaymentItemBean> preparePurchasePaymentItemBeansFromInvoices(
			List<PurchaseInvoiceBean> purchaseInvoiceBeans) {
		List <PurchasePaymentItemBean> purchasePaymentItemBeans=new LinkedList<PurchasePaymentItemBean>();
		for(PurchaseInvoiceBean purchaseInvoiceBean: purchaseInvoiceBeans)
		{
			PurchasePaymentItemBean purchasePaymentItemBean=preparePurchasePaymentItemBeanFromInvoice(purchaseInvoiceBean);
			purchasePaymentItemBeans.add(purchasePaymentItemBean);
		}
		return purchasePaymentItemBeans;
	}

	private static PurchasePaymentItemBean preparePurchasePaymentItemBeanFromInvoice(
			PurchaseInvoiceBean purchaseInvoiceBean) {
		PurchasePaymentItemBean purchasePaymentItemBean=new PurchasePaymentItemBean();
		return purchasePaymentItemBean;
	}
	public static PurchasePaymentBean preparePPBeanWithBill(PurchasePaymentBean purchasePaymentBean, PurchaseInvoiceService purchaseInvoiceService, SupplierService supplierService,String finYear)
	{
		List<PurchaseInvoice> purchaseInvoices=purchaseInvoiceService.listPurchaseInvoicesBySupplier(purchasePaymentBean.getSupplierBean().getSupplierId(),finYear);
		List<PurchaseInvoiceBean> purchaseInvoiceBeans=PurchaseInvoiceUtilities.prepareListofPurchaseInvoiceBean(purchaseInvoices);
		List<PurchasePaymentItemBean> purchasePaymentItemBeans=PurchasePaymentUtilities.preparePurchasePaymentItemBeansFromInvoices(purchaseInvoiceBeans);
		purchasePaymentBean.setPurchasePaymentItemBeans(purchasePaymentItemBeans);
		return purchasePaymentBean;
	}

	public static void updatePurchaseInvoiceAmount(
			List<PurchasePaymentItem> purchasePaymentItems, PurchasePayment savedPurchasePayment,PurchaseInvoiceService purchaseInvoiceService, String operation,String finYear) {
		if(purchasePaymentItems!=null && purchasePaymentItems.size()>0)
		{
			for(PurchasePaymentItem purchasePaymentItem: purchasePaymentItems)
			{
				PurchaseInvoice purchaseInvoice=purchaseInvoiceService.getPurchaseInvoice(purchasePaymentItem.getPurchaseInvoiceId(),finYear);
				double newAmount=purchasePaymentItem.getPaid();
				if(operation.equals(ERPConstants.OP_CREATE))
				{
					if(savedPurchasePayment!=null)
					{
						newAmount=purchasePaymentItem.getPaid()-getAmountFromSaved(savedPurchasePayment,purchasePaymentItem.getSrNo());
					}
				}
				else if(operation.equals(ERPConstants.OP_DELETE))
				{
					newAmount=-purchasePaymentItem.getPaid();
				}
				purchaseInvoice.setPaidAmount(purchaseInvoice.getPaidAmount()+newAmount);
				if((purchaseInvoice.getReturnAmount()>0 && purchaseInvoice.getReturnAmount()-purchaseInvoice.getPaidAmount()==0) || purchaseInvoice.getTotalCost()-purchaseInvoice.getPaidAmount()==0)
				{
					purchaseInvoice.setProcessed(true);
				}
				else
				{
					purchaseInvoice.setProcessed(purchasePaymentItem.isProcessed());
				}
				purchaseInvoiceService.addPurchaseInvoice(purchaseInvoice);
			}
		}
	}

	private static double getAmountFromSaved(PurchasePayment savedPurchasePayment,
			int srNo) {
		if(savedPurchasePayment.getPurchasePaymentItems()!=null && savedPurchasePayment.getPurchasePaymentItems().size()>0)
		{
			for(PurchasePaymentItem purchasePaymentItem: savedPurchasePayment.getPurchasePaymentItems())
			{
				if(srNo==purchasePaymentItem.getSrNo())
				{
					return purchasePaymentItem.getPaid();
				}
			}
		}
		return 0;
	}

	public static PurchasePayment preparePurchasePaymentModelFromSession(
			PurchasePaymentBean purchasePaymentBean, PurchasePaymentBean s1pBean, UserBean user) {
		if(s1pBean!=null)
		{
			purchasePaymentBean.setSupplierBean(s1pBean.getSupplierBean());
		}	
		purchasePaymentBean.setFinYear(user.getFinYear());
		List<PurchasePaymentItemBean> purchasePaymentItemBeans=preparePurchasePaymentItemBeansFromSession(purchasePaymentBean.getPurchasePaymentItemBeans(),purchasePaymentBean.getPurchasePaymentItemBeans(),user);
		purchasePaymentBean.setPurchasePaymentItemBeans(purchasePaymentItemBeans);
		PurchasePayment purchasePayment=preparePurchasePaymentModel(purchasePaymentBean);
		return purchasePayment;
	}

	private static PurchasePayment preparePurchasePaymentModel(
			PurchasePaymentBean purchasePaymentBean) {
		PurchasePayment purchasePayment=new PurchasePayment();
		purchasePayment.setPurchasePaymentDate(purchasePaymentBean.getPurchasePaymentDate());
		purchasePayment.setPurchasePaymentId(purchasePaymentBean.getPurchasePaymentId());
		purchasePayment.setTotalCost(purchasePaymentBean.getTotalCost());
		purchasePayment.setFinYear(purchasePaymentBean.getFinYear());
		List<PurchasePaymentItem> purchasePaymentItems = preparePurchasePaymentItems(
		purchasePaymentBean.getPurchasePaymentItemBeans());
		purchasePayment.setPurchasePaymentItems(purchasePaymentItems);
		purchasePayment.setSupplierId(SupplierUtilities.prepareSupplierModel(purchasePaymentBean.getSupplierBean()));
		purchasePayment.setPaymentMode(purchasePaymentBean.getPaymentMode());
		purchasePayment.setPaymentReference(purchasePaymentBean.getPaymentReference());
		purchasePayment.setAdvance(purchasePaymentBean.getAdvance());
		return purchasePayment;
	}

	private static List<PurchasePaymentItem> preparePurchasePaymentItems(
			List<PurchasePaymentItemBean> purchasePaymentItemBeans) {
		List <PurchasePaymentItem> purchasePaymentItems=new LinkedList<PurchasePaymentItem>();
		for(PurchasePaymentItemBean purchasePaymentItemBean: purchasePaymentItemBeans)
		{
			PurchasePaymentItem purchasePaymentItem=new PurchasePaymentItem();
			purchasePaymentItem.setFinYear(purchasePaymentItemBean.getFinYear());
			purchasePaymentItem.setPaid(purchasePaymentItemBean.getPaid());
			purchasePaymentItem.setPurchaseInvoiceId(purchasePaymentItemBean.getPurchaseInvoiceId());
			purchasePaymentItem.setSrNo(purchasePaymentItemBean.getSrNo());
			purchasePaymentItem.setProcessed(purchasePaymentItemBean.isProcessed());
			purchasePaymentItems.add(purchasePaymentItem);
		}
		return purchasePaymentItems;
	}

	private static List<PurchasePaymentItemBean> preparePurchasePaymentItemBeansFromSession(
			List<PurchasePaymentItemBean> purchasePaymentItemBeans, List<PurchasePaymentItemBean> spItemBeans, UserBean user) {
		for(PurchasePaymentItemBean purchasePaymentItemBean :purchasePaymentItemBeans)
		{
			purchasePaymentItemBean.setFinYear(user.getFinYear());
			for(PurchasePaymentItemBean spItemBean: spItemBeans)
			{
				if(spItemBean.getPurchaseInvoiceId()==purchasePaymentItemBean.getPurchaseInvoiceId())
				{
					purchasePaymentItemBean.setPurchaseInvoiceId(spItemBean.getPurchaseInvoiceId());
				}
			}
		}
		return purchasePaymentItemBeans;
	}
	
}
