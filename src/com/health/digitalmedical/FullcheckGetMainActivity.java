package com.health.digitalmedical;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

/**
 * 取报告单
 *
 */
public class FullcheckGetMainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullcheck_list_item);
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
