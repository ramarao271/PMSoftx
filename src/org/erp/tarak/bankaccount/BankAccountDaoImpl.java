package org.erp.tarak.bankaccount;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("bankAccountDao")
public class BankAccountDaoImpl implements BankAccountDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addBankAccount(BankAccount bankAccount) {
		if (bankAccount.getAccountId() > 0) {
			sessionFactory.getCurrentSession().update(bankAccount);
		} else {
			sessionFactory.getCurrentSession().save(bankAccount);
		}
	}

	public void addBankAccounts(List<BankAccount> bankAccounts) {
		if (bankAccounts != null) {
			for (BankAccount bankAccount : bankAccounts) {
				addBankAccount(bankAccount);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<BankAccount> listBankAccounts() {
		return (List<BankAccount>) sessionFactory.getCurrentSession()
				.createCriteria(BankAccount.class).list();
	}

	public BankAccount getBankAccount(int empid) {
		return (BankAccount) sessionFactory.getCurrentSession().get(
				BankAccount.class, empid);
	}

	public void deleteBankAccount(BankAccount bankAccount) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM BankAccount WHERE accountId = "
								+ bankAccount.getAccountId()).executeUpdate();
	}

	public void deleteBankAccounts(List<BankAccount> bankAccounts) {
		for (BankAccount bankAccount : bankAccounts) {
			deleteBankAccount(bankAccount);
		}
	}
}
