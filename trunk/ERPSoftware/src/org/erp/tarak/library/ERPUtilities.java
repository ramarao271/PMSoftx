package org.erp.tarak.library;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.deliverychallan.DeliveryChallanBean;
import org.erp.tarak.deliverychallan.DeliveryChallanItemBean;
import org.erp.tarak.deliverychallan.DeliveryChallanService;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceItemBean;
import org.erp.tarak.purchaseinvoice.PurchaseInvoiceService;
import org.erp.tarak.purchaseorder.PurchaseOrderBean;
import org.erp.tarak.purchaseorder.PurchaseOrderItemBean;
import org.erp.tarak.purchaseorder.PurchaseOrderService;
import org.erp.tarak.purchasereturn.PurchaseReturnBean;
import org.erp.tarak.purchasereturn.PurchaseReturnItemBean;
import org.erp.tarak.salesinvoice.SalesInvoiceBean;
import org.erp.tarak.salesinvoice.SalesInvoiceItemBean;
import org.erp.tarak.salesinvoice.SalesInvoiceService;
import org.erp.tarak.salesorder.SalesOrderBean;
import org.erp.tarak.salesorder.SalesOrderItemBean;
import org.erp.tarak.salesorder.SalesOrderService;
import org.erp.tarak.salesreturn.SalesReturnBean;
import org.erp.tarak.salesreturn.SalesReturnItemBean;
import org.springframework.web.servlet.ModelAndView;

public class ERPUtilities {

	public static PurchaseInvoiceBean setPODetailsinPI(
			PurchaseInvoiceBean purchaseInvoiceBean, PurchaseOrderService pos) {
		PurchaseOrderBean po = purchaseInvoiceBean.getPurchaseOrderBean();
		List<PurchaseOrderItemBean> pois = po.getPurchaseOrderItemBeans();
		List<PurchaseInvoiceItemBean> pii = new LinkedList<PurchaseInvoiceItemBean>();
		for (PurchaseOrderItemBean poi : pois) {
			PurchaseInvoiceItemBean pib = new PurchaseInvoiceItemBean();
			pib.setDescription(poi.getDescription());
			pib.setFinYear(poi.getFinYear());
			pib.setProductId(poi.getProductId());
			pib.setPurchaseInvoiceId(purchaseInvoiceBean.getPurchaseInvoiceId());
			pib.setQuantity(poi.getQuantity());
			pib.setQuantityType(poi.getQuantityType());
			pib.setRate(poi.getRate());
			pib.setSrNo(poi.getSrNo());
			pib.setTotalCost(poi.getTotalCost());
			pib.setFinYear(purchaseInvoiceBean.getFinYear());
			pib.setVariantId(poi.getVariantId());
			if(poi.getVariantId()>0)
			{
				pib.setVariantCode(poi.getVariantCode());
			}
			else
			{
				pib.setVariantCode(poi.getProductId().getProductCode());
			}
			pii.add(pib);

		}
		purchaseInvoiceBean.setPurchaseInvoiceItemBeans(pii);
		purchaseInvoiceBean.setSupplierBean(po.getSupplierBean());
		purchaseInvoiceBean.setTotalCost(po.getTotalCost());
		return purchaseInvoiceBean;
	}

	public static SalesInvoiceBean setDCDetailsinSI(
			SalesInvoiceBean salesInvoiceBean, DeliveryChallanService pos) {
		DeliveryChallanBean po = salesInvoiceBean.getDeliveryChallanBean();
		List<DeliveryChallanItemBean> pois = po.getDeliveryChallanItemBeans();
		List<SalesInvoiceItemBean> pii = new LinkedList<SalesInvoiceItemBean>();
		for (DeliveryChallanItemBean poi : pois) {
			SalesInvoiceItemBean pib = new SalesInvoiceItemBean();
			pib.setDescription(poi.getDescription());
			pib.setFinYear(poi.getFinYear());
			pib.setProductId(poi.getProductId());
			pib.setSalesInvoiceId(salesInvoiceBean.getSalesInvoiceId());
			pib.setQuantity(poi.getQuantity());
			pib.setQuantityType(poi.getQuantityType());
			pib.setRate(poi.getRate());
			pib.setSrNo(poi.getSrNo());
			pib.setTotalCost(poi.getTotalCost());
			pib.setVariantId(poi.getVariantId());
			if(poi.getVariantId()>0)
			{
				pib.setVariantCode(poi.getVariantCode());
			}
			else
			{
				pib.setVariantCode(poi.getProductId().getProductCode());
			}
			pib.setFinYear(salesInvoiceBean.getFinYear());
			pii.add(pib);

		}
		salesInvoiceBean.setSalesInvoiceItemBeans(pii);
		salesInvoiceBean.setCustomerBean(po.getCustomerBean());
		salesInvoiceBean.setTotalCost(po.getTotalCost());
		return salesInvoiceBean;
	}

