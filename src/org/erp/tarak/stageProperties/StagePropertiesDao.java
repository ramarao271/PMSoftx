package org.erp.tarak.stageProperties;

import java.util.List;


public interface StagePropertiesDao {
	
	public void addStageProperties(StageProperties product);

	public List<StageProperties> listStagePropertiess();
	
	public StageProperties getStageProperties(long stagePropertiesId);
	
	public void deleteStageProperties(StageProperties stageProperties);

	public StageProperties getStagePropertiesByNameNType(
			String stagePropertiesName);

	public List<StageProperties> getStagePropertiesByType(String stagePropertiesType);
}

