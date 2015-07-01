package org.erp.tarak.stage;

import java.util.ArrayList;
import java.util.List;

import org.erp.tarak.library.ERPUtilities;


public class StageUtilities {

	public static List<StageBean> prepareListofStageBean(
			List<Stage> categories) {
		List<StageBean> beans = new ArrayList<StageBean>();
		for (Stage stage : categories) {
			StageBean stageBean = new StageBean();
			stageBean.setStageId(stage.getStageId());
			stageBean.setStageName(stage.getStageName());
			stageBean.setStageCode(stage.getStageCode());
			stageBean.setQuantity(stage.getQuantity());
			beans.add(stageBean);
		}
		return beans;
	}

	public static Stage prepareStageModel(StageBean stageBean,StageService stageService) {
		Stage stage = new Stage();
		stage.setStageId(stageBean.getStageId());
		stage.setStageName(stageBean.getStageName());
		stage.setQuantity(stageBean.getQuantity());
		if(stageBean.getStageId()>0)
		{
			stage.setStageCode(stageBean.getStageCode());
		}
		else
		{
			String stageCode=getStageCode(stageService);
			stage.setStageCode(stageCode);
		}
		
		return stage;
	}

	public static Stage prepareStageModel(StageBean stageBean) {
		Stage stage = new Stage();
		stage.setStageId(stageBean.getStageId());
		stage.setStageName(stageBean.getStageName());
		stage.setStageCode(stageBean.getStageCode());
		stage.setQuantity(stageBean.getQuantity());
		return stage;
	}

	
	
	
	private static String getStageCode(StageService stageService) {
		Stage stage=stageService.getLastStage();
		String stageCode="";
		if(stage!=null)
		{
			stageCode=ERPUtilities.incrementCode(stage.getStageCode());
			stageCode=ERPUtilities.fomatStringToN(stageCode,2);
		}
		else
		{
			stageCode="00";
		}
		return stageCode;
	}

	public static StageBean prepareStageBean(Stage stage) {
		StageBean bean = new StageBean();
		bean.setStageId(stage.getStageId());
		bean.setStageName(stage.getStageName());
		bean.setStageCode(stage.getStageCode());
		bean.setQuantity(stage.getQuantity());
		return bean;
	}


}
