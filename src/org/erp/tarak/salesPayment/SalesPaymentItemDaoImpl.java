package org.erp.tarak.salesPayment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("salesPaymentItemDao")
public class SalesPaymentItemDaoImpl implements SalesPaymentItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesPaymentItem(SalesPaymentItem salesPaymentItem) {
		if (salesPaymentItem.getSalesPaymentId() > 0) {
			sessionFactory.getCurrentSession().update(salesPaymentItem);
		} else {
			Session session= sessionFactory.getCurrentSession();
			session.flush();
			session.save(salesPaymentItem);
			session.flush();
			session.clear();
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesPaymentItem> listSalesPaymentItems() {
		return (List<SalesPaymentItem>) sessionFactory.getCurrentSession()
				.createCriteria(SalesPaymentItem.class).list();
	}

	public SalesPaymentItem getSalesPaymentItem(long empid) {
		return (SalesPaymentItem) sessionFactory.getCurrentSession().get(SalesPaymentItem.class,
				empid);
	}

	public void deleteSalesPaymentItem(SalesPaymentItem salesPaymentItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesPaymentItem WHERE salesPaymentId = "
								+ salesPaymentItem.getSalesPaymentId()).executeUpdate();
	}

	@Override
	public void deleteSalesPaymentItems(List<SalesPaymentItem> pois) {
		for(SalesPaymentItem poi:pois)
		{
			deleteSalesPaymentItem(poi);
		}
		
	}

}
