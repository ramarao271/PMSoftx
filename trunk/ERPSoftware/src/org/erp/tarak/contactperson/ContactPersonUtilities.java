package org.erp.tarak.contactperson;

import java.util.LinkedList;
import java.util.List;


public class ContactPersonUtilities {

	public static List<ContactPerson> populateContactPersons(
			List<ContactPersonBean> contactPersonsBeans) {
		List<ContactPerson> contactPersons = new LinkedList<ContactPerson>();
		if (contactPersonsBeans != null) {
			for (ContactPersonBean contactPersonBean : contactPersonsBeans) {
				ContactPerson contactPerson = new ContactPerson();
				contactPerson.setContactPersonId(contactPersonBean
						.getContactPersonId());
				contactPerson.setContactPersonName(contactPersonBean
						.getContactPersonName());
				contactPerson
						.setDescription(contactPersonBean.getDescription());
				contactPerson.setEmailId(contactPersonBean.getEmailId());
				contactPerson.setMobileNumber(contactPersonBean
						.getMobileNumber());
				contactPersons.add(contactPerson);
			}
		}
		return contactPersons;
	}

	public static List<ContactPersonBean> populateContactPersonBeans(
			List<ContactPerson> contactPersons) {
		List<ContactPersonBean> contactPersonBeans = new LinkedList<ContactPersonBean>();
		for (ContactPerson contactPerson : contactPersons) {
			ContactPersonBean contactPersonBean = new ContactPersonBean();
			contactPersonBean.setContactPersonId(contactPerson
					.getContactPersonId());
			contactPersonBean.setContactPersonName(contactPerson
					.getContactPersonName());
			contactPersonBean.setDescription(contactPerson.getDescription());
			contactPersonBean.setEmailId(contactPerson.getEmailId());
			contactPersonBean.setMobileNumber(contactPerson.getMobileNumber());
			contactPersonBeans.add(contactPersonBean);
		}
		return contactPersonBeans;
	}

}
