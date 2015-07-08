package org.erp.tarak.purchasereturn;

import java.util.Date;
import java.util.List;

import org.erp.tarak.salesinvoice.SalesInvoice;
import org.hibernate.Criteria;
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
				.createCriteria(PurchaseReturn.class).add(Restrictions.eq("finYear",finYear)).add(Restrictions.eq("purchaseReturnId", empid)).list().get(0);
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

	@Override
	public List<PurchaseReturn> listPurchaseReturns(Date balanceSheetDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseReturn.class);
		crit.add(Restrictions.eq("purchaseReturnDate", balanceSheetDate));
		List results = crit.list();
		return results;
	}

}
