package org.erp.tarak.purchasereturn;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("purchaseReturnItemDao")
public class PurchaseReturnItemDaoImpl implements PurchaseReturnItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseReturnItem(PurchaseReturnItem purchaseReturnItem) {
		if (purchaseReturnItem.getPurchaseReturnId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseReturnItem);
		} else {
			sessionFactory.getCurrentSession().save(purchaseReturnItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnItem> listPurchaseReturnItems() {
		return (List<PurchaseReturnItem>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseReturnItem.class).list();
	}

	public PurchaseReturnItem getPurchaseReturnItem(long empid) {
		return (PurchaseReturnItem) sessionFactory.getCurrentSession().get(PurchaseReturnItem.class,
				empid);
	}

	public void deletePurchaseReturnItem(PurchaseReturnItem purchaseReturnItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseReturnItem WHERE purchaseReturnId = "
								+ purchaseReturnItem.getPurchaseReturnId()).executeUpdate();
	}

	@Override
	public void deletePurchaseReturnItems(List<PurchaseReturnItem> pois) {
		for(PurchaseReturnItem poi:pois)
		{
			deletePurchaseReturnItem(poi);
		}
		
	}

}
