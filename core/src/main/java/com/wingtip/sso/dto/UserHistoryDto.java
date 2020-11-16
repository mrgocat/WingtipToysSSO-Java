package com.wingtip.sso.dto;

import java.util.Date;

public class UserHistoryDto {
	private String id;
	private String userId;
	private Date updated;
	private UserDto snapShot;
	private String updateUserId;
	private String updateReason;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public UserDto getSnapShot() {
		return snapShot;
	}
	public void setSnapShot(UserDto snapShot) {
		this.snapShot = snapShot;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
}
