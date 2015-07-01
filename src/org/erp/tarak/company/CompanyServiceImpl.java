package org.erp.tarak.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("companyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCompany(Company company) {
		companyDao.addCompany(company);
	}
	
	public List<Company> listCategories() {
		return companyDao.listCategories();
	}

	public Company getCompany(long companyId) {
		return companyDao.getCompany(companyId);
	}
	
	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}

	@Override
	public Company getLastCompany() {
		return companyDao.getLastCompany();
	}

	@Override
	public Company getCompanyByName(String companyName) {
		return companyDao.getCompanyByName(companyName);
	}

}
