package org.erp.tarak.productioninvoice;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("productionInvoiceItemDao")
public class ProductionInvoiceItemDaoImpl implements ProductionInvoiceItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem) {
		if (productionInvoiceItem.getProductionInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(productionInvoiceItem);
		} else {
			sessionFactory.getCurrentSession().save(productionInvoiceItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ProductionInvoiceItem> listProductionInvoiceItems() {
		return (List<ProductionInvoiceItem>) sessionFactory.getCurrentSession()
				.createCriteria(ProductionInvoiceItem.class).list();
	}

	public ProductionInvoiceItem getProductionInvoiceItem(long empid) {
		return (ProductionInvoiceItem) sessionFactory.getCurrentSession().get(ProductionInvoiceItem.class,
				empid);
	}

	public void deleteProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ProductionInvoiceItem WHERE productionInvoiceId = "
								+ productionInvoiceItem.getProductionInvoiceId()).executeUpdate();
	}

	@Override
	public void deleteProductionInvoiceItems(List<ProductionInvoiceItem> pois) {
		for(ProductionInvoiceItem poi:pois)
		{
			deleteProductionInvoiceItem(poi);
		}
		
	}

}
