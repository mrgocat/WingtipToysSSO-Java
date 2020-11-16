package com.wingtip.sso.dto;

public class UserWrongPasswordDto {
	private String id;
	private int fialedCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getFialedCount() {
		return fialedCount;
	}
	public void setFialedCount(int fialedCount) {
		this.fialedCount = fialedCount;
	}
}
