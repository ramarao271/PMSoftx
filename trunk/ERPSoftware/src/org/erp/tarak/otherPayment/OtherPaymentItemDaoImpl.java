package org.erp.tarak.otherPayment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("otherPaymentItemDao")
public class OtherPaymentItemDaoImpl implements OtherPaymentItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addOtherPaymentItem(OtherPaymentItem otherPaymentItem) {
		if (otherPaymentItem.getOtherPaymentId() > 0) {
			sessionFactory.getCurrentSession().update(otherPaymentItem);
		} else {
			Session session= sessionFactory.getCurrentSession();
			session.flush();
			session.save(otherPaymentItem);
			session.flush();
			session.clear();
		}

	}

	@SuppressWarnings("unchecked")
	public List<OtherPaymentItem> listOtherPaymentItems() {
		return (List<OtherPaymentItem>) sessionFactory.getCurrentSession()
				.createCriteria(OtherPaymentItem.class).list();
	}

	public OtherPaymentItem getOtherPaymentItem(long empid) {
		return (OtherPaymentItem) sessionFactory.getCurrentSession().get(OtherPaymentItem.class,
				empid);
	}

	public void deleteOtherPaymentItem(OtherPaymentItem otherPaymentItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM OtherPaymentItem WHERE otherPaymentId = "
								+ otherPaymentItem.getOtherPaymentId()).executeUpdate();
	}

	@Override
	public void deleteOtherPaymentItems(List<OtherPaymentItem> pois) {
		for(OtherPaymentItem poi:pois)
		{
			deleteOtherPaymentItem(poi);
		}
		
	}

}
