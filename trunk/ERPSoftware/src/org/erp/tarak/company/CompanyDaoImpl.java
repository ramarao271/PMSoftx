package org.erp.tarak.company;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCompany(Company company) {
		if (company.getCompanyId() > 0) {
			sessionFactory.getCurrentSession().update(company);
		} else {
			sessionFactory.getCurrentSession().save(company);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Company> listCategories() {
		return (List<Company>) sessionFactory.getCurrentSession()
				.createCriteria(Company.class).list();
	}

	public Company getCompany(long companyId) {
		return (Company) sessionFactory.getCurrentSession().get(
				Company.class, companyId);
	}

	public void deleteCompany(Company company) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Company WHERE companyId = "
								+ company.getCompanyId()).executeUpdate();
	}

	@Override
	public Company getLastCompany() {
		String hql = "from Company where companyId=(SELECT MAX(companyId) from Company)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Company) results.get(0);
		}
		return null;
	}

	@Override
	public Company getCompanyByName(String companyName) {
		String hql = "from Company where companyName='"+companyName+"')";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Company) results.get(0);
		}
		return null;
		
	}

}
