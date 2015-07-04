package org.erp.tarak.subVariantProperties;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("subVariantPropertiesDao")
public class SubVariantPropertiesDaoImpl implements SubVariantPropertiesDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSubVariantProperties(SubVariantProperties subVariantProperties) {
		if (subVariantProperties.getSubVariantPropertiesId() > 0) {
			sessionFactory.getCurrentSession().update(subVariantProperties);
		} else {
			sessionFactory.getCurrentSession().save(subVariantProperties);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SubVariantProperties> listSubVariantPropertiess() {
		return (List<SubVariantProperties>) sessionFactory.getCurrentSession()
				.createCriteria(SubVariantProperties.class).list();
	}

	public SubVariantProperties getSubVariantProperties(long subVariantPropertiesId) {
		return (SubVariantProperties) sessionFactory.getCurrentSession().get(SubVariantProperties.class,
				subVariantPropertiesId);
	}

	public void deleteSubVariantProperties(SubVariantProperties subVariantProperties) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SubVariantProperties WHERE subVariantPropertiesId = "
								+ subVariantProperties.getSubVariantPropertiesId()).executeUpdate();
	}

	@Override
	public SubVariantProperties getSubVariantPropertiesByNameNType(
			String subVariantPropertiesName) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SubVariantProperties.class);
		crit.add(Restrictions.eq("subVariantPropertiesName", subVariantPropertiesName));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (SubVariantProperties) results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<SubVariantProperties> getSubVariantPropertiesByType(String subVariantPropertiesType) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SubVariantProperties.class);
		crit.add(Restrictions.eq("subVariantPropertiesType", subVariantPropertiesType));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (List<SubVariantProperties>) results;
		} else {
			return null;
		}
	}

}
