package org.erp.tarak.salesreturn;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("salesReturnItemDao")
public class SalesReturnItemDaoImpl implements SalesReturnItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesReturnItem(SalesReturnItem salesReturnItem) {
		if (salesReturnItem.getSalesReturnId() > 0) {
			sessionFactory.getCurrentSession().update(salesReturnItem);
		} else {
			sessionFactory.getCurrentSession().save(salesReturnItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesReturnItem> listSalesReturnItems() {
		return (List<SalesReturnItem>) sessionFactory.getCurrentSession()
				.createCriteria(SalesReturnItem.class).list();
	}

	public SalesReturnItem getSalesReturnItem(long empid) {
		return (SalesReturnItem) sessionFactory.getCurrentSession().get(SalesReturnItem.class,
				empid);
	}

	public void deleteSalesReturnItem(SalesReturnItem salesReturnItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesReturnItem WHERE salesReturnId = "
								+ salesReturnItem.getSalesReturnId()).executeUpdate();
	}

	@Override
	public void deleteSalesReturnItems(List<SalesReturnItem> pois) {
		for(SalesReturnItem poi:pois)
		{
			deleteSalesReturnItem(poi);
		}
		
	}

}
