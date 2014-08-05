package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyHealthActivity extends BaseActivity
{
	
	@ViewInject(R.id.title)
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_health);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.back)
	public void toBack(View v)
	{
		Intent intent = new Intent(MyHealthActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@OnClick(R.id.health_1)
	public void health1(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
	}
	
	@OnClick(R.id.health_2)
	public void health2(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
	}
	
	@OnClick(R.id.health_3)
	public void health3(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
	}
	
	@OnClick(R.id.health_4)
	public void health4(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
	}
	
	@OnClick(R.id.health_5)
	public void health5(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
	}
	
	@OnClick(R.id.health_6)
	public void health6(View v)
	{
		HealthUtil.infoAlert(MyHealthActivity.this, "���ڽ�����...");
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
		title.setText("���ﵵ��");
	}

}
