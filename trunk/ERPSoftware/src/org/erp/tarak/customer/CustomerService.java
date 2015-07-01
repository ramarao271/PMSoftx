package org.erp.tarak.customer;

import java.util.List;


public interface CustomerService {

	public void addCustomer(Customer customer);

	public List<Customer> listCustomers();

	public Customer getCustomer(long customerId);

	public void deleteCustomer(Customer customer);
	
	public List<Customer> listCustomersbyCompanyName(String companyName);

	public List<Customer> listCustomersbyCompanyNameRegex(String search);
}
