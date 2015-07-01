package org.erp.tarak.bankaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("bankAccountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountDao bankAccountDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBankAccount(BankAccount bankAccount) {
		bankAccountDao.addBankAccount(bankAccount);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBankAccounts(List<BankAccount> bankAccounts) {
		bankAccountDao.addBankAccounts(bankAccounts);
	}
	
	public List<BankAccount> listBankAccounts() {
		return bankAccountDao.listBankAccounts();
	}

	public BankAccount getBankAccount(int bankAccountId) {
		return bankAccountDao.getBankAccount(bankAccountId);
	}
	
	public void deleteBankAccount(BankAccount bankAccount) {
		bankAccountDao.deleteBankAccount(bankAccount);
	}
	public void deleteBankAccounts(List<BankAccount> bankAccounts) {
		bankAccountDao.deleteBankAccounts(bankAccounts);
	}

}
