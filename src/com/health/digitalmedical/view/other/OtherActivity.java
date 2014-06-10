package com.health.digitalmedical.view.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.view.user.RegisterActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ¸ü¶à
 *
 */
public class OtherActivity extends BaseActivity
{

	@ViewInject(R.id.soft_update)
	private LinearLayout soft_update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
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

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(OtherActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	@OnClick(R.id.soft_update)
	public void checkVersion(View v)
	{
		Intent intent = new Intent(this, CheckNewVersion.class);
		intent.putExtra("flag", "hand");
		startService(intent);
	}
}
