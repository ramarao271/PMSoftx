package org.erp.tarak.supplier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.address.Address;
import org.erp.tarak.address.AddressBean;
import org.erp.tarak.address.AddressUtilities;
import org.erp.tarak.bankaccount.BankAccount;
import org.erp.tarak.bankaccount.BankAccountBean;
import org.erp.tarak.bankaccount.BankAccountUtilities;
import org.erp.tarak.contactperson.ContactPerson;
import org.erp.tarak.contactperson.ContactPersonBean;
import org.erp.tarak.contactperson.ContactPersonUtilities;

public class SupplierUtilities {

	public static Supplier getSupplierModel(SupplierService supplierService,
			long supplierId) {
		Supplier supplier = supplierService.getSupplier(supplierId);
		return supplier;
	}

	public static Supplier prepareSupplierModel(SupplierBean supplierBean) {
		Supplier supplier = new Supplier();
		supplier.setCompanyTinNo(supplierBean.getCompanyTinNo());
		supplier.setCompanyBranch(supplierBean.getCompanyBranch());
		supplier.setCompanyCstNo(supplierBean.getCompanyCstNo());
		supplier.setCompanyEmail(supplierBean.getCompanyEmail());
		supplier.setCompanyMobile1(supplierBean.getCompanyMobile1());
		supplier.setCompanyMobile2(supplierBean.getCompanyMobile2());
		supplier.setCompanyName(supplierBean.getCompanyName());
		supplier.setCompanyTelephone1(supplierBean.getCompanyTelephone1());
		supplier.setCompanyTelephone2(supplierBean.getCompanyTelephone2());
		supplier.setSupplierId(supplierBean.getSupplierId());
		supplier.setSupplierName(supplierBean.getSupplierName());
		supplier.setOpeningBalance(supplierBean.getOpeningBalance());
		if (supplierBean.getCompanyAddressBean() != null) {
			Address address = AddressUtilities.populateAddress(supplierBean
					.getCompanyAddressBean());
			supplier.setCompanyAddress(address);
		}
		if (supplierBean.getSupplierAccountsBeans() != null) {
			List<BankAccount> bankAccounts = BankAccountUtilities
					.populateBankAccounts(supplierBean
							.getSupplierAccountsBeans());
			supplier.setSupplierAccounts(bankAccounts);
		}
		if (supplierBean.getContactPersonsBeans() != null) {
			List<ContactPerson> contactPersons = ContactPersonUtilities
					.populateContactPersons(supplierBean
							.getContactPersonsBeans());
			supplier.setContactPersons(contactPersons);
		}
		return supplier;
	}

	public static List<SupplierBean> prepareListofSupplierBean(
			List<Supplier> suppliers) {
		List<SupplierBean> beans = null;
		if (suppliers != null && !suppliers.isEmpty()) {
			beans = new ArrayList<SupplierBean>();
			SupplierBean bean = null;
			for (Supplier supplier : suppliers) {
				bean = new SupplierBean();
				bean.setCompanyTinNo(supplier.getCompanyTinNo());
				bean.setCompanyBranch(supplier.getCompanyBranch());
				bean.setCompanyCstNo(supplier.getCompanyCstNo());
				bean.setCompanyEmail(supplier.getCompanyEmail());
				bean.setCompanyMobile1(supplier.getCompanyMobile1());
				bean.setCompanyMobile2(supplier.getCompanyMobile2());
				bean.setCompanyName(supplier.getCompanyName());
				bean.setCompanyTelephone1(supplier.getCompanyTelephone1());
				bean.setCompanyTelephone2(supplier.getCompanyTelephone2());
				bean.setSupplierId(supplier.getSupplierId());
				bean.setSupplierName(supplier.getSupplierName());
				bean.setOpeningBalance(supplier.getOpeningBalance());
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(supplier.getCompanyAddress());
				List<BankAccountBean> bankAccountBeans = BankAccountUtilities
						.prepareBankAccountBean(supplier.getSupplierAccounts());
				List<ContactPersonBean> contactPersons = ContactPersonUtilities
						.populateContactPersonBeans(supplier
								.getContactPersons());
				bean.setCompanyAddressBean(addressBean);
				bean.setSupplierAccountsBeans(bankAccountBeans);
				bean.setContactPersonsBeans(contactPersons);
				beans.add(bean);
			}
		}
		return beans;
	}

