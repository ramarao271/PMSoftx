package org.erp.tarak.stageProperties;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("stagePropertiesDao")
public class StagePropertiesDaoImpl implements StagePropertiesDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addStageProperties(StageProperties stageProperties) {
		if (stageProperties.getStagePropertiesId() > 0) {
			sessionFactory.getCurrentSession().update(stageProperties);
		} else {
			sessionFactory.getCurrentSession().save(stageProperties);
		}
	}

	@SuppressWarnings("unchecked")
	public List<StageProperties> listStagePropertiess() {
		return (List<StageProperties>) sessionFactory.getCurrentSession()
				.createCriteria(StageProperties.class).list();
	}

	public StageProperties getStageProperties(long stagePropertiesId) {
		return (StageProperties) sessionFactory.getCurrentSession().get(StageProperties.class,
				stagePropertiesId);
	}

	public void deleteStageProperties(StageProperties stageProperties) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM StageProperties WHERE stagePropertiesId = "
								+ stageProperties.getStagePropertiesId()).executeUpdate();
	}

	@Override
	public StageProperties getStagePropertiesByNameNType(
			String stagePropertiesName) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				StageProperties.class);
		crit.add(Restrictions.eq("stagePropertiesName", stagePropertiesName));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (StageProperties) results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<StageProperties> getStagePropertiesByType(String stagePropertiesType) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				StageProperties.class);
		crit.add(Restrictions.eq("stagePropertiesType", stagePropertiesType));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (List<StageProperties>) results;
		} else {
			return null;
		}
	}

}
