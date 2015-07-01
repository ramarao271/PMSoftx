package org.erp.tarak.customer;

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

public class CustomerUtilities {

	public static Customer getCustomerModel(CustomerService customerService,
			long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		return customer;
	}

	public static Customer prepareCustomerModel(CustomerBean customerBean) {
		Customer customer = new Customer();
		customer.setCompanyTinNo(customerBean.getCompanyTinNo());
		customer.setCompanyBranch(customerBean.getCompanyBranch());
		customer.setCompanyCstNo(customerBean.getCompanyCstNo());
		customer.setCompanyEmail(customerBean.getCompanyEmail());
		customer.setCompanyMobile1(customerBean.getCompanyMobile1());
		customer.setCompanyMobile2(customerBean.getCompanyMobile2());
		customer.setCompanyName(customerBean.getCompanyName());
		customer.setCompanyTelephone1(customerBean.getCompanyTelephone1());
		customer.setCompanyTelephone2(customerBean.getCompanyTelephone2());
		customer.setCustomerId(customerBean.getCustomerId());
		customer.setCustomerName(customerBean.getCustomerName());
		customer.setOpeningBalance(customerBean.getOpeningBalance());
		if (customerBean.getCompanyAddressBean() != null) {
			Address address = AddressUtilities.populateAddress(customerBean
					.getCompanyAddressBean());
			customer.setCompanyAddress(address);
		}
		if (customerBean.getCustomerAccountsBeans() != null) {
			List<BankAccount> bankAccounts = BankAccountUtilities
					.populateBankAccounts(customerBean
							.getCustomerAccountsBeans());
			customer.setCustomerAccounts(bankAccounts);
		}
		if (customerBean.getContactPersonsBeans() != null) {
			List<ContactPerson> contactPersons = ContactPersonUtilities
					.populateContactPersons(customerBean
							.getContactPersonsBeans());
			customer.setContactPersons(contactPersons);
		}
		return customer;
	}

	public static List<CustomerBean> prepareListofCustomerBean(
			List<Customer> customers) {
		List<CustomerBean> beans = null;
		if (customers != null && !customers.isEmpty()) {
			beans = new ArrayList<CustomerBean>();
			CustomerBean bean = null;
			for (Customer customer : customers) {
				bean = new CustomerBean();
				bean.setCompanyTinNo(customer.getCompanyTinNo());
				bean.setCompanyBranch(customer.getCompanyBranch());
				bean.setCompanyCstNo(customer.getCompanyCstNo());
				bean.setCompanyEmail(customer.getCompanyEmail());
				bean.setCompanyMobile1(customer.getCompanyMobile1());
				bean.setCompanyMobile2(customer.getCompanyMobile2());
				bean.setCompanyName(customer.getCompanyName());
				bean.setCompanyTelephone1(customer.getCompanyTelephone1());
				bean.setCompanyTelephone2(customer.getCompanyTelephone2());
				bean.setCustomerId(customer.getCustomerId());
				bean.setCustomerName(customer.getCustomerName());
				bean.setOpeningBalance(customer.getOpeningBalance());
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(customer.getCompanyAddress());
				List<BankAccountBean> bankAccountBeans = BankAccountUtilities
						.prepareBankAccountBean(customer.getCustomerAccounts());
				List<ContactPersonBean> contactPersons = ContactPersonUtilities
						.populateContactPersonBeans(customer
								.getContactPersons());
				bean.setCompanyAddressBean(addressBean);
				bean.setCustomerAccountsBeans(bankAccountBeans);
				bean.setContactPersonsBeans(contactPersons);
				beans.add(bean);
			}
		}
		return beans;
	}

