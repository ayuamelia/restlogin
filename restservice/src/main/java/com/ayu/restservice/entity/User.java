package com.ayu.restservice.entity;

public class User {
	private Integer userid;
	private String username;
	
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + "]";
	}
	
	
	
	

}
