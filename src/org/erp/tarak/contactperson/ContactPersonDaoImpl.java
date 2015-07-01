package org.erp.tarak.contactperson;

	import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
	@Repository("contactPersonDao")
	public class ContactPersonDaoImpl implements ContactPersonDao {

		@Autowired
		private SessionFactory sessionFactory;
		
		public void addContactPerson(ContactPerson contactPerson) {
			if(contactPerson.getContactPersonId()>0)
			{
				sessionFactory.getCurrentSession().update(contactPerson);
			}
			else
			{
				sessionFactory.getCurrentSession().save(contactPerson);
			}
		}

		@SuppressWarnings("unchecked")
		public List<ContactPerson> listCategories() {
			return (List<ContactPerson>) sessionFactory.getCurrentSession().createCriteria(ContactPerson.class).list();
		}

		public ContactPerson getContactPerson(long contactPersonId) {
			return (ContactPerson) sessionFactory.getCurrentSession().get(ContactPerson.class, contactPersonId);
		}

		public void deleteContactPerson(ContactPerson contactPerson) {
			sessionFactory.getCurrentSession().createQuery("DELETE FROM ContactPerson WHERE contactPersonId = "+contactPerson.getContactPersonId()).executeUpdate();
		}

		@Override
		public void deleteContactPersons(List<ContactPerson> contactPersons) {
			for (ContactPerson contactPerson : contactPersons) {
				deleteContactPerson(contactPerson);
			}

		}

}
