package com.health.digitalmedical.view.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.tools.IDCard;
import com.health.digitalmedical.view.order.HisOrderActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserUpdateActivity extends BaseActivity
{

	@ViewInject(R.id.real_name)
	private EditText realNameET;

	@ViewInject(R.id.tel)
	private EditText telephoneET;

	@ViewInject(R.id.idcard)
	private EditText idCardET;

	@ViewInject(R.id.check_btn)
	private RadioGroup group;

	@ViewInject(R.id.male)
	private RadioButton maleRadio;

	@ViewInject(R.id.female)
	private RadioButton femaleRadio;
	
	private User user;

	private User userT = new User();
	
	private String sex;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_people_info);
		ViewUtils.inject(this);
		initView();
		initValue();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		this.user = HealthUtil.getUserInfo();
		realNameET.setText(user.getUserName());
		idCardET.setText(user.getUserNo());
		telephoneET.setText(user.getTelephone());
		
		if ("��".equals(user.getSex()))
		{
			maleRadio.setChecked(true);
		} else if ("Ů".equals(user.getSex()))
		{
			femaleRadio.setChecked(true);
		}
	}

	@Override
	protected void initValue()
	{

	}

	@OnClick(R.id.edit_commit)
	public void submit(View v)
	{
		String phoneNum = telephoneET.getText() + "";
		String idNum = idCardET.getText() + "";
		String idCheckRst = IDCard.IDCardValidate(idNum);
		RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
		String userNameT=realNameET.getText() + "";
		if("".equals(userNameT))
		{
			HealthUtil.infoAlert(UserUpdateActivity.this, "�û���Ϊ�գ�");
			return;
		}else if(userNameT.length()>4)
		{
			HealthUtil.infoAlert(UserUpdateActivity.this, "�û���������Ч��");
			return;
		}
		if (!HealthUtil.isMobileNum(phoneNum))
		{
			HealthUtil.infoAlert(UserUpdateActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		} else if (!"YES".equals(idCheckRst))
		{
			HealthUtil.infoAlert(UserUpdateActivity.this, idCheckRst);
			return;
		}

		if (radioButton == null)
		{
			HealthUtil.infoAlert(UserUpdateActivity.this, "�û��Ա�Ϊ��!");
			return;
		} else
		{
			this.sex = radioButton.getText().toString();
		}
		
		this.userT.setUserId(user.getUserId());
		this.userT.setTelephone(telephoneET.getText() + "");
		this.userT.setUserName(userNameT);
		this.userT.setUserNo(idCardET.getText() + "");
		this.userT.setPassword(user.getPassword());
		this.userT.setSex(this.sex);

		Gson gson = new Gson();
		String userStr = gson.toJson(userT);
		dialog.setMessage("������,���Ժ�...");
		dialog.show();
		RequestParams param = webInterface.updateUser(userStr);
		invokeWebServer(param, UPDATE_USER);
	}

	/**
	 * ����web����
	 * 
	 * @param param
	 *            2131493634
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

			HealthUtil.infoAlert(UserUpdateActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
			case UPDATE_USER:
				returnMsg(arg0.result, ADD_USER);
				break;
			}
		}

		/*
		 * �����ؽ������
		 */
		private void returnMsg(String json, int code)
		{
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(json);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String executeType = jsonObject.get("executeType").getAsString();
			if ("success".equals(executeType))
			{
				
				Gson gson = new Gson();
				String userStr = gson.toJson(userT);
				
				HealthUtil.writeUserInfo(userStr);
				HealthUtil.writeUserId(userT.getUserId());
				
				HealthUtil.infoAlert(UserUpdateActivity.this, "�û����ϸ��³ɹ�.");
				finish();
			} else
			{
				HealthUtil.infoAlert(UserUpdateActivity.this, "�û����ϸ���ʧ��������.");
			}

		}
	}
}