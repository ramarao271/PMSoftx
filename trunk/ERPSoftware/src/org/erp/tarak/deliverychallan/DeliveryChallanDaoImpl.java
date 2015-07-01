package org.erp.tarak.deliverychallan;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("deliveryChallanDao")
public class DeliveryChallanDaoImpl implements DeliveryChallanDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addDeliveryChallan(DeliveryChallan deliveryChallan) {
		if (deliveryChallan.getDeliveryChallanId() > 0) {
			sessionFactory.getCurrentSession().update(deliveryChallan);
		} else {
			sessionFactory.getCurrentSession().save(deliveryChallan);
		}

	}

	@SuppressWarnings("unchecked")
	public List<DeliveryChallan> listDeliveryChallans(String finYear) {
		return (List<DeliveryChallan>) sessionFactory.getCurrentSession()
				.createCriteria(DeliveryChallan.class)
				.add(Restrictions.eq("finYear", finYear)).list();
	}

	public DeliveryChallan getDeliveryChallan(long deliverChallanId,
			String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				DeliveryChallan.class);
		crit.add(Restrictions.eq("deliveryChallanId", deliverChallanId));
		crit.add(Restrictions.eq("finYear", finYear));
		List results = crit.list();
		return (DeliveryChallan) results.get(0);

	}

	public void deleteDeliveryChallan(DeliveryChallan deliveryChallan) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM DeliveryChallan WHERE deliveryChallanId = "
								+ deliveryChallan.getDeliveryChallanId())
				.executeUpdate();
	}

	@Override
	public List<DeliveryChallan> listPendingDeliveryChallans(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				DeliveryChallan.class);
		crit.add(Restrictions.eq("processed", false));
		crit.add(Restrictions.eq("finYear", finYear));
		List results = crit.list();
		return results;
	}

	@Override
	public List<DeliveryChallan> listProcessedDeliveryChallans(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				DeliveryChallan.class);
		crit.add(Restrictions.eq("processed", true));
		crit.add(Restrictions.eq("finYear", finYear));
		List results = crit.list();
		return results;
	}

}
