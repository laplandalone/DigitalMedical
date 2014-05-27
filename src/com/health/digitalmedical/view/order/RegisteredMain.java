package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ÊÖ»ú¹ÒºÅ
 *
 */
public class RegisteredMain extends BaseActivity
{

	@ViewInject(R.id.expert)
	private Button expertBtn;
	@ViewInject(R.id.normal)
	private Button normalBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guahao_mian);
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.expert)
	public void expertOrder(View v)
	{
		Intent intent = new Intent(RegisteredMain.this,FacultyExpertListActivity.class);
		intent.putExtra("orderType", "expert");
		startActivity(intent);
		finish();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(RegisteredMain.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	
	@OnClick(R.id.normal)
	public void normalOrder(View v)
	{
		Intent intent = new Intent(RegisteredMain.this,FacultyExpertListActivity.class);
		intent.putExtra("orderType", "normal");
		startActivity(intent);
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
