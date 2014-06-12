package com.health.digitalmedical.webservice;

import com.lidroid.xutils.http.RequestParams;



public interface IWebServiceInterface {
	
	public RequestParams queryDoctorList(String expertType,String onLineType,String teamId);
	
	public RequestParams queryTeamList(String hospitalId,String expertType);
	
	public RequestParams queryOrderDoctorList(String teamId);
	
	public RequestParams getOrderNormalNum(String teamId,String registerTime);
	
	public RequestParams queryOrderByDoctorIdList(String doctorId);
	
	public RequestParams queryUser(String telephone,String password);
	
	public RequestParams addUserQuestion(String userQuestion);
	
	public RequestParams addUser(String user);
	
	public RequestParams updateUser(String user);
	
	public RequestParams getUserQuestionsByDoctorId(String doctorId);
	
	public RequestParams getUserQuestionsByIds(String userId,String doctorId);
	
	public RequestParams getUserQuestionsByUserId(String userId);
	
	public RequestParams getUserHospitalBranchs(String hospitalId);
	
	public RequestParams getUserOrderById(String userId);
	
	public RequestParams getTeamByHospitalId(String hospitalId);
	
	public RequestParams getNewsByHospitalId(String hospitalId,String type,String typeId);
	
	public RequestParams addUserRegisterOrder( String userId, String registerId, String doctorId, String doctorName, String orderNum, String orderFee, String registerTime, String userName, String userNo, String userTelephone,String sex, String teamId, String teamName);
	
	public RequestParams checkNewVersion(String param);
	
	public RequestParams getNewsType(String hospitalId,String type);
}
