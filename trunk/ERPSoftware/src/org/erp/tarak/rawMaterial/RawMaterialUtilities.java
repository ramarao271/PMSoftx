package org.erp.tarak.rawMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.erp.tarak.category.Category;
import org.erp.tarak.category.CategoryBean;
import org.erp.tarak.category.CategoryService;
import org.erp.tarak.category.CategoryUtilities;
import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.Measurement;
import org.erp.tarak.measurement.MeasurementBean;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.measurement.MeasurementUtilities;
import org.erp.tarak.productionorder.ProductionOrder;
import org.erp.tarak.productionorder.ProductionOrderBean;
import org.erp.tarak.productionorder.ProductionOrderItem;
import org.erp.tarak.productionorder.ProductionOrderItemBean;
import org.erp.tarak.productionorder.ProductionOrderUtilities;
import org.erp.tarak.variant.Variant;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.variant.VariantService;
import org.erp.tarak.variant.VariantUtilities;
import org.erp.tarak.variantProperties.VariantPropertiesService;

public class RawMaterialUtilities {
	
	static List <String> variantTypes=new LinkedList<String>();
	static List <String> rawMaterialionTypes=new LinkedList<String>();
		
	static {
		variantTypes.add("Select Variant");
		variantTypes.add(ERPConstants.VT_COLOR);
		variantTypes.add(ERPConstants.VT_MATERIAL);
		variantTypes.add(ERPConstants.VT_SIZE);
		rawMaterialionTypes.add(ERPConstants.PT_PURCHASED);
		rawMaterialionTypes.add(ERPConstants.PT_OWN_PRODUCTION);
		rawMaterialionTypes.add(ERPConstants.PT_RAW_MATERIAL);
	}

	public static List<RawMaterialBean> prepareListofRawMaterialBean(
			List<RawMaterial> rawMaterials) {
		List<RawMaterialBean> beans = null;
		if (rawMaterials != null && !rawMaterials.isEmpty()) {
			beans = new ArrayList<RawMaterialBean>();
			RawMaterialBean bean = null;
			for (RawMaterial rawMaterial : rawMaterials) {
				bean = prepareRawMaterialBean(rawMaterial);
				beans.add(bean);
			}
		}
		return beans;
	}

	public static RawMaterialBean prepareRawMaterialBean(RawMaterial rawMaterial) {
		RawMaterialBean bean = new RawMaterialBean();
		if (rawMaterial.getCategory() != null) {
			CategoryBean categoryBean = CategoryUtilities
					.prepareCategoryBean(rawMaterial.getCategory());
			bean.setCategoryBean(categoryBean);
		}
		if (rawMaterial.getMeasurement() != null) {
			MeasurementBean measurementBean = MeasurementUtilities
					.prepareMeasurementBean(rawMaterial.getMeasurement());
			bean.setMeasurementBean(measurementBean);
		}
		bean.setGroupId(rawMaterial.getGroupId());
		bean.setRawMaterialCode(rawMaterial.getRawMaterialCode());
		bean.setRawMaterialId(rawMaterial.getRawMaterialId());
		bean.setRawMaterialName(rawMaterial.getRawMaterialName());
		bean.setQuantity(rawMaterial.getQuantity());
		bean.setCost(rawMaterial.getCost());
		bean.setPrice(rawMaterial.getPrice());
		/*bean.setAllocated(rawMaterial.getAllocated());
		bean.setOrdered(rawMaterial.getOrdered());
		*/bean.setRequired(rawMaterial.getRequired());
		bean.setSold(rawMaterial.getSold());
		bean.setVariantType(rawMaterial.getVariantType());
		bean.setDiscontinued(rawMaterial.isDiscontinued());
		bean.setRawMaterialionType(rawMaterial.getRawMaterialionType());
		bean.setImagePath(rawMaterial.getImagePath());
		List<VariantBean> variantBeans = new LinkedList<VariantBean>();
		double variantQuantity=0;
		for (Variant variant : rawMaterial.getVariants()) {
			VariantBean variantBean = VariantUtilities
					.prepareVariantBean(variant);
			variantBeans.add(variantBean);
			variantQuantity+=variantBean.getQuantity();
		}
		
			VariantBean variantBean =new VariantBean();
			variantBean.setVariantType(ERPConstants.UNASSIGNED);
			variantBean.setVariantName("  ");
			variantBean.setQuantity(rawMaterial.getQuantity()-variantQuantity);
			variantBean.setProductCode(rawMaterial.getRawMaterialCode());
			variantBean.setAllocated(rawMaterial.getAllocated());
			variantBean.setOrdered(rawMaterial.getOrdered());
			variantBean.setRequired(rawMaterial.getRequired());
			variantBean.setSold(rawMaterial.getSold());
			variantBeans.add(variantBean);
		
		bean.setVariantBeans(variantBeans);
		return bean;
	}

