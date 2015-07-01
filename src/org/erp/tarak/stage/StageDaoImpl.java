package org.erp.tarak.stage;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("stageDao")
public class StageDaoImpl implements StageDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addStage(Stage stage) {
		if (stage.getStageId() > 0) {
			sessionFactory.getCurrentSession().update(stage);
		} else {
			sessionFactory.getCurrentSession().save(stage);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Stage> listCategories() {
		return (List<Stage>) sessionFactory.getCurrentSession()
				.createCriteria(Stage.class).list();
	}

	public Stage getStage(long stageId) {
		return (Stage) sessionFactory.getCurrentSession().get(
				Stage.class, stageId);
	}

	public void deleteStage(Stage stage) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Stage WHERE stageId = "
								+ stage.getStageId()).executeUpdate();
	}

	@Override
	public Stage getLastStage() {
		String hql = "from Stage where stageId=(SELECT MAX(stageId) from Stage)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Stage) results.get(0);
		}
		return null;
	}

	@Override
	public Stage getStageByName(String stageName) {
		String hql = "from Stage where stageName='"+stageName+"')";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Stage) results.get(0);
		}
		return null;
		
	}

}
