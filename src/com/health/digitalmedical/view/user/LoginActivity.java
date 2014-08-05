package com.health.digitalmedical.view.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
	
	@ViewInject(R.id.password_find)
	private TextView pswFind;
	
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
	
	@OnClick(R.id.password_find)
	public void pswFind(View v)
	{
		String telephone=userName.getText().toString().trim();
		if (!HealthUtil.isMobileNum(telephone))
		{
			HealthUtil.infoAlert(LoginActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		}
		dialog.setMessage("����������,���Ժ�...");
		dialog.show();
		RequestParams param = webInterface.getAuthCode(telephone,"set_psw");
		invokeWebServer(param, SET_PSW);
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
		title.setText("�û���¼");
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
		String telephone = userName.getText().toString().trim();
		String passwordT = password.getText().toString().trim();
		if (!HealthUtil.isMobileNum(telephone))
		{
			HealthUtil.infoAlert(LoginActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		}
		
		if ("".equals(passwordT))
		{
			HealthUtil.infoAlert(LoginActivity.this, "����Ϊ��");
			return;
		}
		dialog.setMessage("��¼��,���Ժ�...");
		dialog.show();
		if (remberPswFlag)
		{
			HealthUtil.writeUserPhone(telephone);
			HealthUtil.writeUserPassword(passwordT);
		} else
		{
			HealthUtil.writeUserPhone("");
			HealthUtil.writeUserPassword("");
		}

		RequestParams param = webInterface.queryUser(telephone, passwordT);
		invokeWebServer(param, USER_LOGIN);
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

			HealthUtil.infoAlert(LoginActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
			case SET_PSW:
				returnMsg(arg0.result, SET_PSW);
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
			    case USER_LOGIN:
			    JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
				this.user = HealthUtil.json2Object(returnObj.toString(), User.class);
				if (this.user != null)
				{
					HealthUtil.writeUserInfo(returnObj.toString());
					User user = HealthUtil.getUserInfo();
					HealthUtil.writeUserId(user.getUserId());
					HealthUtil.infoAlert(LoginActivity.this, "��¼�ɹ�");
					this.setResult(RESULT_OK, getIntent());
					finish();
					break;
				}
			    case SET_PSW:
			    	String returnObjT = jsonObject.get("returnMsg").getAsString();
					JsonElement jsonElementT = jsonParser.parse(returnObjT);
					JsonObject jsonObjectT = jsonElementT.getAsJsonObject();
					String status=jsonObjectT.get("status").getAsString();
					if(!"100".equals(status))
					{
						HealthUtil.infoAlert(LoginActivity.this, "��������ʧ�ܣ�������...");
					}else
					{
						showSuccessDialog();
					}
				break;

			}
		} catch (Exception e)
		{
			HealthUtil.infoAlert(LoginActivity.this, "����ʧ�ܣ�������...");
		}

	}

	private void showSuccessDialog()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setPositiveButton("ȷ��", new OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				}).setTitle("��ʾ").setMessage("���������������").create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}
}
