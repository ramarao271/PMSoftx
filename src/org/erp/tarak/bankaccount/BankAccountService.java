package org.erp.tarak.bankaccount;

import java.util.List;


public interface BankAccountService {
	public void addBankAccount(BankAccount bankAccount);

	public void addBankAccounts(List<BankAccount> bankAccounts);
	
	public List<BankAccount> listBankAccounts();
	
	public BankAccount getBankAccount(int bankAccountId);
	
	public void deleteBankAccount(BankAccount bankAccount);
	
	public void deleteBankAccounts(List<BankAccount> bankAccounts);

}
