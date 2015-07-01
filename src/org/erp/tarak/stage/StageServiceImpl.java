package org.erp.tarak.stage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("stageService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StageServiceImpl implements StageService {

	@Autowired
	private StageDao stageDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addStage(Stage stage) {
		stageDao.addStage(stage);
	}
	@Override
	public List<Stage> listStages() {
		return stageDao.listCategories();
	}
	public Stage getStage(long stageId) {
		return stageDao.getStage(stageId);
	}
	
	public void deleteStage(Stage stage) {
		stageDao.deleteStage(stage);
	}

	@Override
	public Stage getLastStage() {
		return stageDao.getLastStage();
	}

	@Override
	public Stage getStageByName(String stageName) {
		return stageDao.getStageByName(stageName);
	}
	

	
}
