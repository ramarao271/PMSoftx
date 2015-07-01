package org.erp.tarak.purchasePayment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("purchasePaymentItemDao")
public class PurchasePaymentItemDaoImpl implements PurchasePaymentItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchasePaymentItem(PurchasePaymentItem purchasePaymentItem) {
		if (purchasePaymentItem.getPurchasePaymentId() > 0) {
			sessionFactory.getCurrentSession().update(purchasePaymentItem);
		} else {
			Session session= sessionFactory.getCurrentSession();
			session.flush();
			session.save(purchasePaymentItem);
			session.flush();
			session.clear();
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchasePaymentItem> listPurchasePaymentItems() {
		return (List<PurchasePaymentItem>) sessionFactory.getCurrentSession()
				.createCriteria(PurchasePaymentItem.class).list();
	}

	public PurchasePaymentItem getPurchasePaymentItem(long empid) {
		return (PurchasePaymentItem) sessionFactory.getCurrentSession().get(PurchasePaymentItem.class,
				empid);
	}

	public void deletePurchasePaymentItem(PurchasePaymentItem purchasePaymentItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchasePaymentItem WHERE purchasePaymentId = "
								+ purchasePaymentItem.getPurchasePaymentId()).executeUpdate();
	}

	@Override
	public void deletePurchasePaymentItems(List<PurchasePaymentItem> pois) {
		for(PurchasePaymentItem poi:pois)
		{
			deletePurchasePaymentItem(poi);
		}
		
	}

}
