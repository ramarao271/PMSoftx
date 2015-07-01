package org.erp.tarak.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("addressService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addAddress(Address address) {
		addressDao.addAddress(address);
	}
	
	public List<Address> listAddresss() {
		return addressDao.listAddresss();
	}

	public Address getAddress(long addressId) {
		return addressDao.getAddress(addressId);
	}
	
	public void deleteAddress(Address address) {
		addressDao.deleteAddress(address);
	}

}
