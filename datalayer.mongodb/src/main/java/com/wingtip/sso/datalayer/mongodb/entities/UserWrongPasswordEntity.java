package com.wingtip.sso.datalayer.mongodb.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UserWrongPassword")
public class UserWrongPasswordEntity {
	@Id
	private String id;
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
	private int fialedCount;
}
