package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
	@ViewInject(R.id.title)
	private TextView title;
	
	@ViewInject(R.id.sign_in)
	private ImageButton loginBtn;
	@ViewInject(R.id.rember_psw)
	private ImageButton remberPsw;
	@ViewInject(R.id.login_auto)
	private ImageButton loginAuto;

	@ViewInject(R.id.userName)
	private EditText userName;

	@ViewInject(R.id.password)
	private EditText password;

	private Boolean closeFlag = false;
	private User user;
	private boolean remberPswFlag = false;
	private boolean loginAutoFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sign_in);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.rember_psw)
	public void remberPsw(View v)
	{
		if (remberPswFlag)
		{
			HealthUtil.writeUserPassword("");
			remberPsw.setBackgroundResource(R.drawable.symptom_select_false);
			remberPswFlag = false;
		} else
		{
			HealthUtil.writeUserPhone(userName.getText().toString().trim());
			HealthUtil.writeUserPassword(password.getText().toString().trim());
			remberPsw.setBackgroundResource(R.drawable.symptom_select_true);
			remberPswFlag = true;
		}

	}

	@OnClick(R.id.login_auto)
	public void loginAuto(View v)
	{
		if (loginAutoFlag)
		{
			HealthUtil.writeLoginAuto("");
			loginAuto.setBackgroundResource(R.drawable.symptom_select_false);
			loginAutoFlag = false;
		} else
		{
			HealthUtil.writeLoginAuto("auto");
			loginAuto.setBackgroundResource(R.drawable.symptom_select_true);
			loginAutoFlag = true;
		}
	}

	@OnClick(R.id.registration)
	public void userRegister(View v)
	{
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("用户登录");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		if (!"".equals(HealthUtil.readUserPhone()) && !"".equals(HealthUtil.readUserPassword()))
		{
			this.userName.setText(HealthUtil.readUserPhone());
			this.password.setText(HealthUtil.readUserPassword());
			this.remberPsw.setBackgroundResource(R.drawable.symptom_select_true);
			this.remberPswFlag = true;
		}

		String login=HealthUtil.readLoginAuto();
		if("auto".equals(login))
		{
			this.loginAuto.setBackgroundResource(R.drawable.symptom_select_true);
			Login();
		}
	}

	@OnClick(R.id.sign_in)
	public void userLogin(View v)
	{
		Login();
	}

	public void Login()
	{
		String userNameT = userName.getText().toString().trim();
		String passwordT = password.getText().toString().trim();
		if ("".equals(userNameT))
		{
			HealthUtil.infoAlert(LoginActivity.this, "账号为空");
			return;
		}
		if ("".equals(passwordT))
		{
			HealthUtil.infoAlert(LoginActivity.this, "密码为空");
			return;
		}
		dialog.setMessage("登录中,请稍后...");
		dialog.show();
		if (remberPswFlag)
		{
			HealthUtil.writeUserPhone(userNameT);
			HealthUtil.writeUserPassword(passwordT);
		} else
		{
			HealthUtil.writeUserPhone("");
			HealthUtil.writeUserPassword("");
		}

		RequestParams param = webInterface.queryUser(userNameT, passwordT);
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
					User user = HealthUtil.getUserInfo();
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