	public static RawMaterial prepareRawMaterialModel(RawMaterialBean rawMaterialBean,
			CategoryService categoryService,
			MeasurementService measurementService,RawMaterialService rawMaterialService) {
		RawMaterial rawMaterial = new RawMaterial();
		if (rawMaterialBean.getCategoryBean() != null) {
			Category category = CategoryUtilities
					.prepareCategoryModel(rawMaterialBean.getCategoryBean());
			category = categoryService.getCategory(category.getCategoryId());
			rawMaterial.setCategory(category);
		}
		if (rawMaterialBean.getMeasurementBean() != null) {
			Measurement measurement = MeasurementUtilities
					.prepareMeasurementModel(rawMaterialBean.getMeasurementBean());
			measurement = measurementService.getMeasurement(measurement
					.getMeasurementId());
			rawMaterial.setMeasurement(measurement);
		}
		String rawMaterialCode="";
		if(rawMaterialBean.getRawMaterialId()>0)
		{
			rawMaterialCode=rawMaterialBean.getRawMaterialCode();
		}
		else
		{
			rawMaterialCode=getRawMaterialCode(rawMaterialService,rawMaterial.getCategory());
		}	
		rawMaterial.setRawMaterialCode(rawMaterialCode);
		rawMaterial.setVariantType(rawMaterialBean.getVariantType());
		if (rawMaterialBean.getVariantBeans() != null) {
			List<Variant> variants = new LinkedList<Variant>();
			int variantCount=1;
			for (VariantBean variantBean : rawMaterialBean.getVariantBeans()) {
				if (variantBean.getVariantName() != null && !"".equals(variantBean.getVariantName())) {
					String variantCode="";
					variantBean.setVariantType(rawMaterial.getVariantType());
					if(variantBean.getVariantId()>0)
					{
						variantCode=variantBean.getProductCode();
					}
					else
					{
						variantCode=getVariantCode(rawMaterialCode,variantCount);
					}	
				variantBean.setProductCode(variantCode);
					Variant variant = VariantUtilities
							.prepareVariantModel(variantBean);
					variants.add(variant);
					variantCount++;
				}
			}
			rawMaterial.setVariants(variants);
		}
		rawMaterial.setGroupId(rawMaterialBean.getGroupId());
		rawMaterial.setRawMaterialId(rawMaterialBean.getRawMaterialId());
		rawMaterial.setRawMaterialName(rawMaterialBean.getRawMaterialName());
		rawMaterial.setQuantity(rawMaterialBean.getQuantity());
		rawMaterial.setCost(rawMaterialBean.getCost());
		rawMaterial.setPrice(rawMaterialBean.getPrice());
		rawMaterial.setVariantType(rawMaterialBean.getVariantType());
		rawMaterial.setDiscontinued(rawMaterialBean.isDiscontinued());
		rawMaterial.setRawMaterialionType(rawMaterialBean.getRawMaterialionType());
		rawMaterial.setImagePath(rawMaterialBean.getImagePath());
		return rawMaterial;
	}

	private static String getVariantCode(String rawMaterialCode, int variantCount) {
		String variantCode="";
		variantCode=ERPUtilities.fomatStringToN(""+variantCount,2);
		variantCode=rawMaterialCode+variantCode;
		return variantCode;
	}

