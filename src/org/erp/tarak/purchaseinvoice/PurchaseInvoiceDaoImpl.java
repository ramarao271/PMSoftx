package org.erp.tarak.purchaseinvoice;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.salesinvoice.SalesInvoice;
import org.erp.tarak.supplier.Supplier;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("purchaseInvoiceDao")
public class PurchaseInvoiceDaoImpl implements PurchaseInvoiceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		if (purchaseInvoice.getPurchaseInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseInvoice);
		} else {
			sessionFactory.getCurrentSession().save(purchaseInvoice);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseInvoice> listPurchaseInvoices(String finYear) {
		return (List<PurchaseInvoice>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseInvoice.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public PurchaseInvoice getPurchaseInvoice(long purchaseInvoiceId,String finYear) {
		return (PurchaseInvoice) sessionFactory.getCurrentSession().createCriteria(PurchaseInvoice.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("purchaseInvoiceId", purchaseInvoiceId)).list().get(0);
	}

	public void deletePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseInvoice WHERE purchaseInvoiceId = "
								+ purchaseInvoice.getPurchaseInvoiceId()).executeUpdate();
	}

	@Override
	public List<PurchaseInvoice> listPurchaseInvoicesBySupplier(long supplierId,String finYear) {
		String hql = "from PurchaseInvoice where supplierId="+supplierId+" and finYear='"+finYear+"' )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	}

	@Override
	public void updatePurchaseInvoiceBalance(PurchaseInvoice purchaseInvoice) {
		long siid=purchaseInvoice.getPurchaseInvoiceId();
		String hql="update PurchaseInvoice  where PurchaseInvoiceId="+siid;
		sessionFactory.getCurrentSession().createSQLQuery(hql);
	}

	@Override
	public List<PurchaseInvoice> listPendingPurchaseInvoices(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseInvoice.class);
		crit.add(Restrictions.eq("processed", false));
		crit.add(Restrictions.eq("finYear",finYear));
		List results = crit.list();
		return results;
	
		}

	@Override
	public List<PurchaseInvoice> listPurchaseInvoicesBySupplier(String finYear) {
		String hql = "from PurchaseInvoice where finYear="+finYear+" order by supplier_Id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	
	}

	@Override
	public List<Long> listBilledSuppliers(String finYear) {
		String sql="select distinct(supplier_Id) from purchaseInvoice where finYear='"+finYear+"'";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql);
		List<BigInteger> results=query.list();
		List<Long> cids=new LinkedList<Long>();
		for(BigInteger bi: results)
		{
			cids.add(bi.longValue());
		}
		return cids;
	}

	@Override
	public List<Object[]> listPendingPurchaseInvoicesBySupplier(String finYear) {
		String sql="select {c.*},sum(totalCost) as totalCost, sum(s.returnAmount) as returnAmount,sum(s.paidAmount) as paidAmount from purchaseInvoice s,Supplier c where s.finYear='"+finYear+"' and c.supplierId=s.supplier_Id group by c.supplierId";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity("c", Supplier.class).addScalar("totalCost").addScalar("returnAmount").addScalar("paidAmount");
		return query.list();
	}

	@Override
	public List<PurchaseInvoice> listPurchaseInvoicesByDate(Date balanceSheetDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				PurchaseInvoice.class);
		crit.add(Restrictions.eq("purchaseInvoiceDate", balanceSheetDate));
		List results = crit.list();
		return results;
	}

}
