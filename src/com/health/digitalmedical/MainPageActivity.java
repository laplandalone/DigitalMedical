package com.health.digitalmedical;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.health.digitalmedical.adapter.ImgViewPager;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.expert.OnLineFacultyListActivity;
import com.health.digitalmedical.view.hospital.HospitalDetailActivity;
import com.health.digitalmedical.view.news.NewsActivity;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.health.digitalmedical.view.other.CheckNewVersion;
import com.health.digitalmedical.view.other.OtherActivity;
import com.health.digitalmedical.view.user.LoginActivity;
import com.health.digitalmedical.view.user.MyHealthActivity;
import com.health.digitalmedical.view.user.UserMainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lurencun.android.system.DoubleClickExit;

/**
 * ���ܣ� ��ҳ
 * 
 * @author Lapland_Alone 
 */
public class MainPageActivity extends BaseActivity
{
	/* ����ҽ�� */
	@ViewInject(R.id.faculty_doctor)
	private LinearLayout doctorList;
	/* �ֻ��Һ� */
	@ViewInject(R.id.order_number)
	private LinearLayout order;
	/* ȡ���浥 */
	@ViewInject(R.id.get_full_check)
	private LinearLayout report;
	/* ר������ */
	@ViewInject(R.id.ask_online)
	private LinearLayout doctorOnLine;
	/* ҽԺ���� */
	@ViewInject(R.id.hos_navigate)
	private LinearLayout hospitalMap;
	/* ������Ѷ */
	@ViewInject(R.id.health_lesson)
	private LinearLayout healthMsg;
	/* �����ٿ� */
	@ViewInject(R.id.health_baike)
	private LinearLayout healthTool;
	/* ���ܷ��� */
	@ViewInject(R.id.symptom_check)
	private LinearLayout temp;

	@ViewInject(R.id.user_login)
	private Button userLoginBtn;

	@ViewInject(R.id.more)
	private Button userRegisterBtn;

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
	ImgViewPager myPager; // ͼƬ����

	@ViewInject(R.id.vb)
	LinearLayout ovalLayout; // Բ������

	private List<View> listViews; // ͼƬ��

	private DoubleClickExit doubleClickExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aabg_fontpage);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int scrrenHeight = display.getHeight();
		int w = screenWidth - 4;
		int ww = w / 3;
		int h = scrrenHeight - dip2px(this, 300);
		int hh = h / 2;
		LinearLayout.LayoutParams hint_page_params = new LinearLayout.LayoutParams(ww, hh);
		// hint_page_params.setMargins(10,0,0, 0);//���ñ߾�
		layout1.setLayoutParams(hint_page_params);
		layout2.setLayoutParams(hint_page_params);
		layout3.setLayoutParams(hint_page_params);
		layout4.setLayoutParams(hint_page_params);
		layout5.setLayoutParams(hint_page_params);
		layout6.setLayoutParams(hint_page_params);

		InitViewPager();// ��ʼ��ͼƬ
		myPager.start(this, listViews, 4000, ovalLayout, R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);

		Intent intent = new Intent(this, CheckNewVersion.class);
		intent.putExtra("flag", "auto");
		startService(intent);

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

	@OnClick(R.id.user_login)
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
		Intent intent = new Intent(MainPageActivity.this, NewsActivity.class);
		intent.putExtra("type", "news");
		startActivity(intent);
	}

	@OnClick(R.id.main_img6)
	public void toHealthTools(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, NewsActivity.class);
		intent.putExtra("type", "baike");
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

	@OnClick(R.id.more)
	public void toOther(View v)
	{
		Intent intent = new Intent(MainPageActivity.this, OtherActivity.class);
		startActivity(intent);
	}

	/**
	 * ��ʼ��ͼƬ
	 */
	private void InitViewPager()
	{
		listViews = new ArrayList<View>();
		int[] imageResId = new int[]
		{ R.drawable.a, R.drawable.b, R.drawable.c};
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
		// TODO Auto-generated method stub

	}
}
