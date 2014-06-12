package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
			
		}else if(password.equals(confirmPd))
		{
			User user = new User();
			user.setTelephone(this.telephone);
			user.setUserName(this.telephone);
			user.setPassword(password);
			user.setUserNo(this.telephone);
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
		title.setText("输入密码");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		this.telephone=getIntent().getStringExtra("telephone");
	}





/**
 * 链接web服务
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
 * 获取后台返回的数据
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
		
		HealthUtil.infoAlert(RegisterNextActivity.this, "信息加载失败，请检查网络后重试");
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
		}
	}

	/*
	 * 处理返回结果数据
	 */
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//JsonArray jsonArray = jsonObject.getAsJsonArray("returnMsg");
		//Gson gson = new Gson();  
		
	}
}

}
