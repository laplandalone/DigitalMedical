package com.health.digitalmedical;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

public class SymptomCheckerActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_picture);
		ViewUtils.inject(this);
		addActivity(this);
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
