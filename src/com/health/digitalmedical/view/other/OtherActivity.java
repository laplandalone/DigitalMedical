package com.health.digitalmedical.view.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 更多
 * 
 */
public class OtherActivity extends BaseActivity
{
	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.soft_update)
	private LinearLayout soft_update;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("更多");
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

	@OnClick(R.id.share)
	public void share(View v)
	{
		Uri uri = Uri.parse("smsto://");
		Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
		intent.putExtra("sms_body", this.getResources().getText(R.string.other_temp28));
		startActivity(intent);
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(OtherActivity.this, MainPageActivity.class);
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
	
	@OnClick(R.id.about_law)
	public void aboutLaw(View v)
	{
		HealthUtil.infoAlert(OtherActivity.this, "正在建设中...");
	}
	

	@OnClick(R.id.qanda)
	public void qanda(View v)
	{
		HealthUtil.infoAlert(OtherActivity.this, "正在建设中...");
	}
}
