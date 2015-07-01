package org.erp.tarak.company;

import java.util.List;


public interface CompanyDao {
		
		public void addCompany(Company company);

		public List<Company> listCategories();
		
		public Company getCompany(long companyId);
		
		public void deleteCompany(Company company);

		public Company getLastCompany();

		public Company getCompanyByName(String companyName);
	}
