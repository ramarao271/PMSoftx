package org.erp.tarak.contactperson;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ContactPerson")
public class ContactPerson implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="contactPersonSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long contactPersonId;
	private String contactPersonName;
	private long mobileNumber;
	private String emailId;
	private String description;

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public long getContactPersonId() {
		return contactPersonId;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContactPersonId(long contactPersonId) {
		this.contactPersonId = contactPersonId;
	}
}
