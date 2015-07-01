package org.erp.tarak.library;

import java.util.List;

import org.erp.tarak.contactperson.ContactPersonBean;

public class SupplierDetails {
	private long supplierId;
	private String companyName;
	private List<String> companyBranch;
	public SupplierDetails() {
		super();
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<String> getCompanyBranch() {
		return companyBranch;
	}
	public void setCompanyBranch(List<String> companyBranch) {
		this.companyBranch = companyBranch;
	}
	public List<ContactPersonBean> getContactPersonsBeans() {
		return contactPersonsBeans;
	}
	public void setContactPersonsBeans(List<ContactPersonBean> contactPersonsBeans) {
		this.contactPersonsBeans = contactPersonsBeans;
	}
	private List<ContactPersonBean> contactPersonsBeans;	
	

}
