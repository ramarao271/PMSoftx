package org.erp.tarak.balanceSheet;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("balanceSheetDao")
public class BalanceSheetDaoImpl implements BalanceSheetDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addBalanceSheet(BalanceSheet balanceSheet) {
		if (balanceSheet.getBalanceSheetId() > 0) {
			sessionFactory.getCurrentSession().update(balanceSheet);
		} else {
			sessionFactory.getCurrentSession().save(balanceSheet);
		}

	}

	@SuppressWarnings("unchecked")
	public List<BalanceSheet> listBalanceSheets() {
		return (List<BalanceSheet>) sessionFactory.getCurrentSession()
				.createCriteria(BalanceSheet.class).list();
	}

	public BalanceSheet getBalanceSheet(long empid) {
		return (BalanceSheet) sessionFactory.getCurrentSession().get(BalanceSheet.class,
				empid);
	}

	public void deleteBalanceSheet(BalanceSheet balanceSheet) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM BalanceSheet WHERE balanceSheetId = "
								+ balanceSheet.getBalanceSheetId()).executeUpdate();
	}

	@Override
	public List<BalanceSheet> listPendingBalanceSheets() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				BalanceSheet.class);
		crit.add(Restrictions.eq("processed", false));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<BalanceSheet> listProcessedBalanceSheets() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				BalanceSheet.class);
		crit.add(Restrictions.eq("processed", true));
		List results = crit.list();
		return results;
	}

}
