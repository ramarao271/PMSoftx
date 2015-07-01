package org.erp.tarak.address;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddressBeanValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return AddressBean.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyAddressBean.pinCode", "required");
		
	}

}
