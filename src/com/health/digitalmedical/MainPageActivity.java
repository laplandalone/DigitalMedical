package com.health.digitalmedical;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.health.digitalmedical.adapter.ImgViewPager;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.expert.OnLineFacultyListActivity;
import com.health.digitalmedical.view.hospital.HospitalDetailActivity;
import com.health.digitalmedical.view.news.NewsTypeActivity;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.health.digitalmedical.view.other.OtherActivity;
import com.health.digitalmedical.view.user.LoginActivity;
import com.health.digitalmedical.view.user.MyHealthActivity;
import com.health.digitalmedical.view.user.UserMainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lurencun.android.system.DoubleClickExit;

/**
 * 功能： 主页
 * 
 * @author Lapland_Alone 
 */
public class MainPageActivity extends BaseActivity
{
	/* 科室医生 */
	@ViewInject(R.id.faculty_doctor)
	private LinearLayout doctorList;
	/* 手机挂号 */
	@ViewInject(R.id.order_number)
	private LinearLayout order;
	/* 取报告单 */
	@ViewInject(R.id.get_full_check)
	private LinearLayout report;
	/* 专家在线 */
	@ViewInject(R.id.ask_online)
	private LinearLayout doctorOnLine;
	/* 医院导航 */
	@ViewInject(R.id.hos_navigate)
	private LinearLayout hospitalMap;
	/* 健康资讯 */
	@ViewInject(R.id.health_lesson)
	private LinearLayout healthMsg;
	/* 健康百科 */
	@ViewInject(R.id.health_baike)
	private LinearLayout healthTool;
	/* 智能分诊 */
	@ViewInject(R.id.symptom_check)
	private LinearLayout temp;

	@ViewInject(R.id.user_login)
	private ImageView userLoginBtn;

	@ViewInject(R.id.more)
	private ImageView userRegisterBtn;

	@ViewInject(R.id.lineout1)
	private LinearLayout layout1;

	@ViewInject(R.id.lineout2)
	private LinearLayout layout2;

	@ViewInject(R.id.lineout3)
	private LinearLayout layout3;

	@ViewInject(R.id.lineout4)
	private LinearLayout layout4;

	@ViewInject(R.id.lineout5)
	private LinearLayout layout5;

	@ViewInject(R.id.lineout6)
	private LinearLayout layout6;

	@ViewInject(R.id.imgViewPager)
	ImgViewPager myPager; // 图片容器

	@ViewInject(R.id.vb)
	LinearLayout ovalLayout; // 圆点容器

	private List<View> listViews; // 图片组

	private DoubleClickExit doubleClickExit;
	
	int  spacedip480=10;
	int  spacedip720=12;
	int  imgPagerHeigth=0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aabg_fontpage);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int scrrenHeight = display.getHeight();
		Log.e("screenWidth",screenWidth+"");
		Log.e("scrrenHeight",scrrenHeight+"");
		int space=0;
		if(screenWidth==480)
		{
			space=dip2px(this, spacedip480);
			imgPagerHeigth=dip2px(this, 40);//40：title高度+间隔高度
		}else
		{
			space=dip2px(this, spacedip720);
			imgPagerHeigth=dip2px(this, 30);//30:title高度+间隔高度
		}
		int spaceX =space*4;
		Log.e("spaceX",spaceX+"");
		int w=screenWidth-spaceX;
		int ww = w / 3;
		int hh=ww*296/206;// 296/206:图片比例
		Log.e("itemHH",hh+"");
		LinearLayout.LayoutParams hint_page_params = new LinearLayout.LayoutParams(ww, hh);
		//int left, int top, int right, int bottom
		hint_page_params.setMargins(space,0,0, 0);//设置边距
		layout1.setLayoutParams(hint_page_params);
		layout2.setLayoutParams(hint_page_params);
		layout3.setLayoutParams(hint_page_params);
		layout4.setLayoutParams(hint_page_params);
		layout5.setLayoutParams(hint_page_params);
		layout6.setLayoutParams(hint_page_params);

		myPager.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,scrrenHeight-hh*3-imgPagerHeigth));
		InitViewPager();// 初始化图片
		myPager.start(this, listViews, 4000, ovalLayout, R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.pager_select, R.drawable.pager_item);

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

	@OnClick(R.id.main_img1)
	public void toUserOrderView(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, RegisteredMain.class);
		startActivity(intent);
	}

	// @OnClick(R.id.main_img2)
	// public void toDoctorListView(View v)
	// {
	// Intent intent = new Intent(MainPageActivity.this,
	// OfficeDoctorListActivity.class);
	// startActivity(intent);
	// }

	@OnClick(R.id.main_img2)
	public void toExpertOnline(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, OnLineFacultyListActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.LinearLayout_login)
	public void toUserLogin(View v)
	{
		String user = HealthUtil.readUserInfo();
		if (user != null && !"".equals(user))
		{
			Intent intent = new Intent(MainPageActivity.this, UserMainActivity.class);
			startActivity(intent);
		} else
		{
			Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
			startActivityForResult(intent, 0);
		}

	}

	@OnClick(R.id.main_img4)
	public void toHospitalNews(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, NewsTypeActivity.class);
		intent.putExtra("type", "NEWS");
		startActivity(intent);
	}

	@OnClick(R.id.main_img6)
	public void toHealthTools(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, NewsTypeActivity.class);
		intent.putExtra("type", "BAIKE");
		startActivity(intent);
	}

	@OnClick(R.id.main_img3)
	public void toHospital(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, HospitalDetailActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.main_img5)
	public void toMyHealth(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, MyHealthActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.LinearLayout_more)
	public void toOther(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, OtherActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.back)
	public void toHospitalMain(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, WelcomeActivity.class);
		startActivity(intent);
		exit();
	}
	
	/**
	 * 初始化图片
	 */
	private void InitViewPager()
	{
		listViews = new ArrayList<View>();
		int[] imageResId = new int[]
		{ R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
		for (int i = 0; i < imageResId.length; i++)
		{
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			listViews.add(imageView);
		}
	}

	@Override
	protected void initView()
	{
		doubleClickExit = new DoubleClickExit(this);
		// TODO Auto-generated method stub
		String user = HealthUtil.readUserInfo();
		if (user != null && !"".equals(user))
		{
			userLoginBtn.setBackgroundResource(R.drawable.user_login_unselect);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		switch (resultCode)
		{
		case RESULT_OK:
			String user = HealthUtil.readUserInfo();
			if (user != null && !"".equals(user))
			{
				userLoginBtn.setBackgroundResource(R.drawable.user_login_unselect);
			}
			break;

		default:
			break;
		}
	}

	
	@Override
	public void onBackPressed() 
	{
		doubleClickExit.onKeyClick(KeyEvent.KEYCODE_BACK);
	}
	
	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub 退出
		if(!isNetworkAvailable(this))
		{
			HealthUtil.infoAlert(MainPageActivity.this, "网络不可用，请检查！");
			return;
		}
	}
}
