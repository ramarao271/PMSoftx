package org.erp.tarak.purchaseorder;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("purchaseOrderItemDao")
public class PurchaseOrderItemDaoImpl implements PurchaseOrderItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		if (purchaseOrderItem.getPurchaseOrderId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseOrderItem);
		} else {
			sessionFactory.getCurrentSession().save(purchaseOrderItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseOrderItem> listPurchaseOrderItems() {
		return (List<PurchaseOrderItem>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseOrderItem.class).list();
	}

	public PurchaseOrderItem getPurchaseOrderItem(long empid) {
		return (PurchaseOrderItem) sessionFactory.getCurrentSession().get(PurchaseOrderItem.class,
				empid);
	}

	public void deletePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseOrderItem WHERE purchaseOrderId = "
								+ purchaseOrderItem.getPurchaseOrderId()).executeUpdate();
	}

	@Override
	public void deletePurchaseOrderItems(List<PurchaseOrderItem> pois) {
		for(PurchaseOrderItem poi:pois)
		{
			deletePurchaseOrderItem(poi);
		}
		
	}

}
