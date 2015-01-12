package com.orbit.dynamix.vo;

import java.util.Date;

import com.orbit.dynamix.utils.Utils;

public class UserVO implements java.io.Serializable {

	private Integer userId;

	private String contactName;

	private String emailAddress;

	private String username;

	private String password;
	
	private String newPassword;
	
	private String passwordConfirm;

	private String language;

	private Integer profileId;
	
	private String profileName;

	private boolean active;

	private String dateCreated;

	private String dateLastLogin;

	public UserVO() {
	}

	public UserVO(String contactName, String emailAddress, boolean active) {
		this.contactName = contactName;
		this.emailAddress = emailAddress;
		this.active = active;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = Utils.getString(dateCreated);
	}

	public String getDateLastLogin() {
		return this.dateLastLogin;
	}

	public void setDateLastLogin(Date dateLastLogin) {
		this.dateLastLogin = Utils.getString(dateLastLogin);
	}
	
	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public Integer getProfileId() {
		return profileId;
	}
	
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

}
