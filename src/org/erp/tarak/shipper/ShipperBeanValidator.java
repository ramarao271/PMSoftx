package org.erp.tarak.shipper;

import java.util.Arrays;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ShipperBeanValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(Class<?> arg0) {
		return ShipperBean.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ShipperBean shipperBean = (ShipperBean) arg0;
		String mandatoryFields[] = { "shipperName", "companyName",
				"companyMobile1" };
		List<String> fieldsList = Arrays.asList(mandatoryFields);
		for (String field : fieldsList) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, field);
		}
		String email = shipperBean.getCompanyEmail();
		if (email != null && !"".equals(email)) {
			if (!email.matches(EMAIL_PATTERN)) {
				errors.rejectValue("companyEmail", "emailValidation");
			}
		}
		String numberFields[] = { "companyTelephone1", "companyTelephone2",
				"companyMobile1", "companyMobile2" };
		for (String field : numberFields) {
			try {
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
