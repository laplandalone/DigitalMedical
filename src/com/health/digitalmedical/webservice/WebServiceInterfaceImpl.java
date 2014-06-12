package com.health.digitalmedical.webservice;

import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.http.RequestParams;


public class WebServiceInterfaceImpl implements IWebServiceInterface{

	@Override
	public RequestParams queryDoctorList(String expertType, String onLineType, String teamId)
	{
		return HealthUtil.getRequestParams("BUS2001", new String[]{"expertType","onLineType","teamId"},new Object[]{"1","1",teamId});
	}

	@Override
	public RequestParams queryTeamList(String hospitalId,String expertType)
	{
		return HealthUtil.getRequestParams("BUS2002", new String[]{"hospitalId","expertType"},new Object[]{hospitalId,expertType});
	}

	@Override
	public RequestParams queryOrderDoctorList(String teamId)
	{
		return HealthUtil.getRequestParams("BUS2003", new String[]{"teamId"},new Object[]{teamId});
	}

	@Override
	public RequestParams queryOrderByDoctorIdList(String orderId)
	{
		return HealthUtil.getRequestParams("BUS2004", new String[]{"orderId"},new Object[]{orderId});
	}

	@Override
	public RequestParams queryUser(String telephone, String password)
	{
		return HealthUtil.getRequestParams("BUS2005", new String[]{"telephone","password"},new Object[]{telephone,password});
	}

	@Override
	public RequestParams addUserRegisterOrder( String userId, String registerId, String doctorId, String doctorName, String orderNum,
			String orderFee, String registerTime, String userName, String userNo, String userTelephone,String sex, String teamId,
			String teamName)
	{
		return HealthUtil.getRequestParams("BUS2006", new String[]{ "userId", "registerId", "doctorId", "doctorName", "orderNum",  "orderFee", "registerTime", "userName", "userNo", "userTelephone","sex", "teamId", "teamName" },new Object[]{  userId       , registerId   , doctorId     , doctorName   , orderNum     ,  orderFee     , registerTime , userName     , userNo       , userTelephone, sex,teamId       , teamName});
	}

	@Override
	public RequestParams addUserQuestion(String userQuestion)
	{
		return HealthUtil.getRequestParams("BUS2007", new String[]{"userQestion"},new Object[]{userQuestion});
	}

	@Override
	public RequestParams getUserQuestionsByDoctorId(String doctorId)
	{
		return HealthUtil.getRequestParams("BUS2008", new String[]{"doctorId"},new Object[]{doctorId});
	}

	@Override
	public RequestParams getUserHospitalBranchs(String hospitalId)
	{
		return HealthUtil.getRequestParams("BUS20010", new String[]{"hospitalId"},new Object[]{hospitalId});
	}

	@Override
	public RequestParams addUser(String user)
	{
		return HealthUtil.getRequestParams("BUS20011", new String[]{"user"},new Object[]{user});
	}

	@Override
	public RequestParams updateUser(String user)
	{
		return HealthUtil.getRequestParams("BUS20012", new String[]{"user"},new Object[]{user});
	}

	@Override
	public RequestParams getOrderNormalNum(String teamId, String registerTime)
	{
		return HealthUtil.getRequestParams("BUS20013", new String[]{"teamId","registerTime"},new Object[]{teamId,registerTime});
	}

	@Override
	public RequestParams getUserOrderById(String userId)
	{
		// TODO Auto-generated method stub
		return HealthUtil.getRequestParams("BUS20014", new String[]{"userId"},new Object[]{userId});
	}

	@Override
	public RequestParams getUserQuestionsByUserId(String userId)
	{
		return HealthUtil.getRequestParams("BUS20015", new String[]{"userId"},new Object[]{userId});
	}

	@Override
	public RequestParams getUserQuestionsByIds(String userId, String doctorId)
	{
		return HealthUtil.getRequestParams("BUS20016", new String[]{"userId","doctorId"},new Object[]{userId,doctorId});
	}

	@Override
	public RequestParams getTeamByHospitalId(String hospitalId)
	{
		return HealthUtil.getRequestParams("BUS20017", new String[]{"hospitalId"},new Object[]{hospitalId});
	}
	
	@Override
	public RequestParams getNewsByHospitalId(String hospitalId,String type,String typeId)
	{
		return HealthUtil.getRequestParams("BUS20018", new String[]{"hospitalId","type","typeId"},new Object[]{hospitalId,type,typeId});
	}

	@Override
	public RequestParams checkNewVersion(String param) {
		return HealthUtil.getRequestParams("BUS20019", new String[] {
				"param"}, new Object[] {param});
	}

	@Override
	public RequestParams getNewsType(String hospitalId, String type)
	{
		return HealthUtil.getRequestParams("BUS20020", new String[]{"hospitalId","type"},new Object[]{hospitalId,type});
	}

}