package org.erp.tarak.supplier;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("supplierDao")
public class SupplierDaoImpl implements SupplierDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSupplier(Supplier supplier) {
		if (supplier.getSupplierId() > 0) {
			sessionFactory.getCurrentSession().update(supplier);
		} else {
			sessionFactory.getCurrentSession().save(supplier);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Supplier> listSuppliers() {
		return (List<Supplier>) sessionFactory.getCurrentSession()
				.createCriteria(Supplier.class).list();
	}

	public Supplier getSupplier(long empid) {
		 Supplier supplier=(Supplier) sessionFactory.getCurrentSession().get(
				Supplier.class, empid);
		 return supplier;
	}

	public void deleteSupplier(Supplier supplier) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Supplier WHERE supplierId = "
								+ supplier.getSupplierId()).executeUpdate();
	}

	@Override
	public List<Supplier> listSuppliersbyCompanyName(String companyName) {
		String c=companyName;
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Supplier.class);
		crit.add(Restrictions.eq("companyName", c));
		List results = crit.list();
		return results;
		
	}

	@Override
	public List<Supplier> listSuppliersbyCompanyNameRegex(String search) {
		String c="%"+search+"%";
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Supplier.class);
		crit.add(Restrictions.ilike("companyName", c));
		List results = crit.list();
		return results;
	}
}
