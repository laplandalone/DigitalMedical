package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyHealthActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_health);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.header_left_small)
	public void toBack(View v)
	{
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

}
