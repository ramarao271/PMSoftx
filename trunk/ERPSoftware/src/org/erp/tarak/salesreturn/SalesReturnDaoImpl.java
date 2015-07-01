package org.erp.tarak.salesreturn;

import java.util.List;

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
				.createCriteria(SalesReturn.class).add(Restrictions.eq("finYear",finYear)).add(Restrictions.eq("salesPaymentId", empid));
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

}
