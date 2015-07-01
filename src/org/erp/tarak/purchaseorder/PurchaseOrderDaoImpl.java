package org.erp.tarak.purchaseorder;

import java.util.List;

import org.erp.tarak.product.Product;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("purchaseOrderDao")
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
		if (purchaseOrder.getPurchaseOrderId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseOrder);
		} else {
			sessionFactory.getCurrentSession().save(purchaseOrder);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseOrder> listPurchaseOrders(String finYear) {
		return (List<PurchaseOrder>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseOrder.class).add(Restrictions.eq("finYear",finYear)).list();
	}

	public PurchaseOrder getPurchaseOrder(long purchaseOrderId,String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("purchaseOrderId", purchaseOrderId));
		List results = crit.list();
		return (PurchaseOrder)results.get(0);
	}

	public void deletePurchaseOrder(PurchaseOrder purchaseOrder) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseOrder WHERE purchaseOrderId = "
								+ purchaseOrder.getPurchaseOrderId()+" and finYear='"+purchaseOrder.getFinYear()+"'").executeUpdate();
	}

	@Override
	public List<PurchaseOrder> listPendingPurchaseOrders(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("processed", false));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<PurchaseOrder> listProcessedPurchaseOrders(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("processed", true));
		List results = crit.list();
		return results;
	}

}
