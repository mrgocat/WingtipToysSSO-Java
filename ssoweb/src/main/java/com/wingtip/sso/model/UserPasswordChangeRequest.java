package com.wingtip.sso.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserPasswordChangeRequest {
	@NotBlank(message = "oldPassword is required")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}", message="oldPassword is not valid.")
	private String oldPassword;
	
	@NotBlank(message = "newPassword is required")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}", message="newPassword is not valid.")
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