	public static DeliveryChallanBean setSODetailsinDC(
			DeliveryChallanBean deliveryChallanBean, SalesOrderService pos) {
		SalesOrderBean po = deliveryChallanBean.getSalesOrderBean();
		List<SalesOrderItemBean> pois = po.getSalesOrderItemBeans();
		List<DeliveryChallanItemBean> dci = new LinkedList<DeliveryChallanItemBean>();
		for (SalesOrderItemBean poi : pois) {
			if(!poi.isProcessed())
			{
				DeliveryChallanItemBean dcb = new DeliveryChallanItemBean();
				dcb.setDescription(poi.getDescription());
				dcb.setFinYear(poi.getFinYear());
				dcb.setProductId(poi.getProductId());
				dcb.setDeliveryChallanId(deliveryChallanBean.getDeliveryChallanId());
				dcb.setQuantity(poi.getQuantity());
				dcb.setQuantityType(poi.getQuantityType());
				dcb.setRate(poi.getRate());
				dcb.setSrNo(poi.getSrNo());
				dcb.setTotalCost(poi.getTotalCost());
				dcb.setFinYear(deliveryChallanBean.getFinYear());
				dcb.setVariantId(poi.getVariantId());
				if(poi.getVariantId()>0)
				{
					dcb.setVariantCode(poi.getVariantCode());
				}
				else
				{
					dcb.setVariantCode(poi.getProductId().getProductCode());
				}
				dci.add(dcb);
			}
		}
		deliveryChallanBean.setDeliveryChallanItemBeans(dci);
		deliveryChallanBean.setCustomerBean(po.getCustomerBean());
		deliveryChallanBean.setTotalCost(po.getTotalCost());
		return deliveryChallanBean;
	}

	public static boolean isValidUser(HttpSession session) {
		if (session != null && session.getAttribute("user") != null) {
			return true;
		} else {
			return false;
		}
	}

	public static ModelAndView handleUser(HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		return null;
	}

	public static String incrementCode(String code) {
		if (code == null) {
			return "0";
		}
		int iCode = Integer.parseInt(code);
		return ("" + ++iCode);
	}

	public static String fomatStringToN(String x, int i) {
		if (x.length() < i) {
			while (x.length() < i) {
				x = "0" + x;
			}
		}
		return x;
	}

