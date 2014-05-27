package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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

public class LoginActivity extends BaseActivity
{
	@ViewInject(R.id.sign_in)
	private ImageButton loginBtn;
	private Boolean closeFlag = false;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sign_in);
		ViewUtils.inject(this);
		
	}
	@OnClick(R.id.registration)
	public void userRegister(View v)
	{
		Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
		finish();
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(LoginActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

	@OnClick(R.id.sign_in)
	public void userLogin(View v)
	{
		  dialog.setMessage("登录中,请稍后..."); dialog.show();
		  RequestParams param = webInterface.queryUser("18907181648", "123");
		  invokeWebServer(param, USER_LOGIN);
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

			HealthUtil.infoAlert(LoginActivity.this, "信息加载失败，请检查网络后重试");
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
			case USER_LOGIN:
				returnMsg(arg0.result, USER_LOGIN);
				break;
			}
		}

	}

	/*
	 * 处理返回结果数据
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
			case USER_LOGIN:
				JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
				this.user = HealthUtil.json2Object(returnObj.toString(), User.class);
				if (this.user != null)
				{
					HealthUtil.writeUserInfo(returnObj.toString());
					User user =HealthUtil.getUserInfo();
					HealthUtil.writeUserId(user.getUserId());
					HealthUtil.infoAlert(LoginActivity.this, "登录成功");
					this.setResult(RESULT_OK, getIntent());
					finish();
				}
				break;

			}
		} catch (Exception e)
		{
			HealthUtil.infoAlert(LoginActivity.this, "登录失败，请重试...");
		}

	}


}
