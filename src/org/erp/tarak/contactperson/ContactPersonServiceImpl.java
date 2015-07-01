package org.erp.tarak.contactperson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("contactPersonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ContactPersonServiceImpl implements ContactPersonService {

	@Autowired
	private ContactPersonDao contactPersonDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addContactPerson(ContactPerson contactPerson) {
		contactPersonDao.addContactPerson(contactPerson);
	}
	
	public List<ContactPerson> listCategories() {
		return contactPersonDao.listCategories();
	}

	public ContactPerson getContactPerson(long contactPersonId) {
		return contactPersonDao.getContactPerson(contactPersonId);
	}
	
	public void deleteContactPerson(ContactPerson contactPerson) {
		contactPersonDao.deleteContactPerson(contactPerson);
	}

	@Override
	public void deleteContactPersons(List<ContactPerson> contactPersons) {
		contactPersonDao.deleteContactPersons(contactPersons);
		
	}

}
