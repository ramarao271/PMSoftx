package org.erp.tarak.salesinvoice;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.customer.Customer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("salesInvoiceDao")
public class SalesInvoiceDaoImpl implements SalesInvoiceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesInvoice(SalesInvoice salesInvoice) {
		if (salesInvoice.getSalesInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(salesInvoice);
		} else {
			sessionFactory.getCurrentSession().save(salesInvoice);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesInvoice> listSalesInvoices(String finYear) {
		return (List<SalesInvoice>) sessionFactory.getCurrentSession()
				.createCriteria(SalesInvoice.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public SalesInvoice getSalesInvoice(long salesInvoiceId,String finYear) {
		return (SalesInvoice) sessionFactory.getCurrentSession().createCriteria(SalesInvoice.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("salesInvoiceId", salesInvoiceId)).list().get(0);
	}

	public void deleteSalesInvoice(SalesInvoice salesInvoice) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesInvoice WHERE salesInvoiceId = "
								+ salesInvoice.getSalesInvoiceId()).executeUpdate();
	}

	@Override
	public List<SalesInvoice> listSalesInvoicesByCustomer(long customerId,String finYear) {
		String hql = "from SalesInvoice where customerId="+customerId+" and finYear='"+finYear+"' )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	}

	@Override
	public void updateSalesInvoiceBalance(SalesInvoice salesInvoice) {
		long siid=salesInvoice.getSalesInvoiceId();
		String hql="update SalesInvoice  where SalesInvoiceId="+siid;
		sessionFactory.getCurrentSession().createSQLQuery(hql);
	}

	@Override
	public List<SalesInvoice> listPendingSalesInvoices(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesInvoice.class);
		crit.add(Restrictions.eq("processed", false));
		crit.add(Restrictions.eq("finYear",finYear));
		List results = crit.list();
		return results;
	
		}

	@Override
	public List<SalesInvoice> listSalesInvoicesByCustomer(String finYear) {
		String hql = "from SalesInvoice where finYear="+finYear+" order by customer_Id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	
	}

	@Override
	public List<Long> listBilledCustomers(String finYear) {
		String sql="select distinct(customer_Id) from salesInvoice where finYear='"+finYear+"'";
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
	public List<Object[]> listPendingSalesInvoicesByCustomer(String finYear) {
		String sql="select {c.*},sum(totalCost) as totalCost, sum(s.returnAmount) as returnAmount,sum(s.paidAmount) as paidAmount from salesInvoice s,Customer c where s.finYear='"+finYear+"' and c.customerId=s.customer_Id group by c.customerId";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity("c", Customer.class).addScalar("totalCost").addScalar("returnAmount").addScalar("paidAmount");
		return query.list();
	}

}
