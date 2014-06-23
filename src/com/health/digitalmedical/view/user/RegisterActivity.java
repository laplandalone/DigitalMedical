package com.health.digitalmedical.view.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegisterActivity extends BaseActivity
{
	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.user_name)
	private EditText userNameET;

	@ViewInject(R.id.confirm_password)
	private EditText confirmNum;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sign_up);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
	}

	@OnClick(R.id.get_config_num)
	public void getConfiNum(View v)
	{
		String telephone = userNameET.getText() + "";
		// TelephonyManager tm = (TelephonyManager)
		// this.getSystemService(Context.TELEPHONY_SERVICE);
		// String tel = tm.getLine1Number();
		if (!HealthUtil.isMobileNum(telephone))
		{
			HealthUtil.infoAlert(RegisterActivity.this, "手机号码为空或格式错误!");
			return;
		}
		// else if(!tel.equals(telephone.trim()))
		// {
		// HealthUtil.infoAlert(RegisterActivity.this, "请用本人手机注册");
		// }
		else
		{
			String num = String.valueOf((int) (Math.random() * 9999));
			confirmNum.setText(num);
			showSuccessDialog(num);
		}
	}

	@OnClick(R.id.sign_up)
	public void toNext(View v)
	{
		String telephone = userNameET.getText() + "";
		if (telephone != null || !"".equals(telephone))
		{
			Intent intent = new Intent(RegisterActivity.this, RegisterNextActivity.class);
			intent.putExtra("telephone", telephone);
			startActivity(intent);
			finish();
		}

	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}

	private void showSuccessDialog(String num)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setPositiveButton("确定", new OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				}).setTitle("提示").setMessage("验证码为:" + num).create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("用户注册");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

}
