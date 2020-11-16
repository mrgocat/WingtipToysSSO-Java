package com.wingtip.sso.datalayer.mongodb.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wingtip.sso.dto.UserDto;

@Document(collection="UserHistories")
public class UserHistoryEntity {
	@Id
	private String id;
	private String userId;
	private Date updated;
	private UserDto snapShot;
	private String updateUserId;
	private String updateReason;
	
	private UserEntity user;
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
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