	public static CustomerBean prepareCustomerBean(long customerId,
			CustomerService customerService) {
		CustomerBean bean = new CustomerBean();
		Customer customer = CustomerUtilities.getCustomerModel(customerService,
				customerId);
		bean.setCompanyTinNo(customer.getCompanyTinNo());
		bean.setCompanyBranch(customer.getCompanyBranch());
		bean.setCompanyCstNo(customer.getCompanyCstNo());
		bean.setCompanyEmail(customer.getCompanyEmail());
		bean.setCompanyMobile1(customer.getCompanyMobile1());
		bean.setCompanyMobile2(customer.getCompanyMobile2());
		bean.setCompanyName(customer.getCompanyName());
		bean.setCompanyTelephone1(customer.getCompanyTelephone1());
		bean.setCompanyTelephone2(customer.getCompanyTelephone2());
		bean.setOpeningBalance(customer.getOpeningBalance());
		List<BankAccountBean> bankAccountBeans = BankAccountUtilities
				.prepareBankAccountBean(customer.getCustomerAccounts());
		List<ContactPersonBean> contactPersonBean = ContactPersonUtilities
				.populateContactPersonBeans(customer.getContactPersons());
		AddressBean addressBean = AddressUtilities.prepareAddressBean(customer
				.getCompanyAddress());
		bean.setCompanyAddressBean(addressBean);
		bean.setCustomerAccountsBeans(bankAccountBeans);
		bean.setContactPersonsBeans(contactPersonBean);
		bean.setCustomerId(customer.getCustomerId());
		bean.setCustomerName(customer.getCustomerName());
		return bean;
	}

	public static CustomerBean prepareCustomerBean(Customer customer) {
		CustomerBean bean = new CustomerBean();
		bean.setCompanyTinNo(customer.getCompanyTinNo());
		bean.setCompanyBranch(customer.getCompanyBranch());
		bean.setCompanyCstNo(customer.getCompanyCstNo());
		bean.setCompanyEmail(customer.getCompanyEmail());
		bean.setCompanyMobile1(customer.getCompanyMobile1());
		bean.setCompanyMobile2(customer.getCompanyMobile2());
		bean.setCompanyName(customer.getCompanyName());
		bean.setCompanyTelephone1(customer.getCompanyTelephone1());
		bean.setCompanyTelephone2(customer.getCompanyTelephone2());
		List<BankAccountBean> bankAccountBeans = BankAccountUtilities
				.prepareBankAccountBean(customer.getCustomerAccounts());
		List<ContactPersonBean> contactPersonBean = ContactPersonUtilities
				.populateContactPersonBeans(customer.getContactPersons());
		AddressBean addressBean = AddressUtilities.prepareAddressBean(customer
				.getCompanyAddress());
		bean.setCompanyAddressBean(addressBean);
		bean.setCustomerAccountsBeans(bankAccountBeans);
		bean.setContactPersonsBeans(contactPersonBean);
		bean.setCustomerId(customer.getCustomerId());
		bean.setCustomerName(customer.getCustomerName());
		bean.setOpeningBalance(customer.getOpeningBalance());
		return bean;
	}

	public static List<CustomerBean> prepareListofCustomerBeans(
			List<Customer> customers) {
		List<CustomerBean> beans = null;
		if (customers != null && !customers.isEmpty()) {
			beans = new LinkedList<CustomerBean>();
			CustomerBean bean = null;
			for (Customer customer : customers) {
				bean = new CustomerBean();
				bean.setCompanyTinNo(customer.getCompanyTinNo());
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(customer.getCompanyAddress());
				bean.setCompanyAddressBean(addressBean);
				bean.setCompanyBranch(customer.getCompanyBranch());
				bean.setCompanyCstNo(customer.getCompanyCstNo());
				bean.setCompanyEmail(customer.getCompanyEmail());
				bean.setCompanyMobile1(customer.getCompanyMobile1());
				bean.setCompanyMobile2(customer.getCompanyMobile2());
				bean.setCompanyName(customer.getCompanyName());
				bean.setCompanyTelephone1(customer.getCompanyTelephone1());
				bean.setCompanyTelephone2(customer.getCompanyTelephone2());
				bean.setCustomerId(customer.getCustomerId());
				bean.setCustomerName(customer.getCustomerName());
				bean.setOpeningBalance(customer.getOpeningBalance());
				List<BankAccountBean> bankAccountBeans = BankAccountUtilities
						.prepareBankAccountBean(customer.getCustomerAccounts());
				bean.setCustomerAccountsBeans(bankAccountBeans);

				beans.add(bean);
			}
		}
		return beans;
	}

}