	private static String getRawMaterialCode(RawMaterialService rawMaterialService, Category category) {
		RawMaterial rawMaterial=rawMaterialService.getLastRawMaterialByCategory(category.getCategoryId());
		String rawMaterialCode="";
		if(rawMaterial!=null)
		{
			rawMaterialCode=ERPUtilities.incrementCode(rawMaterial.getRawMaterialCode());
			rawMaterialCode=ERPUtilities.fomatStringToN(rawMaterialCode,5);
		}
		else
		{
			rawMaterialCode=category.getCategoryCode()+"01";
		}
		return rawMaterialCode;
	}

	public static void updateRawMaterialModel(RawMaterialBean pb,
			CategoryService categoryService,
			MeasurementService measurementService,
			VariantService variantService, RawMaterialService rawMaterialService) {
		RawMaterial rawMaterial = RawMaterialUtilities.prepareRawMaterialModel(pb,
				categoryService, measurementService,rawMaterialService);
		rawMaterialService.addRawMaterial(rawMaterial);
	}

	public static RawMaterial getRawMaterialModel(long rawMaterialId,
			RawMaterialService rawMaterialService) {
		RawMaterial rawMaterial = rawMaterialService.getRawMaterial(rawMaterialId);
		return rawMaterial;
	}

	public static void saveRawMaterial(RawMaterial rawMaterial,
			RawMaterialService rawMaterialService) {
		rawMaterialService.addRawMaterial(rawMaterial);
	}

	public static RawMaterial prepareRawMaterialModel(RawMaterialBean rawMaterialBean,
			CategoryService categoryService,
			MeasurementService measurementService) {
		RawMaterial rawMaterial = new RawMaterial();
		if (rawMaterialBean.getCategoryBean() != null) {
			Category category = CategoryUtilities
					.prepareCategoryModel(rawMaterialBean.getCategoryBean());
			category = categoryService.getCategory(category.getCategoryId());
			rawMaterial.setCategory(category);
		}
		if (rawMaterialBean.getMeasurementBean() != null) {
			Measurement measurement = MeasurementUtilities
					.prepareMeasurementModel(rawMaterialBean.getMeasurementBean());
			measurement = measurementService.getMeasurement(measurement
					.getMeasurementId());
			rawMaterial.setMeasurement(measurement);
		}
		rawMaterial.setVariants(prepareVariantProperties(rawMaterialBean,rawMaterial.getVariantType()));
		rawMaterial.setGroupId(rawMaterialBean.getGroupId());
		rawMaterial.setRawMaterialCode(rawMaterialBean.getRawMaterialCode());
		rawMaterial.setRawMaterialId(rawMaterialBean.getRawMaterialId());
		rawMaterial.setRawMaterialName(rawMaterialBean.getRawMaterialName());
		rawMaterial.setQuantity(rawMaterialBean.getQuantity());
		rawMaterial.setCost(rawMaterialBean.getCost());
		rawMaterial.setPrice(rawMaterialBean.getPrice());
		rawMaterial.setVariantType(rawMaterialBean.getVariantType());
		rawMaterial.setDiscontinued(rawMaterialBean.isDiscontinued());
		rawMaterial.setRawMaterialionType(rawMaterialBean.getRawMaterialionType());
		rawMaterial.setImagePath(rawMaterialBean.getImagePath());
		return rawMaterial;	}

	private static List<Variant> prepareVariantProperties(RawMaterialBean rawMaterialBean, String variantType) {
		if (rawMaterialBean.getVariantBeans() != null) {
			List<Variant> variants = new LinkedList<Variant>();
			for (VariantBean variantBean : rawMaterialBean.getVariantBeans()) {
				if (variantBean.getVariantName() != null && !"".equals(variantBean.getVariantName())) {
					Variant variant = VariantUtilities
							.prepareVariantModel(variantBean);
					variant.setVariantType(variantType);
					variants.add(variant);
				}
			}
			return variants;
		}
		return null;
	}

