package org.erp.tarak.otherPayment;

import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryService;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.productioninvoice.ProductionInvoice;
import org.erp.tarak.productioninvoice.ProductionInvoiceBean;
import org.erp.tarak.productioninvoice.ProductionInvoiceService;
import org.erp.tarak.productioninvoice.ProductionInvoiceUtilities;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.WorkerService;
import org.erp.tarak.worker.WorkerUtilities;

public class OtherPaymentUtilities {

	public static List <String> paymentModes=new LinkedList<String>();
	static
	{
		paymentModes.add(ERPConstants.PM_CASH);
		paymentModes.add(ERPConstants.PM_CHEQUE);
		paymentModes.add(ERPConstants.PM_ONLINE_TRANSFER);
	}
	public static OtherPayment getOtherPaymentModel(
			OtherPaymentService otherPaymentService, long otherPaymentId,String finYear) {
		OtherPayment otherPayment = otherPaymentService
				.getOtherPayment(otherPaymentId,finYear);
		return otherPayment;
	}

	public static List<OtherPaymentItemBean> prepareOtherPaymentItemBeans(
			List<OtherPaymentItem> otherPaymentItems) {
		List<OtherPaymentItemBean> otherPaymentItemBeans = new LinkedList<OtherPaymentItemBean>();
		for (OtherPaymentItem otherPaymentItem : otherPaymentItems) {
			OtherPaymentItemBean otherPaymentItemBean = new OtherPaymentItemBean();
			otherPaymentItemBean.setFinYear(otherPaymentItem.getFinYear());
			otherPaymentItemBean.setOtherPaymentId(otherPaymentItem
					.getOtherPaymentId());
			otherPaymentItemBean.setProductionInvoiceId(otherPaymentItem.getProductionInvoiceId());
			otherPaymentItemBean.setSrNo(otherPaymentItem.getSrNo());
			otherPaymentItemBean.setPaid(otherPaymentItem.getPaid());
			otherPaymentItemBean.setProcessed(otherPaymentItem.isProcessed());
			otherPaymentItemBeans.add(otherPaymentItemBean);
		}

		return otherPaymentItemBeans;
	}

