package org.erp.tarak.stageProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("stagePropertiesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StagePropertiesServiceImpl implements StagePropertiesService {

	@Autowired
	private StagePropertiesDao stagePropertiesDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addStageProperties(StageProperties stageProperties) {
		stagePropertiesDao.addStageProperties(stageProperties);
	}
	
	public List<StageProperties> listStagePropertiess() {
		return stagePropertiesDao.listStagePropertiess();
	}

	public StageProperties getStageProperties(long stagePropertiesId) {
		return stagePropertiesDao.getStageProperties(stagePropertiesId);
	}
	
	public void deleteStageProperties(StageProperties stageProperties) {
		stagePropertiesDao.deleteStageProperties(stageProperties);
	}

	@Override
	public StageProperties getStagePropertiesByNameNType(
			String stagePropertiesName) {
		
		return stagePropertiesDao.getStagePropertiesByNameNType(stagePropertiesName);
	}

	@Override
	public List<StageProperties> getStagePropertiesByType(String stagePropertiesType) {
		
		return stagePropertiesDao.getStagePropertiesByType(stagePropertiesType);
	}

}
