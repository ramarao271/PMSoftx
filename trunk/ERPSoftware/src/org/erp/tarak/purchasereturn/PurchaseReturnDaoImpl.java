package org.erp.tarak.purchasereturn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("purchaseReturnDao")
public class PurchaseReturnDaoImpl implements PurchaseReturnDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseReturn(PurchaseReturn purchaseReturn) {
		if (purchaseReturn.getPurchaseReturnId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseReturn);
		} else {
			sessionFactory.getCurrentSession().save(purchaseReturn);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturn> listPurchaseReturns(String finYear) {
		return (List<PurchaseReturn>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseReturn.class).add(Restrictions.eq("finYear",finYear)).list();
	}

	public PurchaseReturn getPurchaseReturn(long empid,String finYear) {
		return (PurchaseReturn) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseReturn.class).add(Restrictions.eq("finYear",finYear)).add(Restrictions.eq("purchasePaymentId", empid));
	}

	public void deletePurchaseReturn(PurchaseReturn purchaseReturn) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseReturn WHERE purchaseReturnId = "
								+ purchaseReturn.getPurchaseReturnId()).executeUpdate();
	}

	@Override
	public List<PurchaseReturn> listPurchaseReturnsBySupplier(long supplierId,String finYear) {
		String hql = "from PurchaseReturn where supplierId="+supplierId+" and finYear='"+finYear+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	}

}