	public static SalesReturnBean setSIDetailsinSR(
			SalesReturnBean salesReturnBean,
			SalesInvoiceService salesInvoiceService) {
		SalesInvoiceBean po = salesReturnBean.getSalesInvoiceBean();
		List<SalesInvoiceItemBean> pois = po.getSalesInvoiceItemBeans();
		List<SalesReturnItemBean> pii = new LinkedList<SalesReturnItemBean>();
		for (SalesInvoiceItemBean poi : pois) {
			SalesReturnItemBean pib = new SalesReturnItemBean();
			pib.setDescription(poi.getDescription());
			pib.setFinYear(poi.getFinYear());
			pib.setProductId(poi.getProductId());
			pib.setSalesReturnId(salesReturnBean.getSalesReturnId());
			pib.setQuantity(poi.getQuantity());
			pib.setQuantityType(poi.getQuantityType());
			pib.setRate(poi.getRate());
			pib.setSrNo(poi.getSrNo());
			pib.setTotalCost(poi.getTotalCost());
			pib.setVariantId(poi.getVariantId());
			if(poi.getVariantId()>0)
			{
				pib.setVariantCode(poi.getVariantCode());
			}
			else
			{
				pib.setVariantCode(poi.getProductId().getProductCode());
			}
			pib.setFinYear(salesReturnBean.getFinYear());
			pii.add(pib);

		}
		salesReturnBean.setSalesReturnItemBeans(pii);
		salesReturnBean.setCustomerBean(po.getCustomerBean());
		salesReturnBean.setTotalCost(po.getTotalCost());
		return salesReturnBean;
	}
	public static PurchaseReturnBean setPIDetailsinPR(
			PurchaseReturnBean purchaseReturnBean,
			PurchaseInvoiceService purchaseInvoiceService) {
		PurchaseInvoiceBean po = purchaseReturnBean.getPurchaseInvoiceBean();
		List<PurchaseInvoiceItemBean> pois = po.getPurchaseInvoiceItemBeans();
		List<PurchaseReturnItemBean> pii = new LinkedList<PurchaseReturnItemBean>();
		for (PurchaseInvoiceItemBean poi : pois) {
			PurchaseReturnItemBean pib = new PurchaseReturnItemBean();
			pib.setDescription(poi.getDescription());
			pib.setFinYear(poi.getFinYear());
			pib.setProductId(poi.getProductId());
			pib.setPurchaseReturnId(purchaseReturnBean.getPurchaseReturnId());
			pib.setQuantity(poi.getQuantity());
			pib.setQuantityType(poi.getQuantityType());
			pib.setRate(poi.getRate());
			pib.setSrNo(poi.getSrNo());
			pib.setTotalCost(poi.getTotalCost());
			pib.setVariantId(poi.getVariantId());
			if(poi.getVariantId()>0)
			{
				pib.setVariantCode(poi.getVariantCode());
			}
			else
			{
				pib.setVariantCode(poi.getProductId().getProductCode());
			}
			pib.setFinYear(purchaseReturnBean.getFinYear());
			pii.add(pib);

		}
		purchaseReturnBean.setPurchaseReturnItemBeans(pii);
		purchaseReturnBean.setSupplierBean(po.getSupplierBean());
		purchaseReturnBean.setTotalCost(po.getTotalCost());
		return purchaseReturnBean;
	}

	/*public static ProductionInvoiceBean setPRODetailsinPRI(
			ProductionInvoiceBean productionInvoiceBean,
			ProductionOrderService productionOrderService) {
		ProductionOrderBean po = productionInvoiceBean.getProductionOrderBean();
		List<ProductionOrderItemBean> pois = po.getProductionOrderItemBeans();
		List<ProductionInvoiceItemBean> pii = new LinkedList<ProductionInvoiceItemBean>();
		for (ProductionOrderItemBean poi : pois) {
			ProductionInvoiceItemBean pib = new ProductionInvoiceItemBean();
			pib.setDescription(poi.getDescription());
			pib.setFinYear(poi.getFinYear());
			pib.setProductBeanId(poi.getProductBeanId());
			pib.setProductionInvoiceId(productionInvoiceBean.getProductionInvoiceId());
			pib.setQuantity(poi.getQuantity());
			pib.setQuantityType(poi.getQuantityType());
			pib.setRate(poi.getRate());
			pib.setSrNo(poi.getSrNo());
			pib.setTotalCost(poi.getTotalCost());
			pib.setFinYear(productionInvoiceBean.getFinYear());
			pib.setVariantId(poi.getVariantId());
			if(poi.getVariantId()>0)
			{
				pib.setVariantCode(poi.getVariantCode());
			}
			else
			{
				pib.setVariantCode(poi.getProductBeanId().getProductCode());
			}
			pii.add(pib);

		}
		productionInvoiceBean.setProductionInvoiceItemBeans(pii);
		productionInvoiceBean.setWorkerBean(po.getWorkerBean());
		productionInvoiceBean.setTotalCost(po.getTotalCost());
		return productionInvoiceBean;
	}*/
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
