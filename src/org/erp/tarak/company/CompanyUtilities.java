package org.erp.tarak.company;

import java.util.ArrayList;
import java.util.List;

import org.erp.tarak.library.ERPUtilities;


public class CompanyUtilities {

	public static List<CompanyBean> prepareListofCompanyBean(
			List<Company> categories) {
		List<CompanyBean> beans = new ArrayList<CompanyBean>();
		for (Company company : categories) {
			CompanyBean companyBean = new CompanyBean();
			companyBean.setCompanyId(company.getCompanyId());
			companyBean.setCompanyName(company.getCompanyName());
			companyBean.setCompanyCode(company.getCompanyCode());
			companyBean.setBalance(company.getBalance());
			beans.add(companyBean);
		}
		return beans;
	}

	public static Company prepareCompanyModel(CompanyBean companyBean,CompanyService companyService) {
		Company company = new Company();
		company.setCompanyId(companyBean.getCompanyId());
		company.setCompanyName(companyBean.getCompanyName());
		if(companyBean.getCompanyId()>0)
		{
			company.setCompanyCode(companyBean.getCompanyCode());
		}
		else
		{
			String companyCode=getCompanyCode(companyService);
			company.setCompanyCode(companyCode);
		}
		company.setBalance(companyBean.getBalance());
		return company;
	}

	public static Company prepareCompanyModel(CompanyBean companyBean) {
		Company company = new Company();
		company.setCompanyId(companyBean.getCompanyId());
		company.setCompanyName(companyBean.getCompanyName());
		company.setCompanyCode(companyBean.getCompanyCode());
		company.setBalance(companyBean.getBalance());
		return company;
	}

	
	
	
	private static String getCompanyCode(CompanyService companyService) {
		Company company=companyService.getLastCompany();
		String companyCode="";
		if(company!=null)
		{
			companyCode=ERPUtilities.incrementCode(company.getCompanyCode());
			companyCode=ERPUtilities.fomatStringToN(companyCode,3);
		}
		else
		{
			companyCode="0000";
		}
		return companyCode;
	}

	public static CompanyBean prepareCompanyBean(Company company) {
		CompanyBean bean = new CompanyBean();
		bean.setCompanyId(company.getCompanyId());
		bean.setCompanyName(company.getCompanyName());
		bean.setCompanyCode(company.getCompanyCode());
		bean.setBalance(company.getBalance());
		return bean;
	}


}
