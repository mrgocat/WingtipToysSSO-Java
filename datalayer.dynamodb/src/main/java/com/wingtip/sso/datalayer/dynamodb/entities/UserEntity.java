package com.wingtip.sso.datalayer.dynamodb.entities;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Users")
public class UserEntity {
	
	@DynamoDBHashKey(attributeName="Id")
	private String id;
	@DynamoDBAttribute(attributeName="Email")
	private String email;
	@DynamoDBAttribute(attributeName="PasswordHash")
	private String passwordHash;
	@DynamoDBAttribute(attributeName="FirstName")
	private String firstName;
	@DynamoDBAttribute(attributeName="LastName")
	private String lastName;
	@DynamoDBAttribute(attributeName="Telephone")
	private String telephone;
	@DynamoDBAttribute(attributeName="ForceChangePassword")
	private boolean forceChangePassword;
	@DynamoDBAttribute(attributeName="Nationality")
	private String nationality;
	@DynamoDBAttribute(attributeName="DateOfBirth")
	private Date dateOfBirth;
	@DynamoDBAttribute(attributeName="IsUser")
	private boolean isUser;
	@DynamoDBAttribute(attributeName="IsAdmin")
	private boolean isAdmin;
	@DynamoDBAttribute(attributeName="IsActivated")
	private boolean isActivated;
	@DynamoDBAttribute(attributeName="IsLocked")
	private boolean isLocked;
	@DynamoDBAttribute(attributeName="Created")
	private Date created;
	@DynamoDBAttribute(attributeName="LastUpdated")
	private Date lastUpdated;
	@DynamoDBAttribute(attributeName="Roles")
	private String roles;
	
	public String getId() {
		return id;
	}
	public void setId(String id) { 
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public boolean isForceChangePassword() {
		return forceChangePassword;
	}
	public void setForceChangePassword(boolean forceChangePassword) {
		this.forceChangePassword = forceChangePassword;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}	

}
