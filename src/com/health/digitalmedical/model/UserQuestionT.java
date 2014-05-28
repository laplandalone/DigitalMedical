package com.health.digitalmedical.model;

import java.util.Date;

/**
 * UserQuestionT entity. @author MyEclipse Persistence Tools
 */

public class UserQuestionT implements java.io.Serializable {

	// Fields

	private String qestionId;
	private String userId;
	private String doctorId;
	private String userTelephone;
	private String recordType;
	private String authType;
	private String content;
	private String state;
	private String createDate;

	// Constructors

	/** default constructor */
	public UserQuestionT() {
	}

	/** minimal constructor */
	public UserQuestionT(String qestionId) {
		this.qestionId = qestionId;
	}

	/** full constructor */
	public UserQuestionT(String qestionId, String userId, String doctorId,
			String userTelephone, String recordType, String authType,
			String content, String state, String createDate) {
		this.qestionId = qestionId;
		this.userId = userId;
		this.doctorId = doctorId;
		this.userTelephone = userTelephone;
		this.recordType = recordType;
		this.authType = authType;
		this.content = content;
		this.state = state;
		this.createDate = createDate;
	}

	// Property accessors

	public String getQestionId() {
		return this.qestionId;
	}

	public void setQestionId(String qestionId) {
		this.qestionId = qestionId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getUserTelephone() {
		return this.userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getRecordType() {
		return this.recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}