	public static boolean isRawMaterialExists(RawMaterialService rawMaterialService,
			String rawMaterialName) {
		RawMaterial rawMaterial=rawMaterialService.getRawMaterialByName(rawMaterialName);
		if(rawMaterial!=null && rawMaterial.getRawMaterialId()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static Variant getVariantById(RawMaterial rawMaterial, long variantId) {
		
		for(Variant variant: rawMaterial.getVariants())
		{
			if(variantId==variant.getVariantId())
			{
				return variant;
			}
		}
		return null;
	}

	public static RawMaterial updateVariantById(RawMaterial rawMaterial, long variantId,
			double quantity, String type) {
		if(variantId==0)
		{
			switch(type)
			{
				case ERPConstants.SALES_ORDER:
					rawMaterial.setRequired(rawMaterial.getRequired()+ quantity);
					break;
				case ERPConstants.DELIVERY_CHALLAN:
					rawMaterial.setRequired(rawMaterial.getRequired()-quantity);
					rawMaterial.setAllocated(rawMaterial.getAllocated()+quantity);
					rawMaterial.setQuantity(rawMaterial.getQuantity()-quantity);
					break;
				case ERPConstants.SALES_INVOICE:
					rawMaterial.setAllocated(rawMaterial.getAllocated()-quantity);
					rawMaterial.setSold(rawMaterial.getSold()+quantity);
					break;
				case ERPConstants.PURCHASE_ORDER:
					rawMaterial.setOrdered(rawMaterial.getOrdered()+quantity);
					break;
				case ERPConstants.PURCHASE_INVOICE:
					rawMaterial.setOrdered(rawMaterial.getOrdered()-quantity);
					rawMaterial.setQuantity(rawMaterial.getQuantity()+quantity);
					break;
			}
			return rawMaterial;
		}
		
		Iterator<Variant> iterator=rawMaterial.getVariants().iterator();
		while(iterator.hasNext())
		{
			Variant variant=iterator.next();
			if(variantId==variant.getVariantId())
			{
				switch(type)
				{
					case ERPConstants.SALES_ORDER:
						variant.setRequired(variant.getRequired()+ quantity);
						break;
					case ERPConstants.DELIVERY_CHALLAN:
						variant.setRequired(variant.getRequired()-quantity);
						variant.setAllocated(variant.getAllocated()+quantity);
						variant.setQuantity(variant.getQuantity()-quantity);
						rawMaterial.setQuantity(rawMaterial.getQuantity()-quantity);
						break;
					case ERPConstants.SALES_INVOICE:
						variant.setAllocated(variant.getAllocated()-quantity);
						variant.setSold(variant.getSold()+quantity);
						break;
					case ERPConstants.PURCHASE_ORDER:
						variant.setOrdered(variant.getOrdered()+quantity);
						break;
					case ERPConstants.PURCHASE_INVOICE:
						variant.setOrdered(variant.getOrdered()-quantity);
						variant.setQuantity(variant.getQuantity()+quantity);
						rawMaterial.setQuantity(rawMaterial.getQuantity()+quantity);
						break;
				}
				break;
			}
		}
		return rawMaterial;
	}
	public static void updateRemovedItems(Object savedobject,Object object, RawMaterialService rawMaterialService, String type)
	{
		switch(type)
		{
			case ERPConstants.PRODUCTION_ORDER:
				ProductionOrder so=(ProductionOrder)savedobject;
				ProductionOrderBean salesOrderBean=(ProductionOrderBean)object; 
				List<ProductionOrderItemBean> ibList=ProductionOrderUtilities.prepareProductionOrderItemBeans(so.getProductionOrderItems());
				Iterator<ProductionOrderItemBean> iter=ibList.iterator();
				while(iter.hasNext())
				{
					boolean flag=false;
					ProductionOrderItemBean salesOrderItemBean=(ProductionOrderItemBean) iter.next();
					for(ProductionOrderItemBean newProductionOrderItemBean: salesOrderBean.getProductionOrderItemBeans())
					{
						if(salesOrderItemBean.getSrNo()==newProductionOrderItemBean.getSrNo())
						{
							flag=true;
							break;
						}
					}
					if(flag==true)
					{
						iter.remove();
					}
				}
				if(ibList.size()>0)
				{
					for(ProductionOrderItemBean soib: ibList)
					{
						updateRawMaterialDetailModel(soib.getRawMaterialBeanId().getRawMaterialId(),rawMaterialService,soib.getVariantId(),-soib.getQuantity(),type);
					}
				}
		}
	}

	public static void updateRawMaterialDetails(
			Object item,RawMaterialService rawMaterialService,Object savedobject,String type,String operation) 
	{
		if(item!=null)
		{
			double newValue=0;
			long rawMaterialId=0;
			long variantId=0;
			switch(type)
			{
				case ERPConstants.PRODUCTION_ORDER:
					if(operation.equals(ERPConstants.OP_CREATE) || operation.equals(ERPConstants.OP_EDIT))
					{
						ProductionOrderBean salesOrderBean=(ProductionOrderBean)item;
						for (ProductionOrderItemBean salesOrderItemBean : salesOrderBean.getProductionOrderItemBeans()) 
						{
							rawMaterialId=salesOrderItemBean.getRawMaterialBeanId().getRawMaterialId();
							newValue=salesOrderItemBean.getQuantity();
							variantId=salesOrderItemBean.getVariantId();
							if (savedobject!=null) 
							{
								Object obj=checkRawMaterialSaved(savedobject,rawMaterialId,variantId,salesOrderItemBean.getSrNo(),type);
								if(obj!=null)
								{
									ProductionOrderItem salesOrderItem1=(ProductionOrderItem)obj;
									if(salesOrderItem1!=null)
									{
										newValue=-salesOrderItem1.getQuantity()+salesOrderItemBean.getQuantity();
									}
								}
							}
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,variantId,newValue,type);
						}
						if(savedobject!=null)
						{
							updateRemovedItems(savedobject, salesOrderBean, rawMaterialService, type);
						}
					}
					else if(operation.equals(ERPConstants.OP_DELETE))
					{
						ProductionOrder salesOrder=(ProductionOrder)savedobject;
						List<ProductionOrderItem> list=salesOrder.getProductionOrderItems();
						for (ProductionOrderItem salesOrderItem : list) 
						{
							rawMaterialId=salesOrderItem.getRawMaterialId().getRawMaterialId();
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,salesOrderItem.getVariantId(),-salesOrderItem.getQuantity(),type);
						}
					}
				break;/*
				case ERPConstants.DELIVERY_CHALLAN:
					if(operation.equals(ERPConstants.OP_CREATE) || operation.equals(ERPConstants.OP_EDIT))
					{
						DeliveryChallanBean deliveryChallanBean=(DeliveryChallanBean)item;
						for (DeliveryChallanItemBean deliveryChallanItemBean : deliveryChallanBean.getDeliveryChallanItemBeans()) 
						{
							rawMaterialId=deliveryChallanItemBean.getRawMaterialId().getRawMaterialId();
							newValue=deliveryChallanItemBean.getQuantity();
							variantId=deliveryChallanItemBean.getVariantId();
							if (savedobject!=null) 
							{
								Object obj=checkRawMaterialSaved(savedobject,rawMaterialId,variantId,deliveryChallanItemBean.getSrNo(),type);
								if(obj!=null)
								{
									DeliveryChallanItem deliveryChallanItem1=(DeliveryChallanItem)obj;
									if(deliveryChallanItem1!=null)
									{
										newValue=-deliveryChallanItem1.getQuantity()+deliveryChallanItemBean.getQuantity();
									}
								}
							}
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,variantId,newValue,type);
						}
					}
					else if(operation.equals(ERPConstants.OP_DELETE))
					{
						DeliveryChallan deliveryChallan=(DeliveryChallan)savedobject;
						List<DeliveryChallanItem> list=deliveryChallan.getDeliveryChallanItems();
						for (DeliveryChallanItem deliveryChallanItem : list) 
						{
							rawMaterialId=deliveryChallanItem.getRawMaterialId().getRawMaterialId();
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,deliveryChallanItem.getVariantId(),-deliveryChallanItem.getQuantity(),type);
						}
					}
				break;
				case ERPConstants.SALES_INVOICE:
					if(operation.equals(ERPConstants.OP_CREATE) || operation.equals(ERPConstants.OP_EDIT))
					{
						ProductionInvoiceBean salesInvoiceBean=(ProductionInvoiceBean)item;
						for (ProductionInvoiceItemBean salesInvoiceItemBean : salesInvoiceBean.getProductionInvoiceItemBeans()) 
						{
							rawMaterialId=salesInvoiceItemBean.getRawMaterialId().getRawMaterialId();
							newValue=salesInvoiceItemBean.getQuantity();
							variantId=salesInvoiceItemBean.getVariantId();
							if (savedobject!=null) 
							{
								Object obj=checkRawMaterialSaved(savedobject,rawMaterialId,variantId,salesInvoiceItemBean.getSrNo(),type);
								if(obj!=null)
								{
									ProductionInvoiceItem salesInvoiceItem1=(ProductionInvoiceItem)obj;
									if(salesInvoiceItem1!=null)
									{
										newValue=-salesInvoiceItem1.getQuantity()+salesInvoiceItemBean.getQuantity();
									}
								}
							}
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,variantId,newValue,type);
						}
					}
					else if(operation.equals(ERPConstants.OP_DELETE))
					{
						ProductionInvoice salesInvoice=(ProductionInvoice)savedobject;
						List<ProductionInvoiceItem> list=salesInvoice.getProductionInvoiceItems();
						for (ProductionInvoiceItem salesInvoiceItem : list) 
						{
							rawMaterialId=salesInvoiceItem.getRawMaterialId().getRawMaterialId();
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,salesInvoiceItem.getVariantId(),-salesInvoiceItem.getQuantity(),type);
						}
					}
				break;	
				case ERPConstants.PURCHASE_ORDER:
					if(operation.equals(ERPConstants.OP_CREATE) || operation.equals(ERPConstants.OP_EDIT))
					{
						PurchaseOrderBean purchaseOrderBean=(PurchaseOrderBean)item;
						for (PurchaseOrderItemBean purchaseOrderItemBean : purchaseOrderBean.getPurchaseOrderItemBeans()) 
						{
							rawMaterialId=purchaseOrderItemBean.getRawMaterialId().getRawMaterialId();
							newValue=purchaseOrderItemBean.getQuantity();
							variantId=purchaseOrderItemBean.getVariantId();
							if (savedobject!=null) 
							{
								Object obj=checkRawMaterialSaved(savedobject,rawMaterialId,variantId,purchaseOrderItemBean.getSrNo(),type);
								if(obj!=null)
								{
									PurchaseOrderItem purchaseOrderItem1=(PurchaseOrderItem)obj;
									if(purchaseOrderItem1!=null)
									{
										newValue=-purchaseOrderItem1.getQuantity()+purchaseOrderItemBean.getQuantity();
									}
								}
							}
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,variantId,newValue,type);
						}
					}
					else if(operation.equals(ERPConstants.OP_DELETE))
					{
						PurchaseOrder purchaseOrder=(PurchaseOrder)savedobject;
						List<PurchaseOrderItem> list=purchaseOrder.getPurchaseOrderItems();
						for (PurchaseOrderItem purchaseOrderItem : list) 
						{
							rawMaterialId=purchaseOrderItem.getRawMaterialId().getRawMaterialId();
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,purchaseOrderItem.getVariantId(),-purchaseOrderItem.getQuantity(),type);
						}
					}
				break;
				case ERPConstants.PURCHASE_INVOICE:
					if(operation.equals(ERPConstants.OP_CREATE) || operation.equals(ERPConstants.OP_EDIT))
					{
						PurchaseInvoiceBean purchaseInvoiceBean=(PurchaseInvoiceBean)item;
						for (PurchaseInvoiceItemBean purchaseInvoiceItemBean : purchaseInvoiceBean.getPurchaseInvoiceItemBeans()) 
						{
							rawMaterialId=purchaseInvoiceItemBean.getRawMaterialId().getRawMaterialId();
							newValue=purchaseInvoiceItemBean.getQuantity();
							variantId=purchaseInvoiceItemBean.getVariantId();
							if (savedobject!=null) 
							{
								Object obj=checkRawMaterialSaved(savedobject,rawMaterialId,variantId,purchaseInvoiceItemBean.getSrNo(),type);
								if(obj!=null)
								{
									PurchaseInvoiceItem purchaseInvoiceItem1=(PurchaseInvoiceItem)obj;
									if(purchaseInvoiceItem1!=null)
									{
										newValue=-purchaseInvoiceItem1.getQuantity()+purchaseInvoiceItemBean.getQuantity();
									}
								}
							}
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,variantId,newValue,type);
						}
					}
					else if(operation.equals(ERPConstants.OP_DELETE))
					{
						PurchaseInvoice purchaseInvoice=(PurchaseInvoice)savedobject;
						List<PurchaseInvoiceItem> list=purchaseInvoice.getPurchaseInvoiceItems();
						for (PurchaseInvoiceItem purchaseInvoiceItem : list) 
						{
							rawMaterialId=purchaseInvoiceItem.getRawMaterialId().getRawMaterialId();
							updateRawMaterialDetailModel(rawMaterialId,rawMaterialService,purchaseInvoiceItem.getVariantId(),-purchaseInvoiceItem.getQuantity(),type);
						}
					}
				break;
				
*/
			}
		}
		
	}

