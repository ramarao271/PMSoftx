package org.erp.tarak.salesorder;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("salesOrderItemDao")
public class SalesOrderItemDaoImpl implements SalesOrderItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesOrderItem(SalesOrderItem salesOrderItem) {
		if (salesOrderItem.getSalesOrderId() > 0) {
			sessionFactory.getCurrentSession().update(salesOrderItem);
		} else {
			sessionFactory.getCurrentSession().save(salesOrderItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderItem> listSalesOrderItems() {
		return (List<SalesOrderItem>) sessionFactory.getCurrentSession()
				.createCriteria(SalesOrderItem.class).list();
	}

	public SalesOrderItem getSalesOrderItem(long empid) {
		return (SalesOrderItem) sessionFactory.getCurrentSession().get(SalesOrderItem.class,
				empid);
	}

	public void deleteSalesOrderItem(SalesOrderItem salesOrderItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesOrderItem WHERE salesOrderId = "
								+ salesOrderItem.getSalesOrderId()).executeUpdate();
	}

	@Override
	public void deleteSalesOrderItems(List<SalesOrderItem> pois) {
		for(SalesOrderItem poi:pois)
		{
			deleteSalesOrderItem(poi);
		}
		
	}

}
