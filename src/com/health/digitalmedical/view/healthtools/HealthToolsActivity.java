package com.health.digitalmedical.view.healthtools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HealthToolsActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_article_list);
		ViewUtils.inject(this);
		addActivity(this);
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(HealthToolsActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
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

}
