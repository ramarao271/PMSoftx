package org.erp.tarak.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("customerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}
	
	public List<Customer> listCustomers() {
		return customerDao.listCustomers();
	}

	public Customer getCustomer(long customerId) {
		return customerDao.getCustomer(customerId);
	}
	
	public void deleteCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
	}

	@Override
	public List<Customer> listCustomersbyCompanyName(String companyName) {
		return customerDao.listCustomersbyCompanyName(companyName);
	}

	@Override
	public List<Customer> listCustomersbyCompanyNameRegex(String search) {
		return customerDao.listCustomersbyCompanyNameRegex(search);
	}

}
