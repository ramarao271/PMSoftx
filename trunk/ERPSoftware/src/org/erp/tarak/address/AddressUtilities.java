package org.erp.tarak.address;


public class AddressUtilities {

	public static Address saveAddress(AddressService addressService,
			Address address) {
		addressService.addAddress(address);
		return address;
	}

	public static Address populateAddress(AddressBean addressBean) {
		Address address = new Address();
		address.setAddressLine1(addressBean.getAddressLine1());
		address.setAddressLine2(addressBean.getAddressLine2());
		address.setCity(addressBean.getCity());
		address.setCountry(addressBean.getCountry());
		address.setDistrict(addressBean.getDistrict());
		address.setPinCode(addressBean.getPinCode());
		address.setState(addressBean.getState());
		address.setAddressId(addressBean.getAddressId());
		return address;
	}

	public static Address getAddressModel(AddressService addressService,
			long addressId) {
		Address address = addressService.getAddress(addressId);
		return address;
	}

	public static AddressBean prepareAddressBean(Address address) {
		AddressBean addressBean = new AddressBean();
		addressBean.setAddressLine1(address.getAddressLine1());
		addressBean.setAddressLine2(address.getAddressLine2());
		addressBean.setCity(address.getCity());
		addressBean.setCountry(address.getCountry());
		addressBean.setDistrict(address.getDistrict());
		addressBean.setPinCode(address.getPinCode());
		addressBean.setState(address.getState());
		addressBean.setAddressId(address.getAddressId());
		return addressBean;
	}

}
