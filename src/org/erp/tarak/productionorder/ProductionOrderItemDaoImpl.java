package org.erp.tarak.productionorder;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("productionOrderItemDao")
public class ProductionOrderItemDaoImpl implements ProductionOrderItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addProductionOrderItem(ProductionOrderItem productionOrderItem) {
		if (productionOrderItem.getProductionOrderId() > 0) {
			sessionFactory.getCurrentSession().update(productionOrderItem);
		} else {
			sessionFactory.getCurrentSession().save(productionOrderItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ProductionOrderItem> listProductionOrderItems() {
		return (List<ProductionOrderItem>) sessionFactory.getCurrentSession()
				.createCriteria(ProductionOrderItem.class).list();
	}

	public ProductionOrderItem getProductionOrderItem(long empid) {
		return (ProductionOrderItem) sessionFactory.getCurrentSession().get(ProductionOrderItem.class,
				empid);
	}

	public void deleteProductionOrderItem(ProductionOrderItem productionOrderItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ProductionOrderItem WHERE productionOrderId = "
								+ productionOrderItem.getProductionOrderId()).executeUpdate();
	}

	@Override
	public void deleteProductionOrderItems(List<ProductionOrderItem> pois) {
		for(ProductionOrderItem poi:pois)
		{
			deleteProductionOrderItem(poi);
		}
		
	}

}
