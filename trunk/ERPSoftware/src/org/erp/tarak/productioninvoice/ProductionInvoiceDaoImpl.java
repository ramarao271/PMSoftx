package org.erp.tarak.productioninvoice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("productionInvoiceDao")
public class ProductionInvoiceDaoImpl implements ProductionInvoiceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addProductionInvoice(ProductionInvoice productionInvoice) {
		if (productionInvoice.getProductionInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(productionInvoice);
		} else {
			sessionFactory.getCurrentSession().save(productionInvoice);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ProductionInvoice> listProductionInvoices() {
		return (List<ProductionInvoice>) sessionFactory.getCurrentSession()
				.createCriteria(ProductionInvoice.class).list();
	}

	public ProductionInvoice getProductionInvoice(long empid) {
		return (ProductionInvoice) sessionFactory.getCurrentSession().get(ProductionInvoice.class,
				empid);
	}

	public void deleteProductionInvoice(ProductionInvoice productionInvoice) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ProductionInvoice WHERE productionInvoiceId = "
								+ productionInvoice.getProductionInvoiceId()).executeUpdate();
	}

	@Override
	public List<ProductionInvoice> listPendingProductionInvoices() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionInvoice.class);
		crit.add(Restrictions.eq("processed", false));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<ProductionInvoice> listProcessedProductionInvoices() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionInvoice.class);
		crit.add(Restrictions.eq("processed", true));
		List results = crit.list();
		return results;
	}

}
