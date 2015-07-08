package org.erp.tarak.salesreturn;

import java.util.Date;
import java.util.List;

import org.erp.tarak.salesinvoice.SalesInvoice;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("salesReturnDao")
public class SalesReturnDaoImpl implements SalesReturnDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesReturn(SalesReturn salesReturn) {
		if (salesReturn.getSalesReturnId() > 0) {
			sessionFactory.getCurrentSession().update(salesReturn);
		} else {
			sessionFactory.getCurrentSession().save(salesReturn);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesReturn> listSalesReturns(String finYear) {
		return (List<SalesReturn>) sessionFactory.getCurrentSession()
				.createCriteria(SalesReturn.class).add(Restrictions.eq("finYear",finYear)).list();
	}

	public SalesReturn getSalesReturn(long empid,String finYear) {
		return (SalesReturn) sessionFactory.getCurrentSession()
				.createCriteria(SalesReturn.class).add(Restrictions.eq("finYear",finYear)).add(Restrictions.eq("salesReturnId", empid)).list().get(0);
	}

	public void deleteSalesReturn(SalesReturn salesReturn) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesReturn WHERE salesReturnId = "
								+ salesReturn.getSalesReturnId()).executeUpdate();
	}

	@Override
	public List<SalesReturn> listSalesReturnsByCustomer(long customerId,String finYear) {
		String hql = "from SalesReturn where customerId="+customerId+" and finYear='"+finYear+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		return results;
	}

	@Override
	public List<SalesReturn> listSalesReturnsByDate(Date balanceSheetDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesReturn.class);
		crit.add(Restrictions.eq("salesReturnDate", balanceSheetDate));
		List results = crit.list();
		return results;
	}

}
