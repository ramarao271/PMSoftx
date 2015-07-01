package org.erp.tarak.purchasePayment;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PurchasePaymentBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return PurchasePaymentBean.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {/*
		PurchasePaymentBean purchasePaymentBean = (PurchasePaymentBean) arg0;
		String mandatoryFields[] = { "purchasePaymentName", "companyName",
				"companyMobile1" };
		List<String> fieldsList = Arrays.asList(mandatoryFields);
		for (String field : fieldsList) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, field);
		}
		String numberFields[] = { "companyTelephone1", "companyTelephone2",
				"companyMobile1", "companyMobile2" };
		for (String field : numberFields) {
			try {
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	*/}
	}
