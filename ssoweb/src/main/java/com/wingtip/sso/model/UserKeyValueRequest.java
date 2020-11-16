package com.wingtip.sso.model;

import javax.validation.constraints.NotBlank;

public class UserKeyValueRequest {
	
	@NotBlank(message = "key is required")
	private String key;
	@NotBlank(message = "value is required")
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
