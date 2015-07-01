package org.erp.tarak.salesPayment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("salesPaymentDao")
public class SalesPaymentDaoImpl implements SalesPaymentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesPayment(SalesPayment salesPayment) {
		if (salesPayment.getSalesPaymentId() > 0) {
			sessionFactory.getCurrentSession().update(salesPayment);
		} else {
			Session session=sessionFactory.getCurrentSession();//.save(salesPayment);
			/*//session.beginTransaction();
			if(salesPayment.getSalesPaymentItems()!=null && salesPayment.getSalesPaymentItems().size()>0)
			{
				Iterator<SalesPaymentItem> itr=salesPayment.getSalesPaymentItems().iterator();
				while(itr.hasNext())
				{
					SalesPaymentItem salesPaymentItem=(SalesPaymentItem) itr.next();
					session.flush();
					session.save(salesPaymentItem);
					session.flush();
					session.clear();
				}
			}*/
			
			session.save(salesPayment);
			//session.close();
			//session.merge(salesPayment);
			}
		//sessionFactory.getCurrentSession().flush();
		//sessionFactory.getCurrentSession().clear();
	}

	@SuppressWarnings("unchecked")
	public List<SalesPayment> listSalesPayments(String finYear) {
		return (List<SalesPayment>) sessionFactory.getCurrentSession()
				.createCriteria(SalesPayment.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public SalesPayment getSalesPayment(long empid,String finYear) {
		SalesPayment salesPayment=(SalesPayment) sessionFactory.getCurrentSession().createCriteria(SalesPayment.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("salesPaymentId",empid));
		return salesPayment;
	}

	public void deleteSalesPayment(SalesPayment salesPayment) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesPayment WHERE salesPaymentId = "
								+ salesPayment.getSalesPaymentId()).executeUpdate();
	}

}
