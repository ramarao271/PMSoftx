package org.erp.tarak.stage;

import java.util.List;


public interface StageService {
	public void addStage(Stage stage);

	public List<Stage> listStages();

	public Stage getStage(long stageId);

	public void deleteStage(Stage stage);

	public Stage getLastStage();

	public Stage getStageByName(String stageName);
}
