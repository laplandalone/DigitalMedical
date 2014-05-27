package com.health.digitalmedical.model;

import java.util.Date;

/**
 * HospitalT entity. @author MyEclipse Persistence Tools
 */

public class HospitalT implements java.io.Serializable {

	// Fields

	private String hospitalId;
	private String parentId;
	private String name;
	private String address;
	private String busRoute;
	private String wwwUrl;
	private String phoneNum;
	private String intorduce;
	private String featureTeam;
	private String state;
	private String remark;
	private String imageUrl;
	private Date createDate;
	private String x;
	private String y;

	// Constructors

	/** default constructor */
	public HospitalT() {
	}

	/** minimal constructor */
	public HospitalT(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/** full constructor */
	public HospitalT(String hospitalId, String parentId, String name,
			String address, String busRoute, String wwwUrl, String phoneNum,
			String intorduce, String featureTeam, String state, String remark,
			String imageUrl, Date createDate, String x, String y) {
		this.hospitalId = hospitalId;
		this.parentId = parentId;
		this.name = name;
		this.address = address;
		this.busRoute = busRoute;
		this.wwwUrl = wwwUrl;
		this.phoneNum = phoneNum;
		this.intorduce = intorduce;
		this.featureTeam = featureTeam;
		this.state = state;
		this.remark = remark;
		this.imageUrl = imageUrl;
		this.createDate = createDate;
		this.x = x;
		this.y = y;
	}

	// Property accessors

	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusRoute() {
		return this.busRoute;
	}

	public void setBusRoute(String busRoute) {
		this.busRoute = busRoute;
	}

	public String getWwwUrl() {
		return this.wwwUrl;
	}

	public void setWwwUrl(String wwwUrl) {
		this.wwwUrl = wwwUrl;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIntorduce() {
		return this.intorduce;
	}

	public void setIntorduce(String intorduce) {
		this.intorduce = intorduce;
	}

	public String getFeatureTeam() {
		return this.featureTeam;
	}

	public void setFeatureTeam(String featureTeam) {
		this.featureTeam = featureTeam;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return this.y;
	}

	public void setY(String y) {
		this.y = y;
	}

}