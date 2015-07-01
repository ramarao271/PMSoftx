package org.erp.tarak.address;

import java.util.List;


public interface AddressService {
	
	public void addAddress(Address address);

	public List<Address> listAddresss();
	
	public Address getAddress(long addressId);
	
	public void deleteAddress(Address address);
}
