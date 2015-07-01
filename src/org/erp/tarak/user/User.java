package org.erp.tarak.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User_Details")
public class User implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	@Id
	@Column(name="Username")
	private String username;
	@Column(name="Password")
	private String password;
	@Column(name="Category")
	private String category;
	@Column(name="security_Question")
	private String securityQuestion;
	@Column(name="Security_Answer")
	private String securityAnswer;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
	
}
