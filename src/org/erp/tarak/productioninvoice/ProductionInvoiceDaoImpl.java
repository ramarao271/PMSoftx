package org.erp.tarak.productioninvoice;

import java.util.List;

import org.erp.tarak.salesinvoice.SalesInvoice;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
	public List<ProductionInvoice> listProductionInvoices(String finYear) {
		return (List<ProductionInvoice>) sessionFactory.getCurrentSession()
				.createCriteria(ProductionInvoice.class).add(Restrictions.eq("finYear", finYear)).list();
	}

	public void deleteProductionInvoice(ProductionInvoice productionInvoice) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ProductionInvoice WHERE productionInvoiceId = "
								+ productionInvoice.getProductionInvoiceId()).executeUpdate();
	}

	@Override
	public List<ProductionInvoice> listPendingProductionInvoices(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionInvoice.class);
		crit.add(Restrictions.eq("processed", false));
		crit.add(Restrictions.eq("finYear", finYear));
		List results = crit.list();
		return results;
	
	}

	@Override
	public List<ProductionInvoice> listProcessedProductionInvoices(String finYear) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ProductionInvoice.class);
		crit.add(Restrictions.eq("processed", true));
		crit.add(Restrictions.eq("finYear", finYear));
		List results = crit.list();
		return results;
	}

	@Override
	public List<ProductionInvoice> listProductionInvoicesByWorker(
			long workerId, String finYear) {
		String hql = "select * from ProductionInvoice where worker_Id="+workerId+" and finYear='"+finYear+"'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(ProductionInvoice.class);
		List results = query.list();
		return results;
	}

	@Override
	public ProductionInvoice getProductionInvoice(long productionInvoiceId,
			String finYear) {
		return (ProductionInvoice) sessionFactory.getCurrentSession().createCriteria(ProductionInvoice.class).add(Restrictions.eq("finYear", finYear)).add(Restrictions.eq("productionInvoiceId", productionInvoiceId)).list().get(0);
	}

}
