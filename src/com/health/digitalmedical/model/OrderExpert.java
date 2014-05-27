package com.health.digitalmedical.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class OrderExpert implements Serializable
{
	@Expose
	private String doctorName;
	@Expose
	private String teamName;
	@Expose
	private String doctorId;
	@Expose
	private String teamId;
	@Expose
	private String day;
	@Expose
	private String introduce;
	@Expose
	private String registerNum;
	@Expose
	private String fee;
	@Expose
	private String workTime;
	@Expose
	private String post;
	@Expose
	String userOrderNum;
	public String getUserOrderNum()
	{
		return userOrderNum;
	}
	public void setUserOrderNum(String userOrderNum)
	{
		this.userOrderNum = userOrderNum;
	}
	public String getRegisterId()
	{
		return registerId;
	}
	public void setRegisterId(String registerId)
	{
		this.registerId = registerId;
	}
	@Expose
	String registerId;
	
	public String getPost()
	{
		return post;
	}
	public void setPost(String post)
	{
		this.post = post;
	}
	public String getDoctorName()
	{
		return doctorName;
	}
	public void setDoctorName(String doctorName)
	{
		this.doctorName = doctorName;
	}
	public String getTeamName()
	{
		return teamName;
	}
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	public String getDoctorId()
	{
		return doctorId;
	}
	public void setDoctorId(String doctorId)
	{
		this.doctorId = doctorId;
	}
	public String getTeamId()
	{
		return teamId;
	}
	public void setTeamId(String teamId)
	{
		this.teamId = teamId;
	}
	public String getDay()
	{
		return day;
	}
	public void setDay(String day)
	{
		this.day = day;
	}
	public String getIntroduce()
	{
		return introduce;
	}
	public void setIntroduce(String introduce)
	{
		this.introduce = introduce;
	}
	public String getRegisterNum()
	{
		return registerNum;
	}
	public void setRegisterNum(String registerNum)
	{
		this.registerNum = registerNum;
	}
	public String getFee()
	{
		return fee;
	}
	public void setFee(String fee)
	{
		this.fee = fee;
	}
	public String getWorkTime()
	{
		return workTime;
	}
	public void setWorkTime(String workTime)
	{
		this.workTime = workTime;
	}

	
}
