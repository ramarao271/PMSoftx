package org.erp.tarak.variant;

import java.util.LinkedList;
import java.util.List;


public class VariantUtilities {

	public static Variant saveVariant(VariantService variantService,
			Variant variant) {
		variantService.addVariant(variant);
		return variant;
	}

	public static Variant getVariantModel(VariantService variantService,
			long variantId) {
		Variant variant = variantService.getVariant(variantId);
		return variant;
	}
	
	public static Variant prepareVariantModel(VariantBean variantBean) {
		Variant variant = new Variant();
		variant.setVariantId(variantBean.getVariantId());
		variant.setCost(variantBean.getCost());
		variant.setProductCode(variantBean.getProductCode());
		variant.setQuantity(variantBean.getQuantity());
		variant.setVariantName(variantBean.getVariantName());
		variant.setVariantType(variantBean.getVariantType());
		return variant;
	}

	public static VariantBean prepareVariantBean(Variant variant) {
		VariantBean variantBean = new VariantBean();
		variantBean.setVariantId(variant.getVariantId());
		variantBean.setCost(variant.getCost());
		variantBean.setProductCode(variant.getProductCode());
		variantBean.setQuantity(variant.getQuantity());
		variantBean.setVariantName(variant.getVariantName());
		variantBean.setVariantType(variant.getVariantType());
		variantBean.setAllocated(variant.getAllocated());
		variantBean.setOrdered(variant.getOrdered());
		variantBean.setRequired(variant.getRequired());
		variantBean.setSold(variant.getSold());
		return variantBean;
	}
	
	public static List<VariantBean> prepareListofVarientBeans(List <Variant> variants)
	{
		List<VariantBean> variantBeans=new LinkedList<VariantBean>();
		for(Variant variant: variants)
		{
			VariantBean variantBean=prepareVariantBean(variant);
			variantBeans.add(variantBean);
		}
		return variantBeans;
	}
	
	public static List<Variant> prepareListofVarients(List <VariantBean> variantBeans)
	{
		List<Variant> variants=new LinkedList<Variant>();
		for(VariantBean variantBean: variantBeans)
		{
			Variant variant=prepareVariantModel(variantBean);
			variants.add(variant);
		}
		return variants;
	}

}
