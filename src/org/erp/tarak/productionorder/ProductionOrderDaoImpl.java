package org.erp.tarak.productionorder;

import java.util.List;

import org.erp.tarak.product.Product;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("productionOrderDao")
public class ProductionOrderDaoImpl implements ProductionOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addProductionOrder(ProductionOrder productionOrder) {
		if (productionOrder.getProductionOrderId() > 0) {
			sessionFactory.getCurrentSession().update(productionOrder);
		} else {
			sessionFactory.getCurrentSession().save(productionOrder);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ProductionOrder> listProductionOrders() {
		return (List<ProductionOrder>) sessionFactory.getCurrentSession()
				.createCriteria(ProductionOrder.class).list();
	}

	public ProductionOrder getProductionOrder(long empid) {
		return (ProductionOrder) sessionFactory.getCurrentSession().get(ProductionOrder.class,
				empid);
	}

	public void deleteProductionOrder(ProductionOrder productionOrder) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ProductionOrder WHERE productionOrderId = "
								+ productionOrder.getProductionOrderId()).executeUpdate();
	}

	@Override
	public List<ProductionOrder> listPendingProductionOrders() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionOrder.class);
		crit.add(Restrictions.eq("processed", false));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<ProductionOrder> listProcessedProductionOrders() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionOrder.class);
		crit.add(Restrictions.eq("processed", true));
		List results = crit.list();
		return results;
	}

}
