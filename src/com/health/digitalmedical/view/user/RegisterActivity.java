package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegisterActivity extends BaseActivity
{
	@ViewInject(R.id.title)
	private TextView title;
	
	@ViewInject(R.id.user_name)
	private EditText userNameET;
	
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

	@OnClick(R.id.sign_up)
	public void toNext(View v)
	{
		String telephone=userNameET.getText()+"";
		if(telephone!=null || !"".equals(telephone))
		{
			Intent intent = new Intent(RegisterActivity.this,RegisterNextActivity.class);
			intent.putExtra("telephone", telephone);
			startActivity(intent);
			finish();
		}
		
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(RegisterActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("�û�ע��");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

}
