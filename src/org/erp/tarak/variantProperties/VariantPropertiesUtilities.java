package org.erp.tarak.variantProperties;

import java.util.LinkedList;
import java.util.List;


public class VariantPropertiesUtilities {

	public static VariantProperties saveVariantProperties(VariantPropertiesService variantPropertiesService,
			VariantProperties variantProperties) {
		variantPropertiesService.addVariantProperties(variantProperties);
		return variantProperties;
	}

	public static VariantProperties getVariantPropertiesModel(VariantPropertiesService variantPropertiesService,
			long variantPropertiesId) {
		VariantProperties variantProperties = variantPropertiesService.getVariantProperties(variantPropertiesId);
		return variantProperties;
	}
	
	public static VariantProperties prepareVariantPropertiesModel(VariantPropertiesBean variantPropertiesBean) {
		VariantProperties variantProperties = new VariantProperties();
		variantProperties.setVariantPropertiesId(variantPropertiesBean.getVariantPropertiesId());
		variantProperties.setVariantPropertiesName(variantPropertiesBean.getVariantPropertiesName());
		variantProperties.setVariantPropertiesType(variantPropertiesBean.getVariantPropertiesType());
		return variantProperties;
	}

	public static VariantPropertiesBean prepareVariantPropertiesBean(VariantProperties variantProperties) {
		VariantPropertiesBean variantPropertiesBean = new VariantPropertiesBean();
		variantPropertiesBean.setVariantPropertiesId(variantProperties.getVariantPropertiesId());
		variantPropertiesBean.setVariantPropertiesName(variantProperties.getVariantPropertiesName());
		variantPropertiesBean.setVariantPropertiesType(variantProperties.getVariantPropertiesType());
		return variantPropertiesBean;
	}
	
	public static List<VariantPropertiesBean> prepareListofVarientBeans(List <VariantProperties> variantPropertiess)
	{
		List<VariantPropertiesBean> variantPropertiesBeans=new LinkedList<VariantPropertiesBean>();
		for(VariantProperties variantProperties: variantPropertiess)
		{
			VariantPropertiesBean variantPropertiesBean=prepareVariantPropertiesBean(variantProperties);
			variantPropertiesBeans.add(variantPropertiesBean);
		}
		return variantPropertiesBeans;
	}
	
	public static List<VariantProperties> prepareListofVarients(List <VariantPropertiesBean> variantPropertiesBeans)
	{
		List<VariantProperties> variantPropertiess=new LinkedList<VariantProperties>();
		for(VariantPropertiesBean variantPropertiesBean: variantPropertiesBeans)
		{
			VariantProperties variantProperties=prepareVariantPropertiesModel(variantPropertiesBean);
			variantPropertiess.add(variantProperties);
		}
		return variantPropertiess;
	}

	public static boolean isVariantPropertiesExists(
			VariantPropertiesService variantPropertiesService,
			String variantPropertiesName,String variantPropertiesType) {
		VariantProperties variantProperties =variantPropertiesService.getVariantPropertiesByNameNType(variantPropertiesName,variantPropertiesType);
		if(variantProperties!=null && variantProperties.getVariantPropertiesId()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public static List<VariantPropertiesBean> prepareListofVariantPropertiesBean(
			List<VariantProperties> listVariantProperties) {
		List<VariantPropertiesBean> vpb=new LinkedList<VariantPropertiesBean>();
		for(VariantProperties variantProperties: listVariantProperties)
		{
			VariantPropertiesBean variantPropertiesBean=prepareVariantPropertiesBean(variantProperties);
			vpb.add(variantPropertiesBean);
		}
		return vpb;
	}

}
