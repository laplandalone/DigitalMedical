package com.health.digitalmedical.view.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.Doctor;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DoctorDetailActivity extends BaseActivity
{
	@ViewInject(R.id.doctor_name)
	private TextView doctorName;
	@ViewInject(R.id.doctot_introduction)
	private TextView doctotIntroduction;
	@ViewInject(R.id.doctor_position)
	private TextView doctorPosition;
	@ViewInject(R.id.out_patient_time)
	private TextView outPatientTime;
	@ViewInject(R.id.out_patient_place)
	private TextView outPatientPlace;
	@ViewInject(R.id.guahao_fee)
	private TextView guahaoFee;
	@ViewInject(R.id.doctor_photo)
	private ImageView photo;
	
	private BitmapUtils bitmapUtils;
	private Doctor  doctor;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_doctor_detail);
		this.doctor=(Doctor) getIntent().getSerializableExtra("doctor");
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(DoctorDetailActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initValue()
	{
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.closeCache();
		// TODO Auto-generated method stub
		this.doctorName.setText(doctor.getName());
		this.doctotIntroduction.setText(doctor.getSkill());
		this.doctorPosition.setText(doctor.getPost());
		this.outPatientTime.setText(doctor.getWorkTime());
		this.guahaoFee.setText(doctor.getRegisterFee());
		this.outPatientPlace.setText(doctor.getWorkAddress());
		String photoUrl=doctor.getPhotoUrl();
		 if(photoUrl.endsWith("jpg") || photoUrl.endsWith("png"))
		 {
			 bitmapUtils.display(photo,photoUrl);
		 }
	}
	
	

}
