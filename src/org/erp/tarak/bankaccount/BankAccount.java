package org.erp.tarak.bankaccount;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Bank_account")
public class BankAccount implements Serializable {
	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@SequenceGenerator(name="my_seq", sequenceName="bankAccountSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	@Column(name = "Accont_Id")
	private long accountId;
	@Column(name = "Accont_Number")
	private long accountNumber;
	@Column(name = "Accont_Holder_Name")
	private String accountHolderName;
	@Column(name = "Bank_IFSC_Code")
	private String bankIfscCode;
	@Column(name = "Bank_Name")
	private String bankName;
	@Column(name = "Bank_Branch")
	private String bankBranch;
	@Column(name = "Bank_Address")
	private String bankAddress;
	/*@ManyToOne
	@JoinColumn(name="Customer_Id")
	private Customer customer;*/

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankIfscCode() {
		return bankIfscCode;
	}

	public void setBankIfscCode(String bankIfscCode) {
		this.bankIfscCode = bankIfscCode;
	}

	/*public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}*/

}
