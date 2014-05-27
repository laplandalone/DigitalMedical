package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.tools.IDCard;
import com.health.digitalmedical.view.user.LoginActivity;
import com.health.digitalmedical.view.user.UserUpdateActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HisOrderActivity extends BaseActivity
{
	@ViewInject(R.id.name)
	private EditText editName;
	
	@ViewInject(R.id.phone)
	private EditText editPhone;
	
	@ViewInject(R.id.idcard)
	private EditText editIdCard;
	
	@ViewInject(R.id.check_btn)
	private RadioGroup group;
	
	private String doctorName="0";
	private String registerTime;
	private String fee;
	private String registerId="0";
	private String userOrderNum="0";
	private String doctorId="0";
	private String teamId;
	private String teamName;
	private String userId;
	private String userName;
	private String userNo;
	private String userTelephone;
	private String sex;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_info_config);
		ViewUtils.inject(this);
		initValue();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void initValue()
	{
		this.user=HealthUtil.getUserInfo();
		if(this.user==null)
		{
			Intent intent = new Intent(HisOrderActivity.this,LoginActivity.class);
			startActivityForResult(intent,0);
		}
		// TODO Auto-generated method stub
		this.doctorName = getIntent().getStringExtra("doctorName");
		this.registerTime = getIntent().getStringExtra("registerTime");
		this.fee = getIntent().getStringExtra("fee");
		this.registerId = getIntent().getStringExtra("registerId");
		this.userOrderNum = getIntent().getStringExtra("userOrderNum");
		this.doctorId = getIntent().getStringExtra("doctorId");
		this.teamId = getIntent().getStringExtra("teamId");
		this.teamName = getIntent().getStringExtra("teamName");
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(HisOrderActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	
	@OnClick(R.id.submit)
	public void submit(View v)
	{
		this.userId=user.getUserId();
		this.userName=editName.getText().toString().trim();
		this.userNo=editIdCard.getText().toString().trim();
		this.userTelephone=editPhone.getText().toString().trim();
		RadioButton radioButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
		
		String idCheckRst = IDCard.IDCardValidate(userNo);
		if("".equals(userName))
		{
			HealthUtil.infoAlert(HisOrderActivity.this, "�û���Ϊ��!");
		}
		if (!HealthUtil.isMobileNum(userTelephone))
		{
			HealthUtil.infoAlert(HisOrderActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		}
		if (!"YES".equals(idCheckRst))
		{
			HealthUtil.infoAlert(HisOrderActivity.this, idCheckRst);
			return;
		}
		if(radioButton==null)
		{
			HealthUtil.infoAlert(HisOrderActivity.this, "�û��Ա�Ϊ��!");
			return;
		}else
		{
			this.sex=radioButton.getText().toString();
		}
		dialog.setMessage("����ԤԼ,���Ժ�...");
		dialog.show();
		RequestParams param = webInterface.addUserRegisterOrder(userId, registerId, doctorId, doctorName, userOrderNum, fee, registerTime, userName, userNo, userTelephone,sex, teamId, teamName);
		invokeWebServer(param,ADD_REGISTER_ORDER);
	}
	/**
	 * ����web����
	 * 
	 * @param param
	 */
	private void invokeWebServer(RequestParams param, int responseCode)
	{
		HealthUtil.LOG_D(getClass(), "connect to web server");
		MineRequestCallBack requestCallBack = new MineRequestCallBack(responseCode);
		if (httpHandler != null)
		{
			httpHandler.stop();
		}
		httpHandler = mHttpUtils.send(HttpMethod.POST, HealthConstant.URL, param, requestCallBack);
	}

	/**
	 * ��ȡ��̨���ص�����
	 */
	class MineRequestCallBack extends RequestCallBack<String>
	{

		private int responseCode;

		public MineRequestCallBack(int responseCode)
		{
			super();
			this.responseCode = responseCode;
		}

		@Override
		public void onFailure(HttpException error, String msg)
		{
			HealthUtil.LOG_D(getClass(), "onFailure-->msg=" + msg);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}
			
			HealthUtil.infoAlert(HisOrderActivity.this, "��Ϣ����ʧ�ܣ��������������");
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0)
		{
			// TODO Auto-generated method stub
			HealthUtil.LOG_D(getClass(), "result=" + arg0.result);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}
			switch (responseCode)
			{
			case GET_LIST:
				returnMsg(arg0.result, GET_LIST);
				break;
			case GET_LIST_MORE:
				returnMsg(arg0.result, GET_LIST_MORE);
				break;
			case ADD_REGISTER_ORDER:
				returnMsg(arg0.result, ADD_REGISTER_ORDER);
				break;
			}
		}

	}

	/*
	 * �����ؽ������
	 */
	private void returnMsg(String json, int responseCode)
	{
		try
		{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		switch (responseCode)
		{
		case GET_LIST:
			
			
			break;
		case ADD_REGISTER_ORDER:
			String result = jsonObject.get("returnMsg").toString();
			if("true".equals(result))
			{
				HealthUtil.infoAlert(HisOrderActivity.this, "ԤԼ�ɹ�...");
				Intent intent = new Intent(HisOrderActivity.this,ConfirmOrderActivity.class);
				intent.putExtra("doctorName", doctorName   ); 
				intent.putExtra("registerTime", registerTime ); 
				intent.putExtra("fee", fee          ); 
				intent.putExtra("userOrderNum", userOrderNum ); 
				intent.putExtra("teamName",  teamName    ); 
				intent.putExtra("userName", userName     ); 
				intent.putExtra("userNo",userNo        ); 
				intent.putExtra("userTelephone",userTelephone ); 
				intent.putExtra("sex", sex);
				startActivity(intent);
				finish();
			}else
			{
				HealthUtil.infoAlert(HisOrderActivity.this, "ԤԼʧ�ܣ�������...");
			}
			break;
		}
		}catch(Exception e)
		{
			HealthUtil.infoAlert(HisOrderActivity.this, "ԤԼʧ�ܣ�������...");
		}
		
	}

}
