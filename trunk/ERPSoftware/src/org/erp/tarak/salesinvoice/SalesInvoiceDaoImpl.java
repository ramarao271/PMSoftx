package org.erp.tarak.salesinvoice;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.customer.Customer;
import org.erp.tarak.customer.CustomerReport;
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
		String hql = "select * from SalesInvoice where customerId="+customerId+" and finYear='"+finYear+"' )";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
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
		String hql = "select * from SalesInvoice where finYear='"+finYear+"' order by customer_Id";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
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

	@Override
	public List<CustomerReport> getAvgTktPrice() {
		String hql = "select c.Company_Name,c.Company_Branch,c.customerId, sum(s.totalCost)-sum(s.returnAmount) from salesInvoice s,customer c where c.customerId=s.customer_Id group by c.customerId;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<CustomerReport> cats=new LinkedList<CustomerReport>();
		List<Object[]> objects	= (List<Object[]>)results;
		for(Object[] x: objects)
		{
			CustomerReport cr=new CustomerReport();
			cr.setCustomerName((String)x[0]);
			cr.setCompanyBranch((String)x[1]);
			cr.setCustomerId((long)x[2]);
			cr.setTotalPrice((double)x[3]);
			cats.add(cr);
		}
		hql="select customer_Id, count(*) from salesInvoice group by customer_Id;";
		query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		results = query.list();
		objects	= (List<Object[]>)results;
		for(Object[] x: objects)
		{
			for(CustomerReport cr: cats)
			{
				if(cr.getCustomerId()==(long)x[0])
				{
					cr.setNoOfBills((int)x[1]);
					cr.setAvgTktPrice(cr.getTotalPrice()/cr.getNoOfBills());
					break;
				}
			}
		}
		return cats;
	}

	@Override
	public List<CustomerReport> getCustomerFrequency() {
		String hql = "select c.Company_Name,c.Company_Branch,c.customerId from salesInvoice s,customer c  where c.customerId=s.customer_Id group by c.customerId;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<CustomerReport> cats=new LinkedList<CustomerReport>();
		List<Object[]> objects	= (List<Object[]>)results;
		for(Object[] x: objects)
		{
			CustomerReport cr=new CustomerReport();
			cr.setCustomerName((String)x[0]);
			cr.setCompanyBranch((String)x[1]);
			cr.setCustomerId((long)x[2]);
			cats.add(cr);
		}
		hql="select customer_Id, count(*) from salesInvoice group by s.customer_Id;";
		query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		results = query.list();
		objects	= (List<Object[]>)results;
		for(Object[] x: objects)
		{
			for(CustomerReport cr: cats)
			{
				if(cr.getCustomerId()==(long)x[0])
				{
					cr.setNoOfBills((int)x[1]);
					break;
				}
			}
		}

		return cats;
	}

	@Override
	public List<SalesInvoice> listSalesInvoicesByDate(Date balanceSheetDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesInvoice.class);
		crit.add(Restrictions.eq("salesInvoiceDate", balanceSheetDate));
		List results = crit.list();
		return results;
	
	}

}
