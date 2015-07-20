package org.erp.tarak.otherPayment;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("otherPaymentDao")
public class OtherPaymentDaoImpl implements OtherPaymentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addOtherPayment(OtherPayment otherPayment) {
		if (otherPayment.getOtherPaymentId() > 0) {
			sessionFactory.getCurrentSession().update(otherPayment);
		} else {
			Session session=sessionFactory.getCurrentSession();//.save(otherPayment);
			/*//session.beginTransaction();
			if(otherPayment.getOtherPaymentItems()!=null && otherPayment.getOtherPaymentItems().size()>0)
			{
				Iterator<OtherPaymentItem> itr=otherPayment.getOtherPaymentItems().iterator();
				while(itr.hasNext())
				{
					OtherPaymentItem otherPaymentItem=(OtherPaymentItem) itr.next();
					session.flush();
					session.save(otherPaymentItem);
					session.flush();
					session.clear();
				}
			}*/
			
			session.save(otherPayment);
			//session.close();
			//session.merge(otherPayment);
			}
		//sessionFactory.getCurrentSession().flush();
		//sessionFactory.getCurrentSession().clear();
	}

	@SuppressWarnings("unchecked")
	public List<OtherPayment> listOtherPayments(String finYear) {
		return (List<OtherPayment>) sessionFactory.getCurrentSession()
				.createCriteria(OtherPayment.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public OtherPayment getOtherPayment(long empid,String finYear) {
		OtherPayment otherPayment=(OtherPayment) sessionFactory.getCurrentSession().createCriteria(OtherPayment.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("otherPaymentId",empid)).list().get(0);
		return otherPayment;
	}

	public void deleteOtherPayment(OtherPayment otherPayment) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM OtherPayment WHERE otherPaymentId = "
								+ otherPayment.getOtherPaymentId()).executeUpdate();
	}

	@Override
	public List<OtherPayment> listProductionInvoicesByDate(Date balanceSheetDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				OtherPayment.class);
		crit.add(Restrictions.eq("otherotherPaymentDateDate", balanceSheetDate));
		List results = crit.list();
		return results;
	}

}
