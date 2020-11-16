package com.wingtip.sso.model;

import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
	@NotBlank(message = "userId is required")
	private String userId;
	@NotBlank(message = "password is required")
	private String password;
	private String loginIP;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
}
