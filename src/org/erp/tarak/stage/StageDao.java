package org.erp.tarak.stage;

import java.util.List;


public interface StageDao {
		
		public void addStage(Stage stage);

		public List<Stage> listCategories();
		
		public Stage getStage(long stageId);
		
		public void deleteStage(Stage stage);

		public Stage getLastStage();

		public Stage getStageByName(String stageName);
	}
