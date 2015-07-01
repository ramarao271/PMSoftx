package org.erp.tarak.variantProperties;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("variantPropertiesDao")
public class VariantPropertiesDaoImpl implements VariantPropertiesDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addVariantProperties(VariantProperties variantProperties) {
		if (variantProperties.getVariantPropertiesId() > 0) {
			sessionFactory.getCurrentSession().update(variantProperties);
		} else {
			sessionFactory.getCurrentSession().save(variantProperties);
		}
	}

	@SuppressWarnings("unchecked")
	public List<VariantProperties> listVariantPropertiess() {
		return (List<VariantProperties>) sessionFactory.getCurrentSession()
				.createCriteria(VariantProperties.class).list();
	}

	public VariantProperties getVariantProperties(long variantPropertiesId) {
		return (VariantProperties) sessionFactory.getCurrentSession().get(VariantProperties.class,
				variantPropertiesId);
	}

	public void deleteVariantProperties(VariantProperties variantProperties) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM VariantProperties WHERE variantPropertiesId = "
								+ variantProperties.getVariantPropertiesId()).executeUpdate();
	}

	@Override
	public VariantProperties getVariantPropertiesByNameNType(
			String variantPropertiesName, String variantPropertiesType) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				VariantProperties.class);
		crit.add(Restrictions.eq("variantPropertiesName", variantPropertiesName));
		crit.add(Restrictions.eq("variantPropertiesType", variantPropertiesType));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (VariantProperties) results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<VariantProperties> getVariantPropertiesByType(String variantPropertiesType) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				VariantProperties.class);
		crit.add(Restrictions.eq("variantPropertiesType", variantPropertiesType));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (List<VariantProperties>) results;
		} else {
			return null;
		}
	}

}
