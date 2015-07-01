package org.erp.tarak.bankaccount;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BankAccountUtilities {

	public static List<BankAccount> saveBankAccounts(
			BankAccountService bankAccountService,
			List<BankAccount> bankAccounts) {
		bankAccountService.addBankAccounts(bankAccounts);
		return bankAccounts;
	}

	public static List<BankAccount> populateBankAccounts(
			List<BankAccountBean> bankAccountBeans) {
		List<BankAccount> bankAccounts = new LinkedList<BankAccount>();
		if (bankAccountBeans != null) {
			for (BankAccountBean bankAccountBean : bankAccountBeans) {
				if (bankAccountBean.getAccountNumber() != 0
						&& !"".equals(bankAccountBean.getAccountNumber())) {
					BankAccount bankAccount = new BankAccount();
					bankAccount.setAccountHolderName(bankAccountBean
							.getAccountHolderName());
					bankAccount.setAccountNumber(bankAccountBean
							.getAccountNumber());
					bankAccount
							.setBankAddress(bankAccountBean.getBankAddress());
					bankAccount.setBankBranch(bankAccountBean.getBankBranch());
					bankAccount.setBankIfscCode(bankAccountBean
							.getBankIfscCode());
					bankAccount.setBankName(bankAccountBean.getBankName());
					bankAccount.setAccountId(bankAccountBean.getAccountId());
					bankAccounts.add(bankAccount);
				}
			}
		}
		return bankAccounts;
	}

	public static List<BankAccountBean> prepareBankAccountBean(
			List<BankAccount> bankAccounts) {
		List<BankAccountBean> bankAccountBeans = new LinkedList<BankAccountBean>();
		Iterator<BankAccount> bankAccountIterator = bankAccounts.iterator();
		while (bankAccountIterator.hasNext()) {
			BankAccount bankAccount = bankAccountIterator.next();
			BankAccountBean bankAccountBean = new BankAccountBean();
			bankAccountBean.setAccountHolderName(bankAccount
					.getAccountHolderName());
			bankAccountBean.setAccountId(bankAccount.getAccountId());
			bankAccountBean.setAccountNumber(bankAccount.getAccountNumber());
			bankAccountBean.setBankAddress(bankAccount.getBankAddress());
			bankAccountBean.setBankBranch(bankAccount.getBankBranch());
			bankAccountBean.setBankIfscCode(bankAccount.getBankIfscCode());
			bankAccountBean.setBankName(bankAccount.getBankName());
			bankAccountBean.setAccountId(bankAccount.getAccountId());
			bankAccountBeans.add(bankAccountBean);
		}
		return bankAccountBeans;
	}

}
