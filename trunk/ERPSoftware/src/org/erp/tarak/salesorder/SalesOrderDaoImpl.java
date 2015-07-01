package org.erp.tarak.salesorder;

import java.util.List;

import org.erp.tarak.product.Product;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("salesOrderDao")
public class SalesOrderDaoImpl implements SalesOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesOrder(SalesOrder salesOrder) {
		if (salesOrder.getSalesOrderId() > 0) {
			sessionFactory.getCurrentSession().update(salesOrder);
		} else {
			sessionFactory.getCurrentSession().save(salesOrder);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesOrder> listSalesOrders(String finYear) {
		return (List<SalesOrder>) sessionFactory.getCurrentSession()
				.createCriteria(SalesOrder.class).add(Restrictions.eq("finYear",finYear)).list();
	}

	public SalesOrder getSalesOrder(long salesOrderId,String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("salesOrderId", salesOrderId));
		List results = crit.list();
		return (SalesOrder)results.get(0);
	}

	public void deleteSalesOrder(SalesOrder salesOrder) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesOrder WHERE salesOrderId = "
								+ salesOrder.getSalesOrderId()+" and finYear='"+salesOrder.getFinYear()+"'").executeUpdate();
	}

	@Override
	public List<SalesOrder> listPendingSalesOrders(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("processed", false));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<SalesOrder> listProcessedSalesOrders(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SalesOrder.class);
		crit.add(Restrictions.eq("finYear",finYear));
		crit.add(Restrictions.eq("processed", true));
		List results = crit.list();
		return results;
	}

}