	private static void updateRawMaterialDetailModel(long rawMaterialId, RawMaterialService rawMaterialService,long variantId, double newValue, String type) {
		RawMaterial rawMaterial = RawMaterialUtilities.getRawMaterialModel(rawMaterialId,rawMaterialService);
		rawMaterial=RawMaterialUtilities.updateVariantById(rawMaterial,variantId,newValue,type);
		RawMaterialUtilities.saveRawMaterial(rawMaterial, rawMaterialService);
		
	}

/*	public static void updateRawMaterialDetailsForDelete(
			List<ProductionOrderItem> list,
			RawMaterialService rawMaterialService,String type) {
		
		
	}
*/	
	private static Object checkRawMaterialSaved(
			Object savedObject, long rawMaterialId,long variantId,int srNo, String type) 
		{
		    if(savedObject!=null)
		    {
		    	switch(type)
		    	{
		    		case ERPConstants.PRODUCTION_ORDER:
		    			ProductionOrder savedProductionOrder=(ProductionOrder) savedObject;
		    			List<ProductionOrderItem> savedProductionOrderItems =savedProductionOrder.getProductionOrderItems();
		    			for(ProductionOrderItem salesOrderItem: savedProductionOrderItems)
		    			{
		    				if(rawMaterialId==salesOrderItem.getRawMaterialId().getRawMaterialId() && variantId==salesOrderItem.getVariantId() && srNo==salesOrderItem.getSrNo())
		    				{
		    					return salesOrderItem;
		    				}
		    			}
		    		break;
		    		/*case ERPConstants.DELIVERY_CHALLAN:
		    			DeliveryChallan savedDeliveryChallan=(DeliveryChallan) savedObject;
		    			List<DeliveryChallanItem> savedDeliveryChallanItems =savedDeliveryChallan.getDeliveryChallanItems();
		    			for(DeliveryChallanItem deliveryChallanItem: savedDeliveryChallanItems)
		    			{
		    				if(rawMaterialId==deliveryChallanItem.getRawMaterialId().getRawMaterialId() && variantId==deliveryChallanItem.getVariantId() && srNo==deliveryChallanItem.getSrNo())
		    				{
		    					return deliveryChallanItem;
		    				}
		    			}
		    		case ERPConstants.SALES_INVOICE:
		    			ProductionInvoice savedProductionInvoice=(ProductionInvoice) savedObject;
		    			List<ProductionInvoiceItem> savedProductionInvoiceItems =savedProductionInvoice.getProductionInvoiceItems();
		    			for(ProductionInvoiceItem salesInvoiceItem: savedProductionInvoiceItems)
		    			{
		    				if(rawMaterialId==salesInvoiceItem.getRawMaterialId().getRawMaterialId() && variantId==salesInvoiceItem.getVariantId() && srNo==salesInvoiceItem.getSrNo())
		    				{
		    					return salesInvoiceItem;
		    				}
		    			}
		    		case ERPConstants.PURCHASE_ORDER:
		    			PurchaseOrder savedPurchaseOrder=(PurchaseOrder) savedObject;
		    			List<PurchaseOrderItem> savedPurchaseOrderItems =savedPurchaseOrder.getPurchaseOrderItems();
		    			for(PurchaseOrderItem purchaseOrderItem: savedPurchaseOrderItems)
		    			{
		    				if(rawMaterialId==purchaseOrderItem.getRawMaterialId().getRawMaterialId() && variantId==purchaseOrderItem.getVariantId() && srNo==purchaseOrderItem.getSrNo())
		    				{
		    					return purchaseOrderItem;
		    				}
		    			}
		    		break;
		    		case ERPConstants.PURCHASE_INVOICE:
		    			PurchaseInvoice savedPurchaseInvoice=(PurchaseInvoice) savedObject;
		    			List<PurchaseInvoiceItem> savedPurchaseInvoiceItems =savedPurchaseInvoice.getPurchaseInvoiceItems();
		    			for(PurchaseInvoiceItem purchaseInvoiceItem: savedPurchaseInvoiceItems)
		    			{
		    				if(rawMaterialId==purchaseInvoiceItem.getRawMaterialId().getRawMaterialId() && variantId==purchaseInvoiceItem.getVariantId() && srNo==purchaseInvoiceItem.getSrNo())
		    				{
		    					return purchaseInvoiceItem;
		    				}
		    			}
		 */   	}
		    }
		    return null;
		}

