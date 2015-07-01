package org.erp.tarak.address;

import java.util.List;


public interface AddressDao {
	
	public void addAddress(Address product);

	public List<Address> listAddresss();
	
	public Address getAddress(long addressId);
	
	public void deleteAddress(Address product);
}
