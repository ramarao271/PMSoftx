package org.erp.tarak.contactperson;

public class ContactPersonBean {
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

	public void setContactPersonId(long contactPersonId) {
		this.contactPersonId = contactPersonId;
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
}