	public static Map<String, Object> getInputsInMap(
			RawMaterialService rawMaterialService, CategoryService categoryService,
			MeasurementService measurementService,
			VariantPropertiesService variantPropertiesService) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("variantTypes", variantTypes);
		model.put("pTypes", rawMaterialionTypes);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		model.put("measurements", MeasurementUtilities
				.prepareListofMeasurementBean(measurementService
						.listMeasurements()));
		return model;

	}

	public static RawMaterial prepareRawMaterialModel(RawMaterialBean rawMaterialBean) {
		RawMaterial rawMaterial=new RawMaterial();
		rawMaterial.setAllocated(rawMaterialBean.getAllocated());
		rawMaterial.setCategory(CategoryUtilities.prepareCategoryModel(rawMaterialBean.getCategoryBean()));
		rawMaterial.setCost(rawMaterialBean.getCost());
		rawMaterial.setDiscontinued(rawMaterialBean.isDiscontinued());
		rawMaterial.setGroupId(rawMaterialBean.getGroupId());
		rawMaterial.setImagePath(rawMaterialBean.getImagePath());
		rawMaterial.setMeasurement(MeasurementUtilities.prepareMeasurementModel(rawMaterialBean.getMeasurementBean()));
		rawMaterial.setOrdered(rawMaterialBean.getOrdered());
		rawMaterial.setPrice(rawMaterialBean.getPrice());
		/*rawMaterial.setPrice1(rawMaterialBean.getPrice1());
		rawMaterial.setPrice2(rawMaterialBean.getPrice2());
		rawMaterial.setPrice3(rawMaterialBean.getPrice3());
		*/
		rawMaterial.setRawMaterialCode(rawMaterialBean.getRawMaterialCode());
		rawMaterial.setRawMaterialId(rawMaterialBean.getRawMaterialId());
		rawMaterial.setRawMaterialionType(rawMaterialBean.getRawMaterialionType());
		rawMaterial.setRawMaterialName(rawMaterialBean.getRawMaterialName());
		rawMaterial.setQuantity(rawMaterial.getQuantity());
		rawMaterial.setRequired(rawMaterialBean.getRequired());
		rawMaterial.setSold(rawMaterialBean.getSold());
		rawMaterial.setVariantType(rawMaterialBean.getVariantType());
		rawMaterial.setVariants(prepareVariantProperties(rawMaterialBean,rawMaterial.getVariantType()));
		return rawMaterial;
	}
}
