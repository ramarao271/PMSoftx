package org.erp.tarak.deliverychallan;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("deliveryChallanItemDao")
public class DeliveryChallanItemDaoImpl implements DeliveryChallanItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem) {
		if (deliveryChallanItem.getDeliveryChallanId() > 0) {
			sessionFactory.getCurrentSession().update(deliveryChallanItem);
		} else {
			sessionFactory.getCurrentSession().save(deliveryChallanItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<DeliveryChallanItem> listDeliveryChallanItems() {
		return (List<DeliveryChallanItem>) sessionFactory.getCurrentSession()
				.createCriteria(DeliveryChallanItem.class).list();
	}

	public DeliveryChallanItem getDeliveryChallanItem(long empid) {
		return (DeliveryChallanItem) sessionFactory.getCurrentSession().get(DeliveryChallanItem.class,
				empid);
	}

	public void deleteDeliveryChallanItem(DeliveryChallanItem deliveryChallanItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM DeliveryChallanItem WHERE deliveryChallanId = "
								+ deliveryChallanItem.getDeliveryChallanId()).executeUpdate();
	}

	@Override
	public void deleteDeliveryChallanItems(List<DeliveryChallanItem> pois) {
		for(DeliveryChallanItem poi:pois)
		{
			deleteDeliveryChallanItem(poi);
		}
		
	}

}
