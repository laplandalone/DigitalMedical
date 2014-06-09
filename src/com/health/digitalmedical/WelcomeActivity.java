package com.health.digitalmedical;

import com.health.digitalmedical.tools.HealthUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class WelcomeActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		addActivity(this);
		initView();
		initValue();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		HealthUtil.writeUserInfo("");
	}

}
