package org.erp.tarak.contactperson;

import java.util.List;


public interface ContactPersonDao {
		
		public void addContactPerson(ContactPerson contactPerson);

		public List<ContactPerson> listCategories();
		
		public ContactPerson getContactPerson(long contactPersonId);
		
		public void deleteContactPerson(ContactPerson contactPerson);

		public void deleteContactPersons(List<ContactPerson> contactPersons);
	}
