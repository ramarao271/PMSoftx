package org.erp.tarak.balanceSheet;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("balanceSheetItemDao")
public class BalanceSheetItemDaoImpl implements BalanceSheetItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addBalanceSheetItem(BalanceSheetItem balanceSheetItem) {
		if (balanceSheetItem.getBalanceSheetId() > 0) {
			sessionFactory.getCurrentSession().update(balanceSheetItem);
		} else {
			sessionFactory.getCurrentSession().save(balanceSheetItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<BalanceSheetItem> listBalanceSheetItems() {
		return (List<BalanceSheetItem>) sessionFactory.getCurrentSession()
				.createCriteria(BalanceSheetItem.class).list();
	}

	public BalanceSheetItem getBalanceSheetItem(long empid) {
		return (BalanceSheetItem) sessionFactory.getCurrentSession().get(BalanceSheetItem.class,
				empid);
	}

	public void deleteBalanceSheetItem(BalanceSheetItem balanceSheetItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM BalanceSheetItem WHERE balanceSheetId = "
								+ balanceSheetItem.getBalanceSheetId()).executeUpdate();
	}

	@Override
	public void deleteBalanceSheetItems(List<BalanceSheetItem> pois) {
		for(BalanceSheetItem poi:pois)
		{
			deleteBalanceSheetItem(poi);
		}
		
	}

}
