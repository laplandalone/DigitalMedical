package com.health.digitalmedical.model;

import java.util.Date;

/**
 * HospitalNewsT entity. @author MyEclipse Persistence Tools
 */

public class HospitalNewsT implements java.io.Serializable {

	// Fields

	private String newsId;
	private String hospitalId;
	private String newsTitle;
	private String newsContent;
	private String newsImages;
	private String state;
	private String createDate;

	// Constructors

	/** default constructor */
	public HospitalNewsT() {
	}

	/** minimal constructor */
	public HospitalNewsT(String newsId, String hospitalId, String newsTitle,
			String newsContent) {
		this.newsId = newsId;
		this.hospitalId = hospitalId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
	}

	/** full constructor */
	public HospitalNewsT(String newsId, String hospitalId, String newsTitle,
			String newsContent, String newsImages, String state, String createDate) {
		this.newsId = newsId;
		this.hospitalId = hospitalId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsImages = newsImages;
		this.state = state;
		this.createDate = createDate;
	}

	// Property accessors

	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getNewsTitle() {
		return this.newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return this.newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsImages() {
		return this.newsImages;
	}

	public void setNewsImages(String newsImages) {
		this.newsImages = newsImages;
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