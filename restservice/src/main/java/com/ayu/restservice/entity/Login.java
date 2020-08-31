package com.ayu.restservice.entity;

public class Login {
	private String ApplicationID = "LAKU";
	private String UserID;
	private String Password;
	
	
	public String getApplicationID() {
		return ApplicationID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	@Override
	public String toString() {
		return "User [ApplicationID=" + ApplicationID + ", UserID=" + UserID + ", Password="
				+ Password + "]";
	}
	
	

}
