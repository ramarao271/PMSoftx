package org.erp.tarak.purchasePayment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("purchasePaymentDao")
public class PurchasePaymentDaoImpl implements PurchasePaymentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchasePayment(PurchasePayment purchasePayment) {
		if (purchasePayment.getPurchasePaymentId() > 0) {
			sessionFactory.getCurrentSession().update(purchasePayment);
		} else {
			Session session=sessionFactory.getCurrentSession();//.save(purchasePayment);
			/*//session.beginTransaction();
			if(purchasePayment.getPurchasePaymentItems()!=null && purchasePayment.getPurchasePaymentItems().size()>0)
			{
				Iterator<PurchasePaymentItem> itr=purchasePayment.getPurchasePaymentItems().iterator();
				while(itr.hasNext())
				{
					PurchasePaymentItem purchasePaymentItem=(PurchasePaymentItem) itr.next();
					session.flush();
					session.save(purchasePaymentItem);
					session.flush();
					session.clear();
				}
			}*/
			
			session.save(purchasePayment);
			//session.close();
			//session.merge(purchasePayment);
			}
		//sessionFactory.getCurrentSession().flush();
		//sessionFactory.getCurrentSession().clear();
	}

	@SuppressWarnings("unchecked")
	public List<PurchasePayment> listPurchasePayments(String finYear) {
		return (List<PurchasePayment>) sessionFactory.getCurrentSession()
				.createCriteria(PurchasePayment.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public PurchasePayment getPurchasePayment(long empid,String finYear) {
		PurchasePayment purchasePayment=(PurchasePayment) sessionFactory.getCurrentSession().createCriteria(PurchasePayment.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("purchasePaymentId",empid));
		return purchasePayment;
	}

	public void deletePurchasePayment(PurchasePayment purchasePayment) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchasePayment WHERE purchasePaymentId = "
								+ purchasePayment.getPurchasePaymentId()).executeUpdate();
	}

}