	public static SupplierBean prepareSupplierBean(long supplierId,
			SupplierService supplierService) {
		SupplierBean bean = new SupplierBean();
		Supplier supplier = SupplierUtilities.getSupplierModel(supplierService,
				supplierId);
		bean.setCompanyTinNo(supplier.getCompanyTinNo());
		bean.setCompanyBranch(supplier.getCompanyBranch());
		bean.setCompanyCstNo(supplier.getCompanyCstNo());
		bean.setCompanyEmail(supplier.getCompanyEmail());
		bean.setCompanyMobile1(supplier.getCompanyMobile1());
		bean.setCompanyMobile2(supplier.getCompanyMobile2());
		bean.setCompanyName(supplier.getCompanyName());
		bean.setCompanyTelephone1(supplier.getCompanyTelephone1());
		bean.setCompanyTelephone2(supplier.getCompanyTelephone2());
		bean.setOpeningBalance(supplier.getOpeningBalance());
		List<BankAccountBean> bankAccountBeans = BankAccountUtilities
				.prepareBankAccountBean(supplier.getSupplierAccounts());
		List<ContactPersonBean> contactPersonBean = ContactPersonUtilities
				.populateContactPersonBeans(supplier.getContactPersons());
		AddressBean addressBean = AddressUtilities.prepareAddressBean(supplier
				.getCompanyAddress());
		bean.setCompanyAddressBean(addressBean);
		bean.setSupplierAccountsBeans(bankAccountBeans);
		bean.setContactPersonsBeans(contactPersonBean);
		bean.setSupplierId(supplier.getSupplierId());
		bean.setSupplierName(supplier.getSupplierName());
		return bean;
	}

	public static SupplierBean prepareSupplierBean(Supplier supplier) {
		SupplierBean bean = new SupplierBean();
		bean.setCompanyTinNo(supplier.getCompanyTinNo());
		bean.setCompanyBranch(supplier.getCompanyBranch());
		bean.setCompanyCstNo(supplier.getCompanyCstNo());
		bean.setCompanyEmail(supplier.getCompanyEmail());
		bean.setCompanyMobile1(supplier.getCompanyMobile1());
		bean.setCompanyMobile2(supplier.getCompanyMobile2());
		bean.setCompanyName(supplier.getCompanyName());
		bean.setCompanyTelephone1(supplier.getCompanyTelephone1());
		bean.setCompanyTelephone2(supplier.getCompanyTelephone2());
		List<BankAccountBean> bankAccountBeans = BankAccountUtilities
				.prepareBankAccountBean(supplier.getSupplierAccounts());
		List<ContactPersonBean> contactPersonBean = ContactPersonUtilities
				.populateContactPersonBeans(supplier.getContactPersons());
		AddressBean addressBean = AddressUtilities.prepareAddressBean(supplier
				.getCompanyAddress());
		bean.setCompanyAddressBean(addressBean);
		bean.setSupplierAccountsBeans(bankAccountBeans);
		bean.setContactPersonsBeans(contactPersonBean);
		bean.setSupplierId(supplier.getSupplierId());
		bean.setSupplierName(supplier.getSupplierName());
		bean.setOpeningBalance(supplier.getOpeningBalance());
		return bean;
	}

	public static List<SupplierBean> prepareListofSupplierBeans(
			List<Supplier> suppliers) {
		List<SupplierBean> beans = null;
		if (suppliers != null && !suppliers.isEmpty()) {
			beans = new LinkedList<SupplierBean>();
			SupplierBean bean = null;
			for (Supplier supplier : suppliers) {
				bean = new SupplierBean();
				bean.setCompanyTinNo(supplier.getCompanyTinNo());
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(supplier.getCompanyAddress());
				bean.setCompanyAddressBean(addressBean);
				bean.setCompanyBranch(supplier.getCompanyBranch());
				bean.setCompanyCstNo(supplier.getCompanyCstNo());
				bean.setCompanyEmail(supplier.getCompanyEmail());
				bean.setCompanyMobile1(supplier.getCompanyMobile1());
				bean.setCompanyMobile2(supplier.getCompanyMobile2());
				bean.setCompanyName(supplier.getCompanyName());
				bean.setCompanyTelephone1(supplier.getCompanyTelephone1());
				bean.setCompanyTelephone2(supplier.getCompanyTelephone2());
				bean.setSupplierId(supplier.getSupplierId());
				bean.setSupplierName(supplier.getSupplierName());
				bean.setOpeningBalance(supplier.getOpeningBalance());
				List<BankAccountBean> bankAccountBeans = BankAccountUtilities
						.prepareBankAccountBean(supplier.getSupplierAccounts());
				bean.setSupplierAccountsBeans(bankAccountBeans);

				beans.add(bean);
			}
		}
		return beans;
	}

}
