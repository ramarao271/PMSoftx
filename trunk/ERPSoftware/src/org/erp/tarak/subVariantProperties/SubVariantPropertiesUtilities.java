package org.erp.tarak.subVariantProperties;

import java.util.LinkedList;
import java.util.List;


public class SubVariantPropertiesUtilities {

	public static SubVariantProperties saveSubVariantProperties(SubVariantPropertiesService subVariantPropertiesService,
			SubVariantProperties subVariantProperties) {
		subVariantPropertiesService.addSubVariantProperties(subVariantProperties);
		return subVariantProperties;
	}

	public static SubVariantProperties getSubVariantPropertiesModel(SubVariantPropertiesService subVariantPropertiesService,
			long subVariantPropertiesId) {
		SubVariantProperties subVariantProperties = subVariantPropertiesService.getSubVariantProperties(subVariantPropertiesId);
		return subVariantProperties;
	}
	
	public static SubVariantProperties prepareSubVariantPropertiesModel(SubVariantPropertiesBean subVariantPropertiesBean) {
		SubVariantProperties subVariantProperties = new SubVariantProperties();
		subVariantProperties.setSubVariantPropertiesId(subVariantPropertiesBean.getSubVariantPropertiesId());
		subVariantProperties.setSubVariantPropertiesName(subVariantPropertiesBean.getSubVariantPropertiesName());
		return subVariantProperties;
	}

	public static SubVariantPropertiesBean prepareSubVariantPropertiesBean(SubVariantProperties subVariantProperties) {
		SubVariantPropertiesBean subVariantPropertiesBean = new SubVariantPropertiesBean();
		subVariantPropertiesBean.setSubVariantPropertiesId(subVariantProperties.getSubVariantPropertiesId());
		subVariantPropertiesBean.setSubVariantPropertiesName(subVariantProperties.getSubVariantPropertiesName());
		return subVariantPropertiesBean;
	}
	
	public static List<SubVariantPropertiesBean> prepareListofVarientBeans(List <SubVariantProperties> subVariantPropertiess)
	{
		List<SubVariantPropertiesBean> subVariantPropertiesBeans=new LinkedList<SubVariantPropertiesBean>();
		for(SubVariantProperties subVariantProperties: subVariantPropertiess)
		{
			SubVariantPropertiesBean subVariantPropertiesBean=prepareSubVariantPropertiesBean(subVariantProperties);
			subVariantPropertiesBeans.add(subVariantPropertiesBean);
		}
		return subVariantPropertiesBeans;
	}
	
	public static List<SubVariantProperties> prepareListofVarients(List <SubVariantPropertiesBean> subVariantPropertiesBeans)
	{
		List<SubVariantProperties> subVariantPropertiess=new LinkedList<SubVariantProperties>();
		for(SubVariantPropertiesBean subVariantPropertiesBean: subVariantPropertiesBeans)
		{
			SubVariantProperties subVariantProperties=prepareSubVariantPropertiesModel(subVariantPropertiesBean);
			subVariantPropertiess.add(subVariantProperties);
		}
		return subVariantPropertiess;
	}

	public static boolean isSubVariantPropertiesExists(
			SubVariantPropertiesService subVariantPropertiesService,
			String subVariantPropertiesName) {
		SubVariantProperties subVariantProperties =subVariantPropertiesService.getSubVariantPropertiesByNameNType(subVariantPropertiesName);
		if(subVariantProperties!=null && subVariantProperties.getSubVariantPropertiesId()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public static List<SubVariantPropertiesBean> prepareListofSubVariantPropertiesBean(
			List<SubVariantProperties> listSubVariantProperties) {
		List<SubVariantPropertiesBean> vpb=new LinkedList<SubVariantPropertiesBean>();
		for(SubVariantProperties subVariantProperties: listSubVariantProperties)
		{
			SubVariantPropertiesBean subVariantPropertiesBean=prepareSubVariantPropertiesBean(subVariantProperties);
			vpb.add(subVariantPropertiesBean);
		}
		return vpb;
	}

}
