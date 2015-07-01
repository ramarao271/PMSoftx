package org.erp.tarak.address;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("addressDao")
public class AddressDaoImpl implements AddressDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addAddress(Address address) {
		if (address.getAddressId() > 0) {
			sessionFactory.getCurrentSession().update(address);
		} else {
			sessionFactory.getCurrentSession().save(address);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Address> listAddresss() {
		return (List<Address>) sessionFactory.getCurrentSession()
				.createCriteria(Address.class).list();
	}

	public Address getAddress(long addressId) {
		return (Address) sessionFactory.getCurrentSession().get(Address.class,
				addressId);
	}

	public void deleteAddress(Address address) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Address WHERE addressId = "
								+ address.getAddressId()).executeUpdate();
	}

}
