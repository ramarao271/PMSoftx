package org.erp.tarak.stageProperties;

import java.util.LinkedList;
import java.util.List;


public class StagePropertiesUtilities {

	public static StageProperties saveStageProperties(StagePropertiesService stagePropertiesService,
			StageProperties stageProperties) {
		stagePropertiesService.addStageProperties(stageProperties);
		return stageProperties;
	}

	public static StageProperties getStagePropertiesModel(StagePropertiesService stagePropertiesService,
			long stagePropertiesId) {
		StageProperties stageProperties = stagePropertiesService.getStageProperties(stagePropertiesId);
		return stageProperties;
	}
	
	public static StageProperties prepareStagePropertiesModel(StagePropertiesBean stagePropertiesBean) {
		StageProperties stageProperties = new StageProperties();
		stageProperties.setStagePropertiesId(stagePropertiesBean.getStagePropertiesId());
		stageProperties.setStagePropertiesName(stagePropertiesBean.getStagePropertiesName());
		return stageProperties;
	}

	public static StagePropertiesBean prepareStagePropertiesBean(StageProperties stageProperties) {
		StagePropertiesBean stagePropertiesBean = new StagePropertiesBean();
		stagePropertiesBean.setStagePropertiesId(stageProperties.getStagePropertiesId());
		stagePropertiesBean.setStagePropertiesName(stageProperties.getStagePropertiesName());
		return stagePropertiesBean;
	}
	
	public static List<StagePropertiesBean> prepareListofVarientBeans(List <StageProperties> stagePropertiess)
	{
		List<StagePropertiesBean> stagePropertiesBeans=new LinkedList<StagePropertiesBean>();
		for(StageProperties stageProperties: stagePropertiess)
		{
			StagePropertiesBean stagePropertiesBean=prepareStagePropertiesBean(stageProperties);
			stagePropertiesBeans.add(stagePropertiesBean);
		}
		return stagePropertiesBeans;
	}
	
	public static List<StageProperties> prepareListofVarients(List <StagePropertiesBean> stagePropertiesBeans)
	{
		List<StageProperties> stagePropertiess=new LinkedList<StageProperties>();
		for(StagePropertiesBean stagePropertiesBean: stagePropertiesBeans)
		{
			StageProperties stageProperties=prepareStagePropertiesModel(stagePropertiesBean);
			stagePropertiess.add(stageProperties);
		}
		return stagePropertiess;
	}

	public static boolean isStagePropertiesExists(
			StagePropertiesService stagePropertiesService,
			String stagePropertiesName) {
		StageProperties stageProperties =stagePropertiesService.getStagePropertiesByNameNType(stagePropertiesName);
		if(stageProperties!=null && stageProperties.getStagePropertiesId()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public static List<StagePropertiesBean> prepareListofStagePropertiesBean(
			List<StageProperties> listStageProperties) {
		List<StagePropertiesBean> vpb=new LinkedList<StagePropertiesBean>();
		for(StageProperties stageProperties: listStageProperties)
		{
			StagePropertiesBean stagePropertiesBean=prepareStagePropertiesBean(stageProperties);
			vpb.add(stagePropertiesBean);
		}
		return vpb;
	}

}