	public static OtherPaymentBean prepareOtherPaymentBean(OtherPayment po,
			WorkerService workerService) {
		OtherPaymentBean pob = new OtherPaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setOtherPaymentDate(po.getOtherPaymentDate());
		pob.setOtherPaymentId(po.getOtherPaymentId());
		List<OtherPaymentItemBean> poib = prepareOtherPaymentItemBeans(po
				.getOtherPaymentItems());
		pob.setOtherPaymentItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId().getWorkerId(), workerService));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static OtherPaymentBean prepareOtherPaymentBean(OtherPayment po) {
		OtherPaymentBean pob = new OtherPaymentBean();
		pob.setFinYear(po.getFinYear());
		pob.setOtherPaymentDate(po.getOtherPaymentDate());
		pob.setOtherPaymentId(po.getOtherPaymentId());
		List<OtherPaymentItemBean> poib = prepareOtherPaymentItemBeans(po
				.getOtherPaymentItems());
		pob.setOtherPaymentItemBeans(poib);
		pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
				.getWorkerId()));
		pob.setTotalCost(po.getTotalCost());
		pob.setPaymentMode(po.getPaymentMode());
		pob.setPaymentReference(po.getPaymentReference());
		pob.setAdvance(po.getAdvance());
		return pob;
	}

	public static OtherPayment prepareOtherPaymentModel(
			OtherPaymentBean otherPaymentBean, UserBean user,
			WorkerService workerService, CategoryService categoryService,
			MeasurementService measurementService, ProductionInvoiceService productionInvoiceService) {
		OtherPayment otherPayment = new OtherPayment();
		otherPayment
				.setOtherPaymentDate(otherPaymentBean.getOtherPaymentDate());
		otherPayment.setOtherPaymentId(otherPaymentBean.getOtherPaymentId());
		otherPayment.setTotalCost(otherPaymentBean.getTotalCost());
		otherPayment.setFinYear(user.getFinYear());
		List<OtherPaymentItem> otherPaymentItems = prepareOtherPaymentItems(
				otherPaymentBean.getOtherPaymentItemBeans(), user,
				categoryService, measurementService,productionInvoiceService);
		otherPayment.setOtherPaymentItems(otherPaymentItems);
		otherPayment.setWorkerId(workerService.getWorker(otherPayment.getWorkerId().getWorkerId()));
		otherPayment.setPaymentMode(otherPaymentBean.getPaymentMode());
		otherPayment.setPaymentReference(otherPaymentBean.getPaymentReference());
		otherPayment.setAdvance(otherPaymentBean.getAdvance());
		return otherPayment;
	}

	public static List<OtherPaymentItem> prepareOtherPaymentItems(
			List<OtherPaymentItemBean> otherPaymentItemBeans, UserBean user,
			CategoryService categoryService,
			MeasurementService measurementService,ProductionInvoiceService productionInvoiceService) {
		List<OtherPaymentItem> otherPaymentItems = new LinkedList<OtherPaymentItem>();
		for (OtherPaymentItemBean otherPaymentItemBean : otherPaymentItemBeans) {
			OtherPaymentItem otherPaymentItem = new OtherPaymentItem();
			otherPaymentItem.setFinYear(user.getFinYear());
			otherPaymentItem.setOtherPaymentId(otherPaymentItemBean
					.getOtherPaymentId());
			otherPaymentItem.setSrNo(otherPaymentItemBean.getSrNo());
			otherPaymentItem.setProductionInvoiceId(otherPaymentItemBean.getProductionInvoiceId());
			otherPaymentItem.setPaid(otherPaymentItemBean.getPaid());
			otherPaymentItem.setProcessed(otherPaymentItemBean.isProcessed());
			otherPaymentItems.add(otherPaymentItem);
		}

		return otherPaymentItems;
	}

	public static List<OtherPaymentBean> prepareListofOtherPaymentBean(
			List<OtherPayment> otherPayments, WorkerService workerService) {
		List<OtherPaymentBean> beans = new LinkedList<OtherPaymentBean>();
		for (OtherPayment po : otherPayments) {
			OtherPaymentBean pob = new OtherPaymentBean();
			pob.setFinYear(po.getFinYear());
			pob.setOtherPaymentDate(po.getOtherPaymentDate());
			pob.setOtherPaymentId(po.getOtherPaymentId());
			List<OtherPaymentItemBean> poib = prepareOtherPaymentItemBeans(po
					.getOtherPaymentItems());
			pob.setOtherPaymentItemBeans(poib);
			pob.setWorkerBean(WorkerUtilities.prepareWorkerBean(po
					.getWorkerId()));
			pob.setTotalCost(po.getTotalCost());
			pob.setPaymentMode(po.getPaymentMode());
			pob.setPaymentReference(po.getPaymentReference());
			pob.setAdvance(po.getAdvance());
			beans.add(pob);
		}

		return beans;
	}

	public static List<OtherPaymentItemBean> prepareOtherPaymentItemBeansFromInvoices(
			List<ProductionInvoiceBean> productionInvoiceBeans) {
		List <OtherPaymentItemBean> otherPaymentItemBeans=new LinkedList<OtherPaymentItemBean>();
		for(ProductionInvoiceBean productionInvoiceBean: productionInvoiceBeans)
		{
			OtherPaymentItemBean otherPaymentItemBean=prepareOtherPaymentItemBeanFromInvoice(productionInvoiceBean);
			otherPaymentItemBeans.add(otherPaymentItemBean);
		}
		return otherPaymentItemBeans;
	}

	private static OtherPaymentItemBean prepareOtherPaymentItemBeanFromInvoice(
			ProductionInvoiceBean productionInvoiceBean) {
		OtherPaymentItemBean otherPaymentItemBean=new OtherPaymentItemBean();
		return otherPaymentItemBean;
	}
	public static OtherPaymentBean prepareOPBeanWithBill(OtherPaymentBean otherPaymentBean, ProductionInvoiceService productionInvoiceService, WorkerService workerService,String finYear)
	{
		List<ProductionInvoice> productionInvoices=productionInvoiceService.listProductionInvoicesByWorker(otherPaymentBean.getWorkerBean().getWorkerId(),finYear);
		List<ProductionInvoiceBean> productionInvoiceBeans=ProductionInvoiceUtilities.prepareListofProductionInvoiceBean(productionInvoices);
		List<OtherPaymentItemBean> otherPaymentItemBeans=OtherPaymentUtilities.prepareOtherPaymentItemBeansFromInvoices(productionInvoiceBeans);
		otherPaymentBean.setOtherPaymentItemBeans(otherPaymentItemBeans);
		return otherPaymentBean;
	}

	public static void updateProductionInvoiceAmount(
			List<OtherPaymentItem> otherPaymentItems, OtherPayment savedOtherPayment,ProductionInvoiceService productionInvoiceService, String operation,String finYear) {
		if(otherPaymentItems!=null && otherPaymentItems.size()>0)
		{
			for(OtherPaymentItem otherPaymentItem: otherPaymentItems)
			{
				ProductionInvoice productionInvoice=productionInvoiceService.getProductionInvoice(otherPaymentItem.getProductionInvoiceId(),finYear);
				double newAmount=otherPaymentItem.getPaid();
				if(operation.equals(ERPConstants.OP_CREATE))
				{
					if(savedOtherPayment!=null)
					{
						newAmount=otherPaymentItem.getPaid()-getAmountFromSaved(savedOtherPayment,otherPaymentItem.getSrNo());
					}
				}
				else if(operation.equals(ERPConstants.OP_DELETE))
				{
					newAmount=-otherPaymentItem.getPaid();
				}
				productionInvoice.setPaidAmount(productionInvoice.getPaidAmount()+newAmount);
				if((productionInvoice.getReturnAmount()>0 && productionInvoice.getReturnAmount()-productionInvoice.getPaidAmount()==0) || productionInvoice.getTotalCost()-productionInvoice.getPaidAmount()==0)
				{
					productionInvoice.setProcessed(true);
				}
				else
				{
					productionInvoice.setProcessed(otherPaymentItem.isProcessed());
				}
				productionInvoiceService.addProductionInvoice(productionInvoice);
			}
		}
	}

	private static double getAmountFromSaved(OtherPayment savedOtherPayment,
			int srNo) {
		if(savedOtherPayment.getOtherPaymentItems()!=null && savedOtherPayment.getOtherPaymentItems().size()>0)
		{
			for(OtherPaymentItem otherPaymentItem: savedOtherPayment.getOtherPaymentItems())
			{
				if(srNo==otherPaymentItem.getSrNo())
				{
					return otherPaymentItem.getPaid();
				}
			}
		}
		return 0;
	}

	public static OtherPayment prepareOtherPaymentModelFromSession(
			OtherPaymentBean otherPaymentBean, OtherPaymentBean s1pBean, UserBean user) {
		if(s1pBean!=null)
		{
			otherPaymentBean.setWorkerBean(s1pBean.getWorkerBean());
		}
		otherPaymentBean.setFinYear(user.getFinYear());
		List<OtherPaymentItemBean> otherPaymentItemBeans=prepareOtherPaymentItemBeansFromSession(otherPaymentBean.getOtherPaymentItemBeans(),otherPaymentBean.getOtherPaymentItemBeans(),user);
		otherPaymentBean.setOtherPaymentItemBeans(otherPaymentItemBeans);
		OtherPayment otherPayment=prepareOtherPaymentModel(otherPaymentBean);
		return otherPayment;
	}

	private static OtherPayment prepareOtherPaymentModel(
			OtherPaymentBean otherPaymentBean) {
		OtherPayment otherPayment=new OtherPayment();
		otherPayment.setOtherPaymentDate(otherPaymentBean.getOtherPaymentDate());
		otherPayment.setOtherPaymentId(otherPaymentBean.getOtherPaymentId());
		otherPayment.setTotalCost(otherPaymentBean.getTotalCost());
		otherPayment.setFinYear(otherPaymentBean.getFinYear());
		List<OtherPaymentItem> otherPaymentItems = prepareOtherPaymentItems(
		otherPaymentBean.getOtherPaymentItemBeans());
		otherPayment.setOtherPaymentItems(otherPaymentItems);
		otherPayment.setWorkerId(WorkerUtilities.prepareWorkerModel(otherPaymentBean.getWorkerBean()));
		otherPayment.setPaymentMode(otherPaymentBean.getPaymentMode());
		otherPayment.setPaymentReference(otherPaymentBean.getPaymentReference());
		otherPayment.setAdvance(otherPaymentBean.getAdvance());
		return otherPayment;
	}

	private static List<OtherPaymentItem> prepareOtherPaymentItems(
			List<OtherPaymentItemBean> otherPaymentItemBeans) {
		List <OtherPaymentItem> otherPaymentItems=new LinkedList<OtherPaymentItem>();
		for(OtherPaymentItemBean otherPaymentItemBean: otherPaymentItemBeans)
		{
			OtherPaymentItem otherPaymentItem=new OtherPaymentItem();
			otherPaymentItem.setFinYear(otherPaymentItemBean.getFinYear());
			otherPaymentItem.setPaid(otherPaymentItemBean.getPaid());
			otherPaymentItem.setProductionInvoiceId(otherPaymentItemBean.getProductionInvoiceId());
			otherPaymentItem.setSrNo(otherPaymentItemBean.getSrNo());
			otherPaymentItem.setProcessed(otherPaymentItemBean.isProcessed());
			otherPaymentItems.add(otherPaymentItem);
		}
		return otherPaymentItems;
	}

	private static List<OtherPaymentItemBean> prepareOtherPaymentItemBeansFromSession(
			List<OtherPaymentItemBean> otherPaymentItemBeans, List<OtherPaymentItemBean> spItemBeans, UserBean user) {
		for(OtherPaymentItemBean otherPaymentItemBean :otherPaymentItemBeans)
		{
			otherPaymentItemBean.setFinYear(user.getFinYear());
			for(OtherPaymentItemBean spItemBean: spItemBeans)
			{
				if(spItemBean.getProductionInvoiceId()==otherPaymentItemBean.getProductionInvoiceId())
				{
					otherPaymentItemBean.setProductionInvoiceId(spItemBean.getProductionInvoiceId());
				}
			}
		}
		return otherPaymentItemBeans;
	}
	
}
