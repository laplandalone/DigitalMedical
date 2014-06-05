package com.health.digitalmedical.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.expert.QuestionActivity;
import com.health.digitalmedical.view.order.UserOrderActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserMainActivity extends BaseActivity
{
	@ViewInject(R.id.item_layout1)
	private LinearLayout itemLayout1;
	
	@ViewInject(R.id.item_layout2)
	private LinearLayout itemLayout2;
	
	@ViewInject(R.id.login_name)
	private TextView loginNameTV;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub 2131493633
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_main);
		ViewUtils.inject(this);
		 initView();
		 initValue();
	}
	
	
	
	@OnClick(R.id.health_data_lay)
	public void toMyHealth(View v)
	{
		Intent intent = new Intent(UserMainActivity.this,MyHealthActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.item_layout1)
	public void toMyOrder(View v)
	{
		Intent intent = new Intent(UserMainActivity.this,UserOrderActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.item_layout2)
	public void toMyQuestion(View v)
	{
		Intent intent = new Intent(UserMainActivity.this,QuestionActivity.class);
		intent.putExtra("questionType", "user");
		startActivity(intent);
	}
	
	@OnClick(R.id.user_info_detail)
	public void updateUser(View v)
	{
		Intent intent = new Intent(UserMainActivity.this,UserUpdateActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(UserMainActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	
	@OnClick(R.id.outLogin)
	public void loginOut(View v)
	{
		HealthUtil.writeUserInfo("");
		Intent intent = new Intent(UserMainActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		User user =HealthUtil.getUserInfo();
		loginNameTV.setText(user.getUserName());
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

}
