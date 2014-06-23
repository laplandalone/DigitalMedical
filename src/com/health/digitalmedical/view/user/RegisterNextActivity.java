package com.health.digitalmedical.view.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegisterNextActivity extends BaseActivity
{
	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.new_password)
	private EditText passwordET;
	
	@ViewInject(R.id.confirm_password)
	private EditText confirmPasswordET;
	
	private String telephone;
	
	private User user ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sign_up_next);
		ViewUtils.inject(this);
		addActivity(this);
		 initValue();
		 initView();
	}

	@OnClick(R.id.registered)
	public void toRegister(View v)
	{
		String password=passwordET.getText()+"";
		String confirmPd=confirmPasswordET.getText()+"";
		if("".equals(password) || "".equals(confirmPd))
		{
			HealthUtil.infoAlert(RegisterNextActivity.this, "����Ϊ�գ�������");
			
		}else if( password.length()<6 || password.length()>12)
		{
			HealthUtil.infoAlert(RegisterNextActivity.this, "���볤��������������");
			
		}else if(!password.equals(confirmPd))
		{
			HealthUtil.infoAlert(RegisterNextActivity.this, "���벻һ�£�����������");
			
		}else if(password.equals(confirmPd))
		{
			dialog.setMessage("��¼��,���Ժ�...");
			dialog.show();
			user = new User();
			user.setTelephone(this.telephone);
			user.setPassword(password);
			Gson gson = new Gson();
			String userStr=gson.toJson(user);
			RequestParams param = webInterface.addUser(userStr);
			invokeWebServer(param, ADD_USER);
		}
	}
	
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(RegisterNextActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("��������");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		this.telephone=getIntent().getStringExtra("telephone");
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
		
		HealthUtil.infoAlert(RegisterNextActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
		case ADD_USER:
			returnMsg(arg0.result, ADD_USER);
			break;
		case USER_LOGIN:
			returnMsg(arg0.result, USER_LOGIN);
			break;
		}
	}

	/*
	 * �����ؽ������
	 */
	private void returnMsg(String json, int code)
	{
		
		JSONObject jsonObject;
		try
		{
			jsonObject = new JSONObject(json);
			String executeType = jsonObject.get("executeType").toString();
			if (!"success".equals(executeType))
			{
				HealthUtil.infoAlert(RegisterNextActivity.this, "ע��ʧ�ܣ�������");
			
			}else
			{
				String returnMsg = jsonObject.get("returnMsg").toString();
				if(returnMsg.equals("1"))
				{
					HealthUtil.infoAlert(RegisterNextActivity.this, "���ֻ�����ע��");
				}else
				{
					HealthUtil.infoAlert(RegisterNextActivity.this, "ע��ɹ�");
					Intent intent = new Intent(RegisterNextActivity.this,LoginActivity.class);
					startActivity(intent);
					exit();
				}
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
