package com.health.digitalmedical.view.order;

import android.content.Intent;
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
 * 
 * นาบลิคิผ
 *
 */
public class ConfirmOrderActivity extends BaseActivity
{

	@ViewInject(R.id.faculty_name)  
	private TextView falcultyNameT;
	
	@ViewInject(R.id.doctor_name) 
	private TextView doctorNameT;
	
	@ViewInject(R.id.date)     
	private TextView dateT;
	
	@ViewInject(R.id.date_time)   
	private TextView dateTimeT;
	
	@ViewInject(R.id.register_num)
	private TextView registerNumT;
	
	@ViewInject(R.id.user_name)   
	private TextView userNameT;
	
	@ViewInject(R.id.sex)         
	private TextView sexT;
	
	@ViewInject(R.id.confirm_num) 
	private TextView comfirmNumT;
	
	@ViewInject(R.id.confirm_price)
	private TextView confirmPriceT;
	
	@ViewInject(R.id.sex)
	private TextView sexTV;
	
	@ViewInject(R.id.confirm_num)
	private TextView confirmNumTV;
	
	@ViewInject(R.id.userOrderNum)
	private LinearLayout linearLayout;
	
	@ViewInject(R.id.teamDoctorId)
	private LinearLayout linearLayout1;
	
	@ViewInject(R.id.feelayout)
	private LinearLayout linearLayout2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_register_proof);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(ConfirmOrderActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		String registerNum=getIntent().getStringExtra("userOrderNum"  );
		String fee=getIntent().getStringExtra("fee"           );
		String doctorName=getIntent().getStringExtra("doctorName"    );
		if("0".equals(registerNum) || "".equals(registerNum))
		{
			linearLayout.setVisibility(View.GONE);
		}
		if("0".equals(fee) || "".equals(fee))
		{
			linearLayout1.setVisibility(View.GONE);
		}
		if("0".equals(doctorName) || "".equals(doctorName))
		{
			linearLayout2.setVisibility(View.GONE);
		}
		doctorNameT.setText(doctorName); 
		dateT.setText(getIntent().getStringExtra("registerTime"  )); 
		confirmPriceT.setText(fee); 
		registerNumT.setText(registerNum); 
		falcultyNameT.setText(	getIntent().getStringExtra("teamName"      )); 
		userNameT.setText(getIntent().getStringExtra("userName"      )); 
		dateTimeT.setText(getIntent().getStringExtra("userNo"        )); 
		sexTV.setText(getIntent().getStringExtra("sex" )); 
		confirmNumTV.setText(getIntent().getStringExtra("userTelephone" ));

	}

	@Override
	protected void initValue()
	{
		
	}
}
