package data;

import java.io.Serializable;

public class UserDetailBean implements Serializable{
	
	private String FirstName;
	private String LastName;
	private String EmailId;
	
	public UserDetailBean() {
		this.EmailId="";
		this.FirstName="";
		this.LastName="";
	}
	
	public UserDetailBean(String emailId, String firstName, String lastName) {
		this.EmailId=emailId;
		this.FirstName=firstName;
		this.LastName=lastName;
	}
	
	public void setFirstname(String name) {
		this.FirstName=name;
	}
	
	public void setLastName(String name) {
		this.LastName=name;
	}
	
	public void setEmailId(String email) {
		this.EmailId=email;
	}
	
	public String getEmailId() {
		return this.EmailId;
	}
	
	public String getLastName() {
		return this.LastName;
	}
	
	public String getFirstName() {
		return this.FirstName;
	}
		
}
