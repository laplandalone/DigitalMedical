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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ����
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
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("����");
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
}
