package org.erp.tarak.variant;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("variantDao")
public class VariantDaoImpl implements VariantDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addVariant(Variant variant) {
		if (variant.getVariantId() > 0) {
			sessionFactory.getCurrentSession().update(variant);
		} else {
			sessionFactory.getCurrentSession().save(variant);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Variant> listVariants() {
		return (List<Variant>) sessionFactory.getCurrentSession()
				.createCriteria(Variant.class).list();
	}

	public Variant getVariant(long variantId) {
		return (Variant) sessionFactory.getCurrentSession().get(Variant.class,
				variantId);
	}

	public void deleteVariant(Variant variant) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Variant WHERE variantId = "
								+ variant.getVariantId()).executeUpdate();
	}

}
