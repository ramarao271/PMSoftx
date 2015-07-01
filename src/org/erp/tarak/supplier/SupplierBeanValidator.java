package org.erp.tarak.supplier;

import java.util.Arrays;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SupplierBeanValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(Class<?> arg0) {
		return SupplierBean.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		SupplierBean supplierBean = (SupplierBean) arg0;
		String mandatoryFields[] = { "supplierName", "companyName",
				"companyMobile1" };
		List<String> fieldsList = Arrays.asList(mandatoryFields);
		for (String field : fieldsList) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, field);
		}
		String email = supplierBean.getCompanyEmail();
		if (email != null && !"".equals(email)) {
			if (!email.matches(EMAIL_PATTERN)) {
				errors.rejectValue("companyEmail", "emailValidation");
			}
		}
	}
}
