package com.health.digitalmedical;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.other.CheckNewVersion;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WelcomeActivity extends BaseActivity
{
	@ViewInject(R.id.hospital)
	private ImageView hospital;

	@ViewInject(R.id.line1)
	private LinearLayout layout1;
	
	@ViewInject(R.id.line2)
	private LinearLayout layout2;

	@ViewInject(R.id.line3)
	private LinearLayout layout3;
	
	@ViewInject(R.id.line4)
	private LinearLayout layout4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_page);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		Intent intent = new Intent(this, CheckNewVersion.class);
		intent.putExtra("flag", "auto");
		startService(intent);
	}
    
	/**
	 * 清华阳光医院：hospital_id:101
	 * @param v
	 */
	@OnClick(R.id.line1)
	public void toMain(View v)
	{
		Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
		HealthUtil.writeHospitalId("101");
		startActivity(intent);
		finish();
	}

	@OnClick(R.id.phone114)
	public void toPhone(View v)
	{
		Intent intent = new Intent();
		// 激活源代码,添加intent对象
		intent.setAction("android.intent.action.CALL");
		intent.setData(Uri.parse("tel:114"));
		// 激活Intent
		startActivity(intent);
	}

	@Override
	protected void initView()
	{
		
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int scrrenHeight = display.getHeight();
		int w = screenWidth - 4;
		int ww = w / 2-120;
		int h = scrrenHeight - dip2px(this, 300);
		int itemHeight = h / 2-120;
		LinearLayout.LayoutParams hint_page_params = new LinearLayout.LayoutParams(ww, itemHeight);
		// hint_page_params.setMargins(10,100,0, 0);//设置边距
		
		LinearLayout.LayoutParams hint_space_params = new LinearLayout.LayoutParams(ww, itemHeight);
		hint_space_params.setMargins(40,0,0, 0);//设置边距
		
		layout1.setLayoutParams(hint_page_params);
		layout2.setLayoutParams(hint_space_params);
		layout3.setLayoutParams(hint_page_params);
		layout4.setLayoutParams(hint_space_params);
	}

	public static int dip2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}
}
